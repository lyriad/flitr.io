package com.lyriad.flitrio.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.lyriad.flitrio.R;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        backButton = findViewById(R.id.subscription_back_button);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.subscription_back_button){
            finish();
        }
    }
}
