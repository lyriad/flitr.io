package com.lyriad.flitrio.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lyriad.flitrio.Classes.StaticData;
import com.lyriad.flitrio.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore fireDatabase;

    EditText dateText;
    TextInputEditText textGivenName, textMiddleName, textLastName, textEmail,
            textUsername, textPassword;
    ImageView dateButton, subscriptionHelp, backButton, profilePicture;
    Spinner countrySpinner, subscriptionSpinner;
    Button signUpButton, selectPictureButton;
    RadioButton male, female;
    RadioGroup genderGroup;
    ArrayAdapter<String> countryAdapter, subscriptionAdapter;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fireDatabase = FirebaseFirestore.getInstance();

        backButton = findViewById(R.id.sign_up_back_button);
        profilePicture = findViewById(R.id.sign_up_profile_picture);
        selectPictureButton = findViewById(R.id.sign_up_profile_picture_button);
        textGivenName = findViewById(R.id.sign_up_given_name);
        textMiddleName = findViewById(R.id.sign_up_middle_name);
        textLastName = findViewById(R.id.sign_up_last_name);
        textUsername = findViewById(R.id.sign_up_username);
        textEmail = findViewById(R.id.sign_up_email);
        dateText = findViewById(R.id.sign_up_date);
        dateButton = findViewById(R.id.sign_up_date_button);
        genderGroup = findViewById(R.id.sign_up_gender);
        male = findViewById(R.id.sign_up_male);
        female = findViewById(R.id.sign_up_female);
        countrySpinner = findViewById(R.id.sign_up_country);
        subscriptionSpinner = findViewById(R.id.sign_up_subscription);
        subscriptionHelp = findViewById(R.id.sign_up_subscription_help);
        textPassword = findViewById(R.id.sign_up_password);
        signUpButton = findViewById(R.id.sign_up_button);

        countryAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, StaticData.getCountries());
        countrySpinner.setAdapter(countryAdapter);

        subscriptionAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, StaticData.getSubscriptions());
        subscriptionSpinner.setAdapter(subscriptionAdapter);

        backButton.setOnClickListener(this);
        selectPictureButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        subscriptionHelp.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up_back_button:
                finish();
                break;

            case R.id.sign_up_profile_picture_button:
                Toast.makeText(this, "Select profile picture",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.sign_up_date_button:

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String formatedDay = (dayOfMonth < 10) ?
                                "0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                        String formatedMonth = (month < 10) ?
                                "0" + String.valueOf(month) : String.valueOf(month);

                        dateText.setText(formatedDay + "/" + formatedMonth + "/" + year);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        this, dateSetListener, year, month, day);
                dialog.show();
                break;

            case R.id.sign_up_subscription_help:
                startActivity(new Intent(SignUpActivity.this,
                        SubscriptionActivity.class));
                break;

            case R.id.sign_up_button:
                registerUser();
                finish();
                break;
        }
    }

    private void registerUser(){
        if (emptyField()){
            return;
        }

        DocumentReference reference = fireDatabase.collection("User")
                .document();
        String gender = "";
        if (male.isChecked()){
            gender = "Male";
        }else if(female.isChecked()){
            gender = "Female";
        }

        Map<String, Object> newUser = new HashMap<>();
        try {
            newUser.put("Given Name", textGivenName.getText().toString().trim());
            newUser.put("Middle Name", textMiddleName.getText().toString().trim());
            newUser.put("Last Name", textLastName.getText().toString().trim());
            newUser.put("Birthdate", dateText.getText().toString().trim());
            newUser.put("Country of Origin", countrySpinner.getSelectedItem().toString());
            newUser.put("Gender", gender);
            newUser.put("Username", textUsername.getText().toString().trim());
            newUser.put("Password", textPassword.getText().toString());
            newUser.put("Email", textEmail.getText().toString().trim());
            newUser.put("Register date", new Date());
            newUser.put("Last Access date", new Date());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        fireDatabase.collection(StaticData.getUserCollection()).document(reference.getId())
                .set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUpActivity.this, "Signed Up successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Sign Up failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean emptyField(){

        InputMethodManager keyboard = (InputMethodManager) getSystemService(SignUpActivity.this.
                INPUT_METHOD_SERVICE);

        if (textGivenName.getText().toString().isEmpty()){
            Toast.makeText(SignUpActivity.this, "Please enter your name",
                    Toast.LENGTH_SHORT).show();
            textGivenName.requestFocus();
            keyboard.showSoftInput(textGivenName, InputMethodManager.SHOW_IMPLICIT);
            return true;
        }else if(textLastName.getText().toString().isEmpty()){
            Toast.makeText(SignUpActivity.this, "Please enter you last name",
                    Toast.LENGTH_SHORT).show();
            textLastName.requestFocus();
            keyboard.showSoftInput(textLastName, InputMethodManager.SHOW_IMPLICIT);
            return true;
        }else if (textUsername.getText().toString().isEmpty()){
            Toast.makeText(SignUpActivity.this, "Please enter a username",
                    Toast.LENGTH_SHORT).show();textUsername.requestFocus();
            keyboard.showSoftInput(textUsername, InputMethodManager.SHOW_IMPLICIT);
            return true;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString().trim()).matches()){
            Toast.makeText(SignUpActivity.this, "Please enter a valid email adress",
                    Toast.LENGTH_SHORT).show();
            textEmail.requestFocus();
            keyboard.showSoftInput(textEmail, InputMethodManager.SHOW_IMPLICIT);
            return true;
        }else if (dateText.getText().toString().isEmpty()){
            Toast.makeText(SignUpActivity.this, "Please enter your birth date",
                    Toast.LENGTH_SHORT).show();
            dateText.requestFocus();
            return true;
        }else if (!male.isChecked() && !female.isChecked()){
            Toast.makeText(SignUpActivity.this, "Please select your gender",
                    Toast.LENGTH_SHORT).show();
            return true;
        }else if (countrySpinner.getSelectedItemPosition() == 0){
            Toast.makeText(SignUpActivity.this, "Please select your country of origin",
                    Toast.LENGTH_SHORT).show();
            return true;
        }else if (subscriptionSpinner.getSelectedItemPosition() == 0){
            Toast.makeText(SignUpActivity.this, "Please select a subscription",
                    Toast.LENGTH_SHORT).show();
            return true;
        }else if (textPassword.getText().toString().isEmpty()){
            Toast.makeText(SignUpActivity.this, "Please enter a password",
                    Toast.LENGTH_SHORT).show();
            textPassword.requestFocus();
            keyboard.showSoftInput(textPassword, InputMethodManager.SHOW_IMPLICIT);
            return true;
        }
        return false;
    }
}
