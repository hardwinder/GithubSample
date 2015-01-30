package com.example.dapurmasak08.githubsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.dapurmasak08.githubsample.data.SearchResultAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dapurmasak08 on 1/23/15.
 */
public class SearchResultActivity extends Activity {
    private static final String EXTRA_QUERY = "extra_query";

    @InjectView(R.id.my_recycler_view)
    RecyclerView searchResultListView;

    private SearchResultAdapter adapter;

    public static void launch(Context context, String query) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(EXTRA_QUERY, query);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.inject(this);

        adapter = new SearchResultAdapter(searchResultListView);

        String query = getIntent().getStringExtra(EXTRA_QUERY);
        submit(query);
    }

    @Override
    public void onDestroy() {
        adapter.destroy();
        super.onDestroy();
    }

    private void submit(String query) {
        adapter.update(query);
    }
}
