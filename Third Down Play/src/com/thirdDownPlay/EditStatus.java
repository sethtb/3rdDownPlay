package com.thirdDownPlay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
public class EditStatus extends Activity{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_status);
        Intent data = this.getIntent();
        
        SharedPreferences settings = getPreferences(0);
		if (!settings.contains("player_name")){
			SharedPreferences.Editor editor = settings.edit();
    		editor.putString("player_name",data.getStringExtra("player_name"));
    		editor.commit();
		}
		else{
			if(!settings.getString("player_name", "false").equals(data.getStringExtra("player_name"))){
				SharedPreferences.Editor editor = settings.edit();
	    		editor.putString("player_name",data.getStringExtra("player_name"));
	    		editor.commit();
			}
		}
		setupPosts(settings);
    }
    
    private void setupPosts(SharedPreferences settings){
    	 String result = "";
         try{
 			HttpClient httpclient = new DefaultHttpClient();
 			HttpPost httppost = new HttpPost("http://ibrahima.scripts.mit.edu/scripts/grabme813.php");
 			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 			nameValuePairs.add(new BasicNameValuePair("name",settings.getString("player_name", null)));
 			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 			HttpResponse response = httpclient.execute(httppost);
 			HttpEntity entity = response.getEntity();
 			InputStream is = entity.getContent();
 			
 			try{
 	        	BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
 	        	StringBuilder sb = new StringBuilder();
 	        	String line = null;
 	        	while ((line = reader.readLine()) != null){       
 	        		sb.append(line + "\n");
 	        	}
 	        	is.close();
 	        	result = sb.toString();
 			}
 	        catch(Exception e){
 	        	Log.e("log_tag","Error converting result "+e.toString());
 	        }
 			try{
 				JSONArray jArray = new JSONArray(result);
 				LinearLayout pView = (LinearLayout) findViewById(R.id.postView);
 				for (int i=0;i<jArray.length();i++){
 					JSONObject json_data = jArray.getJSONObject(i);
 					//Log.i("log_tag","name: "+json_data.getString("Name"));
 					TableRow row = new TableRow(this);
 					
 					TextView classNo = new TextView(this);
 					classNo.setText(json_data.getString("Class"));
 					classNo.setId(6);
 					classNo.setPadding(3,3,3,3);
 					
 					TextView loc = new TextView(this);
 					loc.setText(json_data.getString("Location"));
 					loc.setId(7);
 					loc.setPadding(3,3,3,3);
 					
 					Button edit = new Button(this);
 					edit.setText("Edit");
 					edit.setId(8);
 					edit.setPadding(3,3,3,3);
 					
 					row.addView(classNo);
 					row.addView(loc);
 					row.addView(edit);
 					pView.addView(row);
 					
 				}
 			}
 			catch(JSONException e){
 				Log.e("log_tag", "Error converting result "+e.toString());
 			}
 		}
 		catch(Exception e){
 			Log.e("log_tag","Error in http connection "+e.toString());
 		}
         
    }
    public void update_location(View button){
    	final EditText locField = (EditText) findViewById(R.id.editText1);
    	String loc = locField.getText().toString();
    	
    	if (loc.length()==0){
    		locField.setHintTextColor(Color.RED);
    	}
    	else{
    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		SharedPreferences settings = getPreferences(0);
    		Log.i("log_tag",settings.getString("player_name", "false"));
    		nameValuePairs.add(new BasicNameValuePair("name",settings.getString("player_name", null)));
    		nameValuePairs.add(new BasicNameValuePair("loc",loc));
    		
    		try{
    			HttpClient httpclient = new DefaultHttpClient();
    			HttpPost httppost = new HttpPost("http://ibrahima.scripts.mit.edu/scripts/changeLoc813.php");
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
    		
    		Toast.makeText(this, "Your location is now "+loc, Toast.LENGTH_LONG).show(); 
    	}
    	
    }
    
    public void go_offline(View button){
    	
    	SharedPreferences settings = getPreferences(0);
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
		
		Toast.makeText(this, "Your are not available", Toast.LENGTH_LONG).show();  
		Intent intent = new Intent();
		setResult(RESULT_OK,intent);
		finish();
    }
}
