package com.appdev.spideradtask4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appdev.spideradtask4.Model.Movie;
import com.appdev.spideradtask4.Model.MovieRow;
import com.appdev.spideradtask4.Rest.ApiClient;
import com.appdev.spideradtask4.Rest.ApiInterface;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMovie extends AppCompatActivity {
TextView genreA, plotA, ratingA, typeA;
        ImageView posterA;
        EditText titleA;
        Button add;

        MovieDbHelper movDb;
        ApiInterface apiService;

        boolean got;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        genreA = (TextView) findViewById(R.id.addGenre);
        plotA = (TextView) findViewById(R.id.addPlot);
        posterA = (ImageView) findViewById(R.id.addImg);
        titleA = (EditText) findViewById(R.id.addTxt);
        add = (Button) findViewById(R.id.addBtn);
        ratingA = (TextView) findViewById(R.id.addRat);
        typeA = (TextView) findViewById(R.id.addType);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        String query = titleA.getText().toString();
        getMovie(query);
        }
        });
        }

private MovieDbHelper getHelper() {
        if (movDb == null) {
        movDb = OpenHelperManager.getHelper(this, MovieDbHelper.class);
        }
        return movDb;
        }

public boolean addMovie(String po, String ti, String ge,String di, String pl, String ra, String ty) {
        try {
final RuntimeExceptionDao<MovieRow, Integer> movieDao = getHelper().getMovieRowRunEDao();

        if (movieDao.create(new MovieRow(ti,ra,ge,di,pl,po,ty)) == 1) {
        got = true;
        Toast.makeText(AddMovie.this, "Addition Successful", Toast.LENGTH_SHORT).show();
        Intent ani = new Intent(AddMovie.this, ViewSuper.class);
        startActivity(ani);
        } else {
        got = false;
        Toast.makeText(AddMovie.this, "Addition Unsuccessful", Toast.LENGTH_SHORT).show();
        }
        } catch (Exception e) {
        e.printStackTrace();
        }
        return got;
        }

public void getMovie(String title) {
        got = false;
        Map<String, String> data = new HashMap<>();
        data.put("t", title);
        data.put("r", "json");
        Call<Movie> call = apiService.getMoT(data);
        call.enqueue(new Callback<Movie>() {
@Override
public void onResponse(Call<Movie> call, Response<Movie> response) {
        if (response.body().getResponse().equals("True")) {
        setTitle(response.body().getTitle());
        plotA.setText(response.body().getPlot());
        //posterText.setText(response.body().getPoster());
        Picasso.with(AddMovie.this)
        .load(response.body().getPoster())
        .placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
        .into(posterA);

        genreA.setText(response.body().getGenre());
        ratingA.setText("IMDBRating: " + response.body().getImdbRating());
        typeA.setText(response.body().getType());
        if (addMovie(response.body().getPoster(), response.body().getTitle(), response.body().getGenre(),
                response.body().getDirector(), response.body().getPlot(), response.body().getImdbRating(),
                response.body().getType())) {
        got = true;
        }
        } else {
        Toast.makeText(AddMovie.this, "Response was false", Toast.LENGTH_SHORT).show();
        Toast.makeText(AddMovie.this, "Error: " + response.body().getError(), Toast.LENGTH_SHORT).show();
        got = false;
        }
        }

@Override
public void onFailure(Call<Movie> call, Throwable t) {
        Toast.makeText(AddMovie.this, "The error is" + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
     });
        }

@Override
protected void onDestroy() {
        super.onDestroy();
    if (movDb != null) {
        OpenHelperManager.releaseHelper();
        movDb = null;
     }
    finish();
 }


}