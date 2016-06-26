package com.appdev.spidertask3ad;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by SasiDKM on 25-06-2016.
 */
public class SongPlayer extends AsyncTask<String,Void,String> implements MediaPlayer.OnCompletionListener {
    MediaPlayer player;
    Button play,stop;
    Context con;

    public SongPlayer(Button a,Button b,Context context){
        this.play=a;
        this.stop=b;
        this.con=context;
    }


    protected void onCancelled() {
        super.onCancelled();
        Toast.makeText(con,"Song was cancelled",Toast.LENGTH_SHORT).show();
        play.setEnabled(true);
        stop.setEnabled(false);
    }

    @Override
    protected String doInBackground(String... params) {
        String song = params[0];

        //Choose track
        switch (song){
            case "Badhalu Tochani":
                player = MediaPlayer.create(con,R.raw.badalu);
                break;
            case "Jagdish On Mission":
                player = MediaPlayer.create(con,R.raw.jagonmiss);
                break;
            case "Nerupu Da":
                player = MediaPlayer.create(con,R.raw.neruppuda);
                break;
            case "Engo Odigindrai":
                player = MediaPlayer.create(con,R.raw.ngo);
                break;

            default:
                Toast.makeText(con,"This song is not available",Toast.LENGTH_SHORT).show();
        }
        player.start();
        player.setOnCompletionListener(this);
        long songTime = player.getCurrentPosition();
        while(songTime<player.getDuration() && player.isPlaying() && !isCancelled()){
            songTime = player.getCurrentPosition();
            if(isCancelled()){
                songTime = player.getDuration();
                break;
            }
        }


        return null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
