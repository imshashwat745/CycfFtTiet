package com.imshashwat745.cycfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private Button loginbutton;
    private TextView signupButton;
    private EditText email,password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
    }
    private void init(){
        mAuth=FirebaseAuth.getInstance();
        loginbutton=findViewById(R.id.signupButton);
        signupButton=findViewById(R.id.existingUserButton);
        email=findViewById(R.id.emailInputSignup);
        password=findViewById(R.id.passwordInputSignup);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId=email.getText().toString();
                if(!EmailValidator.isThaparEmail(emailId)){
                    Toast.makeText(SignUpActivity.this, "Not a valid thapar email", Toast.LENGTH_SHORT).show();
                    return;
                }
                String pass=password.getText().toString();
                if(pass.length()<8){
                    Toast.makeText(SignUpActivity.this, "Minimum 8 length password", Toast.LENGTH_SHORT).show();
                    return;
                }
                createUser(emailId,pass);
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(i);
//                finish();
            }
        });
    }

    private void createUser(String emailId,String pass) {
        mAuth.createUserWithEmailAndPassword(emailId, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Signup success
                            sendEmailVerificationLink();
                        } else {
                            // Signup failed
                            Log.w("SignupActivity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendEmailVerificationLink() {
        mAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this,
                                    "Verification email sent. Please check your email.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("SignupActivity", "sendEmailVerification", task.getException());
                            Toast.makeText(SignUpActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}