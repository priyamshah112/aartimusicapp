package com.example.letsplay;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Calendar;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class TimerSetting extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private Button btnSubmit;
    private Button mgnSubmit;
    private Button aftSubmit;
    private Button eveSubmit;
    public Integer var=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_setting);
        addListenerOnButtonBtn();
        addListenerOnButtonMgn();
        addListenerOnButtonEve();
        addListenerOnButtonAft();

    }


    public void addListenerOnButtonBtn() {

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar c1 = Calendar.getInstance();
                c1.set(Calendar.HOUR_OF_DAY, 20);
                c1.set(Calendar.MINUTE, 0);
                c1.set(Calendar.SECOND, 0);
                var=1;
                startAlarm(c1,var);
                Calendar c2 = Calendar.getInstance();
                c2.set(Calendar.HOUR_OF_DAY, 13);
                c2.set(Calendar.MINUTE, 30);
                c2.set(Calendar.SECOND, 0);
                var=2;
                startAlarm(c2,var);
                Calendar c3 = Calendar.getInstance();
                c3.set(Calendar.HOUR_OF_DAY, 20);
                c3.set(Calendar.MINUTE, 0);
                c3.set(Calendar.SECOND, 0);
                var=3;
                startAlarm(c3,var);

                Intent myIntent = new Intent(TimerSetting.this,MainActivity.class);
                startActivity(myIntent);
            }

        });
    }


    public void addListenerOnButtonMgn() {
        mgnSubmit = (Button) findViewById(R.id.imageButton3);

        mgnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                var=1;
            }

        });
    }


    public void addListenerOnButtonAft() {
        aftSubmit = (Button) findViewById(R.id.imageButton2);
        aftSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                var=2;
            }

        });
    }


    public void addListenerOnButtonEve() {
        eveSubmit = (Button) findViewById(R.id.imageButton);
        eveSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                var=3;
            }

        });
    }


    private void startAlarm(Calendar c,Integer getvar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (getvar == 1) {
            Intent intent = new Intent(this, AlertReceiver.class);
            Bundle extras = new Bundle();
            extras.putInt("arg_1", getvar);
            intent.putExtras(extras);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, FLAG_UPDATE_CURRENT);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else if (getvar == 2) {
            Intent intent = new Intent(this, AlertReceiver2.class);
            Bundle extras = new Bundle();
            extras.putInt("arg_1", getvar);
            intent.putExtras(extras);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, FLAG_UPDATE_CURRENT);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        } else if (getvar == 3) {
            Intent intent = new Intent(this, AlertReceiver3.class);
            Bundle extras = new Bundle();
            extras.putInt("arg_1", getvar);
            intent.putExtras(extras);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, FLAG_UPDATE_CURRENT);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        System.out.println(hourOfDay);
        c.set(Calendar.MINUTE, minute);
        System.out.println(minute);
        c.set(Calendar.SECOND, 0);
        startAlarm(c,var);
    }

}


