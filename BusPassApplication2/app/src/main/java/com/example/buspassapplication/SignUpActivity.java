package com.example.buspassapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword,ConfirmPassword;
    private Button signupButton;
    private TextView LoginRedirectText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        ConfirmPassword = findViewById(R.id.signup_confirm);
        signupButton = findViewById(R.id.signup_btn);
        LoginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String confirmPassword = ConfirmPassword.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (user.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                    return;
                }
                else if (!user.matches(emailPattern)) {
                    Toast.makeText(SignUpActivity.this, "Invalid email format" , Toast.LENGTH_SHORT).show();
                    return;
                }

                String passwordPattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).+";


                if (pass.isEmpty()) {
                    signupPassword.setError("Passwords cannot be empty");
                    return;

                }
                else if (pass.length() < 8) {
                    Toast.makeText(SignUpActivity.this, "Password should be at least 8 characters long" , Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!pass.matches(passwordPattern)) {
                    Toast.makeText(SignUpActivity.this, "Invalid password format" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pass.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match" , Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        });
        LoginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

    }
}