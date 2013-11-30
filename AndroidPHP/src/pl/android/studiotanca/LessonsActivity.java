package pl.android.studiotanca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.androidphp.R;

public class LessonsActivity extends Activity {
	ProgressDialog pDialog;
	ArrayList<Wydarzenie> eventList;
	ArrayList<HashMap<String,String>> eventsList;
	int workerId;
	ListView view;
	
	
	private static final String TAG_TITLE = "title";
	private static final String TAG_START_DATE = "startDate";
	private static final String TAG_END_DATE = "endDate";
	private static final String TAG_GROUPID = "groupid";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lessons);
		setupActionBar();
		view = (ListView)findViewById(R.id.lessonListView);
		Bundle extras = getIntent().getExtras();
		
        if(extras != null){
        	workerId = extras.getInt("workerId");
        }
        eventsList = new ArrayList<HashMap<String,String>>();
        
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Wydarzenie wydarzenie = eventList.get(position);
				
				Intent intent = new Intent(LessonsActivity.this, PresenceActivity.class);
				intent.putExtra("workerId", workerId);
				intent.putExtra("presenceId", 0);
				intent.putExtra("eventId", wydarzenie.getWydarzenia_ID());
				intent.putExtra("groupId", wydarzenie.getGrupa_ID());
				startActivity(intent);

			}
		});
		
        
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
					Collections.sort(eventList);
					for(int i = 0 ; i < eventList.size(); i++){
						Wydarzenie temp = eventList.get(i);
						HashMap<String,String> map = new HashMap<String,String>();
						map.put(TAG_START_DATE, temp.getWydarzenia_StartDate().toString());
						map.put(TAG_END_DATE, temp.getWydarzenia_KoniecDate().toString());
						map.put(TAG_GROUPID, temp.getGrupa_ID().toString());
						map.put(TAG_TITLE, temp.getWydarzenia_Tytul().toString());
						eventsList.add(map);
					}
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
				//parent.onResume();
				ListAdapter adapter = new SpecialAdapter(
						LessonsActivity.this,eventsList,R.layout.activity_events_items, new String[] {TAG_START_DATE, TAG_END_DATE, TAG_GROUPID, TAG_TITLE,},new int[]{R.id.startDate, R.id.endDate, R.id.groupID, R.id.name});
				view.setAdapter(adapter);
			}
		
		}

}
}
