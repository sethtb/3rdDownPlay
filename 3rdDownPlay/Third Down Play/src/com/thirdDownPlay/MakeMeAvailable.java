package com.thirdDownPlay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MakeMeAvailable extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makemeavailable);
        
        Button cancel = (Button) findViewById(R.id.button1);
        cancel.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
	        	Intent intent = new Intent();
	        	setResult(RESULT_OK, intent);
	        	finish();
        	}
        });
        
        Button submit = (Button) findViewById(R.id.button2);
        submit.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
	        	Intent intent = new Intent();
	        	setResult(RESULT_OK, intent);
	        	finish();
        	}
        });
    }
}