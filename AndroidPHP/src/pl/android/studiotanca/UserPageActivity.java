package pl.android.studiotanca;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.androidphp.R;
 
public class UserPageActivity extends Activity {
 
	int workerID;
	Button bntViewStudents;
	Button bntViewActivities;
	Button bntNotifications;
	Button bntNotificationsRead;
	Button bntMyGroups;
	Button presenceBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);
        
        bntViewStudents = (Button) findViewById(R.id.bntAllStudents);
        bntViewActivities = (Button) findViewById(R.id.bntAllActivities);
        bntNotifications = (Button)findViewById(R.id.bntNotifications);
        bntNotificationsRead = (Button)findViewById(R.id.bntNotificationsRead);
        bntMyGroups = (Button)findViewById(R.id.bntGroups);
        presenceBtn = (Button)findViewById(R.id.presenceBtn);
        
        Bundle extras = getIntent().getExtras();
        if(extras != null){
        	workerID = extras.getInt("workerID");
        }
        
        bntViewStudents.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), AllStudentsActivity.class));
			}
		});
        
        bntViewStudents.setHint("lolololo");
        bntViewActivities.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), AllLeaderEventsActivity.class);
				intent.putExtra("workerID", workerID);
				startActivity(intent);
				
			}
		});
        
        bntNotifications.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
				intent.putExtra("workerID", workerID);
				startActivity(intent);
				
			}
		});
        
        bntNotificationsRead.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), NotificationsReadActivity.class));
			}
		});
        
        bntMyGroups.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), AllLeaderGroupsActivity.class);
				intent.putExtra("workerID", workerID);
				startActivity(intent);
				
			}
		});
        
        presenceBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	
				Intent intent = new Intent(getApplicationContext(), LessonsActivity.class);
				intent.putExtra("workerId", workerID);
				startActivity(intent);
			}
		});
        
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
}
