package pl.android.studiotanca;


import pl.sfs.service.SFSService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidphp.R;
 
public class LoginActivity extends Activity {
    
	Button b;
    EditText login,pass;
    ProgressDialog dialog = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        b = (Button)findViewById(R.id.Button01);
        login = (EditText)findViewById(R.id.username);
        pass= (EditText)findViewById(R.id.password);
         
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(LoginActivity.this, "", 
                        "Przetwarzanie danych...", true);
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
             
            if(response == true){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this,"Zalogowano u¿ytkownika", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext()/*AndroidPHPLogin.this*/, UserPageActivity.class);
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
        LoginActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("B³¹d logowania");
                builder.setMessage("Nie znaleziono u¿ytkownika")  
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

