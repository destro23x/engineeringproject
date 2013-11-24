package com.example.androidphp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
 
public class UserPage extends Activity {
 
	int workerID;
	Button bntViewStudents;
	Button bntViewActivities;
	Button bntNotifications;
	Button bntNotificationsRead;
	Button bntMyGroups;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);
        
        bntViewStudents = (Button) findViewById(R.id.bntAllStudents);
        bntViewActivities = (Button) findViewById(R.id.bntAllActivities);
        bntNotifications = (Button)findViewById(R.id.bntNotifications);
        bntNotificationsRead = (Button)findViewById(R.id.bntNotificationsRead);
        bntMyGroups = (Button)findViewById(R.id.bntGroups);
        
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
				Intent intent = new Intent(getApplicationContext(), AllLeaderActivities.class);
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
				Intent intent = new Intent(getApplicationContext(), AllLeaderGroups.class);
				intent.putExtra("workerID", workerID);
				startActivity(intent);
				
			}
		});
        
    }
}
