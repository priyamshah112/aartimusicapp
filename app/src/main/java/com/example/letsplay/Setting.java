package com.example.letsplay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Setting extends AppCompatActivity {
    private Button btnSubmit1;
    private Button btnSubmit2;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        addListenerOnButtonBtn1();
        addListenerOnButtonBtn2();
    }
    public void addListenerOnButtonBtn1() {


        btnSubmit1 = (Button) findViewById(R.id.btnSubmit);

        btnSubmit1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent myIntent = new Intent(Setting.this,LanguageSelector.class);
                myIntent.putExtra("id",1);
                startActivity(myIntent);
            }

        });
    }
    public void addListenerOnButtonBtn2() {

        btnSubmit2 = (Button) findViewById(R.id.btnSubmit2);

        btnSubmit2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Setting.this,TimerSetting.class);
                startActivity(myIntent);
            }

        });
    }
}
