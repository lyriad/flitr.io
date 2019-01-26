package com.lyriad.flitrio.Activities;

import android.content.Intent;
import com.google.android.material.textfield.TextInputEditText;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.Classes.StaticData;
import com.lyriad.flitrio.R;

import java.time.LocalDate;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseFirestore fireDatabase;
    private FirebaseAuth fireAuthentication;

    InputMethodManager keyboard;
    TextInputEditText textUsernameEmail, textPassword;
    TextView textSignUp;
    Button buttonSignIn;
    LinearLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireDatabase = FirebaseFirestore.getInstance();
        fireAuthentication = FirebaseAuth.getInstance();
        FirebaseData.loadMedia(fireDatabase);
        StaticData.loadGenres();
        keyboard = (InputMethodManager) getSystemService(SignInActivity.this.
                INPUT_METHOD_SERVICE);

        setContentView(R.layout.activity_sign_in);
        buttonSignIn = findViewById(R.id.sign_in_button);
        textUsernameEmail = findViewById(R.id.sign_in_username_email);
        textPassword = findViewById(R.id.sign_in_password);
        textSignUp = findViewById(R.id.sign_in_create_account);
        progress = findViewById(R.id.sign_in_progress_layout);

        buttonSignIn.setOnClickListener(this);
        textSignUp.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (fireAuthentication.getCurrentUser() != null){
            buttonSignIn.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);
            loadUserData();
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.sign_in_create_account){
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        }else if (v.getId() == R.id.sign_in_button){
            checkInputFields();
        }
    }

    private void checkInputFields(){

        String usernameEmail = textUsernameEmail.getText().toString().trim();
        String password = textPassword.getText().toString();

        if (TextUtils.isEmpty(usernameEmail)){
            textUsernameEmail.requestFocus();
            keyboard.showSoftInput(textUsernameEmail, InputMethodManager.SHOW_IMPLICIT);
            textUsernameEmail.setError("Please enter an email or username");
            return;
        }else if (TextUtils.isEmpty(password)){
            textPassword.requestFocus();
            keyboard.showSoftInput(textPassword, InputMethodManager.SHOW_IMPLICIT);
            textPassword.setError("Please enter a password");
            return;
        }

        keyboard.hideSoftInputFromWindow(textUsernameEmail.getWindowToken(), 0);
        keyboard.hideSoftInputFromWindow(textPassword.getWindowToken(), 0);
        buttonSignIn.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);

        if (Patterns.EMAIL_ADDRESS.matcher(usernameEmail).matches()){
            logIn(usernameEmail, password);
        }else{
            logInWithUsername(usernameEmail);
        }

    }

    private void logInWithUsername(final String username){

        fireDatabase.collection(StaticData.getUserCollection()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String email = "";
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.get("Username").toString().equals(username)){
                            email = document.getId();
                            break;
                        }
                    }
                    logIn(email, textPassword.getText().toString());
                } else if(task.getException() != null){
                    progress.setVisibility(View.INVISIBLE);
                    buttonSignIn.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }else{
                    progress.setVisibility(View.INVISIBLE);
                    buttonSignIn.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "SignIn failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void logIn(String email, String password){
        fireAuthentication.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progress.setVisibility(View.INVISIBLE);
                        buttonSignIn.setVisibility(View.VISIBLE);
                        loadUserData();
                    }else if (task.getException() != null){
                        progress.setVisibility(View.INVISIBLE);
                        buttonSignIn.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),
                                task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void loadUserData(){
        fireDatabase.document(StaticData.getUserCollection() + "/" + fireAuthentication.getCurrentUser().getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    FirebaseData.getCurrentUser().setGivenName(doc.get("Given Name").toString());
                    FirebaseData.getCurrentUser().setMiddleName(doc.get("Middle Name").toString());
                    FirebaseData.getCurrentUser().setLastName(doc.get("Last Name").toString());
                    FirebaseData.getCurrentUser().setBirthDate(LocalDate.parse(doc.get("Birthdate").toString(), StaticData.getDateFormat()));
                    FirebaseData.getCurrentUser().setCountryOfOrigin(doc.get("Country of Origin").toString());
                    FirebaseData.getCurrentUser().setGender(doc.get("Gender").toString());
                    FirebaseData.getCurrentUser().setUsername(doc.get("Username").toString());
                    FirebaseData.getCurrentUser().setSubscription(doc.get("Subscription").toString());
                    startActivity(new Intent(SignInActivity.this,
                            MainActivity.class));
                    finish();
                }
            }
        });
    }
}
