package com.example.profileenhancer;

import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class CustomListViewAdapter extends ArrayAdapter<Job> {
 
    Context context;
 
    public CustomListViewAdapter(Context context, int resourceId, List<Job> items) {
        super(context, resourceId, items);
        this.context = context;
    }
     
   

	/*private view holder class*/
    private class ViewHolder {
    	 TextView txtJobTitle;
         TextView txtCompany;
         TextView txtCity;
         TextView txtState;
     }
      
     public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder = null;
         Job rowItem = getItem(position);
         
         Log.i("row item", rowItem.getCity());
         LayoutInflater mInflater = (LayoutInflater) context
                 .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
         if (convertView == null) {
             convertView = mInflater.inflate(R.layout.item_view, null);
             holder = new ViewHolder();
             holder.txtJobTitle = (TextView) convertView.findViewById(R.id.jobTitle);
             holder.txtCompany = (TextView) convertView.findViewById(R.id.company);
             holder.txtCity = (TextView) convertView.findViewById(R.id.city);
             holder.txtState = (TextView) convertView.findViewById(R.id.state);
           
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
                 
        holder.txtJobTitle.setText("JOB TITLE: "+rowItem.getJobTitle());
        holder.txtCompany.setText("COMPANY: "+rowItem.getCompany());
        holder.txtCity.setText("CITY: "+rowItem.getCity());
        holder.txtState.setText("STATE: "+rowItem.getState());
     
        return convertView;
    }
}
