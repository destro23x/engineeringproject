package com.example.androidphp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.sfs.model.Grupa;
import pl.sfs.model.Grupa_Kurs;
import pl.sfs.service.SFSException;
import pl.sfs.service.SFSService;

import com.example.androidphp.AllLeaderActivities.LoadAllLeaderActivities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class AllLeaderGroups extends ListActivity {

	int workerID;
	ListView lv;
	ProgressDialog pDialog;
	ArrayList<HashMap<String,String>> groupsList;
	
	private static final String TAG_PIDGROUP = "pidgroup";
	private static final String TAG_PIDCOURSE = "pidcourse";
	private static final String TAG_GROUPNAME = "groupname";
	private static final String TAG_GROUPACTIVE = "groupactive";
	private static final String TAG_GROUPLEVEL = "grouplevel";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_students);
		
		Bundle extras = getIntent().getExtras();
        if(extras != null){
        	workerID = extras.getInt("workerID");
        }
        
		groupsList = new ArrayList<HashMap<String,String>>();
		lv = getListView();
		
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ContentGroup.class);
				Log.d("System", groupsList.get(position).get(TAG_PIDGROUP).toString());
				intent.putExtra("groupID", Integer.valueOf(groupsList.get(position).get(TAG_PIDGROUP)));
				startActivity(intent);
					
			}
			
		});
        
		new LoadAllGroups().execute();
	}
	
	class LoadAllGroups extends AsyncTask<String,String,String>{

		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(AllLeaderGroups.this);
			pDialog.setMessage("Loading groups. Please wait ...");
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
				ArrayList<Grupa_Kurs> groups = service.getGroupsForUser(workerID);                 
				int k = 0;
				for(int i = 0 ; i < groups.size(); i++){
					Grupa temp = groups.get(i);
					HashMap<String,String> map = new HashMap<String,String>();
					map.put(TAG_PIDGROUP, temp.getGrupy_ID().toString());
					map.put(TAG_GROUPNAME, temp.getGrupy_Nazwa().toString());
					groupsList.add(map);
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
							AllLeaderGroups.this,groupsList,R.layout.list_item, new String[] {TAG_PIDGROUP,TAG_GROUPNAME},new int[]{R.id.pid,R.id.name});
					setListAdapter(adapter);
				
				}
			});
		}
		
	}
}
