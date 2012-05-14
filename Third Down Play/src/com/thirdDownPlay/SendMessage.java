package com.thirdDownPlay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SendMessage extends Activity{

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendmessage);
        Intent data = this.getIntent();
        
        String from_name = data.getStringExtra("player_name");
        String to_name = data.getStringExtra("other_name");
        
        TextView to = (TextView) findViewById(R.id.toPerson);
        TextView from = (TextView) findViewById(R.id.fromPerson);
        EditText box = (EditText) findViewById(R.id.messageBox);
        to.setText(to_name);
        from.setText(from_name);
        box.setText("Would you like to work together on a pset?");
	}
	
	public void submit(View button){
		Toast.makeText(this, "Your message has been sent", Toast.LENGTH_LONG).show();  
		Intent intent = new Intent();
		setResult(RESULT_OK,intent);
		finish();
	}
}
