package com.appdev.spidertask3ad;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class imageTrack extends AppCompatActivity implements AdapterView.OnItemSelectedListener,SensorEventListener {
    private int sec=0,pos=0,flag=0,x=0;
    private int[] images = {R.drawable.first,R.drawable.second,R.drawable.third,R.drawable.fourth,R.drawable.fifth,R.drawable.sixth};
    private boolean slideRun=false,swipeEnable=false,songPlaying=false;
    private ImageView imageShow;
    private TextView timeView;
    private Button play,stop,slide,enable,disable;
    private Intent toPlayIntent;
    private float disp;
    private String SongChoice;
    private Spinner songTrack;
    SongPlayer songplay;
    SensorManager swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_track);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        timeView = (TextView) findViewById(R.id.timeShow);
        imageShow = (ImageView)findViewById(R.id.imageShow);
        play=(Button)findViewById(R.id.playButton);
        stop=(Button)findViewById(R.id.stopButton);
        slide=(Button)findViewById(R.id.slideButton);
        enable=(Button)findViewById(R.id.toEnable);
        disable=(Button)findViewById(R.id.toDisable);
        songTrack=(Spinner)findViewById(R.id.songSet);
        swipe= (SensorManager)getSystemService(SENSOR_SERVICE);
        disable.setEnabled(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(imageTrack.this,R.array.tracks,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        songTrack.setAdapter(adapter);
        songTrack.setOnItemSelectedListener(this);

        if(swipe.getSensorList(Sensor.TYPE_PROXIMITY).size()!=0){
            Sensor swp = swipe.getSensorList(Sensor.TYPE_PROXIMITY).get(0);
            swipe.registerListener(this,swp,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(imageTrack.this,"This phone doesn't possess the sense of proximity",Toast.LENGTH_SHORT).show();
        }



    }

    public void onClickSlide(View v){
        slideRun=true;
        slide.setEnabled(false);
        enable.setEnabled(false);
        disable.setEnabled(false);
        runSlideTimer();
    }


    private void runSlideTimer() {
        final Handler handler = new Handler();
        final Handler slider = new Handler();
        slider.post(new Runnable(){
            @Override
            public void run() {
                imageShow.setImageResource(images[pos]);
                if (slideRun) {
                    if(pos<images.length-1){
                        pos++;
                    }
                    else{
                        pos=0;
                        flag=1;
                        slideRun=false;
                    }
                }

                slider.postDelayed(this,3000);

            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = sec / 3600;
                int mins = (sec % 3600) / 60;
                int seconds = sec % 60;
                String time = String.format("%d:%02d:%02d", hours, mins, seconds);
                timeView.setText(time);
                if (slideRun) {
                    sec++;
                }
                else if(flag==1)
                {if(x<3)
                {sec++;
                    x++;
                }
                else{
                    sec=0;
                    slide.setEnabled(true);
                    enable.setEnabled(true);
                    disable.setEnabled(true);
                    flag=0;

                }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SongChoice=parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(),"Nothing is Selected",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        disp = event.values[0];
        if(swipeEnable) {
            if(disp<1.0) {
                pos++;
                pos = pos % images.length;
                imageShow.setImageResource(images[pos]);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
       //Just a Formality
    }

    public void onClickPlay(View v){
        songplay = new SongPlayer( play, stop,imageTrack.this);
        songplay.execute(SongChoice);
        stop.setEnabled(true);
        play.setEnabled(false);
        songPlaying = true;

    }

    public void onClickStop(View v){
        songplay.cancel(true);
        songplay.player.stop();
        songplay.player.release();
        stop.setEnabled(false);
        play.setEnabled(true);
        songPlaying=false;

    }

    public void onPause(){
        super.onPause();
       //For Saving Battery
        swipe.unregisterListener(imageTrack.this);
        if(songPlaying){
            songplay.cancel(true);
            songplay.player.stop();
            songplay.player.release();

        }
    }

    public void enableSense(View v){
        slide.setEnabled(false);
        disable.setEnabled(true);
        swipeEnable = true;
        enable.setEnabled(false);


    }
    public void disableSense(View v){
        slide.setEnabled(true);
        disable.setEnabled(true);
        swipeEnable= false;
        enable.setEnabled(true);
    }


}
