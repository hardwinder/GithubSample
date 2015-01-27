package com.example.dapurmasak08.githubsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dapurmasak08.githubsample.data.GsonProvider;
import com.rejasupotaro.octodroid.models.User;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by dapurmasak08 on 1/22/15.
 */
public class ProfileActivity extends ActionBarActivity {
    private Subscription subscription = Subscriptions.empty();
    private User user = new User();
    private View itemView;
    @InjectView(R.id.userName)
    TextView userName;
    @InjectView(R.id.loginName)
    TextView loginName;
    @InjectView(R.id.userAvatar)
    ImageView userAvatar;
    @InjectView(R.id.memberSinceDate)
    TextView memberSinceDate;
    @InjectView(R.id.followerCount)
    TextView followerCount;
    @InjectView(R.id.starred)
    TextView starred;
    @InjectView(R.id.following)
    TextView following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String serializedUser = getIntent().getStringExtra("user");
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        if (!TextUtils.isEmpty(serializedUser)) {
           bind(serializedUser);
        }

    }

    private void bind(String serializedUser){
        Log.e("DEBUG", "serializedUser: " + serializedUser);
        // deserialize

        user = GsonProvider.get().fromJson(serializedUser, User.class);
        Log.e("DEBUG", "user: " + user.getLogin());
        userName.setText(user.getName());
        loginName.setText(user.getLogin());
        Picasso.with(this.getBaseContext())
                .load(user.getAvatarUrl())
                .into(userAvatar);
        memberSinceDate.setText(user.getCreatedAt());
        followerCount.setText(String.valueOf(user.getFollowers()));
        starred.setText(String.valueOf(user.getPublicRepos()));
        following.setText(String.valueOf(user.getFollowing()));
        if (user.getName() != null){
            Log.d("DEBUG user name", user.getName());
        }
        if (user.getCreatedAt() != null) {
            Log.d("DEBUG user created at", user.getCreatedAt());
        }
        if (user.getAvatarUrl() != null) {
            Log.d("DEBUG user created at", user.getAvatarUrl());
        }

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
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }
}
