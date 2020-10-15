package com.example.locusmapsandroid;

import android.app.Application;
import android.util.Log;

import com.locuslabs.sdk.llpublic.LLConfiguration;
import com.locuslabs.sdk.llpublic.LLMapPackFinder;
import com.locuslabs.sdk.llpublic.LLOnUnpackCallback;

import java.util.ArrayList;
import java.util.List;

import static com.example.locusmapsandroid.MyConstants.ACCOUNT_ID_LOCUSLABS_DEMO;
import static com.example.locusmapsandroid.MyConstants.logTag;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LLConfiguration.Companion.getSingleton().setApplicationContext(getApplicationContext());
        LLConfiguration.Companion.getSingleton().setAccountID(ACCOUNT_ID_LOCUSLABS_DEMO);

        List<String> accountIdsForMapPacks = new ArrayList<String>();
        accountIdsForMapPacks.add(ACCOUNT_ID_LOCUSLABS_DEMO);
        for (int i = 0; i < accountIdsForMapPacks.size(); i++) {
            String accountIdsForMapPack = accountIdsForMapPacks.get(i);

            LLOnUnpackCallback callback = new LLOnUnpackCallback() {
                @Override
                public void onUnpack(boolean b, Throwable throwable) {
                    if (throwable != null) {
                        Log.e(logTag, "MapPack installation failed because: " +
                                throwable.getMessage());
                    }
                }
            };

            LLMapPackFinder.Companion.installMapPack(accountIdsForMapPack, null, callback);
        }
    }
}
