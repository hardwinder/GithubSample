package com.example.dapurmasak08.githubsample;

import android.app.Application;

import com.rejasupotaro.octodroid.GitHub;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // initialize something here
        GitHub.client().userAgent("GithubSample"); // request should have a User-Agent header
    }
}
