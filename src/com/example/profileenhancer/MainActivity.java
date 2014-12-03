package com.example.profileenhancer;


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



import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.support.v7.app.ActionBarActivity;
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
	private String query = null;
	JSONArray jsonArray = null;
	
	JobList jobList = new JobList();
	List<Job> listSearch = new ArrayList<Job>();
	ListView listView;
	ListAdapter adapter;
	CareerBuilder careerBuilder =new CareerBuilder();
	Indeed indeed = new Indeed();
	//String jobTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
    	
		
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
	 class LoadJobs extends AsyncTask<String, String, List<String>>{
	    	List<String> returnString = new ArrayList<String>();
	    	XMLParser parser;
	    	static final String KEY_ITEM = "item";
	    	EditText title = (EditText)findViewById(R.id.jobId);
	    	String jobTitle = title.getText().toString();
			private boolean flag = false;
	    	LoadJobs()
	    	{
	    		
	    	}
	    	LoadJobs(String type)
	    	{
	    		
	    	}
	    	@Override
			protected List<String> doInBackground(String... params) {
	    		
	
	    		Log.i("Result of API","Job Title is "+ jobTitle);
	    		query = careerBuilder.setQuery(jobTitle);
	    		Log.i("Result of API",query.toString());
	    		String result = careerBuilder.searchJobs(query.toString());
	    		Log.i("Result of API","XML OUTPUT "+ result);
	            returnString.add(result);
	    		
//	            query = indeed.setQuery(jobTitle);
//	    		Log.i("Result of API",query.toString());
//	            returnString.add(indeed.searchJobs(query.toString()));

	    		return returnString;
	    	}
	    	@Override
			protected void onPostExecute(List<String> returnStrings) {
	    		
	    		 
	    		 //jobList.clear();
	    		 parser = new XMLParser();
	    		 Document doc = parser.getDomElement(returnStrings.get(0)); // getting DOM element
	    		 
	    		 NodeList list = doc.getElementsByTagName("JobSearchResult");
	    	
	    		 Log.i("Result of API","list.getLength()="+list.getLength());
	    		// looping through all item nodes <JobSearchResult>
	    		 for (int i = 0; i < list.getLength(); i++) {
	    			 Node jobSearchNode = list.item(i);
	    			 Element cElement = (Element) jobSearchNode;
	    			 
	    			 
	    			 String company = cElement.getElementsByTagName("Company").item(0).getTextContent();
	    			 String jobtitle = cElement.getElementsByTagName("JobTitle").item(0).getTextContent();
	    			 String city = cElement.getElementsByTagName("City").item(0).getTextContent();
	    			 String state = cElement.getElementsByTagName("State").item(0).getTextContent();
	    			 String url = cElement.getElementsByTagName("CompanyDetailsURL").item(0).getTextContent();
	    					 
	    			 Log.i("Result of parsing",company);
	    			 Log.i("Result of parsing",jobtitle);
	    			
	    			 
	    			 Job job = new Job();
	    			 job.setCompany(company);
	    			 job.setJobTitle(jobtitle);
	    			 job.setCity(city);
	    			 job.setState(state);
	    			 job.setUrl(url);
	    			 job.setChecked(false);
	    			 listSearch.add(job);
	    			 
	    		//	 HashMap<String, String> map = new HashMap<String, String>();
 	            //	map.put("company", company);
 	           // 	map.put("jobtitle", jobtitle);
 	          //  	jobList.add(map);
	            	//listView = (ListView)findViewById(R.id.listViewJobs);
	            	//adapter = new SimpleAdapter(MainActivity.this, jobList,R.layout.list_v,new String[] { "company","jobtitle" }, new int[] {R.id.company, R.id.jobtitle });
	            	//listView.setAdapter(adapter);
	    		 }
	    		 
	    		 jobList.setJobList(listSearch);
	    		 
	    			
	    			Gson gS = new Gson();
	    			String target = gS.toJson(jobList);
	    			Intent i = new Intent();
	    			i.putExtra("MyObjectAsString", target);
	    			i.setClass(MainActivity.this, ShowList.class);
	    			startActivity(i);
	    			
	    		 //flag  = true;
	    		 
	    		 //Button title = (Button)findViewById(R.id.button1);
	    		// title.setText("Click to View Results");
	    		 
	    		}
	    	
	    	
	    	}
}

