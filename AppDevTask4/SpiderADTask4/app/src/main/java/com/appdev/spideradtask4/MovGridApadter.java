package com.appdev.spideradtask4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdev.spideradtask4.Model.MovieDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SasiDKM on 02-07-2016.
 */
public class MovGridApadter extends BaseAdapter {
    Context context;
    ArrayList<MovieDetail> movieList;

    public MovGridApadter(Context context,String[] t,String[]r,String[]g,String[]d,String[]pl,String[]po,String[]ty) {
        this.context = context;
        int i=0;
        while(i<t.length){
            movieList.add(new MovieDetail(t[i],r[i],g[i],d[i],pl[i],po[i],ty[i]));
            i++;
        }
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class sinHold{

        ImageView posterI;
        TextView titleI,descI,genreI,ratI;
        sinHold(View v){
            posterI=(ImageView)v.findViewById(R.id.movPoster);
            titleI=(TextView)v.findViewById(R.id.movTitle);
            descI=(TextView)v.findViewById(R.id.movDesc);
            genreI=(TextView)v.findViewById(R.id.movGenre);
            ratI=(TextView)v.findViewById(R.id.movRating);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        sinHold viewHolder=null;
        View movRow= convertView;
        if(movRow==null){
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            movRow= layoutInflater.inflate(R.layout.single_row,parent,false);
            viewHolder=new sinHold(movRow);
            movRow.setTag(viewHolder);
        }
        else{
            viewHolder= (sinHold) movRow.getTag();
        }

        MovieDetail movDe= movieList.get(position);
        Picasso.with(context)
                .load(movDe.getPosterD())
                .placeholder(R.mipmap.ic_launcher)
                .resize(130,130)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.posterI);

        viewHolder.posterI.setTag(movDe);
        viewHolder.titleI.setText(movDe.getTitleD());
        viewHolder.descI.setText(movDe.getPlotD());
        viewHolder.genreI.setText(movDe.getGenreD());
        viewHolder.ratI.setText(movDe.getRatingD());

        return movRow;
    }
}
