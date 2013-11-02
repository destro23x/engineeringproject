package com.example.androidphp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidphp.AllLeaderGroups.LoadAllGroups;

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

public class ContentGroup extends ListActivity {

	int groupNumber;
	
	static String urlContentGroup = "http://10.0.0.5/getContentGroup.php";
	
	String leader;
	ListView lv;
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	ArrayList<HashMap<String,String>> contentGroupsList;
	ArrayList<Group> contentGroupsList1;
	
	//JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_CLIENTS = "clients";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";
	private static final String TAG_SURNAME = "surname";
	private static final String TAG_PESEL = "pesel";
	private static final String TAG_SEX = "sex";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_MAIL = "mail";
	private static final String TAG_STREET = "street";
	private static final String TAG_CITY = "city";
	private static final String TAG_HOUSENUMBER = "housenumber";
	private static final String TAG_CODE = "code";
	private static final String TAG_ACTIVE = "active";
	
	JSONArray clients = null;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_students);
		
		Bundle extras = getIntent().getExtras();
        if(extras != null){
        	groupNumber = Integer.parseInt(extras.getString("group"));
        }
		
        contentGroupsList = new ArrayList<HashMap<String,String>>();
		contentGroupsList1 = new ArrayList<Group>();
		lv = getListView();
		
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(getApplicationContext(), ContentGroup.class);
				//intent.putExtra("group", groupsList1.get(position).getIdGroup());
				//startActivity(intent);
					
			}
			
		});
        
		new LoadContentGroup().execute();
	}
	class LoadContentGroup extends AsyncTask<String,String,String>{

		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(ContentGroup.this);
			pDialog.setMessage("Loading content of group. Please wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@SuppressWarnings("unchecked")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("groupNumber",String.valueOf(groupNumber)));
            Log.d("GroupNumber", String.valueOf(groupNumber));
			JSONObject json = jsonParser.makeHttpRequest(urlContentGroup, "POST", parameters);
			Log.d("Content group",json.toString());
			try{
				int success = json.getInt(TAG_SUCCESS);
				Log.d("Success", String.valueOf(success));
				if (success == 1){
					clients = json.getJSONArray(TAG_CLIENTS);
					for(int i = 0; i < clients.length(); i++ ){
						JSONObject c = clients.getJSONObject(i);
						//Group temp = new Group(c.getString(TAG_PIDGROUP),c.getString(TAG_PIDCOURSE),c.getString(TAG_GROUPNAME),c.getString(TAG_GROUPACTIVE),c.getString(TAG_GROUPLEVEL));
						//groupsList1.add(temp);
						//Log.d("Name",temp.groupName);
						//Log.d("Id",temp.getIdGroup());
						HashMap<String,String> map = new HashMap<String,String>();
						map.put(TAG_PID, c.getString(TAG_PID));
						map.put(TAG_SURNAME, c.getString(TAG_SURNAME) );
						
						contentGroupsList.add(map);
						
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
							ContentGroup.this,contentGroupsList,R.layout.list_item, new String[] {TAG_PID,TAG_SURNAME},new int[]{R.id.pid,R.id.name});
					setListAdapter(adapter);
					
				}
			});
		}
		
	}
}
