package com.example.locusmapsandroid;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.locuslabs.sdk.llpublic.LLDependencyInjector;
import com.locuslabs.sdk.llpublic.LLLocusMapsFragment;
import com.locuslabs.sdk.llpublic.LLOnFailureListener;
import com.locuslabs.sdk.llpublic.LLOnPOIPhoneClickedListener;
import com.locuslabs.sdk.llpublic.LLOnPOIURLClickedListener;
import com.locuslabs.sdk.llpublic.LLOnProgressListener;

import java.util.Calendar;

import static com.example.locusmapsandroid.MyConstants.VENUE_LAX_ASSET_VERSION;
import static com.example.locusmapsandroid.MyConstants.VENUE_LAX_ID;
import static com.example.locusmapsandroid.MyConstants.VENUE_LAX_VENUE_FILES;
import static com.example.locusmapsandroid.MyConstants.logTag;
import static com.locuslabs.sdk.llprivate.ConstantsKt.FRACTION_TO_PERCENT_CONVERSION_RATIO;
import static com.locuslabs.sdk.llprivate.ConstantsKt.PROGRESS_BAR_FRACTION_FINISH;

public class MainActivity extends AppCompatActivity {

    private LLLocusMapsFragment llLocusMapsFragment;
    private View initializationAnimationViewBackground;
    private ImageView initializationAnimationView;
    private AnimationDrawable initializationAnimationDrawable;
    private ProgressBar loadingProgressBar;
    private long loadingStartTimeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewIDs();

        initLocusMaps();

        initInitializationProgressIndicator();

        showInitializationProgressIndicator();
    }

    @Override
    protected void onResume() {
        super.onResume();
        llLocusMapsFragment.loadVenue(VENUE_LAX_ID, VENUE_LAX_ASSET_VERSION, VENUE_LAX_VENUE_FILES);
    }

    private void initViewIDs() {
        initializationAnimationViewBackground =
                findViewById(R.id.initializationAnimationViewBackground);

        initializationAnimationView = findViewById(R.id.initializationAnimationView);

        loadingProgressBar = findViewById(R.id.loadingProgressBar);
    }

    private void initLocusMaps() {
        llLocusMapsFragment = (LLLocusMapsFragment) getSupportFragmentManager().findFragmentById(R.id.llLocusMapsFragment);

        LLDependencyInjector.Companion.getSingleton().setOnInitializationProgressListener(
                new LLOnProgressListener() {
                    @Override
                    public void onProgressUpdate(double fractionComplete, String progressDescription) {
                        if (PROGRESS_BAR_FRACTION_FINISH == fractionComplete) {
                            hideInitializationProgressIndicator();
                        }
                    }
                }
        );

        LLDependencyInjector.Companion.getSingleton().setOnLevelLoadingProgressListener(
                new LLOnProgressListener() {
                    @Override
                    public void onProgressUpdate(double fractionComplete, String progressDescription) {
                        updateLevelLoadingProgressIndicator(fractionComplete, progressDescription);
                    }
                }
        );

        LLDependencyInjector.Companion.getSingleton().setOnPOIURLClickedListener(
                new LLOnPOIURLClickedListener() {
                    @Override
                    public void onPOIURLClicked(String url) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                }
        );

        LLDependencyInjector.Companion.getSingleton().setOnPOIPhoneClickedListener(
                new LLOnPOIPhoneClickedListener() {
                    @Override
                    public void onPOIPhoneClicked(String phone) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(phone));
                        startActivity(intent);
                    }
                }
        );

        LLDependencyInjector.Companion.getSingleton().setOnFailureListener(new LLOnFailureListener() {
            @Override
            public void onFailure(Throwable throwable) {
                Log.e(logTag, "stack trace: " + Log.getStackTraceString(throwable));
                Log.e(logTag, "stack trace cause: " + Log.getStackTraceString(throwable.getCause()));
            }
        });
    }

    private void initInitializationProgressIndicator() {
        initializationAnimationView.setBackgroundResource(R.drawable.ll_navigation_loading_animation);
        initializationAnimationDrawable = (AnimationDrawable) initializationAnimationView.getBackground();
        initializationAnimationDrawable.start();
        initializationAnimationDrawable.setVisible(false, false);
    }

    private void showInitializationProgressIndicator() {
        initializationAnimationViewBackground.setVisibility(View.VISIBLE);
        initializationAnimationView.setVisibility(View.VISIBLE);
        initializationAnimationDrawable.setVisible(true, true);
    }

    private void hideInitializationProgressIndicator() {
        initializationAnimationViewBackground.setVisibility(View.GONE);
        initializationAnimationView.setVisibility(View.GONE);
        initializationAnimationDrawable.setVisible(false, false);
    }

    private void updateLevelLoadingProgressIndicator(double fractionComplete, String progressDescription) {
        if (0.0 == fractionComplete) {
            loadingStartTimeInMillis = Calendar.getInstance().getTimeInMillis();
        }

        int percentComplete = (int) (fractionComplete * FRACTION_TO_PERCENT_CONVERSION_RATIO);
        double timeElapsedInMillis = Calendar.getInstance().getTimeInMillis() - loadingStartTimeInMillis;

        Log.d(logTag, "LocusMaps Android SDK Level Loading Progress: " + percentComplete + "\t" + timeElapsedInMillis + "\t" + progressDescription);

        loadingProgressBar.setProgress(percentComplete);
        loadingProgressBar.setVisibility(View.VISIBLE);

        if (PROGRESS_BAR_FRACTION_FINISH == fractionComplete) {
            (new Handler(Looper.getMainLooper(), null)).postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingProgressBar.setVisibility(View.GONE);
                }
            }, 50);
        }
    }

    @Override
    public void onBackPressed() {
        if (llLocusMapsFragment.hasBackStackItems()) {
            llLocusMapsFragment.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}