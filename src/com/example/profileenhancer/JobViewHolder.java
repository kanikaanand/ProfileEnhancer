package com.example.profileenhancer;

import android.widget.CheckBox;
import android.widget.TextView;

public class JobViewHolder {
	private CheckBox checkBox ;  
	TextView txtJobTitle;
    TextView txtCompany;
    TextView txtCity;
    TextView txtState;
    
    public CheckBox getCheckBox() {  
        return checkBox;  
      }  
      public void setCheckBox(CheckBox checkBox) {  
        this.checkBox = checkBox;  
      }  
}
