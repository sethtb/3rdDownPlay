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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FindOthers extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findothers);
        
        String result = "";
        try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://ibrahima.scripts.mit.edu/scripts/grab813.php");
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
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
				LinearLayout pView = (LinearLayout) findViewById(R.id.peopleView);
				for (int i=0;i<jArray.length();i++){
					JSONObject json_data = jArray.getJSONObject(i);
					//Log.i("log_tag","name: "+json_data.getString("Name"));
					TableRow row = new TableRow(this);
					
					TextView name = new TextView(this);
					name.setText(json_data.getString("Name"));
					name.setId(5);
					name.setPadding(3,3,3,3);
					
					TextView classNo = new TextView(this);
					classNo.setText(json_data.getString("Class"));
					classNo.setId(6);
					classNo.setPadding(3,3,3,3);
					
					TextView loc = new TextView(this);
					loc.setText(json_data.getString("Location"));
					loc.setId(7);
					loc.setPadding(3,3,3,3);
					
					row.addView(name);
					row.addView(classNo);
					row.addView(loc);
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
        
        
       /* Button cancel = (Button) findViewById(R.id.findothers_cancel);
        cancel.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
	        	Intent intent = new Intent();
	        	setResult(RESULT_OK, intent);
	        	finish();
        	}
        });*/
    }
}