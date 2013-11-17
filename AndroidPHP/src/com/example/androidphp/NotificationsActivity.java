package com.example.androidphp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import pl.sfs.service.SFSService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotificationsActivity extends Activity{
	
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	EditText editTitle;
	EditText editDescription;
	Button bntSend;
	
	private static String url_create_notification = "http://10.0.0.5/createNotifications.php";
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notifications);
		
		editTitle = (EditText)findViewById(R.id.editTitle);
		editDescription = (EditText)findViewById(R.id.editDescribe);
		bntSend = (Button)findViewById(R.id.buttonSend);
		
		bntSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CreateNotification().execute();
			}
		});
	}
	
	class CreateNotification extends AsyncTask<String,String,String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NotificationsActivity.this);
			pDialog.setMessage("Creating Notification..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String title = editTitle.getText().toString();
			String description = editDescription.getText().toString();
			
			SFSService service = new SFSService();
			service.addNotification("qwerty", "", pracownikId);
			
			
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("title",title));
			parameters.add(new BasicNameValuePair("description",description));
			JSONObject json = jsonParser.makeHttpRequest(url_create_notification,
					"POST", parameters);
			Log.d("Create notification",json.toString());
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					
					// closing this screen
					finish();
				} else {
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}
	}

}
