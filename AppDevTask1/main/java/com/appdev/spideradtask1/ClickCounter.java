package com.appdev.spideradtask1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClickCounter extends Activity {

    private int count=0;
    TextView Counted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_counter);

        Counted= (TextView) findViewById(R.id.count);

        if(savedInstanceState!=null) {
            count = savedInstanceState.getInt("count");
        }
        String strCount=String.valueOf(count);
        Counted.setText(strCount);
    }

    //A function that is called before the activity is destroyed
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("count",count);
    }
    //A function that is called when the Start Button is clicked
    public void CounterColorChange(View v){

        count=count+1;
        String strCount= String.valueOf(count);
        Counted.setText(strCount);

    }

    //A function to reset the counter
    public void CounterReset(View v){

        count=0;
        String strCount= String.valueOf(count);
        Counted.setText(strCount);
    }

}
