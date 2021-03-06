package com.example.dapurmasak08.githubsample;

import android.content.Context;
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
import android.widget.Toast;

import com.example.dapurmasak08.githubsample.data.GsonProvider;
import com.example.dapurmasak08.githubsample.utils.DateUtils;
import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.User;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

/**
 * Created by dapurmasak08 on 1/22/15.
 */
public class ProfileActivity extends ActionBarActivity {
    private static final String EXTRA_USER = "extra_user";

    private Subscription subscription = Subscriptions.empty();
    private User user = new User();
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
    @InjectView(R.id.welcomeMessage)
    TextView welcomeMessage;
    @InjectView(R.id.followerCountLabel)
    TextView followerCountLabel;
    @InjectView(R.id.starredLabel)
    TextView starredLabel;
    @InjectView(R.id.followingLabel)
    TextView followingLabel;

    public static void launch(Context context) {
        Intent profile = new Intent(context, ProfileActivity.class);
        context.startActivity(profile);
    }

    public static void launch(Context context, User user) {
        String serializedUser = GsonProvider.get().toJson(user);

        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER, serializedUser);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);

        String serializedUser = getIntent().getStringExtra(EXTRA_USER);
        if (!TextUtils.isEmpty(serializedUser)) {
            bind(serializedUser);
        }
    }

    public void updateUI() {
        welcomeMessage.setText(R.string.wait_for_response);

        subscription.unsubscribe();
        subscription = AppObservable.bindActivity(this, GitHub.client().user(user.getLogin()))
                .subscribe(new Action1<Response<User>>() {
                    @Override
                    public void call(Response<User> r) {
                        user = r.entity();

                        userName.setText(user.getName());
                        loginName.setText(user.getLogin());

                        memberSinceDate.setText(DateUtils.format(user.getCreatedAt()));

                        followerCount.setText(String.valueOf(user.getFollowers()));
                        starred.setText(String.valueOf(user.getPublicRepos()));
                        following.setText(String.valueOf(user.getFollowing()));
                        Picasso.with(getBaseContext())
                                .load(user.getAvatarUrl())
                                .into(userAvatar);
                        welcomeMessage.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(ProfileActivity.this, R.string.error_message, Toast.LENGTH_SHORT).show();
                        Log.e("DEBUG", throwable.getMessage());
                    }
                });
    }

    private void bind(String serializedUser) {
        user = GsonProvider.get().fromJson(serializedUser, User.class);
        updateUI();
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
        return super.onOptionsItemSelected(item);
    }

    private void submit(String query) {
        SearchResultActivity.launch(this, query);
    }
}
