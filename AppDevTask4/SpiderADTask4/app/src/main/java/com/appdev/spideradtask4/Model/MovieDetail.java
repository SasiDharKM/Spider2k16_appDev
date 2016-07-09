package com.appdev.spideradtask4.Model;

/**
 * Created by SasiDKM on 02-07-2016.
 */
public class MovieDetail {
    String titleD;
    String ratingD;
    String genreD;
    String directorD;
    String plotD;
    String posterD;
    String typeD;

    public MovieDetail(String titleD, String ratingD, String genreD, String directorD, String plotD, String posterD, String typeD) {
        this.titleD = titleD;
        this.ratingD = ratingD;
        this.genreD = genreD;
        this.directorD = directorD;
        this.plotD = plotD;
        this.posterD = posterD;
        this.typeD = typeD;
    }

    public String getTitleD() {
        return titleD;
    }

    public void setTitleD(String titleD) {
        this.titleD = titleD;
    }

    public String getRatingD() {
        return ratingD;
    }

    public void setRatingD(String ratingD) {
        this.ratingD = ratingD;
    }

    public String getGenreD() {
        return genreD;
    }

    public void setGenreD(String genreD) {
        this.genreD = genreD;
    }

    public String getDirectorD() {
        return directorD;
    }

    public void setDirectorD(String directorD) {
        this.directorD = directorD;
    }

    public String getPlotD() {
        return plotD;
    }

    public void setPlotD(String plotD) {
        this.plotD = plotD;
    }

    public String getPosterD() {
        return posterD;
    }

    public void setPosterD(String posterD) {
        this.posterD = posterD;
    }

    public String getTypeD() {
        return typeD;
    }

    public void setTypeD(String typeD) {
        this.typeD = typeD;
    }
}
