package com.lyriad.flitrio.Activities.PopUpActivities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import com.lyriad.flitrio.R;

public class ChangePasswordPopUp extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_change_password);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels, height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int)(height * 0.8));
    }
}
