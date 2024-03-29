package com.thirdDownPlay;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MakeMeAvailable extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makemeavailable);
        
        SharedPreferences settings = getPreferences(0);
		if (settings.contains("player_name")){
			final EditText nameField = (EditText) findViewById(R.id.editText1);
        	nameField.setText(settings.getString("player_name", null));
		}
        
        
    }
    
    public void sendMyInfo(View button){
    	final EditText nameField = (EditText) findViewById(R.id.editText1);
    	String name = nameField.getText().toString();

    	final EditText classField = (EditText) findViewById(R.id.autoCompleteTextView1);
    	String classNo = classField.getText().toString();

    	final EditText psetInfoField = (EditText) findViewById(R.id.editText3);
    	String psetInfo = psetInfoField.getText().toString();
    	
    	final Spinner groupSizeField = (Spinner) findViewById(R.id.spinner1);
    	String groupSize = groupSizeField.getSelectedItem().toString();
    	
    	final EditText locField = (EditText) findViewById(R.id.editText4);
    	String loc = locField.getText().toString();
    	
    	
    	
    	if (name.length()==0 && classNo.length()==0 && loc.length()==0){
    		nameField.setHintTextColor(Color.RED);
    		classField.setHintTextColor(Color.RED);
    		locField.setHintTextColor(Color.RED);
    	}
    	else if (loc.length()==0 && classNo.length()==0){
    		locField.setHintTextColor(Color.RED);
    		classField.setHintTextColor(Color.RED);
    	}
    	else if (name.length()==0 && loc.length()==0){
    		nameField.setHintTextColor(Color.RED);
    		locField.setHintTextColor(Color.RED);
    	}
    	else if (name.length()==0 && classNo.length()==0){
    		nameField.setHintTextColor(Color.RED);
    		classField.setHintTextColor(Color.RED);
    	}
    	else if (name.length() == 0){
    		nameField.setHintTextColor(Color.RED);
    	}
    	else if (classNo.length()==0){
    		classField.setHintTextColor(Color.RED);
    	}
    	else if (loc.length()==0){
    		locField.setHintTextColor(Color.RED);
    	}
    	else{
    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		nameValuePairs.add(new BasicNameValuePair("name",name));
    		nameValuePairs.add(new BasicNameValuePair("class",classNo));
    		nameValuePairs.add(new BasicNameValuePair("pset",psetInfo));
    		nameValuePairs.add(new BasicNameValuePair("group",groupSize));
    		nameValuePairs.add(new BasicNameValuePair("location",loc));
    		
    		try{
    			HttpClient httpclient = new DefaultHttpClient();
    			HttpPost httppost = new HttpPost("http://ibrahima.scripts.mit.edu/scripts/add813.php");
    			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    			HttpResponse response = httpclient.execute(httppost);
    			HttpEntity entity = response.getEntity();
    		}
    		catch(Exception e){
    			Log.e("log_tag","Error in http connection "+e.toString());
    			Toast.makeText(this, "Could not connect to Database", Toast.LENGTH_LONG);
    			Intent intent = new Intent();
    			setResult(RESULT_CANCELED,intent);
    			finish();
    		}
    		
    		SharedPreferences settings = getPreferences(0);
    		if (!settings.contains("player_name")){
    			SharedPreferences.Editor editor = settings.edit();
        		editor.putString("player_name",name);
        		editor.commit();
    		}
    		
    		Toast.makeText(this, "Your are now available", Toast.LENGTH_LONG).show();  
    		Intent intent = new Intent();
    		intent.putExtra("player_name", name);
    		setResult(RESULT_OK,intent);
    		finish();
    	}
    	
    	

    }
}