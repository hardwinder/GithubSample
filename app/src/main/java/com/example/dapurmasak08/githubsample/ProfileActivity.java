package com.example.dapurmasak08.githubsample;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.http.params.Order;
import com.rejasupotaro.octodroid.http.params.Sort;
import com.rejasupotaro.octodroid.models.Repository;
import com.rejasupotaro.octodroid.models.SearchResult;
import com.rejasupotaro.octodroid.models.User;

import java.util.List;

import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by dapurmasak08 on 1/22/15.
 */
public class ProfileActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
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
        GitHub.client().userAgent("GithubSample"); // request should have a User-Agent header

        GitHub.client().searchUsers(query, Sort.FOLLOWERS, Order.DESC)
                .subscribe(new Action1<Response<SearchResult<User>>>() {
                    @Override
                    public void call(Response<SearchResult<User>> r) {
                        List<User> users = r.entity().getItems();
                        for (User user : users) {
                            Log.e("DEBUG", user.getLogin());
                        }
                    }
                });
    }
}
