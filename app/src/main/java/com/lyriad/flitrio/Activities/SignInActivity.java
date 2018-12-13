package com.lyriad.flitrio.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lyriad.flitrio.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText textUsername, textPassword;
    private TextView textSignUp;
    private Button buttonSignIn;
    private ImageView settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        buttonSignIn = findViewById(R.id.sign_in_button);
        textUsername = findViewById(R.id.sign_in_username);
        textPassword = findViewById(R.id.sign_in_password);
        textSignUp = findViewById(R.id.sign_in_create_account);
        settingsButton = findViewById(R.id.settings_button);

        buttonSignIn.setOnClickListener(this);
        textSignUp.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_create_account:
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                break;
            case R.id.sign_in_button:
                Toast.makeText(getBaseContext(), "Logged in Successfully",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings_button:
                Toast.makeText(getBaseContext(), "Settings",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
