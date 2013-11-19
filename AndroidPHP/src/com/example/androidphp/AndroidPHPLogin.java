package com.example.androidphp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import pl.sfs.service.SFSService;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class AndroidPHPLogin extends Activity {
    
	Button b;
    EditText login,pass;
    TextView tv;
    ProgressDialog dialog = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setTitle("siema"); 
        
        b = (Button)findViewById(R.id.Button01);
        login = (EditText)findViewById(R.id.username);
        pass= (EditText)findViewById(R.id.password);
        tv = (TextView)findViewById(R.id.tv);
         
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(AndroidPHPLogin.this, "", 
                        "Validating user...", true);
                 new Thread(new Runnable() {
                        public void run() {
                            login();                          
                        }
                      }).start();               
            }
        });
    }
     
    void login(){
        try{  
        	final boolean response;
        	SFSService service = new SFSService();
        	response = service.verifyUser(login.getText().toString(), pass.getText().toString());
            System.out.println("Response : " + response); 
            runOnUiThread(new Runnable() {
                public void run() {
                	if(response == true){
                		tv.setText("User authenticated");
                	}
                	else{
                		tv.setText("User not found");
                	}
                    dialog.dismiss();
                }
            });
             
            if(response == true){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AndroidPHPLogin.this,"Login Success", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext()/*AndroidPHPLogin.this*/, UserPage.class);
                intent.putExtra("workerID", service.getAssignWorker(login.getText().toString()));
                startActivity(intent);
                finish();
            }else{
                showAlert();                
            }
             
        }catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
    
    public void showAlert(){
        AndroidPHPLogin.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(AndroidPHPLogin.this);
                builder.setTitle("Login Error.");
                builder.setMessage("User not Found.")  
                       .setCancelable(false)
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                           }
                       });                     
                AlertDialog alert = builder.create();
                alert.show();               
            }
        });
    }
}

