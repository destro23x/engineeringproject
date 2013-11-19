package com.example.androidphp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
 
public class UserPage extends Activity {
 
	int workerID;
	Button bntViewStudents;
	Button bntViewActivities;
	Button bntNotifications;
	Button bntMyGroups;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);
        
        bntViewStudents = (Button) findViewById(R.id.bntAllStudents);
        bntViewActivities = (Button) findViewById(R.id.bntAllActivities);
        bntNotifications = (Button)findViewById(R.id.bntNotifications);
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
