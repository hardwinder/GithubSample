package com.example.dapurmasak08.githubsample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dapurmasak08.githubsample.data.SearchResultAdapter;

/**
 * Created by dapurmasak08 on 1/23/15.
 */
public class SearchResultActivity extends Activity {
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new SearchResultAdapter();
        recyclerView.setAdapter(adapter);

        String query = getIntent().getStringExtra("query");
        submit(query);
    }

    private void submit(String query) {
        adapter.update(query);
    }

}
