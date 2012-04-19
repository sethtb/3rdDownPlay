package com.thirdDownPlay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FindOthers extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findothers);
        
        Button cancel = (Button) findViewById(R.id.findothers_cancel);
        cancel.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
	        	Intent intent = new Intent();
	        	setResult(RESULT_OK, intent);
	        	finish();
        	}
        });
    }
}