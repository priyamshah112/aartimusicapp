package com.example.letsplay;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Aarti extends Fragment implements View.OnClickListener {
    Button mrngbutton;
    Button noonbutton;
    Button eveningbutton;
    //Context thiscontext;
    private AdView mAdView;
    int[] allSongs = {R.raw.morning,R.raw.afternoon,R.raw.evening};
    public Aarti() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //thiscontext = container.getContext();
        System.out.println("Inflating Aarti Fragment here");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_aarti, container, false);


        mrngbutton = (Button) v.findViewById(R.id.MorningBtn);
        mrngbutton.setOnClickListener(this);

        noonbutton = (Button) v.findViewById(R.id.NoonBtn);
        noonbutton.setOnClickListener(this);

        eveningbutton = (Button) v.findViewById(R.id.EveningBtn);
        eveningbutton.setOnClickListener(this);
        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return v;
    }

    public ArrayList<File> findMusics(File file){

        ArrayList<File> musicLists = new ArrayList<File>();

        File[] files = file.listFiles();

        for(File singleFile: files ){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                musicLists.addAll(findMusics(singleFile));
            }else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".m4a") || singleFile.getName().endsWith(".wav") || singleFile.getName().endsWith(".m4b")){
                    musicLists.add(singleFile);
                }
            }
        }

        return musicLists;

    }

    @Override
    public void onClick(View v) {
        Intent singleplay = new Intent(getActivity(),SingleMusicPlayer.class);
        switch (v.getId()) {
            case R.id.MorningBtn:
                System.out.println("morning");
                String songName = "morning";
                singleplay.putExtra("id",1);
                startActivity(singleplay);
                break;
            case R.id.NoonBtn:
                System.out.println("Noon");
                String songName1 = "afternoon";
                singleplay.putExtra("id",2);
                startActivity(singleplay);
                break;
            case R.id.EveningBtn:
                System.out.println("Evening");
                String songName2 = "evening";
                singleplay.putExtra("id",3);
                startActivity(singleplay);
                break;
        }
    }

    public void MorningAartiPlayer(View view){
        mrngbutton = (Button) view.findViewById(R.id.MorningBtn);


    }

    public void NoonAartiPlayer(View view){
        noonbutton = (Button) view.findViewById(R.id.NoonBtn);

    }

    public void EveningAartiPlayer(View view){
        eveningbutton = (Button) view.findViewById(R.id.EveningBtn);

    }

}
