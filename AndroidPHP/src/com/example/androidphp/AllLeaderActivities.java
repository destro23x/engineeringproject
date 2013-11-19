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

import pl.sfs.model.Klient;
import pl.sfs.model.Wydarzenie;
import pl.sfs.service.SFSException;
import pl.sfs.service.SFSService;

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
	
	int workerID;
	ListView lv;
	ProgressDialog pDialog;
	ArrayList<HashMap<String,String>> eventsList;
	
	//JSON Node names
	private static final String TAG_TITLE = "title";
	private static final String TAG_PID = "pid";
	private static final String TAG_NOTE = "note";
	private static final String TAG_START_DATE = "startDate";
	private static final String TAG_END_DATE = "endDate";
	private static final String TAG_COLOR = "color";
	private static final String TAG_PERSON = "person";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_students);
		
		eventsList = new ArrayList<HashMap<String,String>>();
		lv = getListView();
		
		Bundle extras = getIntent().getExtras();
        if(extras != null){
        	workerID = extras.getInt("workerID");
        }
		
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb = new AlertDialog.Builder(AllLeaderActivities.this);
				adb.setTitle("ListView OnClick");
				adb.setMessage(eventsList.get(position).toString() +
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
			
			SFSService service = new SFSService();
			
			try {
				ArrayList<Wydarzenie> events = service.getEventsForUser(workerID);
				Collections.sort(events);
				Log.d("sciema","niema");
				for(int i = 0 ; i < events.size(); i++){
					Wydarzenie temp = events.get(i);
					HashMap<String,String> map = new HashMap<String,String>();
					map.put(TAG_PID, temp.getWydarzenia_ID().toString());
					map.put(TAG_TITLE, temp.getWydarzenia_Tytul().toString());
					map.put(TAG_START_DATE, temp.getWydarzenia_StartDate().toString());
					eventsList.add(map);
				}
			} catch (SFSException sfs) {
				// TODO Auto-generated catch block
				sfs.printStackTrace();
				//Wyswietlenie informacji zeby pobra³ dane jeszcze raz zwróciæ wartoœæ jak¹æ do g³ównej aktywnoœci
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//Wyswietlenie informacji zeby pobra³ jeszcze raz
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
