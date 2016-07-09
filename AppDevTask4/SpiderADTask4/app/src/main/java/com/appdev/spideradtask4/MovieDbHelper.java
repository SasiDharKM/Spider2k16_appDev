package com.appdev.spideradtask4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.appdev.spideradtask4.Model.MovieRow;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by SasiDKM on 02-07-2016.
 */
public class MovieDbHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME="movies.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<MovieRow,Integer> movieRowDao =null;
    private RuntimeExceptionDao<MovieRow,Integer> movieRowRunEDao=null;

    public MovieDbHelper(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    public Dao<MovieRow, Integer> getMovieRowDao() throws SQLException{

        if(movieRowDao==null){
            movieRowDao=getDao(MovieRow.class);
        }
        return movieRowDao;
    }

    public RuntimeExceptionDao<MovieRow, Integer> getMovieRowRunEDao() throws SQLException {

        if(movieRowRunEDao==null){
            movieRowRunEDao=getRuntimeExceptionDao(MovieRow.class);
        }
        return movieRowRunEDao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource,MovieRow.class);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try{
            TableUtils.dropTable(connectionSource,MovieRow.class,true);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
