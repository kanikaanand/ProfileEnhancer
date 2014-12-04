package com.example.profileenhancer;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class sqldatabase 
{
public static final String KEY_ROWID = "_id";
//public static final String messageID = "_msg_id";
public static final String jobTitle = "jobTitle";
public static final String company = "company";
public static final String city = "city";
public static final String state = "state";
public static final String url = "url";
public static final String checked = "checked";

private static final String DATABASE_NAME = "MSGDB";
private static final String DATABASE_TABLE = "MessageTable";
private static final int DATABASE_VERSION = 1;

private DbHelper ourHelper;
private final Context ourContext;
private SQLiteDatabase ourDatabase;


private static class DbHelper extends SQLiteOpenHelper{
       
       
       public DbHelper(Context context) {
               super(context, DATABASE_NAME, null,DATABASE_VERSION);
               // TODO Auto-generated constructor stub
         
       }

       @Override
       public void onCreate(SQLiteDatabase db) {
               // TODO Auto-generated method stub
    	   db.execSQL("CREATE TABLE "+ DATABASE_TABLE + " (" +
    	              KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
    	              jobTitle + " STRING NOT NULL, " +
    	              company + " STRING NOT NULL, " +
    	              city + " STRING NOT NULL, " +
    	              state + " STRING NOT NULL, " +
    	              url + " STRING NOT NULL, " +
    	        		 checked  + " BOOLEAN NOT NULL);" );  
       }
       
      

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
               // TODO Auto-generated method stub
              db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
              onCreate(db);
       }
       
      
}


public sqldatabase(Context c){
       
       ourContext = c;
}

public sqldatabase open() throws SQLException{
	ourHelper = new DbHelper(ourContext);
	ourDatabase = ourHelper.getWritableDatabase();
	return this;
}

public void close(){
	ourHelper.close();
}

public List<Job> viewSavedJobs()
{
	   List<Job> jobsSaved = new ArrayList<Job>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;
	    
	    ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
	 
	    Cursor cursor = ourDatabase.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Job job = new Job();
	            job.setId(cursor.getInt(0));
	            job.setJobTitle(cursor.getString(1));
	            job.setCompany(cursor.getString(2));
	            job.setCity(cursor.getString(3));
	            job.setState(cursor.getString(4));
	            job.setUrl(cursor.getString(5));
	            jobsSaved.add(job);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return jobsSaved;
}

public void deleteJob(Job job) {
	 ourHelper = new DbHelper(ourContext);
	 ourDatabase = ourHelper.getWritableDatabase();
	 ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + " = ?",
            new String[] { String.valueOf(job.getId()) });
	 ourDatabase.close();
}


public long createEntry(Job job) {
	// TODO Auto-generated method stub
	ContentValues cv = new ContentValues();
	cv.put(jobTitle, job.getJobTitle());
	cv.put(company, job.getCompany());
	cv.put(city, job.getCity());
	cv.put(state, job.getState());
	cv.put(url, job.getUrl());
	cv.put(checked, false);
	
	return ourDatabase.insert(DATABASE_TABLE, null, cv);
}
}