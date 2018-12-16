package com.lyriad.flitrio.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.lyriad.flitrio.R;

import java.util.ArrayList;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText dateText;
    private TextInputEditText textGivenName, textMiddleName, textLastName, textEmail, passwordText,
            confirmPasswordText;
    private ImageView dateButton, subscriptionHelp, backButton, profilePicture;
    private Spinner countrySpinner, subscriptionSpinner;
    private Button signUpButton, selectPictureButton;
    private RadioButton male, female;
    private ArrayList<String> countries = new ArrayList<>(), subscriptions = new ArrayList<>();
    private ArrayAdapter<String> countryAdapter, subscriptionAdapter;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backButton = findViewById(R.id.sign_up_back_button);
        profilePicture = findViewById(R.id.sign_up_profile_picture);
        selectPictureButton = findViewById(R.id.sign_up_profile_picture_button);
        textGivenName = findViewById(R.id.sign_up_given_name);
        textMiddleName = findViewById(R.id.sign_up_middle_name);
        textLastName = findViewById(R.id.sign_up_last_name);
        textEmail = findViewById(R.id.sign_up_email);
        dateText = findViewById(R.id.sign_up_date);
        dateButton = findViewById(R.id.sign_up_date_button);
        male = findViewById(R.id.sign_up_male);
        female = findViewById(R.id.sign_up_female);
        countrySpinner = findViewById(R.id.sign_up_country);
        subscriptionSpinner = findViewById(R.id.sign_up_subscription);
        subscriptionHelp = findViewById(R.id.sign_up_subscription_help);
        passwordText = findViewById(R.id.sign_up_password);
        confirmPasswordText = findViewById(R.id.sign_up_password_confirm);
        signUpButton = findViewById(R.id.sign_up_button);

        countries.add("<Select country of origin>");
        countries.add("Dominican Republic");
        countries.add("United States");
        countries.add("Canada");
        countries.add("Mexico");
        countryAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, countries);
        countrySpinner.setAdapter(countryAdapter);

        subscriptions.add("<Select subscription plan>");
        subscriptions.add("Basic plan");
        subscriptions.add("Standard plan");
        subscriptions.add("Premium plan");
        subscriptionAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, subscriptions);
        subscriptionSpinner.setAdapter(subscriptionAdapter);

        backButton.setOnClickListener(this);
        selectPictureButton.setOnClickListener(this);
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
                        this,
                        dateSetListener,
                        year, month, day);
                dialog.show();
                break;

            case R.id.sign_up_subscription_help:
                startActivity(new Intent(SignUpActivity.this,
                        SubscriptionActivity.class));
                break;

            case R.id.sign_up_button:
                Toast.makeText(this, "Signed Up successfully",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
