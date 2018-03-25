package christson.hackernews.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.net.UnknownHostException;
import java.util.ArrayList;

import christson.hackernews.R;
import christson.hackernews.adapters.RecyclerViewStoryAdapter;
import christson.hackernews.api.ApiClient;
import christson.hackernews.app.BaseActivity;
import christson.hackernews.entities.Story;
import christson.hackernews.interfaces.OnStoryAdapterClickListener;
import christson.hackernews.util.AppUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity
{
    private Disposable disposable;

    private SwipeRefreshLayout swipeRefreshLayout;
    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private ArrayList<Integer> listOfId;
    private ArrayList<Story> listOfStory;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listOfId = new ArrayList<Integer>();
        listOfStory = new ArrayList<Story>();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new MyRefreshListener());

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView) ;
        nestedScrollView.setOnScrollChangeListener(new ScrollViewChangedListener());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerViewStoryAdapter(this, listOfStory, new AdapterClickListener()));
        recyclerView.setNestedScrollingEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // get data from server when app open
        loadNewsFeed();
    }

    @Override
    protected void onDestroy()
    {
        dispose();
        super.onDestroy();
    }

    /**
     * Retrieve newest stories from server
     */
    private void loadNewsFeed()
    {
        ApiClient.create().getListOfNewStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Integer>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        swipeRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onSuccess(ArrayList<Integer> ids)
                    {
                        swipeRefreshLayout.setRefreshing(false);
                        // clear list for new entries
                        listOfId.clear();
                        listOfId.addAll(ids);
                        // clear UI
                        listOfStory.clear();
                        recyclerView.getAdapter().notifyDataSetChanged();

                        loadStories(0);
                    }

                    @Override
                    public void onError(Throwable ex)
                    {
                        swipeRefreshLayout.setRefreshing(false);
                        // display error in dialog
                        if (ex instanceof UnknownHostException)
                            showAlert(getString(R.string.error_no_internet), getString(R.string.error_no_internet_desc));
                    }
                });

    }

    /**
     * Retrieve a story from server based on id of the story.
     * Execute at most 8 times when this method is called.
     * @param startIndex position of id in {@link #listOfId}
     */
    private void loadStories(int startIndex)
    {
        // still in the process of getting data from server
        if (disposable != null)
            return;

        int count = AppUtil.getRangeCount(startIndex, listOfId.size(), 8);
        disposable = Observable.range(startIndex, count)
                .flatMap(new Function<Integer, ObservableSource<Story>>()
                {
                    @Override
                    public ObservableSource<Story> apply(Integer index) throws Exception
                    {
                        return ApiClient.create().getStory(listOfId.get(index));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Story>()
                {
                    @Override
                    protected void onStart()
                    {
                        super.onStart();
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(Story story)
                    {
                        listOfStory.add(story);
                        recyclerView.getAdapter().notifyItemInserted(listOfStory.size() - 1);
                    }

                    @Override
                    public void onComplete()
                    {
                        progressBar.setVisibility(View.GONE);
                        disposable.dispose();
                        disposable = null;
                    }

                    @Override
                    public void onError(Throwable ex)
                    {
                        // display error in dialog
                        if (ex instanceof UnknownHostException)
                            showAlert(getString(R.string.error_no_internet), getString(R.string.error_no_internet_desc));

                        progressBar.setVisibility(View.GONE);
                        disposable.dispose();
                        disposable = null;
                    }
                });
    }

    /**
     * Terminate any running {@link #disposable}
     */
    private void dispose()
    {
        if (disposable != null)
        {
            disposable.dispose();
            disposable = null;
        }
    }

    /**
     * Callback to be invoked when an item in {@link #recyclerView} is clicked
     */
    private class AdapterClickListener implements OnStoryAdapterClickListener
    {
        @Override
        public void onContentClick(int position)
        {
            String url = listOfStory.get(position).getUrl();

            if (url == null || url.isEmpty())
                return;

            // open browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }

        @Override
        public void onCommentClick(int position)
        {
            // navigate to comment screen
            Intent intent = new Intent(MainActivity.this, CommentActivity.class);
            intent.putExtra("title", listOfStory.get(position).getTitle());
            intent.putExtra("list", listOfStory.get(position).getListCommentIds());
            startActivity(intent);
        }
    }

    /**
     * Inner class listener to be invoked when user swipe down for refresh
     */
    private class MyRefreshListener implements SwipeRefreshLayout.OnRefreshListener
    {
        @Override
        public void onRefresh()
        {
            // cancel any ongoing process
            dispose();
            // load the data from server again
            loadNewsFeed();
        }
    }

    /**
     * Pull more data {#link loadStories} when scroll view is scrolled to the bottom
     */
    private class ScrollViewChangedListener implements NestedScrollView.OnScrollChangeListener
    {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
        {
            if (listOfId.isEmpty())
                return;

            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition();

            // pull data if reach bottom of the recyclerview
            if (pastVisibleItems + visibleItemCount >= totalItemCount && totalItemCount != listOfId.size())
                loadStories(totalItemCount);
        }
    }
}
