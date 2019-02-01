package com.lyriad.flitrio.Activities.PopUpActivities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lyriad.flitrio.R;

public class ChangePasswordPopUp extends Activity implements View.OnClickListener{

    TextView oldPassword, newPassword, confirmPassword;
    Button changePasswordButton;
    ImageView backButton;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_change_password);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.8), (int)(dm.heightPixels * 0.7));

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        backButton = findViewById(R.id.change_password_back);
        oldPassword = findViewById(R.id.change_password_old_password);
        newPassword = findViewById(R.id.change_password_new_password);
        confirmPassword = findViewById(R.id.change_password_confirm_password);
        changePasswordButton = findViewById(R.id.change_password_button);

        backButton.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_password_back:
                finish();
                break;
            case R.id.change_password_button:
                verifyOldPassword();
                finish();
                break;
        }
    }

    private void verifyOldPassword(){
        String oldPass = oldPassword.getText().toString();
        AuthCredential credential = EmailAuthProvider.getCredential(
                currentUser.getEmail(), oldPass);

        currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    changePassword();
                }else if (task.getException() != null){
                    Toast.makeText(ChangePasswordPopUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changePassword(){

        if(newPassword.getText().toString().length() < 6){
            newPassword.setError("New password must be at least 6 characters long");
        }else if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())){
            confirmPassword.setError("Passwords do not match");
        }else{
            String newPass = newPassword.getText().toString();
            currentUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ChangePasswordPopUp.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    }else if (task.getException() != null){
                        Toast.makeText(ChangePasswordPopUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
