package com.example.letsplay;

import android.app.Activity;
import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class LanguageSelector extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnSubmit;
    private String str;
    SharedPreferences prefs;
    public static final String fileName = "login";
    public static final String language = "lang";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if(prefs.contains(language)){
            Intent i = new Intent(LanguageSelector.this,MainActivity.class);
            prefs.getString(language,"");
            startActivity(i);
            loadLocale();
        }
        //loadLocale();

        //Change actionbar title, if u dont change it will be according to the system default
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(R.string.app_name));

        setContentView(R.layout.activity_language_selector);

        addListenerOnButton();
    }



    // get the selected dropdown list value
    public void addListenerOnButton() {

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        setLocale("en");
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String lang=null;
                radioGroup = (RadioGroup) findViewById(R.id.radio);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                System.out.println("I am here in add listener");
                System.out.println(selectedId);
                radioButton = (RadioButton) findViewById(selectedId);
                str=(String) radioButton.getText();
                System.out.println("Printing string of radio Button");
                System.out.println(str);
                if (str.equals("English")){
                    setLocale("en");
                    lang="en";
                }
                else if(str.equals("हिन्दी")){
                    setLocale("hi");
                    lang="hi";
                }
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(language,lang);
                editor.commit();
                Intent myIntent = new Intent(LanguageSelector.this,TimerSetting.class);
                startActivity(myIntent);
                myIntent.putExtra("language", (String)radioButton.getText());
                }
            }
        );
    }
    private void setLocale(String lang){
        Log.w("changing the locale"+lang,"language change");
        Locale locale = new Locale(lang);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        //saved data to Shared preferences
        //SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        //editor.putString("My_Lang",lang);
        //editor.apply();
    }
    public void loadLocale(){
        prefs = getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String language = prefs.getString("lang","");
        setLocale(language);
        System.out.println(language);
        if(language!=null){
            if(language=="en") {
                setLocale(language);
            }else {
                setLocale(language);
            }
            Intent i = new Intent(LanguageSelector.this,MainActivity.class);
            startActivity(i);
        }
    }
}
