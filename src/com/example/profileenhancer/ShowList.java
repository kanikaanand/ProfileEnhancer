package com.example.profileenhancer;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ShowList extends ActionBarActivity implements
OnItemClickListener {
	
	Button btnGetJobs;
	private String query = null;
	JSONArray jsonArray = null;
	 JobList  jobList = new JobList();
	ListView listView;
	ListAdapter adapter;
	CareerBuilder careerBuilder =new CareerBuilder();
	Indeed indeed = new Indeed();
	//String jobTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 String src = getIntent().getStringExtra("MyObjectAsString");
		 Gson gS = new Gson();
		jobList = gS.fromJson(src, JobList.class);
	   // Log.i("in list view", jobList.getJobList().get(0).getJobTitle().toString());
		  Log.i("in list view", src);
		
		setContentView(R.layout.list_items);
	     
	     listView = (ListView) findViewById(R.id.listViewJobs);
	     CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.item_view, jobList.getJobList());
	     listView.setAdapter(adapter);
	     listView.setOnItemClickListener((OnItemClickListener) this); 
	    }
		
    	
	 public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
	    	String url = jobList.getJobList().get(position).getUrl();
	    	
	    	Intent i = new Intent(Intent.ACTION_VIEW);
	    	i.setData(Uri.parse(url));
	    	startActivity(i);
	    }
	    


	

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.action_settings) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	}