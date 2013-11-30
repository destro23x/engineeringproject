package pl.android.studiotanca;

import java.util.ArrayList;
import java.util.HashMap;

import pl.sfs.model.Powiadomienie;
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

public class NotificationsReadActivity extends ListActivity {
	
	ListView lv;
	ProgressDialog pDialog;
	ArrayList<HashMap<String,String>> notificationsList;
	ArrayList<Powiadomienie> notifications;
	
	//JSON Node names
	private static final String TAG_TITLE = "title";
	private static final String TAG_EPIC = "epic";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		notificationsList = new ArrayList<HashMap<String,String>>();
		lv = getListView();
		new LoadAllNotifications().execute();
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				AlertDialog.Builder adb = new AlertDialog.Builder(NotificationsReadActivity.this);
				adb.setTitle("Szczegó³y powiadomienia");
				Powiadomienie temp = notifications.get(position);
				StringBuilder sb = new StringBuilder();
				sb.append("Tytu³:" + temp.getPowiadomienia_Tytul().toString());
				sb.append("\n");
				sb.append("Opis:" + temp.getPowiadomienia_Tresc().toString());
				sb.append("\n");
				adb.setMessage(sb);
				adb.setPositiveButton("Ok", null);
				adb.show();
				
			}
			
		});
	}
	
	class LoadAllNotifications extends AsyncTask<String,String,String> {

		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(NotificationsReadActivity.this);
			pDialog.setMessage("Przetwarzanie listy powiadomieñ. Proszê czekaæ ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			SFSService service = new SFSService();
			try {
				notifications = service.getNotifications();
				for(int i = 0 ; i < notifications.size(); i++){
					Powiadomienie temp = notifications.get(i);
					HashMap<String,String> map = new HashMap<String,String>();
					map.put(TAG_TITLE, temp.getPowiadomienia_Tytul().toString());
					map.put(TAG_EPIC, temp.getPowiadomienia_Tresc().toString());
					notificationsList.add(map);
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
							NotificationsReadActivity.this,notificationsList,R.layout.activity_notificationsread_items, new String[] {TAG_TITLE,TAG_EPIC},new int[]{R.id.title,R.id.epic});
					setListAdapter(adapter);
					
				}
			});
		}
		
	}
}