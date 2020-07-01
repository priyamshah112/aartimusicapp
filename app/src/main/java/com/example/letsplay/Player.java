package com.example.letsplay;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity {
    SeekBar mSeekBar;
    SeekBar vSeekBar;
    TextView songTitle;
    ArrayList<File> allSongs;

    int position;
    TextView curTime;
    TextView totTime;
    ImageView playIcon;
    ImageView prevIcon;
    ImageView nextIcon;
    Intent playerData;
    Bundle bundle;
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

        if (Global.mMediaPlayer != null) {
            Global.mMediaPlayer.stop();
        }

        playerData = getIntent();
        bundle = playerData.getExtras();

        allSongs = (ArrayList) bundle.getParcelableArrayList("songs");
        position = bundle.getInt("position", 0);
        initPlayer(position);



        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
        prevIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position <= 0) {
                    position = allSongs.size() - 1;
                } else {
                    position--;
                }

                initPlayer(position);

            }
        });

        nextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < allSongs.size() - 1) {
                    position++;
                } else {
                    position = 0;

                }
                initPlayer(position);
            }
        });

    }


    private void initPlayer(final int position) {

        //stop aarti player
        try{
            if (Global.sMediaPlayer.isPlaying()) {
                System.out.println("playing aarti stop from Library");
                Global.sMediaPlayer.stop();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

        if (Global.mMediaPlayer != null && Global.mMediaPlayer.isPlaying()) {
            Global.mMediaPlayer.reset();
        }

        String sname = allSongs.get(position).getName().replace(".mp3", "").replace(".m4a", "").replace(".wav", "").replace(".m4b", "");
        songTitle.setText(sname);
        Uri songResourceUri = Uri.parse(allSongs.get(position).toString());

        Global.mMediaPlayer = MediaPlayer.create(getApplicationContext(), songResourceUri); // create and load mediaplayer with song resources
        Global.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                String totalTime = createTimeLabel(Global.mMediaPlayer.getDuration());
                totTime.setText(totalTime);
                mSeekBar.setMax(Global.mMediaPlayer.getDuration());
                Global.mMediaPlayer.start();
                playIcon.setImageResource(R.drawable.ic_pause_black_24dp);

            }
        });

        Global.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int curSongPoition = position;
                // code to repeat songs until the
                if (curSongPoition < allSongs.size() - 1) {
                    curSongPoition++;
                    initPlayer(curSongPoition);
                } else {
                    curSongPoition = 0;
                    initPlayer(curSongPoition);
                }

                //playIcon.setImageResource(R.drawable.ic_play_arrow_black_24dp);

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
                    Global.mMediaPlayer.seekTo(progress);
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
                while (Global.mMediaPlayer != null) {
                    try {
//                        Log.i("Thread ", "Thread Called");
                        // create new message to send to handler
                        if (Global.mMediaPlayer.isPlaying()) {
                            Message msg = new Message();
                            msg.what = Global.mMediaPlayer.getCurrentPosition();
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
//            Log.i("handler ", "handler called");
            int current_position = msg.what;
            mSeekBar.setProgress(current_position);
            String cTime = createTimeLabel(current_position);
            curTime.setText(cTime);
        }
    };


    private void play() {

        if (Global.mMediaPlayer != null && !Global.mMediaPlayer.isPlaying()) {
            Global.mMediaPlayer.start();
            playIcon.setImageResource(R.drawable.ic_pause_black_24dp);
        } else {
            pause();
        }

    }

    private void pause() {
        if (Global.mMediaPlayer.isPlaying()) {
            Global.mMediaPlayer.pause();
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
