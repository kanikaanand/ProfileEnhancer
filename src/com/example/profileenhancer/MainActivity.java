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

//import com.example.testgoogleplus.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.Gson;



import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements 
OnClickListener,ConnectionCallbacks, OnConnectionFailedListener {
	
	private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";
 
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
 
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
 
    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;
 
    private boolean mSignInClicked;
 
    private ConnectionResult mConnectionResult;
 
    private SignInButton btnSignIn;	
    private TextView txtName, txtEmail;
    private LinearLayout llProfileLayout;
	
	
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
		
		
		btnSignIn = (SignInButton) findViewById(R.id.signin);
		txtName = (TextView) findViewById(R.id.textViewName);
		//txtEmail = (TextView) findViewById(R.id.textViewEmail);
		llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
		
		 btnSignIn.setOnClickListener(this);
		
		 // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
	}
	protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
 
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    
    /**
     * Button on click listener
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.signin:
            // Signin button clicked
        	 Log.i("TestGooglePlus","going ");
            signInWithGplus();
           
            break;
        /*
        case R.id.btn_sign_out:
            // Signout button clicked
            signOutFromGplus();
            break;
        case R.id.btn_revoke_access:
            // Revoke access button clicked
            revokeGplusAccess();
            break;
            */
        }
    }
    
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
     
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
     
            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
     
    }
     
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }
     
            mIntentInProgress = false;
     
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }
     
    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
     
        // Get user's information
        getProfileInformation();
     
        // Update the UI after signin
        updateUI(true);
     
    }
     
    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }
     
    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            //btnSignOut.setVisibility(View.VISIBLE);
            //btnRevokeAccess.setVisibility(View.VISIBLE);
            llProfileLayout.setVisibility(View.VISIBLE);
            Log.i("TestGooglePlus","UI should be visible now ");
            
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            //btnSignOut.setVisibility(View.GONE);
            //btnRevokeAccess.setVisibility(View.GONE);
            //llProfileLayout.setVisibility(View.GONE);
        }
    }
    /**
     * Sign-in into google
     * */
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }
     
    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
     
                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);
     
                txtName.setText(personName);
                //txtEmail.setText(email);
     
                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;
     
                //new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
     
            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
	    		//Log.i("Result of API",query.toString());
	    		String result = careerBuilder.searchJobs(query.toString());
	    		Log.i("Result of API","XML OUTPUT "+ "it come here ");
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
	    			

	    			 HashMap<String, String> map = new HashMap<String, String>();
 	            	map.put("company", company);
 	            	map.put("jobtitle", jobtitle);
 	            	//jobList.add(map);
	            	listView = (ListView)findViewById(R.id.listViewJobs);
	            	//adapter = new SimpleAdapter(MainActivity.this, jobList,R.layout.list_v,new String[] { "company","jobtitle" }, new int[] {R.id.company, R.id.jobtitle });
	            	listView.setAdapter(adapter);
	            	
	            	//clicking of a job
	            	listView.setClickable(true);
	                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	                    @Override
	                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
	                        Object o = listView.getItemAtPosition(position);
	                        //String str=(String)o;//As you are using Default String Adapter
	                        //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
	                        HashMap temp  = (HashMap)o;
	                        String company = (String)temp.get("company");
	                    	 Toast.makeText(getApplicationContext(),company, Toast.LENGTH_SHORT).show();
	                    }
	                });
	            	

	    			 
	    			 Job job = new Job();
	    			 job.setCompany(company);
	    			 job.setJobTitle(jobtitle);
	    			 job.setCity(city);
	    			 job.setState(state);
	    			 job.setUrl(url);
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

