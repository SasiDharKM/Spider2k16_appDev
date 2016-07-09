package com.appdev.spideradtask4.Model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by SasiDKM on 02-07-2016.
 */
public class MovieRow {
    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(canBeNull = false,unique = true)
    String title;

    @DatabaseField(canBeNull = false)
    String rating;

    @DatabaseField(canBeNull = false)
    String genre;

    @DatabaseField(canBeNull = false)
    String director;

    @DatabaseField(canBeNull = false)
    String plot;

    @DatabaseField(canBeNull = false)
    String poster;

    @DatabaseField(canBeNull = false)
    String type;

    public MovieRow( String title, String rating, String genre, String director, String plot, String poster, String type) {
        this.title = title;
        this.rating = rating;
        this.genre = genre;
        this.director = director;
        this.plot = plot;
        this.poster = poster;
        this.type = type;
    }

    @Override
    public String toString() {
        return "MovieRow{" +
                "id=" + id +
                ", title=" + title +
                ", rating=" + rating +
                ", genre=" + genre +
                ", director=" + director +
                ", plot=" + plot  +
                ", poster=" + poster +
                ", type=" + type  +
                "}";
    }

    public MovieRow() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
