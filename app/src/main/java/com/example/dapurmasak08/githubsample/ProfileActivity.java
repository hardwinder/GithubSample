package com.example.dapurmasak08.githubsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dapurmasak08.githubsample.data.GsonProvider;
import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.User;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

/**
 * Created by dapurmasak08 on 1/22/15.
 */
public class ProfileActivity extends ActionBarActivity {
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

    public void updateUI() {
        welcomeMessage.setText(R.string.wait_for_response);
        GitHub.client().user(user.getLogin())
                .subscribe(new Action1<Response<User>>() {
                    @Override
                    public void call(Response<User> r) {
                        user = r.entity();

                        userName.setText(user.getName());
                        loginName.setText(user.getLogin());

                        DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();
                        DateTime dt = parser.parseDateTime(user.getCreatedAt());
                        DateTimeFormatter formatter = DateTimeFormat.mediumDate();
                        memberSinceDate.setText(formatter.print(dt));

                        followerCount.setText(String.valueOf(user.getFollowers()));
                        starred.setText(String.valueOf(user.getPublicRepos()));
                        following.setText(String.valueOf(user.getFollowing()));
                        Picasso.with(getBaseContext())
                                .load(user.getAvatarUrl())
                                .into(userAvatar);
                        welcomeMessage.setVisibility(View.GONE);
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
        // launch SearchResultActivity with query
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }

}
