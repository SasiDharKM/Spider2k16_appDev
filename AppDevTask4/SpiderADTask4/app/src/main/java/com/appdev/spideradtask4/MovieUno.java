package com.appdev.spideradtask4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieUno extends AppCompatActivity {

    TextView genreU,directorU,ratingU,typeU,plotU;
    ImageView posterU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_uno);
        Intent intent=getIntent();
        genreU=(TextView)findViewById(R.id.genreUno);
        directorU=(TextView)findViewById(R.id.directorUno);
        ratingU=(TextView)findViewById(R.id.ratingUno);
        typeU=(TextView)findViewById(R.id.typeUno);
        plotU=(TextView)findViewById(R.id.plotUno);
        posterU=(ImageView)findViewById(R.id.posterUno);

        if (!(intent==null)){
            setTitle(intent.getStringExtra("iTit"));
            genreU.setText(intent.getStringExtra("iGen"));
            directorU.setText(intent.getStringExtra("iDir"));
            ratingU.setText(intent.getStringExtra("iRat"));
            typeU.setText(intent.getStringExtra("iTyp"));
            plotU.setText(intent.getStringExtra("iPlo"));
            Picasso.with(MovieUno.this).load(intent.getStringExtra("iPos"))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(posterU);
        }
        else{
            setTitle("");
            posterU.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
