package com.thirdDownPlay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ThirdDownPlay extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

    	Button makeMeAvailable = (Button) findViewById(R.id.makemeavailable);
        makeMeAvailable.setOnClickListener(new OnClickListener () {
        	public void onClick(View view) {
        		Intent myIntent = new Intent(view.getContext(), MakeMeAvailable.class);
        		startActivityForResult(myIntent, 0);
        	}
        });
        
        Button findOthers = (Button) findViewById(R.id.findpeople);
        findOthers.setOnClickListener(new OnClickListener () {
        	public void onClick(View view) {
        		Intent myIntent = new Intent(view.getContext(), FindOthers.class);
        		startActivityForResult(myIntent, 0);
        	}
        });
    }
}