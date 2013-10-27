package com.example.androidphp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidphp.AllStudentsActivity.LoadAllStudents;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
						Event temp = new Event(c.getString(TAG_PID),c.getString(TAG_TITLE),c.getString(TAG_NOTE),c.getString(TAG_START_DATE),c.getString(TAG_END_DATE),c.getString(TAG_COLOR),c.getString(TAG_PERSON));
						eventsList1.add(temp);
						//String name = c.getString(TAG_NAME);
						//String id = c.getString(TAG_PID);
						Log.d("Title", temp.getTitle());
						Log.d("Id",temp.getId());
						HashMap<String,String> map = new HashMap<String,String>();
						map.put(TAG_PID, temp.getId());
						map.put(TAG_TITLE, temp.getTitle());
						eventsList.add(map);
					}
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
							AllLeaderActivities.this,eventsList,R.layout.list_item, new String[] {TAG_PID,TAG_TITLE},new int[]{R.id.pid,R.id.name});
					setListAdapter(adapter);
					
				}
			});
		}
		
	}
	
}
