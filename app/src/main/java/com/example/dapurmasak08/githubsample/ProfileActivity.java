package com.example.dapurmasak08.githubsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.rejasupotaro.octodroid.models.User;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by dapurmasak08 on 1/22/15.
 */
public class ProfileActivity extends ActionBarActivity {
    private Subscription subscription = Subscriptions.empty();
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User intentUser = getIntent().getStringExtra("user");
        if (intentUser != null){
            user = intentUser;

        }
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                submit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void submit(String query) {
        // launch SearchResultActivity with query
        Intent intent = new Intent(this, SearchResultActivity.class );
        intent.putExtra("query", query);
        startActivity(intent);
    }
}
