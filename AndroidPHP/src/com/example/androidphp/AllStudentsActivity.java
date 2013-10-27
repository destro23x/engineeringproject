package com.example.androidphp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class AllStudentsActivity extends ListActivity {

	static String urlAllStudents = "http://10.0.0.5/getAllStudents.php";
	
	ListView lv;
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	ArrayList<HashMap<String,String>> studentsList;
	ArrayList<Student> studentsList1;
	
	//JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STUDENTS = "students";
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
	JSONArray students = null;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_students);
		studentsList = new ArrayList<HashMap<String,String>>();
		studentsList1 = new ArrayList<Student>();
		lv = getListView();
		new LoadAllStudents().execute();
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				String pid = arg1.findViewById(R.id.pid).toString();
				
				
				
				AlertDialog.Builder adb = new AlertDialog.Builder(AllStudentsActivity.this);
				adb.setTitle("ListView OnClick");
				adb.setMessage(studentsList1.get(position).toString() +
						"Selected Item is = "
				+ lv.getItemAtPosition(position) + " czyli " + position);
				adb.setPositiveButton("Ok", null);
				adb.show();               
				
				
			}
			
		});
	}
	
	class LoadAllStudents extends AsyncTask<String,String,String> {

		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(AllStudentsActivity.this);
			pDialog.setMessage("Loading students. Please wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			JSONObject json = jsonParser.makeHttpRequest(urlAllStudents, "GET", parameters);
			Log.d("All students",json.toString());
			try{
				int success = json.getInt(TAG_SUCCESS);
				Log.d("Success", String.valueOf(success));
				if (success == 1){
					students = json.getJSONArray(TAG_STUDENTS);
					for(int i = 0; i < students.length(); i++ ){
						JSONObject c = students.getJSONObject(i);
						Student temp = new Student(c.getString(TAG_PID),c.getString(TAG_NAME),c.getString(TAG_SURNAME),c.getString(TAG_PESEL),c.getString(TAG_PHONE),c.getString(TAG_MAIL),c.getString(TAG_STREET),c.getString(TAG_CITY), c.getString(TAG_HOUSE_NUMBER), c.getString(TAG_CITYCODE), c.getString(TAG_ACTIVE));
						studentsList1.add(temp);
						//String name = c.getString(TAG_NAME);
						//String id = c.getString(TAG_PID);
						Log.d("Name",temp.getId());
						Log.d("Id",temp.getName());
						HashMap<String,String> map = new HashMap<String,String>();
						map.put(TAG_PID, temp.getId());
						map.put(TAG_NAME, temp.getName());
						
						studentsList.add(map);
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
							AllStudentsActivity.this,studentsList,R.layout.list_item, new String[] {TAG_PID,TAG_NAME},new int[]{R.id.pid,R.id.name});
					setListAdapter(adapter);
					
				}
			});
		}
		
	}
}
