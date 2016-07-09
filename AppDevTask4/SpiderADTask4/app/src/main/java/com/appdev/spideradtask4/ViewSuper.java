package com.appdev.spideradtask4;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.appdev.spideradtask4.Model.MovieRow;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class ViewSuper extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    MovieDbHelper movDb = null;

    private static AutoCompleteTextView actv;
    ArrayAdapter adapter;
    EditText reactHere;
    boolean searched = false;
    private Subscription subscription;
    String[] just;

    List<String> titles = new ArrayList<>();
    String myTitle = "MOVIES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_super);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(myTitle);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        initializeAdapter();
        just = titles.toArray(new String[0]);

        actv = (AutoCompleteTextView) findViewById(R.id.rxAutoTxt);
        actv.setVisibility(View.VISIBLE);
        adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, just);
        actv.setThreshold(1);
        actv.setAdapter(adapter);

        reactHere = (EditText) findViewById(R.id.rxTxt);
        reactHere.setVisibility(View.INVISIBLE);

        subscription = RxTextView.textChangeEvents(reactHere)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(new Func1<TextViewTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        if (reactHere.getText().toString().equals("")) return false;
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSearchObserver());
        actv.addTextChangedListener(new TextWatcher() {
            String beffore;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Do nothing
                beffore = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(beffore.equals(s.toString()))) {
                    reactHere.setText(s.toString());
                    reactHere.setSelection(reactHere.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Do nothing
            }
        });
    }

    private MovieDbHelper getHelper() {
        if (movDb == null) {
            movDb = OpenHelperManager.getHelper(this, MovieDbHelper.class);
        }
        return movDb;
    }

    public void initializeAdapter() {
        try {
            final RuntimeExceptionDao<MovieRow, Integer> moviesDao = getHelper().getMovieRowRunEDao();
            List<MovieRow> movies = moviesDao.queryForAll();
            int i = 0;
            while (i < movies.size()) {
                titles.add(movies.get(i).getTitle());
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<MovieRow> getSingleMovie(String ti) {
        List<MovieRow> movie = new ArrayList<MovieRow>();
        try {
            final RuntimeExceptionDao<MovieRow, Integer> moviesDao = getHelper().getMovieRowRunEDao();
            movie = moviesDao.queryForEq("title", ti);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    private Observer<TextViewTextChangeEvent> getSearchObserver() {
        return new Observer<TextViewTextChangeEvent>() {

            @Override
            public void onCompleted() {
                Toast.makeText(ViewSuper.this, "Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(ViewSuper.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                if (!(actv.getText().toString().equals(textViewTextChangeEvent.text().toString()))) {
                    actv.setText(textViewTextChangeEvent.text().toString());
                    actv.showDropDown();
                }
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_super, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.Search:
                if (!searched) {
                    searched = true;
                    reactHere.setVisibility(View.VISIBLE);
                } else {
                    if (reactHere.getText().toString().equals("")) {
                        searched = false;
                        Toast.makeText(ViewSuper.this, "No Prompt entered to search", Toast.LENGTH_SHORT).show();
                        reactHere.setVisibility(View.INVISIBLE);
                    } else {
                        //Search
                        searched = false;
                        String query = reactHere.getText().toString();
                        Toast.makeText(ViewSuper.this, "Searching for " + query, Toast.LENGTH_SHORT).show();
                        List<MovieRow> movi = getSingleMovie(query);
                        if (movi.size() == 0) {
                            Toast.makeText(ViewSuper.this, "Movie " + query + " does not exist in the database", Toast.LENGTH_SHORT).show();
                        } else {
                            String tit = movi.get(0).getTitle();
                            String pl = movi.get(0).getPlot();
                            String po = movi.get(0).getPoster();
                            String ge = movi.get(0).getGenre();
                            String dir=movi.get(0).getDirector();
                            String ty = movi.get(0).getType();
                            String ra = movi.get(0).getRating();

                            Intent i = new Intent(ViewSuper.this, MovieUno.class);
                            i.putExtra("iTit", tit);
                            i.putExtra("iPlo", pl);
                            i.putExtra("iPos", po);
                            i.putExtra("iGen", ge);
                            i.putExtra("iRat", ra);
                            i.putExtra("iTyp", ty);
                            i.putExtra("iDir",dir);

                            startActivity(i);
                        }
                    }
                }
                break;
            case R.id.AddMovMenu:
                Intent addMov = new Intent(ViewSuper.this, AddMovie.class);
                startActivity(addMov);
                break;
            case R.id.Sort:
                break;
            default:
                Toast.makeText(ViewSuper.this, "You made the wrong choice", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    setTitle("MOVIES");
                    myTitle = "MOVIES";
                   // getSupportActionBar().setTitle(myTitle);
                    return MovieFrag.newInstance(ViewSuper.this);
                case 1:
                    setTitle("SERIES");
                    myTitle = "SERIES";
                   // getSupportActionBar().setTitle(myTitle);
                    return SeriesFrag.newInstance(ViewSuper.this);
                default:
                    Toast.makeText(ViewSuper.this, "No Such Fragment", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MOVIES";
                case 1:
                    return "SERIES";
            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movDb!= null) {
            OpenHelperManager.releaseHelper();
            movDb = null;
        }
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
