package com.example.dapurmasak08.githubsample.data;

import com.google.gson.Gson;

public class GsonProvider {
    private static Gson gson = new Gson();

    public static Gson get() {
        return gson;
    }
}
