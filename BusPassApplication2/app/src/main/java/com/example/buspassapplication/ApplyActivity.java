package com.example.buspassapplication;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ApplyActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button selectImageButton;
    private EditText nameet, college, fromet, toet, yearet, aadhaar, mobile;
    private RadioGroup genderRadioGroup;
    private DatabaseReference databaseRef;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        imageView = findViewById(R.id.imageView);
        selectImageButton = findViewById(R.id.ImageButton);
        databaseRef = FirebaseDatabase.getInstance().getReference("ApplyData");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("Image");

        nameet = findViewById(R.id.etName);
        college = findViewById(R.id.college);
        fromet = findViewById(R.id.From);
        toet = findViewById(R.id.To);
        yearet = findViewById(R.id.Yearstudy);
        aadhaar = findViewById(R.id.Aadhaar);
        mobile = findViewById(R.id.Number);
        genderRadioGroup = findViewById(R.id.radioGroupGender);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        Uri imageUri = result;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        Button saveButton = findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToFirebase();
            }
        });
    }

    private void openImagePicker() {
        imagePickerLauncher.launch("image/*");
    }

    private void saveDataToFirebase() {
        int selectedRadioButtonId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String gender = selectedRadioButton.getText().toString();

        String name = nameet.getText().toString().trim();
        String collegeName = college.getText().toString().trim();
        String from = fromet.getText().toString().trim();
        String to = toet.getText().toString().trim();
        String year = yearet.getText().toString().trim();
        String aadhaarNo = aadhaar.getText().toString().trim();
        String mobileNo = mobile.getText().toString().trim();



        Apply apply = new Apply(name, collegeName, gender, from, to, year, aadhaarNo, mobileNo);
        apply.setPassStatus("pending");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseRef.child(userId).setValue(apply);

        String filename = name + ".jpg";
        StorageReference imageRef = storageRef.child("Image/" + filename);

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        ByteArrayOutputStream image = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, image);
        byte[] imageData = image.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(imageData);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageURL = uri.toString();
                apply.setImageURL(imageURL);

                // Add the imageURL to the database
                databaseRef.child(userId).child("imageURL").setValue(imageURL);
                databaseRef.child(userId).setValue(apply);

                college.setText("");
                fromet.setText("");
                toet.setText("");
                yearet.setText("");
                aadhaar.setText("");
                mobile.setText("");
                genderRadioGroup.clearCheck();

                Toast.makeText(this, "Data stored successfully", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show();
        });
    }
}