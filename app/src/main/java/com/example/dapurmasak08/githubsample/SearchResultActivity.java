package com.example.dapurmasak08.githubsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dapurmasak08.githubsample.data.SearchResultAdapter;

/**
 * Created by dapurmasak08 on 1/23/15.
 */
public class SearchResultActivity extends Activity {
    private static final String EXTRA_QUERY = "extra_query";

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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new SearchResultAdapter(this);
        recyclerView.setAdapter(adapter);

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
