package com.lyriad.flitrio.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lyriad.flitrio.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText textUsername, textPassword;
    private TextView textSignUp;
    private Button buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        buttonSignIn = findViewById(R.id.sign_in_button);
        textUsername = findViewById(R.id.sign_in_username);
        textPassword = findViewById(R.id.sign_in_password);
        textSignUp = findViewById(R.id.sign_in_create_account);

        buttonSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button){
            Toast.makeText(getBaseContext(), "Logged in Successfully",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
