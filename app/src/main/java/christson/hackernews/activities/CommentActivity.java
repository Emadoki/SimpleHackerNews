package christson.hackernews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.UnknownHostException;
import java.util.ArrayList;

import christson.hackernews.R;
import christson.hackernews.adapters.RecyclerViewCommentAdapter;
import christson.hackernews.api.ApiClient;
import christson.hackernews.app.BaseActivity;
import christson.hackernews.entities.Comment;
import christson.hackernews.util.AppUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CommentActivity extends BaseActivity
{
    private Disposable disposable;

    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView txtTitle;

    private ArrayList<Integer> listOfId;
    private ArrayList<Comment> listOfComment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        listOfId = new ArrayList<Integer>();
        listOfComment = new ArrayList<Comment>();

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        nestedScrollView.setOnScrollChangeListener(new ScrollViewChangedListener());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerViewCommentAdapter(this, listOfComment));
        // disable recyclerview scroll since its already wrapped in nestedscrollview
        recyclerView.setNestedScrollingEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        txtTitle = (TextView) findViewById(R.id.txtTitle);

        initialize();
    }

    @Override
    protected void onDestroy()
    {
        dispose();
        super.onDestroy();
    }

    private void initialize()
    {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        ArrayList<Integer> list = intent.getIntegerArrayListExtra("list");

        if (title != null)
            txtTitle.setText(title);
        if (list != null)
            listOfId.addAll(list);

        // get the first list of comments
        loadComments(0);
    }

    private void loadComments(int startIndex)
    {
        // still in the process of getting data from server
        if (disposable != null)
            return;

        int count = AppUtil.getRangeCount(startIndex, 8, listOfId.size());
        disposable = Observable.range(startIndex, count)
                .flatMap(new Function<Integer, ObservableSource<Comment>>()
                {
                    @Override
                    public ObservableSource<Comment> apply(Integer position) throws Exception
                    {
                        return ApiClient.create().getComment(listOfId.get(position));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Comment>()
                {
                    @Override
                    protected void onStart()
                    {
                        super.onStart();
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(Comment comment)
                    {
                        listOfComment.add(comment);
                        recyclerView.getAdapter().notifyItemInserted(listOfComment.size() - 1);
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
     * Pull more data {#link loadComments} when scroll view is scrolled to the bottom
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

            if (pastVisibleItems + visibleItemCount >= totalItemCount && totalItemCount != listOfId.size())
                loadComments(totalItemCount);
        }
    }

}
