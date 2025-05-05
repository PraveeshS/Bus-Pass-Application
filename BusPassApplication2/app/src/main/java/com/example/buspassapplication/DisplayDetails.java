package com.example.buspassapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DisplayDetails extends AppCompatActivity {
    Button approve_btn;
    private StorageReference databaseRef;
    private boolean isButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);
        approve_btn=findViewById(R.id.Approve);

        // Retrieve the extras from the Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String college = intent.getStringExtra("college");
        String gender = intent.getStringExtra("gender");
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");
        String year = intent.getStringExtra("year");
        String aadhaar= intent.getStringExtra("aadhaar");
        String mobile = intent.getStringExtra("mobile");
        String imageURL = intent.getStringExtra("imageUrl");

        // Display the details in the TextViews or other UI components
        TextView Name = findViewById(R.id.textView2);
        TextView College = findViewById(R.id.textView3);
        TextView Gender = findViewById(R.id.textView4);
        TextView From = findViewById(R.id.textView5);
        TextView To = findViewById(R.id.textView6);
        TextView Year = findViewById(R.id.textView7);
        TextView Aadhaar = findViewById(R.id.textView8);
        TextView Mobile = findViewById(R.id.textView9);
        ImageView imageView = findViewById(R.id.imageView);

        Name.setText("Name: " + name);
        College.setText("College: " + college);
        Gender.setText("Gender: " + gender);
        From.setText("From: " + from);
        To.setText("To: " + to);
        Year.setText("Year: " + year);
        Aadhaar.setText("Aadhaar No: " + aadhaar);
        Mobile.setText("Mobile No: " + mobile);

        Picasso.get()
                .load(imageURL)
                .into(imageView);
        approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isButtonClicked) {
                    // Get the current user ID
                    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("ApplyData").child(currentUser);
                    databaseRef.child("passStatus").setValue("approved");
                    // Retrieve the extras from the Intent
                    Intent intent = getIntent();
                    String name = intent.getStringExtra("name");
                    String college = intent.getStringExtra("college");
                    String gender = intent.getStringExtra("gender");
                    String from = intent.getStringExtra("from");
                    String to = intent.getStringExtra("to");
                    String year = intent.getStringExtra("year");
                    String aadhaar = intent.getStringExtra("aadhaar");
                    String mobile = intent.getStringExtra("mobile");
                    String imageURL = intent.getStringExtra("imageUrl");

                    DatabaseReference approvedRef = FirebaseDatabase.getInstance().getReference("ApprovedData").child(currentUser);
                    approvedRef.child("name").setValue(name);
                    approvedRef.child("college").setValue(college);
                    approvedRef.child("gender").setValue(gender);
                    approvedRef.child("from").setValue(from);
                    approvedRef.child("to").setValue(to);
                    approvedRef.child("year").setValue(year);
                    approvedRef.child("aadhaarNo").setValue(aadhaar);
                    approvedRef.child("mobileNo").setValue(mobile);
                    approvedRef.child("imageURL").setValue(imageURL);

                    Intent intent1 = new Intent(DisplayDetails.this, currentPassActivity.class);
                    intent1.putExtra("name", name);
                    intent1.putExtra("imageURL", imageURL);
                    intent1.putExtra("college", college);
                    intent1.putExtra("from", from);
                    intent1.putExtra("to", to);


                    startActivity(intent1);
                    approve_btn.setEnabled(false);
                    isButtonClicked = true;
                }
            }
        });


    }
}