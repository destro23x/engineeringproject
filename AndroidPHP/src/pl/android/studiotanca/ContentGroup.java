package pl.android.studiotanca;

import java.util.ArrayList;
import java.util.HashMap;

import pl.sfs.model.Klient;
import pl.sfs.service.SFSException;
import pl.sfs.service.SFSService;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
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

public class ContentGroup extends ListActivity {

	int groupID;
	ListView lv;
	ProgressDialog pDialog;
	ArrayList<HashMap<String,String>> contentGroupsList;
	ArrayList<Klient> clients;
	
	
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
	
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		Bundle extras = getIntent().getExtras();
        if(extras != null){
        	groupID = extras.getInt("groupID");
        	Log.d("Otrzymano",String.valueOf(groupID));
        }
        contentGroupsList = new ArrayList<HashMap<String,String>>();
		lv = getListView();
		
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				AlertDialog.Builder adb = new AlertDialog.Builder(ContentGroup.this);
				adb.setTitle("Dane studenta");
				Klient temp = clients.get(position);
				StringBuilder sb = new StringBuilder();
				sb.append("Imiê:" + temp.getKlienci_Imie().toString());
				sb.append("\n");
				sb.append("Nazwisko:" + temp.getKlienci_Nazwisko().toString());
				sb.append("\n");
				sb.append("Miasto:" + temp.getKlienci_Miasto().toString());
				sb.append("\n");
				sb.append("Ulica:" + temp.getKlienci_Ulica().toString());
				sb.append("\n");
				sb.append("Numer domu:" + temp.getKlienci_Numer_domu().toString());
				sb.append("\n");
				sb.append("Kod:" + temp.getKlienci_Kod().toString());
				sb.append("\n");
				sb.append("Pesel:" + temp.getKlienci_Pesel().toString());
				sb.append("\n");
				sb.append("Email:" + temp.getKlienci_Mail().toString());
				sb.append("\n");
				sb.append("Telefon:" + temp.getKlienci_Telefon().toString());
				sb.append("\n");
				sb.append("P³eæ:" + temp.getKlienci_Plec().toString());
				sb.append("\n");
				sb.append("Aktywnoœæ:" + temp.getKlienci_Aktywni().toString());
				sb.append("\n");
				adb.setMessage(sb);
				adb.setPositiveButton("Ok", null);
				adb.show();
					
			}
			
		});
        
		new LoadContentGroup().execute();
	}
	class LoadContentGroup extends AsyncTask<String,String,String>{

		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(ContentGroup.this);
			pDialog.setMessage("Przetwarzam listê klientów. Proszê czekaæ ...");
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
				clients = service.getClientsAssingToGroup(groupID);                 
				for(int i = 0 ; i < clients.size(); i++){
					Klient temp = clients.get(i);
					HashMap<String,String> map = new HashMap<String,String>();
					map.put(TAG_SURNAME, temp.getKlienci_Nazwisko().toString());
					map.put(TAG_NAME, temp.getKlienci_Imie().toString());
					map.put(TAG_CITY, temp.getKlienci_Miasto().toString());
					map.put(TAG_PHONE, temp.getKlienci_Telefon().toString());
					map.put(TAG_MAIL, temp.getKlienci_Mail().toString());
					contentGroupsList.add(map);
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
							ContentGroup.this,contentGroupsList,R.layout.activity_contentgroup_items, new String[] {TAG_SURNAME, TAG_NAME, TAG_CITY, TAG_PHONE, TAG_MAIL},new int[]{R.id.surname, R.id.name, R.id.city, R.id.phone, R.id.mail});
					setListAdapter(adapter);
					
				}
			});
		}
		
	}
}
