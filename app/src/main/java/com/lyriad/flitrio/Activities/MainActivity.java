package com.lyriad.flitrio.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lyriad.flitrio.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth fireAuthentication;
    private FirebaseUser currentUser;

    ImageView settingsButton;
    CircularImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireAuthentication = FirebaseAuth.getInstance();
        currentUser = fireAuthentication.getCurrentUser();

        profileImage = findViewById(R.id.main_profile_picture);
        settingsButton = findViewById(R.id.main_settings_button);

        profileImage.setOnClickListener(this);
        settingsButton.setOnClickListener(this);

        profileImage.setImageURI(currentUser.getPhotoUrl());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_profile_picture:
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                break;
            case R.id.main_settings_button:
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
