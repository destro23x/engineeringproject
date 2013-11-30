package pl.android.studiotanca;

import pl.sfs.service.SFSService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidphp.R;

public class NotificationsActivity extends Activity{
	
	int workerID;
	private ProgressDialog pDialog;
	EditText editTitle;
	EditText editDescription;
	Button bntSend;
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notifications);
		
		Bundle extras = getIntent().getExtras();
        if(extras != null){
        	workerID = extras.getInt("workerID");
        }
		
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
			try {
				service.addNotification(title,description,workerID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish();
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}
	}

}
