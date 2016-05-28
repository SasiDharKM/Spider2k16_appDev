package com.appdev.spideradtask1extended;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class imageClick extends AppCompatActivity {
    TextView Long,Normal;
    private int counter = 0;
    private int long_counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_click);

        if(savedInstanceState!=null) {
            counter = savedInstanceState.getInt("counter");
            long_counter = savedInstanceState.getInt("long_counter");
        }

        Long=(TextView)findViewById(R.id.long_count);
        Normal=(TextView)findViewById(R.id.norm_count);
        String LongCounterLog = String.valueOf(long_counter);
        Long.setText(LongCounterLog);
        String CounterLog=String.valueOf(counter);
        Normal.setText(CounterLog);

        ImageView click = (ImageView) findViewById(R.id.click_button);
        if (click != null) {
            click.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    long_counter++;

                    String LongCounterLog = String.valueOf(long_counter);
                    Long.setText(LongCounterLog);
                    return true;
                }

            });
        }

        if (click != null) {
            click.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    counter++;
                    String CounterLog=String.valueOf(counter);
                    Normal.setText(CounterLog);
                }
            });
        }


    }
    public void onSaveInstanceState(Bundle savedInsState)
    {
        savedInsState.putInt("counter",counter);
        savedInsState.putInt("long_counter",long_counter);
    }

}
