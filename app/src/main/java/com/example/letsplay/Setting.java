package com.example.letsplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
