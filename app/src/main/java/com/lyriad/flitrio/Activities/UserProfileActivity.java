package com.lyriad.flitrio.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.Classes.User;
import com.lyriad.flitrio.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore fireDatabase;
    private FirebaseAuth fireAuthentication;
    private FirebaseUser currentUser;
    private User myUser;

    CircularImageView profileImage;
    TextView username, names;
    ImageView backButton;
    Button signOutButton;

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
        signOutButton = findViewById(R.id.user_profile_sign_out);

        backButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);

        Glide.with(this).load(currentUser.getPhotoUrl()).into(profileImage);
        username.setText(myUser.getUsername());
        names.setText(currentUser.getDisplayName());
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
        }
    }
}
