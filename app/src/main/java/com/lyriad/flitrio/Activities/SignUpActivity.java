package com.lyriad.flitrio.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lyriad.flitrio.Classes.StaticData;
import com.lyriad.flitrio.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore fireDatabase;
    private FirebaseAuth fireAuthentication;
    private StorageReference storageRef;
    private Uri imageUri;

    EditText dateText;
    TextInputEditText textGivenName, textMiddleName, textLastName, textEmail,
            textUsername, textPassword, textConfirmPassword;
    ImageView dateButton, subscriptionHelp, backButton;
    CircularImageView profilePicture;
    Spinner countrySpinner, subscriptionSpinner;
    Button signUpButton;
    RadioButton male, female;
    RadioGroup genderGroup;
    ArrayAdapter<String> countryAdapter, subscriptionAdapter;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fireDatabase = FirebaseFirestore.getInstance();
        fireAuthentication = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        imageUri = null;

        backButton = findViewById(R.id.sign_up_back_button);
        profilePicture = findViewById(R.id.sign_up_profile_picture);
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
        textConfirmPassword = findViewById(R.id.sign_up_confirm_password);
        signUpButton = findViewById(R.id.sign_up_button);

        countryAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, StaticData.getCountries());
        countrySpinner.setAdapter(countryAdapter);

        subscriptionAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, StaticData.getSubscriptions());
        subscriptionSpinner.setAdapter(subscriptionAdapter);

        backButton.setOnClickListener(this);
        profilePicture.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        subscriptionHelp.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up_back_button:
                finish();
                break;

            case R.id.sign_up_profile_picture:
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
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
                if (registerUser()){
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&
                resultCode == RESULT_OK && data != null){

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                imageUri = result.getUri();
                profilePicture.setImageURI(imageUri);

        }
    }

    private boolean registerUser(){
        if (emptyField()){
            return false;
        }

        fireAuthentication.createUserWithEmailAndPassword(textEmail.getText().toString().trim(),
                textPassword.getText().toString()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (imageUri != null){
                        String ext = imageUri.toString().substring(imageUri.toString().lastIndexOf('.'));
                        StorageReference filePath = storageRef
                                .child(StaticData.getProfilePictureFolder())
                                .child(textUsername.getText().toString() + ext);

                        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        saveToDatabase(uri.toString());
                                    }
                                });
                            }
                        });
                    }else{
                        saveToDatabase(StaticData.getEmptyPicture());
                    }
                }else{
                    if (task.getException() != null){
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Failed to register user",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return true;
    }

    private boolean emptyField(){

        InputMethodManager keyboard = (InputMethodManager) getSystemService(SignUpActivity.this.
                INPUT_METHOD_SERVICE);

        if (textGivenName.getText().toString().trim().isEmpty()){
            Toast.makeText(SignUpActivity.this, "Please enter your name",
                    Toast.LENGTH_SHORT).show();
            textGivenName.requestFocus();
            keyboard.showSoftInput(textGivenName, InputMethodManager.SHOW_IMPLICIT);
            return true;
        }else if(textLastName.getText().toString().trim().isEmpty()){
            Toast.makeText(SignUpActivity.this, "Please enter you last name",
                    Toast.LENGTH_SHORT).show();
            textLastName.requestFocus();
            keyboard.showSoftInput(textLastName, InputMethodManager.SHOW_IMPLICIT);
            return true;
        }else if (textUsername.getText().toString().trim().isEmpty()){
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
        }else if (dateText.getText().toString().trim().isEmpty()){
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
        }else if (textPassword.getText().toString().length() < 6){
            Toast.makeText(SignUpActivity.this, "The password must be at least 6 characters long",
                    Toast.LENGTH_SHORT).show();
            textPassword.requestFocus();
            keyboard.showSoftInput(textPassword, InputMethodManager.SHOW_IMPLICIT);
            return true;
        }else if(textConfirmPassword.getText().toString().isEmpty()){
            Toast.makeText(SignUpActivity.this, "Please confirm your password",
                    Toast.LENGTH_SHORT).show();
            textConfirmPassword.requestFocus();
            keyboard.showSoftInput(textConfirmPassword, InputMethodManager.SHOW_IMPLICIT);
            return true;
        }else if (!textPassword.getText().toString().
                equals(textConfirmPassword.getText().toString())){
            Toast.makeText(SignUpActivity.this, "Passwords do not match",
                    Toast.LENGTH_SHORT).show();
            textPassword.requestFocus();
            return true;
        }
        return false;
    }

    private void saveToDatabase(String imageUrl){
        DocumentReference reference = fireDatabase
                .collection(StaticData.getUserCollection()).document();
        Map<String, Object> newUser = new HashMap<>();

        String gender = "";
        if (male.isChecked()) gender = "Male";
        if(female.isChecked()) gender = "Female";

        try {
            newUser.put("Profile picture", imageUrl);
            newUser.put("Given Name", textGivenName.getText().toString().trim());
            newUser.put("Middle Name", textMiddleName.getText().toString().trim());
            newUser.put("Last Name", textLastName.getText().toString().trim());
            newUser.put("Birthdate", dateText.getText().toString().trim());
            newUser.put("Country of Origin",
                    countrySpinner.getSelectedItem().toString());
            newUser.put("Gender", gender);
            newUser.put("Username", textUsername.getText().toString().trim());
            newUser.put("Email", textEmail.getText().toString().trim());
            newUser.put("Register date", new Date());
            newUser.put("Last Access date", new Date());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        fireDatabase.collection(StaticData.getUserCollection())
                .document(reference.getId()).set(newUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.getException() != null){
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
