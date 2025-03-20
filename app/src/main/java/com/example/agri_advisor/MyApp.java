package com.example.agri_advisor;

import android.os.Bundle;
import android.app.Application;
import com.google.firebase.FirebaseApp;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}