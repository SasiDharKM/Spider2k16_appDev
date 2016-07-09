package com.appdev.spideradtask4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MovieSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.AddMovMenu:
                Intent addM = new Intent(MovieSearch.this, AddMovie.class);
                startActivity(addM);
                break;
            case R.id.ViewAll:
                Intent viewAll = new Intent(MovieSearch.this, ViewSuper.class);
                startActivity(viewAll);
                break;
            default:
                Toast.makeText(MovieSearch.this, "Hmm", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
