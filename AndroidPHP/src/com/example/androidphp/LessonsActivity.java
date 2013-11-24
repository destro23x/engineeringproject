package com.example.androidphp;

import java.util.ArrayList;
import java.util.Iterator;

import pl.sfs.model.Wydarzenie;
import pl.sfs.service.SFSException;
import pl.sfs.service.SFSService;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LessonsActivity extends Activity {
	ProgressDialog pDialog;
	ArrayList<Wydarzenie> eventList;
	LessonsActivity _this;
	int workerId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lessons);
		setupActionBar();
		
		Bundle extras = getIntent().getExtras();
		
        if(extras != null){
        	workerId = extras.getInt("workerId");
        }
        
        new LoadLessons().execute("loadData", this);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lessons, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (eventList != null){
		
		ListView view = (ListView)findViewById(R.id.lessonListView);
		
		String[] wydarzenia = new String[eventList.size()];
		int i = 0;
		int lp = 1;
		
		for (Iterator<Wydarzenie> iterator = eventList.iterator(); iterator.hasNext();) {
			Wydarzenie k = (Wydarzenie) iterator.next();			
			
			wydarzenia[i] = lp + ". Grupa " + k.getGrupa_ID().toString() + " - " + k.getWydarzenia_Tytul() + " - " + k.getWydarzenia_StartDate();
			i++;
			lp++;
		}
		
		_this = this;
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,17367043, wydarzenia);
		
		view.setAdapter(adapter);
		
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Wydarzenie wydarzenie = eventList.get(position);
				
				Intent intent = new Intent(_this, PresenceActivity.class);
				intent.putExtra("workerId", workerId);
				intent.putExtra("presenceId", 0);
				intent.putExtra("eventId", wydarzenie.getWydarzenia_ID());
				intent.putExtra("groupId", wydarzenie.getGrupa_ID());
				startActivity(intent);

			}
		});
		}
	}
	
	class LoadLessons extends AsyncTask<Object,String,String>{
		String action;
		LessonsActivity parent;
		
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(LessonsActivity.this);
			pDialog.setMessage("Przetwarzam. Proszê czekaæ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@SuppressWarnings("unchecked")
		@Override
		protected String doInBackground(Object... params) {
			action = (String) params[0];
			parent = (LessonsActivity) params[1];
			
			SFSService service = new SFSService();
			try {
				if (action.equals("loadData")){
					eventList = service.getEventsForUser(workerId);
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
			if (action.equals("loadData")){
				parent.onResume();
			}
		
		}

}
}
