ProfileEnhancer
===============
Profile Enhancer version 1.0 12/4/2014


GENERAL USAGE NOTES
-------------------------------------
1. You must have android SDK installed successfully on your machine
2. You must have access to an android device with updated Google Play services. 
3. You must be logged in to Google Play Services with a valid Google account. 


Any questions, bugs, suggestions for ProfileEnhancer should be mailed at:
kanika.anand@sjsu.edu, pulkit.gupta@sjsu.edu, shweta.seth@sjsu.edu


The files contained in the directory are:
------------------------------------------
1.  AndroidManifest.xml
This XML file tells about what ProfileEnhancer can do. 


2.  res/layout/activity_main.xml
This XML has the layout of the main screen for user. 


3. res/layout/item_view.xml
This XML has the layout for each list view item in the search results


4. res/layout/list_items.xml
This XML has the layout for the list view screen.


5. res/layout/list_v.xml
This XML is to display items in a SimpleAdapter and is an optional file.


6. res/layout/saved_items.xml
This XML has the layout for the saved jobs screen. 


7. src/com/example/ProfileEnhancer/MainActivtiy.java
This is the main file of the project and holds functionaliy for Google+ Sign in and Async functions. 

8. src/com/example/ProfileEnhancer/CareerBuilder.java
This class holds the functionality to set the query for CareerBuilder API call, makes HTTP call and returns the data in the form of String. 

9. src/com/example/ProfileEnhancer/CustomListViewAdapter.java
This class holds the funcionality to convert string data on to customer adapter to put in list view.


10. src/com/example/ProfileEnhancer/Indeed.java
This class holds the functionality to set the query for Indeed API call, makes HTTP call and returns the data in the form of String. 


11. src/com/example/ProfileEnhancer/Job.java
This class is a POJO, which is used to store the Job info received from parsing an xml script and saved in database. The entities contained are job id, job title, company, city, state, url and checked option. 


12. src/com/example/ProfileEnhancer/JobList.java
This is a wrapped class used to hold a list of jobs. This has been constructed for ease of converting to json object for transporting list of jobs via intents


13. src/com/example/ProfileEnhancer/JobViewHolder.java
This class is a class which represents each element in the list view of the UI. This helps in easy mapping of data onto the row elements.


14. src/com/example/ProfileEnhancer/ShowList.java
This class is used to show the results of initial search after user has entered the job title and locatoin. It used to custom list view adapter class to display the results. This class also has a method to save the jobs that the user has checked to save.


15. src/com/example/ProfileEnhancer/ShowSavedList.java
This class is used to show the saved result, it gets the saved jobs from database or from Show List class where the jobs are being saved. 


16. src/com/example/ProfileEnhancer/sqldatabase.java
This is the class that creates the database. It has an instance of database that can be used to do operations on the database like add a job, view all jobs and delete a job.


17. src/com/example/ProfileEnhancer/XMLParser.java
This class is used to parse the XML responses received from the APIs using a DOM parser.

Below are the set up instructions for the project ProfileEnhancer:


1. Import the source code into your android eclipse workspace. 

2. Please have latest installation of Google Play services in Extras of SDK manager. 

3. Go to Google API console (https://code.google.com/apis/console) and create a project with the name of your project.

4. Go to the project you just created in Google Developer’s console and do the following :

	4.1. Go to API’s tab and add Google+ API to your project. 
	4.2. go to consent screen tab and save the consent by giving your email ID and project name 
	4.3. Go to credentials tab and create a new client ID. SHA fingerprint can be obtained by giving the following commands:
		Windows: keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
		MAC: keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android

5. go to Java build path and include gson-2.3 jar and google_play_services_lib.

6.  Run your project.
