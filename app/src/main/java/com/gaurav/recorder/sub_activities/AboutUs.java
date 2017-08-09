package com.gaurav.recorder.sub_activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.gaurav.recorder.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by gaurav on 31/07/17.
 */

public class AboutUs extends AppCompatActivity {

    ImageView facebook;
    ImageView google;
    ImageView twitter;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        facebook = (ImageView) findViewById(R.id.developer_facebook);
        google = (ImageView) findViewById(R.id.developer_google);
        twitter = (ImageView) findViewById(R.id.developer_twitter);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("About us");


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri facebook_uri = Uri.parse("https://www.facebook.com/grv.takte");
                Intent intent = new Intent(Intent.ACTION_VIEW, facebook_uri);
                startActivity(intent);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri google_uri = Uri.parse("https://plus.google.com/116662379776708037132");
                Intent intent = new Intent(Intent.ACTION_VIEW, google_uri);
                startActivity(intent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri twitter_uri = Uri.parse("https://twitter.com/grvTakte");
                Intent intent = new Intent(Intent.ACTION_VIEW, twitter_uri);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId(getString(R.string.admob_intertial_ad));
        AdRequest request = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(request);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    private void showInterstitial(){
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
