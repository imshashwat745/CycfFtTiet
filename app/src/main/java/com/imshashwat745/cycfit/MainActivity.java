package com.imshashwat745.cycfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button loginbutton;
    private TextView signupButton;
    private EditText email,password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }
    private void init(){
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null && user.isEmailVerified()){
            Intent i=new Intent(MainActivity.this,Home.class);
            startActivity(i);
            finish();
        }
        loginbutton=findViewById(R.id.loginButton);
        signupButton=findViewById(R.id.newUserButton);
        email=findViewById(R.id.emailInput);
        password=findViewById(R.id.passwordInput);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin();
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void tryLogin() {
        String emailId=email.getText().toString();
        String pass=password.getText().toString();
        if(email.length()==0||pass.length()==0){
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
       mAuth.signInWithEmailAndPassword(emailId,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   FirebaseUser currUser=mAuth.getCurrentUser();
                   if(currUser!=null&&!currUser.isEmailVerified()){
                       Toast.makeText(MainActivity.this, "Verify your email first", Toast.LENGTH_SHORT).show();
                   }else{
                       Intent i=new Intent(MainActivity.this,Home.class);
                       startActivity(i);
                       finish();
                   }
               }else{
                   Toast.makeText(MainActivity.this, "Login Failed;", Toast.LENGTH_SHORT).show();
               }
           }
       });
    }
}