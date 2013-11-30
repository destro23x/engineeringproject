package pl.android.studiotanca;

import java.util.ArrayList;
import java.util.HashMap;

import pl.sfs.model.Grupa;
import pl.sfs.model.Grupa_Kurs;
import pl.sfs.service.SFSException;
import pl.sfs.service.SFSService;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.androidphp.R;

public class AllLeaderGroupsActivity extends ListActivity {

	int workerID;
	ListView lv;
	ProgressDialog pDialog;
	ArrayList<HashMap<String,String>> groupsList;
	ArrayList<Grupa_Kurs> groups;
	
	private static final String TAG_PIDGROUP = "pidgroup";
	private static final String TAG_PIDCOURSE = "pidcourse";
	private static final String TAG_GROUPNAME = "groupname";
	private static final String TAG_GROUPACTIVE = "groupactive";
	private static final String TAG_GROUPLEVEL = "grouplevel";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
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
				Log.d("System", groups.get(position).getGrupy_ID().toString());
				intent.putExtra("groupID", groups.get(position).getGrupy_ID());
				startActivity(intent);
					
			}
			
		});
        
		new LoadAllGroups().execute();
	}
	
	class LoadAllGroups extends AsyncTask<String,String,String>{

		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(AllLeaderGroupsActivity.this);
			pDialog.setMessage("Przetwarzanie listy grup. Proszê czekaæ ...");
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
				groups = service.getGroupsForUser(workerID);                 
				for(int i = 0 ; i < groups.size(); i++){
					Grupa temp = groups.get(i);
					HashMap<String,String> map = new HashMap<String,String>();
					map.put(TAG_GROUPNAME, temp.getGrupy_Nazwa().toString());
					map.put(TAG_GROUPLEVEL, temp.getGrupy_Stopien().toString());
					map.put(TAG_GROUPACTIVE, temp.getGrupy_Aktywny().toString());
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
					ListAdapter adapter = new SpecialAdapter(
							AllLeaderGroupsActivity.this,groupsList,R.layout.activity_groups_items, new String[] {TAG_GROUPNAME, TAG_GROUPLEVEL, TAG_GROUPACTIVE},new int[]{R.id.name,R.id.level, R.id.active});
					setListAdapter(adapter);
				
				}
			});
		}
		
	}
}
