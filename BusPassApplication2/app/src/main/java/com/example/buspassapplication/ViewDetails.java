package com.example.buspassapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewDetails extends AppCompatActivity {
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        databaseRef = FirebaseDatabase.getInstance().getReference(); // Initialize the database reference

        retrieveChildNodeNames(); // Call the method to retrieve and display child node names
    }

    private void retrieveChildNodeNames() {
        databaseRef.child("ApplyData").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String childNodeName = dataSnapshot.getKey();
                String name = dataSnapshot.child("name").getValue(String.class);

                // Retrieve the LinearLayout container
                LinearLayout linearLayoutContainer = findViewById(R.id.linearLayoutContainer);

                // Create a new CardView
                CardView cardView = new CardView(ViewDetails.this);
                cardView.setBackground(getResources().getDrawable(R.drawable.lavender_border));
                LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        150);
                cardLayoutParams.setMargins(40, 16, 40, 16);
                cardView.setLayoutParams(cardLayoutParams);
                cardView.setPadding(16, 16, 16, 16);
                cardView.setCardElevation(4);
                cardView.setUseCompatPadding(true);

                // Create a new LinearLayout to hold the TextView and Button
                LinearLayout linearLayout = new LinearLayout(ViewDetails.this);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setPadding(100, 16, 16, 16);

                // Create a TextView for the child node name
                // Create a TextView for the child node name
                TextView textViewChildNode = new TextView(ViewDetails.this);
                textViewChildNode.setText(name);
                textViewChildNode.setTextSize(24);
                textViewChildNode.setTextColor(getResources().getColor(R.color.black));
                LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );
                textLayoutParams.setMargins(100, 0, 30, 0); // Set the left margin to 20 pixels
                textViewChildNode.setLayoutParams(textLayoutParams);
                linearLayout.addView(textViewChildNode);


                // Create a Button for viewing details
                Button buttonViewDetails = new Button(ViewDetails.this);
                buttonViewDetails.setText("View Details");
                buttonViewDetails.setTextColor(getResources().getColor(R.color.white));
                buttonViewDetails.setBackground(getResources().getDrawable(R.drawable.button));
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(300,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                buttonLayoutParams.setMargins(0, 0, 60, 0); // Set the right margin to 20 pixels
                buttonViewDetails.setLayoutParams(buttonLayoutParams);
                buttonViewDetails.setLayoutParams(buttonLayoutParams);
                linearLayout.addView(buttonViewDetails);

                // Add the LinearLayout to the CardView
                cardView.addView(linearLayout);

                // Add the CardView to the LinearLayout container
                linearLayoutContainer.addView(cardView);

                buttonViewDetails.setOnClickListener(view -> {
                    databaseRef.child("ApplyData").child(childNodeName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Apply apply = dataSnapshot.getValue(Apply.class);
                            String imageURL = apply.getImageURL();

                            Intent intent = new Intent(ViewDetails.this, DisplayDetails.class);
                            // Pass the data as extras
                            intent.putExtra("name", apply.getName());
                            intent.putExtra("college", apply.getCollegeName());
                            intent.putExtra("gender", apply.getGender());
                            intent.putExtra("from", apply.getFrom());
                            intent.putExtra("to", apply.getTo());
                            intent.putExtra("year", apply.getYear());
                            intent.putExtra("aadhaar", apply.getAadhaarNo());
                            intent.putExtra("mobile", apply.getMobileNo());
                            intent.putExtra("imageUrl", imageURL);

                            // Start the new activity
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ViewDetails.this, "Error retrieving data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }

            // Other ChildEventListener methods

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Handle child changed event
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Handle child removed event
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Handle child moved event
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewDetails.this, "Error retrieving child nodes: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
