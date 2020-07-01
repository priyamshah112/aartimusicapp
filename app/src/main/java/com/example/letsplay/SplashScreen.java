package com.example.letsplay;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this,LanguageSelector.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

//    public void QuitApp() {
//        finish();
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(1);
//        Toast.makeText(getApplicationContext(), "Closed Completely and Safely", Toast.LENGTH_LONG).show();
//    }
}
