package com.example.androidphp;

import java.util.ArrayList;
import java.util.HashMap;

import pl.sfs.model.Klient;
import pl.sfs.service.SFSException;
import pl.sfs.service.SFSService;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class PresentActivity extends ListActivity {
	
	ListView lv;
	ProgressDialog pDialog;
	int groupID;

	ArrayList<HashMap<String,String>> presentStudentsList;
	
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
		
		Bundle extras = getIntent().getExtras();
        if(extras != null){
        	groupID = extras.getInt("groupID");
        }
		
		presentStudentsList = new ArrayList<HashMap<String,String>>();
		lv = getListView();
		new LoadPresentAllStudents().execute();
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				AlertDialog.Builder adb = new AlertDialog.Builder(PresentActivity.this);
				adb.setTitle("ListView OnClick");
				adb.setMessage(presentStudentsList.get(position).toString() +
						"Selected Item is = "
				+ lv.getItemAtPosition(position) + " czyli " + position);
				adb.setPositiveButton("Ok", null);
				adb.show();               
				
			}
			
		});
		
	}
	
	class LoadPresentAllStudents extends AsyncTask<String,String,String> {

		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(PresentActivity.this);
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
				
				
				ArrayList<Klient> presentStudents = service.getClientsAssingToGroup(groupID);
				for(int i = 0 ; i < presentStudents.size(); i++){
					Klient temp = presentStudents.get(i);
					HashMap<String,String> map = new HashMap<String,String>();
					map.put(TAG_NAME, temp.getKlienci_Imie().toString());
					map.put(TAG_SURNAME, temp.getKlienci_Nazwisko().toString());
					presentStudentsList.add(map);
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
							PresentActivity.this,presentStudentsList,R.layout.list_present_content_activity, new String[] {TAG_NAME,TAG_SURNAME},new int[]{R.id.name,R.id.surname});
					setListAdapter(adapter);
					
				}
			});
		}
		
	}
}
