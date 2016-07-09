package com.appdev.spideradtask4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.appdev.spideradtask4.Model.MovieDetail;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.appdev.spideradtask4.Model.MovieRow;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;


import java.util.ArrayList;
import java.util.List;


public class MovieFrag extends Fragment {
    private static Context context;

    String[] titGrd;
    String[] ratGrd;
    String[] genGrd;
    String[] ploGrd;
    String[] dirGrd;
    String[] posGrd;
    String[] typGrd;
    GridView movGrd;
    MovieDbHelper movDb = null;
    List<String> titleF = new ArrayList<>();
    List<String> ratingF = new ArrayList<>();
    List<String> genreF = new ArrayList<>();
    List<String> plotF = new ArrayList<>();
    List<String> directorF = new ArrayList<>();
    List<String> posterF = new ArrayList<>();
    List<String> typeF = new ArrayList<>();


    public MovieFrag() {
        // Required empty public constructor
    }

    public static MovieFrag newInstance(Context ctx) {
        MovieFrag fragment = new MovieFrag();
        context = ctx;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View grdView = inflater.inflate(R.layout.fragment_movie, container, false);
        movGrd = (GridView) grdView.findViewById(R.id.movieGrid);
        getMovieList();

        setHasOptionsMenu(true);

        titGrd = titleF.toArray(new String[0]);
        posGrd = posterF.toArray(new String[0]);
        typGrd = typeF.toArray(new String[0]);
        dirGrd = directorF.toArray(new String[0]);
        ratGrd = ratingF.toArray(new String[0]);
        ploGrd = plotF.toArray(new String[0]);
        genGrd = genreF.toArray(new String[0]);

        movGrd.setAdapter(new MovGridApadter(context, titGrd, ratGrd, genGrd, dirGrd, ploGrd, posGrd, typGrd));
        movGrd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, MovieUno.class);
                MovGridApadter.sinHold sinHolder = (MovGridApadter.sinHold) view.getTag();
                MovieDetail temp = (MovieDetail) sinHolder.posterI.getTag();

                intent.putExtra("iGen", temp.getGenreD());
                intent.putExtra("iDir", temp.getDirectorD());
                intent.putExtra("iPlo", temp.getPlotD());
                intent.putExtra("iPos", temp.getPosterD());
                intent.putExtra("iRat", temp.getRatingD());
                intent.putExtra("iTit", temp.getTitleD());
                intent.putExtra("iTyp", temp.getTypeD());
                startActivity(intent);
            }
        });


        return grdView;
    }


    public void getMovieList() {

        try {
            final RuntimeExceptionDao<MovieRow, Integer> movieListDao = getHelper(context).getMovieRowRunEDao();
            List<MovieRow> movieList = movieListDao.queryForEq("type", "movie");
            int i = 0;
            while (i < movieList.size()) {
                titleF.add(movieList.get(i).getTitle());
                ratingF.add(movieList.get(i).getRating());
                genreF.add(movieList.get(i).getGenre());
                posterF.add(movieList.get(i).getPoster());
                plotF.add(movieList.get(i).getPlot());
                directorF.add(movieList.get(i).getDirector());
                typeF.add(movieList.get(i).getType());
                i++;
            }

            if (movDb != null) {
                OpenHelperManager.releaseHelper();
                movDb = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MovieDbHelper getHelper(Context c) {
        if (movDb == null) {
            movDb = OpenHelperManager.getHelper(c, MovieDbHelper.class);
        }
        return movDb;
    }

    public void getRatingSorted() {
        try {
            QueryBuilder<MovieRow, Integer> qB = getHelper(context).getMovieRowRunEDao().queryBuilder();
            qB.where().eq("type", "movie");
            qB.orderBy("rating", false);
            List<MovieRow> movieList = qB.query();
            titleF.clear();
            plotF.clear();
            posterF.clear();
            typeF.clear();
            ratingF.clear();
            directorF.clear();
            genreF.clear();

            int i = 0;
            while (i < movieList.size()) {
                titleF.add(movieList.get(i).getTitle());
                ratingF.add(movieList.get(i).getRating());
                genreF.add(movieList.get(i).getGenre());
                posterF.add(movieList.get(i).getPoster());
                plotF.add(movieList.get(i).getPlot());
                directorF.add(movieList.get(i).getDirector());
                typeF.add(movieList.get(i).getType());
                i++;
            }

            titGrd = titleF.toArray(new String[0]);
            posGrd = posterF.toArray(new String[0]);
            typGrd = typeF.toArray(new String[0]);
            dirGrd = directorF.toArray(new String[0]);
            ratGrd = ratingF.toArray(new String[0]);
            ploGrd = plotF.toArray(new String[0]);
            genGrd = genreF.toArray(new String[0]);

            movGrd.setAdapter(new MovGridApadter(context, titGrd, ratGrd, genGrd, dirGrd, ploGrd, posGrd, typGrd));
            if (movDb != null) {
                OpenHelperManager.releaseHelper();
                movDb = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.Sort)
        {
            getRatingSorted();
        }
        return super.onOptionsItemSelected(item);
    }
}



