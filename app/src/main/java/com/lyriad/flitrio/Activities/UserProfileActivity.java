package com.lyriad.flitrio.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.Classes.User;
import com.lyriad.flitrio.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseFirestore fireDatabase;
    private FirebaseAuth fireAuthentication;
    FirebaseUser currentUser;
    User myUser;

    CircularImageView profileImage, toolbarProfilePicture;
    TextView username, names, birthdate, countryOfOrigin, email, subscription;
    ImageView backButton, homeButton, searchButton;
    Button changePasswordButton, signOutButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fireDatabase = FirebaseFirestore.getInstance();
        fireAuthentication = FirebaseAuth.getInstance();
        currentUser = fireAuthentication.getCurrentUser();
        myUser = FirebaseData.getCurrentUser();

        backButton = findViewById(R.id.user_profile_back_button);
        profileImage = findViewById(R.id.user_profile_picture);
        username = findViewById(R.id.user_profile_username);
        names = findViewById(R.id.user_profile_names);
        birthdate = findViewById(R.id.user_profile_birthdate);
        countryOfOrigin = findViewById(R.id.user_profile_country);
        email = findViewById(R.id.user_profile_email);
        subscription = findViewById(R.id.user_profile_subscription);
        changePasswordButton = findViewById(R.id.user_profile_change_password);
        signOutButton = findViewById(R.id.user_profile_sign_out);
        toolbarProfilePicture = findViewById(R.id.user_profile_picture_toolbar);
        searchButton = findViewById(R.id.user_profile_search_button);
        homeButton = findViewById(R.id.user_profile_home);

        backButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);

        String date = String.valueOf(myUser.getBirthDate().getDayOfMonth()) +
                " " + myUser.getBirthDate().getMonth().toString().charAt(0) +
                myUser.getBirthDate().getMonth().toString().toLowerCase().substring(1) +
                " " + String.valueOf(myUser.getBirthDate().getYear());

        Glide.with(this).load(currentUser.getPhotoUrl()).into(profileImage);
        Glide.with(this).load(currentUser.getPhotoUrl()).into(toolbarProfilePicture);
        username.setText(myUser.getUsername());
        names.setText(currentUser.getDisplayName());
        birthdate.setText(date);
        countryOfOrigin.setText(myUser.getCountryOfOrigin());
        email.setText(currentUser.getEmail());
        subscription.setText(myUser.getSubscription());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_profile_back_button:
                finish();
                break;
            case R.id.user_profile_sign_out:
                fireAuthentication.signOut();
                finish();
                break;
            case R.id.user_profile_search_button:
                startActivity(new Intent(UserProfileActivity.this, SearchActivity.class));
                break;
            case R.id.user_profile_home:
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.user_profile_change_password:
                Toast.makeText(this, "change password", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
