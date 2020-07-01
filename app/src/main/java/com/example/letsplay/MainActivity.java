package com.example.letsplay;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    PagerAdapter mPagerAdapter;
    TabLayout mTabLayout;
    TabItem libraryTabItem;
    TabItem aartiTabItem;
    ViewPager mViewPager;
    ImageButton setting;

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
