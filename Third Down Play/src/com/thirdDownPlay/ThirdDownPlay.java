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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        		startActivityForResult(myIntent,5);
        	}
        });
        
        Button findOthers = (Button) findViewById(R.id.findpeople);
        findOthers.setOnClickListener(new OnClickListener () {
        	public void onClick(View view) {
        		Intent myIntent = new Intent(view.getContext(), FindOthers.class);
        		startActivityForResult(myIntent, 0);
        	}
        });
        
        Button editStatus = (Button) findViewById(R.id.vieweditstatus);
        editStatus.setOnClickListener(new OnClickListener () {
        	public void onClick(View view) {
        		Intent myIntent = new Intent(view.getContext(), EditStatus.class);
        		startActivityForResult(myIntent, 4);
        	}
        });
        
        //Check if a previous attempt to remove failed. Remove the name if it did
        SharedPreferences settings = getPreferences(0);
        if (settings.contains("error_removing")){
        	if (settings.getBoolean("error_removing", false)){
        		SharedPreferences.Editor editor = settings.edit();
        		editor.putBoolean("error_removing",false);
        		editor.commit();
        		
        		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    		nameValuePairs.add(new BasicNameValuePair("name",settings.getString("player_name",null)));
	    		try{
	    			HttpClient httpclient = new DefaultHttpClient();
	    			HttpPost httppost = new HttpPost("http://ibrahima.scripts.mit.edu/scripts/remove813.php");
	    			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    			HttpResponse response = httpclient.execute(httppost);
	    			HttpEntity entity = response.getEntity();
	    		}
	    		catch(Exception e){
	    			Log.e("log_tag","Error in http connection "+e.toString());
	    			editor.putBoolean("error_removing", true);
	    			editor.commit();
	    		}
        	}
    		
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode==Activity.RESULT_OK && requestCode ==5){
    		TextView status_bar = (TextView) findViewById(R.id.textView1);
    		status_bar.setText(R.string.online);
    		status_bar.setBackgroundColor(Color.GREEN);
    		SharedPreferences settings = getPreferences(0);
    		SharedPreferences.Editor editor = settings.edit();
    		editor.putString("status","online");
    		editor.putString("player_name",data.getStringExtra("player_name"));
    		editor.commit();
    		
    	}
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	SharedPreferences settings = getPreferences(0);
		if (settings.contains("status")){
		   	String status = settings.getString("status", null);
		   	// If online, remove person from database, set offline
		   	if (status == "online"){
		   		SharedPreferences.Editor editor = settings.edit();
	    		editor.putString("status","offline");
	    		editor.commit();
	    		
	    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    		nameValuePairs.add(new BasicNameValuePair("name",settings.getString("player_name",null)));
	    		try{
	    			HttpClient httpclient = new DefaultHttpClient();
	    			HttpPost httppost = new HttpPost("http://ibrahima.scripts.mit.edu/scripts/remove813.php");
	    			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    			HttpResponse response = httpclient.execute(httppost);
	    			HttpEntity entity = response.getEntity();
	    		}
	    		catch(Exception e){
	    			Log.e("log_tag","Error in http connection "+e.toString());
	    			editor.putBoolean("error_removing", true);
	    			editor.commit();
	    		}
		   	}
		}
		
    }
}