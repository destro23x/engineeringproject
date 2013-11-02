package com.example.androidphp;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidphp.AllStudentsActivity.LoadAllStudents;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class AllLeaderActivities extends ListActivity {
	static String urlAllEvents = "http://10.0.0.5/getAllLeaderActivities.php";
	
	String leader;
	ListView lv;
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	ArrayList<HashMap<String,String>> eventsList;
	ArrayList<Event> eventsList1;
	
	//JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_EVENTS = "events";
	private static final String TAG_TITLE = "title";
	private static final String TAG_PID = "pid";
	private static final String TAG_NOTE = "note";
	private static final String TAG_START_DATE = "startDate";
	private static final String TAG_END_DATE = "endDate";
	private static final String TAG_COLOR = "color";
	private static final String TAG_PERSON = "person";
	
	JSONArray events = null;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_students);
		
		eventsList = new ArrayList<HashMap<String,String>>();
		eventsList1 = new ArrayList<Event>();
		lv = getListView();
		
		Bundle extras = getIntent().getExtras();
        if(extras != null){
        	leader = extras.getString("username");
        }
		
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb = new AlertDialog.Builder(AllLeaderActivities.this);
				adb.setTitle("ListView OnClick");
				adb.setMessage(eventsList1.get(position).toString() +
						"Selected Item is = "
				+ lv.getItemAtPosition(position) + " czyli " + position);
				adb.setPositiveButton("Ok", null);
				adb.show();               
				
				
			}
			
		});
        
		new LoadAllLeaderActivities().execute();
		
		
	}
	
	class LoadAllLeaderActivities extends AsyncTask<String,String,String>{

		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(AllLeaderActivities.this);
			pDialog.setMessage("Loading events. Please wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@SuppressWarnings("unchecked")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("leader",leader));
            Log.d("Leader", leader);
			JSONObject json = jsonParser.makeHttpRequest(urlAllEvents, "POST", parameters);
			Log.d("All events",json.toString());
			try{
				int success = json.getInt(TAG_SUCCESS);
				Log.d("Success", String.valueOf(success));
				if (success == 1){
					events = json.getJSONArray(TAG_EVENTS);
					for(int i = 0; i < events.length(); i++ ){
						JSONObject c = events.getJSONObject(i);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date startDate = null;
						Date endDate = null;
						try {
							startDate = format.parse(c.getString(TAG_START_DATE));
							endDate = format.parse(c.getString(TAG_END_DATE));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (new Date().before(startDate)){
							Event temp = new Event(c.getString(TAG_PID),c.getString(TAG_TITLE),c.getString(TAG_NOTE), startDate /*c.getString(TAG_START_DATE)*/,endDate/*c.getString(TAG_END_DATE)*/,c.getString(TAG_COLOR),c.getString(TAG_PERSON));
							eventsList1.add(temp);
							
							//String title = c.getString(TAG_TITLE);
							//String id = c.getString(TAG_PID);
							//Log.d("Title", temp.getTitle());
							//Log.d("Id",temp.getId());
							//HashMap<String,String> map = new HashMap<String,String>();
							//map.put(TAG_PID, temp.getId());
							//map.put(TAG_TITLE, temp.getTitle());
							//map.put(TAG_START_DATE, temp.getStartDate().toString());
							//eventsList.add(map);
						}
					}
					
					Collections.sort(eventsList1);
					for(Event temp : eventsList1){
						HashMap<String,String> map = new HashMap<String,String>();
						map.put(TAG_PID, temp.getId());
						map.put(TAG_TITLE, temp.getTitle());
						map.put(TAG_START_DATE, temp.getStartDate().toString());
						Log.d("Title", temp.getTitle());
						Log.d("Id",temp.getId());
						eventsList.add(map);
						System.out.println(temp.getTitle());
					}
					//System.out.println(eventsList1.toString());
					/*Iterator<Event> iterator = eventsList1.iterator();
					while(iterator.hasNext()){
						HashMap<String,String> map = new HashMap<String,String>();
						map.put(TAG_PID, iterator.next().getId());
						map.put(TAG_TITLE, iterator.next().getTitle());
						map.put(TAG_START_DATE, iterator.next().getStartDate().toString());
						Log.d("Title", iterator.next().getTitle());
						Log.d("Id",iterator.next().getId());
						eventsList.add(map);
						//eventsList.get(1).
					}*/
				}else{
					// No student found launch the new student activity
				}
			} catch (JSONException e){
				e.printStackTrace();
			}
			
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable(){
				public void run(){
					ListAdapter adapter = new SimpleAdapter(
							AllLeaderActivities.this,eventsList,R.layout.list_item, new String[] {TAG_PID,TAG_TITLE,TAG_START_DATE},new int[]{R.id.pid,R.id.name,R.id.startDate});
					setListAdapter(adapter);
					
				}
			});
		}
		
	}
	
}
