package com.example.androidphp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pl.sfs.model.Klient;
import pl.sfs.service.SFSException;
import pl.sfs.service.SFSService;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

public class PresenceActivity extends Activity {
	ProgressDialog pDialog;
	ArrayList<Klient> clientsAssingToGroup;
	PresenceActivity _this;
	int workerId, eventId, presenceId,groupId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Bundle extras = getIntent().getExtras();
		
        if(extras != null){
        	workerId = extras.getInt("workerId");
        	presenceId = extras.getInt("presenceId");
        	eventId = extras.getInt("eventId");
        	groupId = extras.getInt("groupId");
        }
						
		setContentView(R.layout.activity_presence);
		
		new LoadPresence().execute("loadData", this, groupId);
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		
		if (clientsAssingToGroup != null){
		
		GridView view = (GridView)findViewById(R.id.gridView1);
		
		String[] uczestnicy = new String[clientsAssingToGroup.size()];
		int i = 0;
		int lp = 1;
		
		for (Iterator<Klient> iterator = clientsAssingToGroup.iterator(); iterator.hasNext();) {
			Klient k = (Klient) iterator.next();
			
			uczestnicy[i] = lp + ". " + k.getKlienci_Imie() + " " + k.getKlienci_Nazwisko();
			i++;
			lp++;
		}
		_this = this;
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,17367045, uczestnicy);
		
		view.setAdapter(adapter);

		Button accept = (Button)findViewById(R.id.acceptListBtn);
		
		accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GridView view = (GridView)findViewById(R.id.gridView1);
				
				Map<Integer, Boolean> listMap = new HashMap<Integer, Boolean>();
				SparseBooleanArray checkedItemPositions = view.getCheckedItemPositions();
				
				int i = 0;
				for (Iterator<Klient> iterator = clientsAssingToGroup.iterator(); iterator.hasNext();) {
					Klient k = (Klient) iterator.next();
					
					listMap.put(k.getKlienci_ID(), checkedItemPositions.get(i));
					i++;
				}
				
				new LoadPresence().execute("savePresence",_this, listMap, eventId, workerId, presenceId);
			}
		});
		}
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
		getMenuInflater().inflate(R.menu.presence, menu);
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
	
	class LoadPresence extends AsyncTask<Object,String,String>{
		String action;
		PresenceActivity parent;
		boolean savePresenceStatus = false;
		
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(PresenceActivity.this);
			pDialog.setMessage("Przetwarzam. Proszê czekaæ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@SuppressWarnings("unchecked")
		@Override
		protected String doInBackground(Object... params) {
			action = (String) params[0];
			parent = (PresenceActivity) params[1];
			
			SFSService service = new SFSService();
			try {
				if (action.equals("loadData")){
					clientsAssingToGroup = service.getClientsAssingToGroup((Integer)params[2]);
				}else if (action.equals("savePresence")){
					Map<Integer,Boolean> presenceList = (Map<Integer, Boolean>) params[2];
					Integer eventId = (Integer)params[3];
					Integer workerId = (Integer)params[4];
					Integer presenceId = (Integer)params[5];
					
					savePresenceStatus = service.checkPresence(presenceId, eventId, workerId, presenceList);
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
			}else if (action.equals("savePresence")){

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(parent);
                if (savePresenceStatus){
                	dlgAlert.setMessage("Zapisano obecnoœæ");	
                }else{
                	dlgAlert.setMessage("Wyst¹pi³ b³¹d z zapisem, sprawdŸ po³¹czenie lub spróbuj jeszcze raz");
                }
                
                dlgAlert.setTitle("Informacja");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);

                dlgAlert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        	if(savePresenceStatus){
                        		parent.finish();
                        	}
                    }
                });
                
                dlgAlert.create().show();
			}
		
		}

}
}
