package com.rajesh.goaltracker;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by RajeshRaman on 14-Mar-18.
 */

public class GoalTracker extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
