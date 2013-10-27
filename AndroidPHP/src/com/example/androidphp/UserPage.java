package com.example.androidphp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
 
public class UserPage extends Activity {
 
	String username;
	Button bntViewStudents;
	Button bntViewActivities;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);
        
        bntViewStudents = (Button) findViewById(R.id.bntAllStudents);
        bntViewActivities = (Button) findViewById(R.id.bntAllActivities);
        
        Bundle extras = getIntent().getExtras();
        if(extras != null){
        	username = extras.getString("username");
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
				intent.putExtra("username", username);
				startActivity(intent);
				
			}
		});
        
    }
}
