package com.appdev.spideradtask2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;

public class FormFilled extends AppCompatActivity {
    public static final String theName="";
    public String nameIt;
    TextView thank,timeStamp;
    String stamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_filled);

        Intent intent=getIntent();
        nameIt=intent.getStringExtra(theName);
        timeStamp =(TextView)findViewById(R.id.stamp);
        thank=(TextView)findViewById(R.id.thanks);
        stamp =getTimeStamp();

        thank.setText("Thank you "+nameIt+" for your response");
        timeStamp.setText("This Response Recorded time is "+stamp);
    }
    public static String getTimeStamp() {
        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%Y_%m_%d_%H_%M_%S");
        return sTime;
    }
    public void goBack(View v)
    {
        onBackPressed();

    }

}
