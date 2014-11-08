package com.example.profileenhancer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FindJobs {
	public String searchJobs(String query){
		Log.i("Result of API","print here1");
		HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        JSONObject jsonObj = null ;
        Log.i("Result of API","print here2");
        Log.i("Result of API",httpclient.toString());
        try {
        	Log.i("Result of API","print here3");
        	Log.i("Result of API",query.toString());
            response = httpclient.execute(new HttpGet(query.toString()));
            Log.i("Result of API","print here4");
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //do something with the response
                responseString = EntityUtils.toString(entity);
            }
            else 
            	Log.i("Result of API","print here5");
           
        }
        catch(NullPointerException e)
        {
        	Log.i("Result of API","null pointer exception");
        }
//        catch(JSONException e)
//        {
//        	
//        }
        catch (ClientProtocolException e) {
            Log.e("ERROR", e.getMessage());
        	} catch (IOException e) {
            Log.e("ERROR", e.getMessage());
        }
        return responseString;
	}
	
}

