package com.shivamdev.admobdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.shivamdev.admobdemo.common.ads.AdFragment1;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addAdFragment();
    }

    private void addAdFragment() {
        AdFragment1 adFragment = AdFragment1.newInstance();
        replaceFragment(adFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ll_fragment, fragment, TAG).commit();
    }
}
