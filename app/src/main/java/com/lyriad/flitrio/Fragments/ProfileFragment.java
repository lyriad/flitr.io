package com.lyriad.flitrio.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lyriad.flitrio.Activities.PopUpActivities.ChangePasswordPopUp;
import com.lyriad.flitrio.Activities.SignInActivity;
import com.lyriad.flitrio.Adapters.RecyclerViewFilmCategoryAdapter;
import com.lyriad.flitrio.Classes.Film;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.Classes.User;
import com.lyriad.flitrio.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private FirebaseAuth fireAuthentication;
    private FirebaseUser currentUser;
    private User myUser;
    private List<Film> userFilmList;

    private CircularImageView profileImage;
    private TextView username, names, birthdate, countryOfOrigin, email, subscription, emptyLabel;
    private Button changePasswordButton, signOutButton;
    private RecyclerView userList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireAuthentication = FirebaseAuth.getInstance();
        currentUser = fireAuthentication.getCurrentUser();
        myUser = FirebaseData.getCurrentUser();
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpUserFilmList();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profile_picture);
        username = view.findViewById(R.id.profile_username);
        names = view.findViewById(R.id.profile_names);
        birthdate = view.findViewById(R.id.profile_birthdate);
        countryOfOrigin = view.findViewById(R.id.profile_country);
        email = view.findViewById(R.id.profile_email);
        subscription = view.findViewById(R.id.profile_subscription);
        userList = view.findViewById(R.id.profile_list);
        emptyLabel = view.findViewById(R.id.profile_empty_label);
        changePasswordButton = view.findViewById(R.id.profile_change_password);
        signOutButton = view.findViewById(R.id.profile_sign_out);

        signOutButton.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);

        String date = String.valueOf(myUser.getBirthDate().getDayOfMonth()) +
                " " + myUser.getBirthDate().getMonth().toString().charAt(0) +
                myUser.getBirthDate().getMonth().toString().toLowerCase().substring(1) +
                " " + String.valueOf(myUser.getBirthDate().getYear());

        Glide.with(this).load(currentUser.getPhotoUrl()).into(profileImage);
        username.setText(myUser.getUsername());
        names.setText(currentUser.getDisplayName());
        birthdate.setText(date);
        countryOfOrigin.setText(myUser.getCountryOfOrigin());
        email.setText(currentUser.getEmail());
        subscription.setText(myUser.getSubscription());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_sign_out:
                fireAuthentication.signOut();
                startActivity(new Intent(getActivity(), SignInActivity.class));
                getActivity().finish();
                break;
            case R.id.profile_change_password:
                startActivity(new Intent(getActivity(), ChangePasswordPopUp.class));
                break;
        }
    }

    private void setUpUserFilmList(){

        if (!myUser.getList().isEmpty()){

            userFilmList = new ArrayList<>();

            for (Film aux : FirebaseData.getFilmList()){
                if (myUser.getList().contains(aux.getTitle())){
                    userFilmList.add(aux);
                }
            }

            RecyclerViewFilmCategoryAdapter userListAdapter = new RecyclerViewFilmCategoryAdapter(userFilmList, getActivity());
            userList.setAdapter(userListAdapter);
            userList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        }else{
            userList.setAdapter(null);
            emptyLabel.setVisibility(View.VISIBLE);
        }
    }
}
