package com.shivamdev.admobdemo.common.ads;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.shivamdev.admobdemo.R;
import com.shivamdev.admobdemo.common.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shivam on 25/10/16.
 */

public class AdFragment1 extends Fragment {

    private static final String TAG = AdFragment1.class.getSimpleName();

    @BindView(R.id.rv_ads_list)
    RecyclerView rvAds;

    @BindView(R.id.ad_banner_1)
    AdView adViewBanner;

    @BindView(R.id.b_start_interstitial_ad)
    Button bInterstitialAd;

    @BindView(R.id.tv_interstitial_ad_loaded)
    TextView tvAdLoaded;

    @BindView(R.id.ll_ads_layout)
    LinearLayout llAdsLayout;

    private InterstitialAd interstitialAd;

    private FirebaseRemoteConfig remoteConfig;

    public static AdFragment1 newInstance() {
        AdFragment1 fragment = new AdFragment1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ad_1, container, false);
        ButterKnife.bind(this, view);
        remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();
        remoteConfig.setConfigSettings(remoteConfigSettings);
        remoteConfig.setDefaults(R.xml.remote_config_defaults);
        fetchConfig();

        final Boolean showAds = remoteConfig.getBoolean(Constants.RemoteConfig.SHOW_ADS);

        bInterstitialAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchConfig();
                updateAdsFromRemoteConfig(showAds);
            }
        });

        return view;
    }

    private void updateAdsFromRemoteConfig(boolean showAds) {
        if (showAds) {
            llAdsLayout.setVisibility(View.VISIBLE);
        } else {
            llAdsLayout.setVisibility(View.GONE);
        }
        interstitialAd();
        bannerAd();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            tvAdLoaded.setText("Not loading ad");
        }
    }

    private void fetchConfig() {
        boolean ads = remoteConfig.getBoolean(Constants.RemoteConfig.SHOW_ADS);
        Log.d(TAG, "fetchConfig1: " + ads);
        remoteConfig.fetch(0)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Task successful: ");
                            remoteConfig.activateFetched();
                        } else {
                            Log.d(TAG, "Task unsuccessful: ");
                        }
                    }
                });
        boolean adConfig = remoteConfig.getBoolean(Constants.RemoteConfig.SHOW_ADS);
        Log.d(TAG, "fetchConfig1: " + adConfig);
        updateAdsFromRemoteConfig(adConfig);
    }

    private void interstitialAd() {
        interstitialAd = new InterstitialAd(getActivity());
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_1));
        interstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdOpened() {
                tvAdLoaded.setText("Add Opened");
            }

            @Override
            public void onAdLoaded() {
                tvAdLoaded.setText("Add Loaded");
            }

            @Override
            public void onAdClosed() {
                tvAdLoaded.setText("Add Closed");
            }
        });

        requestInterstitialAd();
    }

    private void requestInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("560952F19DAC0470AC6104E57675F1A0")
                .addTestDevice("AB7E541670FA5610113C4D6E5A34F67A")
                .build();
        interstitialAd.loadAd(adRequest);
    }

    private void bannerAd() {
        MobileAds.initialize(getActivity(), Constants.Ads.ADMOB_NATIVE_UNIT_ID);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("560952F19DAC0470AC6104E57675F1A0")
                .addTestDevice("AB7E541670FA5610113C4D6E5A34F67A")
                .build();
        //AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> titles = new ArrayList<>();
        List<String> desc = new ArrayList<>();
        addDataInList(titles, desc);
        AdsAdapter1 adapter1 = new AdsAdapter1(getActivity(), titles, desc);
        rvAds.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAds.setAdapter(adapter1);
    }

    private void addDataInList(List<String> titles, List<String> desc) {
        titles.add("Title One");
        titles.add("Title Two");
        titles.add("Title Three");
        titles.add("Title Four");
        titles.add("Title Five");
        titles.add("Title Six");
        titles.add("Title Seven");
        titles.add("Title Eight");
        titles.add("Title Nine");
        titles.add("Title Ten");
        titles.add("Title Eleven");

        desc.add("This is desc One");
        desc.add("This is desc Two");
        desc.add("This is desc Three");
        desc.add("This is desc Four");
        desc.add("This is desc Five");
        desc.add("This is desc Six");
        desc.add("This is desc Seven");
        desc.add("This is desc Eight");
        desc.add("This is desc Nine");
        desc.add("This is desc Ten");
        desc.add("This is desc Eleven");
    }
}
