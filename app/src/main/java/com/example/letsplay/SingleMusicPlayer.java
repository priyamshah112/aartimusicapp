package com.example.letsplay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class SingleMusicPlayer extends AppCompatActivity {
    SeekBar mSeekBar;
    TextView songTitle;
    TextView curTime;
    TextView totTime;
    ImageView playIcon;
    ImageView prevIcon;
    ImageView nextIcon;
    Intent playerData;
    Bundle bundle;
    SeekBar vSeekBar;
    AudioManager audioManager;
    String MyAdUnitId;
    InterstitialAd mInterstitialAd;
    Integer flagmob=0;

    String MyAdUnitId1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInterstitialAd = new InterstitialAd(this);
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        Firebase.setAndroidContext(this);
        Firebase myFirebase = new Firebase("https://bhajan-d2833.firebaseio.com/admob");
        myFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                MyAdUnitId1 = dataSnapshot.getValue(String.class);
                Log.w("gt the id "+MyAdUnitId1,"captured the id in main acrtivuty");
                if(MyAdUnitId1!=null) {
                    flagmob=1;
                    MyAdUnitId = dataSnapshot.getValue(String.class);

                    mInterstitialAd.setAdUnitId(MyAdUnitId);
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }

                //mInterstitialAd.show();
                setContentView(R.layout.activity_player);

                mSeekBar = findViewById(R.id.mSeekBar);
                songTitle = findViewById(R.id.songTitle);
                curTime = findViewById(R.id.curTime);
                totTime = findViewById(R.id.totalTime);

                playIcon = findViewById(R.id.playIcon);
                prevIcon = findViewById(R.id.prevIcon);
                nextIcon = findViewById(R.id.nextIcon);
                vSeekBar = findViewById(R.id.vseekBar);

                audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);



                if (Global.sMediaPlayer != null) {
                    Global.sMediaPlayer.stop();
                }

                playerData = getIntent();
                bundle = playerData.getExtras();

                initPlayer();



                playIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flagmob==1) {

                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                        play();
                    }
                });

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }



        });
    }


    private void initPlayer() {
        //stop mMediaPlayer

        try{

            if (Global.mMediaPlayer.isPlaying()) {
                System.out.println("playing library stop from Aarti Player");
                Global.mMediaPlayer.stop();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        if (Global.sMediaPlayer != null && Global.sMediaPlayer.isPlaying()) {
            Global.sMediaPlayer.reset();
        }

        if(bundle.getInt("id")==1){
            songTitle.setText("Nitayniyam");
            Global.sMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.morning); // create and load mediaplayer with song resources

            int noOfSecond = 1;
            if(MyAdUnitId1!=null) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //TODO Set your button auto perform click.
                        playIcon.performClick();
                    }
                }, noOfSecond * 1000);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //TODO Set your button auto perform click.
                        playIcon.performClick();
                    }
                }, noOfSecond * 1000);
            }
        }
        if(bundle.getInt("id")==2){
            songTitle.setText("Ramini");
            Global.sMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.afternoon); // create and load mediaplayer with song resources

            if(MyAdUnitId1!=null) {
                int noOfSecond = 1;

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //TODO Set your button auto perform click.
                        playIcon.performClick();
                    }
                }, noOfSecond * 1000);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //TODO Set your button auto perform click.
                        playIcon.performClick();
                    }
                }, noOfSecond * 1000);
            }
        }
        if(bundle.getInt("id")==3){
            songTitle.setText("Sandhya");
            Global.sMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.evening); // create and load mediaplayer with song resources

            if(MyAdUnitId1!=null) {
                int noOfSecond = 1;

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //TODO Set your button auto perform click.
                        playIcon.performClick();
                    }
                }, noOfSecond * 1000);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //TODO Set your button auto perform click.
                        playIcon.performClick();
                    }
                }, noOfSecond * 1000);
            }
        }
        Global.sMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                String totalTime = createTimeLabel(Global.sMediaPlayer.getDuration());
                totTime.setText(totalTime);
                mSeekBar.setMax(Global.sMediaPlayer.getDuration());
                Global.sMediaPlayer.start();

            }
        });

        Global.sMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onBackPressed();
            }
        });
        //volume Seekbar

        vSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        vSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        vSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int newVolume, boolean b) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        //Music Seekbar
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    Global.sMediaPlayer.seekTo(progress);
                    mSeekBar.setProgress(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Global.sMediaPlayer != null) {
                    try {
//                        Log.i("Thread ", "Thread Called");
                        // create new message to send to handler
                        if (Global.sMediaPlayer.isPlaying()) {
                            Message msg = new Message();
                            msg.what = Global.sMediaPlayer.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int current_position = msg.what;
            mSeekBar.setProgress(current_position);
            String cTime = createTimeLabel(current_position);
            curTime.setText(cTime);
        }
    };


    private void play() {


        if (Global.sMediaPlayer != null && !Global.sMediaPlayer.isPlaying()) {
            Global.sMediaPlayer.start();
            playIcon.setImageResource(R.drawable.ic_pause_black_24dp);
//
//                int noOfSecond = 1;
//
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        //TODO Set your button auto perform click.
//                        playIcon.performClick();
//                    }
//                }, noOfSecond * 1000);
//
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        //TODO Set your button auto perform click.
//                        playIcon.performClick();
//                    }
//                }, noOfSecond * 1000);

            playIcon.setImageResource(R.drawable.ic_pause_black_24dp);
        } else {
            pause();
        }

    }

    private void pause() {

        if (Global.sMediaPlayer.isPlaying()) {
            Global.sMediaPlayer.pause();
            playIcon.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        }

    }




    public String createTimeLabel(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;


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
        super.onBackPressed();
    }
}
/*
package com.example.letsplay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
//import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

//import java.io.File;
//import java.util.ArrayList;
// errror tweek https://dominoc925.blogspot.com/2019/08/resolving-android-studio-cannot-fit.html#:~:text=August%2012%2C%202019-,Resolving%20Android%20Studio%20%22Cannot%20fit%20requested%20classes,a%20single%20dex%20file%22%20error&text=The%20solution%20to%20this%20is,new%20dependency%20to%20the%20androidx.

public class SingleMusicPlayer extends AppCompatActivity {
    SeekBar mSeekBar;
    TextView songTitle;
    //static MediaPlayer Global.sMediaPlayer;
    int position;
    TextView curTime;
    TextView totTime;
    ImageView playIcon;
    ImageView prevIcon;
    ImageView nextIcon;
    Intent playerData;
    Bundle bundle;
    SeekBar vSeekBar;
    AudioManager audioManager;
    String MyAdUnitId;
    InterstitialAd mInterstitialAd;
    Integer flagmob=0;

    String MyAdUnitId1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInterstitialAd = new InterstitialAd(this);
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        Firebase.setAndroidContext(this);
        Firebase myFirebase = new Firebase("https://bhajan-d2833.firebaseio.com/admob");
        myFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                MyAdUnitId1 = dataSnapshot.getValue(String.class);
                Log.w("gt the id "+MyAdUnitId1,"captured the id in main acrtivuty");
                if(MyAdUnitId1!=null) {
                    flagmob=1;
                    MyAdUnitId = dataSnapshot.getValue(String.class);

                    mInterstitialAd.setAdUnitId(MyAdUnitId);
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }

                //mInterstitialAd.show();
                setContentView(R.layout.activity_player);

                mSeekBar = findViewById(R.id.mSeekBar);
                songTitle = findViewById(R.id.songTitle);
                curTime = findViewById(R.id.curTime);
                totTime = findViewById(R.id.totalTime);

                playIcon = findViewById(R.id.playIcon);
                prevIcon = findViewById(R.id.prevIcon);
                nextIcon = findViewById(R.id.nextIcon);
                vSeekBar = findViewById(R.id.vseekBar);

                audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);



                if (Global.sMediaPlayer != null) {
                    Global.sMediaPlayer.stop();
                }

                playerData = getIntent();
                bundle = playerData.getExtras();

                //position = bundle.getInt("position", 0);
                initPlayer();
                //AdRequest adRequest = new AdRequest.Builder().build();
                //mInterstitialAd.loadAd(adRequest);
                //mInterstitialAd.show();



                playIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flagmob==1) {
//                    AdRequest adRequest = new AdRequest.Builder().build();
//                    mInterstitialAd.loadAd(adRequest);

                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                        play();
                    }
                });

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }



        });
//        setContentView(R.layout.activity_player);
//        mInterstitialAd = new InterstitialAd(this);
//        MobileAds.initialize(this,
//                "ca-app-pub-3940256099942544~3347511713");
//
//        Firebase.setAndroidContext(this);
//        Firebase myFirebase = new Firebase("https://bhajan-d2833.firebaseio.com/admob");
//        myFirebase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                MyAdUnitId1 = dataSnapshot.getValue(String.class);
//                Log.w("gt the id "+MyAdUnitId1,"captured the id in main acrtivuty");
//                if(MyAdUnitId1!=null) {
//                    flagmob=1;
//                    MyAdUnitId = dataSnapshot.getValue(String.class);
//
//                    mInterstitialAd.setAdUnitId(MyAdUnitId);
//                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                }
//
//                //mInterstitialAd.show();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//
//
//
//        });
//
//        mSeekBar = findViewById(R.id.mSeekBar);
//        songTitle = findViewById(R.id.songTitle);
//        curTime = findViewById(R.id.curTime);
//        totTime = findViewById(R.id.totalTime);
//
//        playIcon = findViewById(R.id.playIcon);
//        prevIcon = findViewById(R.id.prevIcon);
//        nextIcon = findViewById(R.id.nextIcon);
//        vSeekBar = findViewById(R.id.vseekBar);
//
//        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
//
//
//
//        if (Global.sMediaPlayer != null) {
//            Global.sMediaPlayer.stop();
//        }
//
//        playerData = getIntent();
//        bundle = playerData.getExtras();
//
//        //position = bundle.getInt("position", 0);
//        initPlayer();
//        //AdRequest adRequest = new AdRequest.Builder().build();
//        //mInterstitialAd.loadAd(adRequest);
//        //mInterstitialAd.show();
//
//
//
//        playIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(flagmob==1) {
////                    AdRequest adRequest = new AdRequest.Builder().build();
////                    mInterstitialAd.loadAd(adRequest);
//
//                    if (mInterstitialAd.isLoaded()) {
//                        mInterstitialAd.show();
//                    } else {
//                        Log.d("TAG", "The interstitial wasn't loaded yet.");
//                    }
//                }
//                play();
//            }
//        });

    }


    private void initPlayer() {
        //stop mMediaPlayer

        try{

            if (Global.mMediaPlayer.isPlaying()) {
                System.out.println("playing library stop from Aarti Player");
                Global.mMediaPlayer.stop();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        if (Global.sMediaPlayer != null && Global.sMediaPlayer.isPlaying()) {
            Global.sMediaPlayer.reset();
        }

        if(bundle.getInt("id")==1){
            songTitle.setText("Morning");
            Global.sMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.morning); // create and load mediaplayer with song resources

            int noOfSecond = 1;

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    playIcon.performClick();
                }
            }, noOfSecond * 1000);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    playIcon.performClick();
                }
            }, noOfSecond * 1000);
        }
        if(bundle.getInt("id")==2){
            songTitle.setText("Noon");
            Global.sMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.afternoon); // create and load mediaplayer with song resources

            int noOfSecond = 1;

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    playIcon.performClick();
                }
            }, noOfSecond * 1000);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    playIcon.performClick();
                }
            }, noOfSecond * 1000);

        }
        if(bundle.getInt("id")==3){
            songTitle.setText("Evening");
            Global.sMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.evening); // create and load mediaplayer with song resources

            int noOfSecond = 1;

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    playIcon.performClick();
                }
            }, noOfSecond * 1000);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    playIcon.performClick();
                }
            }, noOfSecond * 1000);

        }
        Global.sMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                String totalTime = createTimeLabel(Global.sMediaPlayer.getDuration());
                totTime.setText(totalTime);
                mSeekBar.setMax(Global.sMediaPlayer.getDuration());
                Global.sMediaPlayer.start();

            }
        });


        //volume Seekbar

        vSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        vSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        vSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int newVolume, boolean b) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        //Music Seekbar
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    Global.sMediaPlayer.seekTo(progress);
                    mSeekBar.setProgress(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Global.sMediaPlayer != null) {
                    try {
//                        Log.i("Thread ", "Thread Called");
                        // create new message to send to handler
                        if (Global.sMediaPlayer.isPlaying()) {
                            Message msg = new Message();
                            msg.what = Global.sMediaPlayer.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int current_position = msg.what;
            mSeekBar.setProgress(current_position);
            String cTime = createTimeLabel(current_position);
            curTime.setText(cTime);
        }
    };


    private void play() {


        if (Global.sMediaPlayer != null && !Global.sMediaPlayer.isPlaying()) {
            Global.sMediaPlayer.start();
                            playIcon.setImageResource(R.drawable.ic_pause_black_24dp);

                int noOfSecond = 1;

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //TODO Set your button auto perform click.
                        playIcon.performClick();
                    }
                }, noOfSecond * 1000);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //TODO Set your button auto perform click.
                        playIcon.performClick();
                    }
                }, noOfSecond * 1000);

            playIcon.setImageResource(R.drawable.ic_pause_black_24dp);
        } else {
            pause();
        }

    }

    private void pause() {

        if (Global.sMediaPlayer.isPlaying()) {
            Global.sMediaPlayer.pause();
            playIcon.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        }

    }




    public String createTimeLabel(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;


    }


}

 */
