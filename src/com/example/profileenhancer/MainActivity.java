package com.example.profileenhancer;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ActionBarActivity {
	
	Button btnGetJobs;
	private StringBuilder query;
	JSONArray jsonArray = null;
	ArrayList<HashMap<String, String>> jobList = new ArrayList<HashMap<String, String>>();
	ListView listView;
	ListAdapter adapter;
	CareerBuilder careerBuilder;
	String jobTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		query = new StringBuilder();
		EditText title = (EditText)findViewById(R.id.jobtitle);
    	jobTitle = title.getText().toString();
    	
		
	}
	public void ViewJobs(View v)
	{
		
		new LoadJobs().execute();
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
	 class LoadJobs extends AsyncTask<String, String, String>{
	    	String returnString;
	    	XMLParser parser;
	    	static final String KEY_ITEM = "item";
	    	LoadJobs()
	    	{
	    		
	    	}
	    	LoadJobs(String type)
	    	{
	    		
	    	}
	    	protected String doInBackground(String... params) {
	    		Log.i("Result of API","Start of doInbackground");
	
	    		careerBuilder = new CareerBuilder();
	    		careerBuilder.setQuery(jobTitle);
	    		
	    		Log.i("Result of API",query.toString());
	            returnString = careerBuilder.searchJobs(query.toString());
	    		return returnString;
	    	}
	    	protected void onPostExecute(String xml) {
	    		 Log.i("Result of API","start of onPostexecute list implemented");
	    		 jobList.clear();
	    		 parser = new XMLParser();
	    		 Document doc = parser.getDomElement(xml); // getting DOM element
	    		 
	    		 NodeList list = doc.getElementsByTagName("JobSearchResult");
	    	
	    		 Log.i("Result of API","list.getLength()="+list.getLength());
	    		// looping through all item nodes <JobSearchResult>
	    		 for (int i = 0; i < list.getLength(); i++) {
	    			 Node jobSearchNode = list.item(i);
	    			 Element cElement = (Element) jobSearchNode;
	    			 
	    			 
	    			 String company = cElement.getElementsByTagName("Company").item(0).getTextContent();
	    			 String jobtitle = cElement.getElementsByTagName("JobTitle").item(0).getTextContent();
	    			 Log.i("Result of parsing",company);
	    			 Log.i("Result of parsing",jobtitle);
	    			 Log.i("Result of parsing","###################");
	    			 HashMap<String, String> map = new HashMap<String, String>();
 	            	map.put("company", company);
 	            	map.put("jobtitle", jobtitle);
 	            	jobList.add(map);
	            	listView = (ListView)findViewById(R.id.listViewJobs);
	            	adapter = new SimpleAdapter(MainActivity.this, jobList,R.layout.list_v,new String[] { "company","jobtitle" }, new int[] {R.id.company, R.id.jobtitle });
	            	listView.setAdapter(adapter);
	    				         }

	    			 //Element el = (Element) jobSearchNode;
	    			 //NodeList list1 = el.getElementsByTagName("Company");
	    			
	    		 }
	    	}
	             
	    }

