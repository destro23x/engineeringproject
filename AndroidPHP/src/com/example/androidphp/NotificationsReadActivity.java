package com.example.androidphp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.sfs.model.Klient;
import pl.sfs.model.Powiadomienie;
import pl.sfs.model.Wydarzenie;
import pl.sfs.service.SFSException;
import pl.sfs.service.SFSService;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NotificationsReadActivity extends ListActivity {
	
	ListView lv;
	ProgressDialog pDialog;
	ArrayList<HashMap<String,String>> notificationsList;
	
	//JSON Node names
	private static final String TAG_NAME = "name";
	private static final String TAG_PID = "pid";
	private static final String TAG_SURNAME = "surname";
	private static final String TAG_PESEL = "pesel";
	private static final String TAG_SEX = "sex";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_MAIL = "mail";
	private static final String TAG_STREET = "street";
	private static final String TAG_CITY = "city";
	private static final String TAG_HOUSE_NUMBER = "houseNumber";
	private static final String TAG_CITYCODE = "cityCode";
	private static final String TAG_ACTIVE = "active";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_students);
		notificationsList = new ArrayList<HashMap<String,String>>();
		lv = getListView();
		new LoadAllNotifications().execute();
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				AlertDialog.Builder adb = new AlertDialog.Builder(NotificationsReadActivity.this);
				adb.setTitle("ListView OnClick");
				adb.setMessage(notificationsList.get(position).toString() +
						"Selected Item is = "
				+ lv.getItemAtPosition(position) + " czyli " + position);
				adb.setPositiveButton("Ok", null);
				adb.show();               
				
				
			}
			
		});
	}
	
	class LoadAllNotifications extends AsyncTask<String,String,String> {

		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(NotificationsReadActivity.this);
			pDialog.setMessage("Loading students. Please wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			SFSService service = new SFSService();
			try {
				ArrayList<Powiadomienie> notifications = service.getNotifications();
				for(int i = 0 ; i < notifications.size(); i++){
					Powiadomienie temp = notifications.get(i);
					HashMap<String,String> map = new HashMap<String,String>();
					map.put(TAG_PID, temp.getPowiadomienia_Tytul().toString());
					map.put(TAG_NAME, temp.getPowiadomienia_Tresc().toString());
					notificationsList.add(map);
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
							NotificationsReadActivity.this,notificationsList,R.layout.list_item, new String[] {TAG_PID,TAG_NAME},new int[]{R.id.pid,R.id.name});
					setListAdapter(adapter);
					
				}
			});
		}
		
	}
}