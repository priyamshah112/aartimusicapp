package com.example.letsplay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
            System.out.println("entered the tranistion"+prefs.getString(language,""));
            String k=prefs.getString(language,"");

            setLocale(k);
            Intent i = new Intent(LanguageSelector.this,MainActivity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_language_selector);

        addListenerOnButton();
    }



    // get the selected dropdown list value
    public void addListenerOnButton() {

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

                                         @Override
                                         public void onClick(View v) {

                                             String lang=null;

                                             radioGroup = (RadioGroup) findViewById(R.id.radio);
                                             int selectedId = radioGroup.getCheckedRadioButtonId();
                                             radioButton = (RadioButton) findViewById(selectedId);
                                             str=(String) radioButton.getText();

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
        Locale locale = new Locale(lang);

        //locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        //saved data to Shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("filename",MODE_PRIVATE).edit();
        editor.putString("language",lang);
        editor.commit();
    }

}
