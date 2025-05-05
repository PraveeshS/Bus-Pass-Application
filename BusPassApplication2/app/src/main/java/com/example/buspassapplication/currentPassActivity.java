package com.example.buspassapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class currentPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_pass);

        // Retrieve the current user ID
        String currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Retrieve the approved details from the Firebase Realtime Database
        DatabaseReference approvedRef = FirebaseDatabase.getInstance().getReference("ApprovedData").child(currentUser);
        approvedRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve the necessary information from the dataSnapshot
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String url = dataSnapshot.child("imageURL").getValue(String.class);
                    String college = dataSnapshot.child("college").getValue(String.class);
                    String from = dataSnapshot.child("from").getValue(String.class);
                    String to = dataSnapshot.child("to").getValue(String.class);
//                    Long mobileNo=dataSnapshot.child("mobileNo").getValue(Long.class);


                    // Display the approved details in the TextViews or other UI components
                    TextView nameTextView = findViewById(R.id.name);
                    ImageView imageView = findViewById(R.id.imageView2);
                    TextView collegeTv = findViewById(R.id.textView11);
                    TextView fromTv = findViewById(R.id.textView13);
                    TextView toTV = findViewById(R.id.textView14);

                    nameTextView.setText(" " + name);
                    collegeTv.setText(" " + college);
                    fromTv.setText(" " + from);
                    toTV.setText(" " + to);


                    Picasso.get().load(url).into(imageView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error appropriately
            }
        });
    }
}