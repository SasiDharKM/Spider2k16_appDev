package com.appdev.spideradtask2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class FormGet extends AppCompatActivity {
    EditText name,roll;
    private boolean checkState=false,appState=false,tronixState=false,algoState=false,webState=false,nameState=false,rollState=false;
    CheckBox app,tronix,algo,web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_get);
        name=(EditText) findViewById(R.id.name);
        roll=(EditText) findViewById(R.id.roll);
        app =(CheckBox) findViewById(R.id.app);
        tronix =(CheckBox) findViewById(R.id.tronix);
        algo =(CheckBox) findViewById(R.id.algo);
        web =(CheckBox) findViewById(R.id.web);
    }

/**    public void ifChecked(View v){
        boolean state=((CheckBox)v).isChecked();
        switch(v.getId())
        {
            case(R.id.app):
            { if(state)
                    appState=true;
                else
                    appState=false;
                break;}
            case(R.id.tronix):
            {if(state)
                    tronixState=true;
                else if(!state)
                    tronixState=false;
                break;}
            case(R.id.algo):
               {if(state)
                    algoState=false;
                else
                    algoState=state;
                break;}
            case(R.id.web): {
                if (state)
                    webState = state;
                else if (!state)
                    webState = state;
            }


        }

        if(appState||tronixState||algoState||webState)
              checkState=true;


        }*/

    public void ifAppChecked(View v){
        boolean state=((CheckBox)v).isChecked();
        if(state)
            appState=true;
        else
            appState=false;

    }
    public void ifTronixChecked(View v){
        boolean state=((CheckBox)v).isChecked();
        if(state)
            tronixState=true;
        else
            tronixState=false;
    }
    public void ifAlgoChecked(View v){
        boolean state=((CheckBox)v).isChecked();
        if(state)
            algoState=true;
        else
            algoState=false;
    }
    public void ifWebChecked(View v){
        boolean state=((CheckBox)v).isChecked();
        if(state)
            webState=true;
        else
            webState=false;
    }



    public void submitForm(View v){
        String getName = name.getText().toString();
        String getRoll = roll.getText().toString();
        if (getName.matches(""))
            nameState=false;
        else
            nameState=true;

        if (getRoll.matches(""))
           rollState=false;
        else
            rollState=true;

        if(appState||tronixState||algoState||webState)
            checkState=true;


        if(checkState&&nameState&&rollState)
        {
            Intent intent= new Intent(this,FormFilled.class);
            intent.putExtra(FormFilled.theName,getName);
            startActivity(intent);
        }
        else if(checkState&&nameState&&!rollState)
            Toast.makeText(getApplicationContext(),"The Roll no. field is blank",Toast.LENGTH_SHORT).show();
        else if(checkState&&!nameState&&rollState)
            Toast.makeText(getApplicationContext(),"The Name field is blank",Toast.LENGTH_SHORT).show();
        else if(!checkState&&nameState&&rollState)
            Toast.makeText(getApplicationContext(),"no profile is selected",Toast.LENGTH_SHORT).show();
        else if(checkState&&!nameState&&!rollState)
            Toast.makeText(getApplicationContext(),"The Name and Roll no. field is blank",Toast.LENGTH_SHORT).show();
        else if(!checkState&&nameState&&!rollState)
            Toast.makeText(getApplicationContext(),"No Profile is selected and the Roll no is blank",Toast.LENGTH_SHORT).show();
        else if(!checkState&&!nameState&&rollState)
            Toast.makeText(getApplicationContext(),"No Profile is selected and the Name is blank",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"No Profile is selected and the Roll no and Name is blank",Toast.LENGTH_LONG).show();

    }
}
