package com.example.letsplay;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.HashMap;

import static com.google.android.gms.ads.AdSize.BANNER;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    PagerAdapter mPagerAdapter;
    TabLayout mTabLayout;
    TabItem libraryTabItem;
    TabItem aartiTabItem;
    ViewPager mViewPager;
    ImageButton setting;

    String MyAdUnitId1;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

//    private AdView mmAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //"ca-app-pub-3940256099942544/1033173712"
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setting = findViewById(R.id.setting);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
       // AdView adView = new AdView(this);

        mTabLayout = findViewById(R.id.tabLayout);
        libraryTabItem = findViewById(R.id.libraryTabItem);
        aartiTabItem = findViewById(R.id.aartiTabItem);
        mViewPager = findViewById(R.id.pager);

        //Remote Config
        HashMap<String, Object> defaultsRate = new HashMap<>();
        defaultsRate.put("version", String.valueOf(getVersionCode()));

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10) // change to 3600 on published app
                .build();

        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(defaultsRate);

        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    final String version = mFirebaseRemoteConfig.getString("version");

                    //change package name here
                    if(Integer.parseInt(version) > getVersionCode())
                        showTheDialog("com.example.letsplay", version );
                }
                else Log.e("MYLOG", "mFirebaseRemoteConfig.fetchAndActivate() NOT Successful");

            }
        });


        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount()); // call the pager class
        mViewPager.setAdapter(mPagerAdapter); // set adapter for pager
        setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,Setting.class);
                startActivity(myIntent);
            }

        });
        // do something when the tab is selected
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition()); // set current tab position

                if(tab.getPosition() == 1){
//                    Toast.makeText(MainActivity.this, "Album Tab Selected.", Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() == 2){
//                    Toast.makeText(MainActivity.this, "Music Tab Selected.", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(MainActivity.this, "Playlist Tab Selected.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        Firebase.setAndroidContext(this);
        Firebase myFirebase = new Firebase("https://bhajan-d2833.firebaseio.com/admob1");
        AdView mAdView = new AdView(this);
        myFirebase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                MyAdUnitId1 = dataSnapshot.getValue(String.class);
                Log.w("gt the id "+MyAdUnitId1,"captured the banner admob id in main activity");
                if(MyAdUnitId1!=null) {


                    mAdView.setAdSize(AdSize.BANNER);
                    mAdView.setAdUnitId(MyAdUnitId1);
                    AdRequest adRequest = new AdRequest.Builder()
                            .build();
                    if(mAdView.getAdSize() != null || mAdView.getAdUnitId() != null)
                        mAdView.loadAd(adRequest);
                    // else Log state of adsize/adunit
                    ((LinearLayout)findViewById(R.id.adView)).addView(mAdView);
//                    adView.setAdSize(AdSize.BANNER);
//                    adView.setAdUnitId(MyAdUnitId1);
////                    mmAdView = findViewById(R.id.adView);
////                    mmAdView.setAdSize(AdSize.BANNER);
////                    mmAdView.setAdUnitId(MyAdUnitId1);
//                    AdRequest aadRequestt = new AdRequest.Builder().build();
//                    adView.loadAd(aadRequestt);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
//        mmAdView = findViewById(R.id.adView);
//        AdRequest aadRequestt = new AdRequest.Builder().build();
//        mmAdView.loadAd(aadRequestt);
    }

    private void showTheDialog(final String appPackageName, String versionFromRemoteConfig){
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Update")
                .setMessage("New version of app is available. Version : "+versionFromRemoteConfig)
                .setPositiveButton("UPDATE", null)
                .show();

        dialog.setCancelable(false);

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
    }

    private PackageInfo pInfo;
    public int getVersionCode() {
        pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            Log.i("MYLOG", "NameNotFoundException: "+e.getMessage());
        }
        System.out.println(pInfo.versionCode);
        return pInfo.versionCode;
    }
    
    @Override
    public void onBackPressed() {
        try {
            System.out.println("Back pressed");
            if (Global.sMediaPlayer.isPlaying()) {
                System.out.println("playing aarti stop");
                Global.sMediaPlayer.stop();
            }
        }
        catch (Exception e) {
            System.out.println("Aarti Player Null Object");
        }

        try {
            if (Global.mMediaPlayer.isPlaying()) {
                System.out.println("playing library stop");
                Global.mMediaPlayer.stop();
            }
        }
        catch (Exception e) {
            System.out.println("Media Player Null Object");
        }

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

    }

}
/*
package com.example.letsplay;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    PagerAdapter mPagerAdapter;
    TabLayout mTabLayout;
    TabItem libraryTabItem;
    TabItem aartiTabItem;
    ViewPager mViewPager;
    ImageButton setting;
    String MyAdUnitId1;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
//    private AdView mAdView;
    private AdView mmAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setting = findViewById(R.id.setting);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        mTabLayout = findViewById(R.id.tabLayout);
        libraryTabItem = findViewById(R.id.libraryTabItem);
        aartiTabItem = findViewById(R.id.aartiTabItem);
        mViewPager = findViewById(R.id.pager);

        Firebase.setAndroidContext(this);
        Firebase myFirebase = new Firebase("https://bhajan-d2833.firebaseio.com/admob1");
        myFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                MyAdUnitId1 = dataSnapshot.getValue(String.class);
                Log.w("gt the id "+MyAdUnitId1,"captured the id in main acrtivuty");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //Remote Config
        HashMap<String, Object> defaultsRate = new HashMap<>();
        defaultsRate.put("version", String.valueOf(getVersionCode()));

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10) // change to 3600 on published app
                .build();

        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(defaultsRate);

        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    final String version = mFirebaseRemoteConfig.getString("version");

                    //change package name here
                    if(Integer.parseInt(version) > getVersionCode())
                        showTheDialog("com.facebook.lite", version );
                }
                else Log.e("MYLOG", "mFirebaseRemoteConfig.fetchAndActivate() NOT Successful");

            }
        });


        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount()); // call the pager class
        mViewPager.setAdapter(mPagerAdapter); // set adapter for pager
        setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,Setting.class);
                startActivity(myIntent);
            }

        });
        // do something when the tab is selected
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition()); // set current tab position

                if(tab.getPosition() == 1){
//                    Toast.makeText(MainActivity.this, "Album Tab Selected.", Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() == 2){
//                    Toast.makeText(MainActivity.this, "Music Tab Selected.", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(MainActivity.this, "Playlist Tab Selected.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }

    private void showTheDialog(final String appPackageName, String versionFromRemoteConfig){
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Update")
                .setMessage("New version of app is available. Version : "+versionFromRemoteConfig)
                .setPositiveButton("UPDATE", null)
                .show();

        dialog.setCancelable(false);

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
    }

    private PackageInfo pInfo;
    public int getVersionCode() {
        pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            Log.i("MYLOG", "NameNotFoundException: "+e.getMessage());
        }
        System.out.println(pInfo.versionCode);
        return pInfo.versionCode;
    }

    @Override
    public void onBackPressed() {
        try {
            System.out.println("Back pressed");
            if (Global.sMediaPlayer.isPlaying()) {
                System.out.println("playing aarti stop");
                Global.sMediaPlayer.stop();
            }
        }
        catch (Exception e) {
            System.out.println("Aarti Player Null Object");
        }

        try {
            if (Global.mMediaPlayer.isPlaying()) {
                System.out.println("playing library stop");
                Global.mMediaPlayer.stop();
            }
        }
        catch (Exception e) {
            System.out.println("Media Player Null Object");
        }

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

    }

}

 */
