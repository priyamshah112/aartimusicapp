package com.example.letsplay;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class LanguageSelector extends AppCompatActivity {

    private Spinner spinner1;
    private Button btnSubmit;
    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();

        //Change actionbar title, if u dont change it will be according to the system default
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(R.string.app_name));

        setContentView(R.layout.activity_language_selector);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }


    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.w("on click occured","ckicked the button");
                //error is in the below statement which is tobe resolved as it is giving a deprecated issue
                str=(String) spinner1.getSelectedItem();
                if (str.equals("English")){
                    Log.w("enetered the locale 1"+String.valueOf(   spinner1.getSelectedItem()),"entered english");
                    //setLocale("en");
                }
                else if(str.equals("Hindi")){
                    Log.w("enetered the locale 2"+String.valueOf(spinner1.getSelectedItem()),"entered hindi");
                    //setLocale("hi");
                }
//                the below statements are to be recommented once the error is resoled
//                Intent myIntent = new Intent(LanguageSelector.this,TimerSetting.class);
//                startActivity(myIntent);
//                myIntent.putExtra("language", String.valueOf(spinner1.getSelectedItem()));
                }
            }
        );
    }
    private void setLocale(String lang){
        Log.w("changing the locale","language change");
        Locale locale = new Locale(lang);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        //saved data to Shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }
}
