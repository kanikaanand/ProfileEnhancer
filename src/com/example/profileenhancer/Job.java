package com.example.profileenhancer;


public class Job {

	int id;
	String jobTitle;
	String company;
	String city;
	String state;
	String url;
	private boolean checked = false ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getCompany() {
		return company;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	 public boolean isChecked() {  
	      return checked;  
	    }  
	 public void setChecked(boolean checked) {  
	      this.checked = checked;  
	    }   
	 
	 public void toggleChecked() {  
	      checked = !checked ;  
	    }  

}
