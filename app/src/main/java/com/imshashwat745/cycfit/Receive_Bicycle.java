package com.imshashwat745.cycfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Receive_Bicycle extends AppCompatActivity {
    private Button Open;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_bicycle);
        Open=findViewById(R.id.open);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("id");
        Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.setValue("1234").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Successfully updated the value
                            Toast.makeText(Receive_Bicycle.this, "Enjoy Your Ride!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Receive_Bicycle.this,Home.class));
                            finish();
                        } else {
                            // Handle update failure
                            Toast.makeText(Receive_Bicycle.this, "Some error Ocuured", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}