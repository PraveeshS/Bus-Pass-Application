package com.example.buspassapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectPassActivity extends AppCompatActivity {
    CardView newPass_btn, currentPass_btn;
    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pass);

        newPass_btn = findViewById(R.id.new_pass);
        currentPass_btn = findViewById(R.id.current_pass);
        databaseRef = FirebaseDatabase.getInstance().getReference();

        newPass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectPassActivity.this, ApplyActivity.class));
            }
        });

        currentPass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPassStatus();
            }
        });
    }

    private void checkPassStatus() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = databaseRef.child("ApplyData").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String passStatus = dataSnapshot.child("passStatus").getValue(String.class);
                    if (passStatus != null && passStatus.equals("approved")) {
                        startActivity(new Intent(SelectPassActivity.this, currentPassActivity.class));
                    } else if (passStatus.equals("pending")){
                        Toast.makeText(SelectPassActivity.this, "Your pass has not been approved yet", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SelectPassActivity.this, "You haven't applied for pass", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}
