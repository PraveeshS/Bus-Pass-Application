package com.example.buspassapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FirstActivity extends AppCompatActivity {
    CardView btn_1,btn_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        btn_1=findViewById(R.id.admin);
        btn_2=findViewById(R.id.user);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstActivity.this,AdminLogin.class);
                startActivity(intent);

            }
        });
    }
}