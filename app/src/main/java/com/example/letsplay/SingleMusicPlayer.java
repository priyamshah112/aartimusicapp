package com.example.letsplay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
//import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

//import java.io.File;
//import java.util.ArrayList;

public class SingleMusicPlayer extends AppCompatActivity {
    SeekBar mSeekBar;
    TextView songTitle;
    static MediaPlayer mMediaPlayer;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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



        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }

        playerData = getIntent();
        bundle = playerData.getExtras();

        //position = bundle.getInt("position", 0);
        initPlayer();



        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

    }


    private void initPlayer() {

        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.reset();
        }

        if(bundle.getInt("id")==1){
            songTitle.setText("Morning");
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.morning); // create and load mediaplayer with song resources
        }
        if(bundle.getInt("id")==2){
            songTitle.setText("Noon");
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.afternoon); // create and load mediaplayer with song resources
        }
        if(bundle.getInt("id")==3){
            songTitle.setText("Evening");
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.evening); // create and load mediaplayer with song resources
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                String totalTime = createTimeLabel(mMediaPlayer.getDuration());
                totTime.setText(totalTime);
                mSeekBar.setMax(mMediaPlayer.getDuration());
                mMediaPlayer.start();
                playIcon.setImageResource(R.drawable.ic_pause_black_24dp);

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
                    mMediaPlayer.seekTo(progress);
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
                while (mMediaPlayer != null) {
                    try {
//                        Log.i("Thread ", "Thread Called");
                        // create new message to send to handler
                        if (mMediaPlayer.isPlaying()) {
                            Message msg = new Message();
                            msg.what = mMediaPlayer.getCurrentPosition();
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

        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            playIcon.setImageResource(R.drawable.ic_pause_black_24dp);
        } else {
            pause();
        }

    }

    private void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
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
