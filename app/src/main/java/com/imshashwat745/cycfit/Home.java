package com.imshashwat745.cycfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity implements LocationAdapter.BookButtonClickInterface {
    private RecyclerView locationRV;
    private List<LocationModel> lst;
    private LocationAdapter locationAdapter;
    private Button signout;
    private FirebaseAuth mauth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        init();

    }
    private void init(){
        locationRV=findViewById(R.id.locationRV);
        lst=new ArrayList<>();

        populateRV();
        locationAdapter=new LocationAdapter(lst,this);
        locationRV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        locationRV.setNestedScrollingEnabled(false);
        locationRV.setAdapter(locationAdapter);
        mauth=FirebaseAuth.getInstance();
        signout=findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mauth.signOut();
                Intent i=new Intent(Home.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void populateRV() {
        db.collection("Locations").document("LocationSet1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot=task.getResult();
                    if(snapshot.exists()){
                        Map<String,Object>  data=snapshot.getData();
                        for(Map.Entry<String,Object> entry:data.entrySet()){
                            String locationName= entry.getKey();
                            Object fieldValue = entry.getValue();

                            if (fieldValue instanceof Long) {
                                // If the field is a Long, convert it to int
                                int intValue = ((Long) fieldValue).intValue();
                                if(intValue>0){
                                    lst.add(new LocationModel(locationName,intValue));
                                    locationAdapter.notifyDataSetChanged();
                                }
                            } else {
                                // Handle other types or leave as Object if needed
                            }

                        }
                    }
                }
            }
        });

    }

    @Override
    public void click(int position) {
        String locationName = lst.get(position).getLocationName();
        Toast.makeText(this, position + " position clicked", Toast.LENGTH_SHORT).show();

        db.collection("Locations").document("LocationSet1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    Map<String, Object> data = snapshot.getData();
                    if (data != null && data.containsKey(locationName)) {
                        // Get the current number of bikes
                        long currentNumberOfBikes = (long) data.get(locationName);

                        // Check if there are bikes available
                        if (currentNumberOfBikes > 0) {
                            // Reduce the number of bikes by one
                            long newNumberOfBikes = currentNumberOfBikes - 1;

                            // Update the database with the new number of bikes
                            db.collection("Locations").document("LocationSet1")
                                    .update(locationName, newNumberOfBikes)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> updateTask) {
                                            if (updateTask.isSuccessful()) {
                                                // Successfully updated the number of bikes
                                                Intent i=new Intent(Home.this,Receive_Bicycle.class);
                                                startActivity(i);
                                            } else {
                                                // Handle update failure
                                                Toast.makeText(Home.this, "Failed to update the number of bikes", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // No bikes available to reduce
                            Toast.makeText(Home.this, "No bikes available at " + locationName, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

}