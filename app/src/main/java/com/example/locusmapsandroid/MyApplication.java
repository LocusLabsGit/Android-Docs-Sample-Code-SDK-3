package com.example.locusmapsandroid;

import android.app.Application;

import com.locuslabs.sdk.llpublic.LLConfiguration;

import static com.example.locusmapsandroid.MyConstants.ACCOUNT_ID_LOCUSLABS_DEMO;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LLConfiguration.Companion.getSingleton().setApplicationContext(getApplicationContext());
        LLConfiguration.Companion.getSingleton().setAccountID(ACCOUNT_ID_LOCUSLABS_DEMO);
    }
}
