package pl.android.studiotanca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import pl.sfs.model.Wydarzenie;
import pl.sfs.service.SFSException;
import pl.sfs.service.SFSService;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.androidphp.R;

public class AllLeaderEventsActivity extends ListActivity {
	
	int workerID;
	ListView lv;
	ProgressDialog pDialog;
	ArrayList<HashMap<String,String>> eventsList;
	ArrayList<Wydarzenie> events;
	
	private static final String TAG_TITLE = "title";
	private static final String TAG_START_DATE = "startDate";
	private static final String TAG_END_DATE = "endDate";
	private static final String TAG_GROUPID = "groupid";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
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
				AlertDialog.Builder adb = new AlertDialog.Builder(AllLeaderEventsActivity.this);
				adb.setTitle("Szczegó³y wydarzenia");
				Wydarzenie temp = events.get(position);
				StringBuilder sb = new StringBuilder();
				sb.append("Tytu³:" + temp.getWydarzenia_Tytul().toString());
				sb.append("\n");
				sb.append("Data rozpoczêcia:" + temp.getWydarzenia_StartDate().toString());
				sb.append("\n");
				sb.append("Data zakoñczenia:" + temp.getWydarzenia_KoniecDate().toString());
				sb.append("\n");
				sb.append("Kolor:" + temp.getWydarzenia_Kolor().toString());
				sb.append("\n");
				sb.append("Notka:" + temp.getWydarzenia_Notka().toString());
				sb.append("\n");
				adb.setMessage(sb);
				adb.setPositiveButton("Ok", null);
				adb.show();
				// TODO Auto-generated method stub
				
			}
			
		});
		new LoadAllLeaderActivities().execute();
	}
	
	class LoadAllLeaderActivities extends AsyncTask<String,String,String>{

		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(AllLeaderEventsActivity.this);
			pDialog.setMessage("Przetwarzanie listy wydarzeñ. Proszê czekaæ ...");
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
				events = service.getEventsForUser(workerID);
				Collections.sort(events);
				for(int i = 0 ; i < events.size(); i++){
					Wydarzenie temp = events.get(i);
					HashMap<String,String> map = new HashMap<String,String>();
					map.put(TAG_START_DATE, temp.getWydarzenia_StartDate().toString());
					map.put(TAG_END_DATE, temp.getWydarzenia_KoniecDate().toString());
					map.put(TAG_GROUPID, temp.getGrupa_ID().toString());
					map.put(TAG_TITLE, temp.getWydarzenia_Tytul().toString());
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
					ListAdapter adapter = new SpecialAdapter(
							AllLeaderEventsActivity.this,eventsList,R.layout.activity_events_items, new String[] {TAG_START_DATE, TAG_END_DATE, TAG_GROUPID, TAG_TITLE,},new int[]{R.id.startDate, R.id.endDate, R.id.groupID, R.id.name});
					setListAdapter(adapter);
					
				}
			});
		}
		
	}
	
}
