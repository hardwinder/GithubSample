package com.example.dapurmasak08.githubsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @OnClick(R.id.login_button)
    void onLoginButtonClick() {
        Intent intent = new Intent(this, ProfileActivity.class);
//        User user = null;
//        intent.putExtra("user", user);
        startActivity(intent);
    }
}
