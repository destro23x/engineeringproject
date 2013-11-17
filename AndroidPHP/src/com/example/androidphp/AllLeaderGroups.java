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

	
	static String urlAllGroups = "http://10.0.0.5/getAllLeaderGroups.php";
	
	String leader;
	ListView lv;
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	ArrayList<HashMap<String,String>> groupsList;
	ArrayList<Group> groupsList1;
	
	//JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_GROUPS = "groups";
	private static final String TAG_PIDGROUP = "pidgroup";
	private static final String TAG_PIDCOURSE = "pidcourse";
	private static final String TAG_GROUPNAME = "groupname";
	private static final String TAG_GROUPACTIVE = "groupactive";
	private static final String TAG_GROUPLEVEL = "grouplevel";
	
	JSONArray groups = null;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_students);
		
		Bundle extras = getIntent().getExtras();
        if(extras != null){
        	leader = extras.getString("username");
        }
        
		groupsList = new ArrayList<HashMap<String,String>>();
		groupsList1 = new ArrayList<Group>();
		lv = getListView();
		
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ContentGroup.class);
				intent.putExtra("group", groupsList1.get(position).getIdGroup());
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
				ArrayList<Grupa> groups2 = service.getGroups();
				int k = 0;
			} catch (SFSException sfs) {
				// TODO Auto-generated catch block
				sfs.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("leader",leader));
            Log.d("Leader", leader);
			JSONObject json = jsonParser.makeHttpRequest(urlAllGroups, "POST", parameters);
			Log.d("All groups",json.toString());
			try{
				int success = json.getInt(TAG_SUCCESS);
				Log.d("Success", String.valueOf(success));
				if (success == 1){
					groups = json.getJSONArray(TAG_GROUPS);
					for(int i = 0; i < groups.length(); i++ ){
						JSONObject c = groups.getJSONObject(i);
						Group temp = new Group(c.getString(TAG_PIDGROUP),c.getString(TAG_PIDCOURSE),c.getString(TAG_GROUPNAME),c.getString(TAG_GROUPACTIVE),c.getString(TAG_GROUPLEVEL));
						groupsList1.add(temp);
						Log.d("Name",temp.groupName);
						Log.d("Id",temp.getIdGroup());
						HashMap<String,String> map = new HashMap<String,String>();
						map.put(TAG_PIDGROUP, temp.getIdGroup());
						map.put(TAG_GROUPNAME, temp.getGroupName());
						
						groupsList.add(map);
						
					}
					
					
				}else{
					// no group found launch the new group activity
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
							AllLeaderGroups.this,groupsList,R.layout.list_item, new String[] {TAG_PIDGROUP,TAG_GROUPNAME},new int[]{R.id.pid,R.id.name});
					setListAdapter(adapter);
					
				}
			});
		}
		
	}
}
