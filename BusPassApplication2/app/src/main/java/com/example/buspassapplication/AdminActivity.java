package com.example.buspassapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends AppCompatActivity {
    private TextView textViewData;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        databaseRef = FirebaseDatabase.getInstance().getReference("ApplyData");

        textViewData = findViewById(R.id.textView);

        retrieveDataFromFirebase();
    }

    private void retrieveDataFromFirebase() {
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder dataBuilder = new StringBuilder();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Apply apply = childSnapshot.getValue(Apply.class);

                    if (apply != null) {
                        String data = "Name: " + apply.getName() + "\n"
                                + "College: " + apply.getCollegeName() + "\n"
                                + "Gender: " + apply.getGender() + "\n"
                                + "From: " + apply.getFrom() + "\n"
                                + "To: " + apply.getTo() + "\n"
                                + "Year: " + apply.getYear() + "\n"
                                + "Aadhaar No: " + apply.getAadhaarNo() + "\n"
                                + "Mobile No: " + apply.getMobileNo() + "\n\n";

                        dataBuilder.append(data);
                    }
                }

                String allData = dataBuilder.toString();
                textViewData.setText(allData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminActivity.this, "Error retrieving data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
