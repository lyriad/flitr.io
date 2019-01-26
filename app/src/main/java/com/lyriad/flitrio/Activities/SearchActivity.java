package com.lyriad.flitrio.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lyriad.flitrio.Adapters.ListViewSearchAdapter;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    ListView searchResults;
    ListViewSearchAdapter adapter;
    ImageView homeButton;
    CircularImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        searchResults = findViewById(R.id.search_results);
        profileImage = findViewById(R.id.search_profile_picture);
        homeButton = findViewById(R.id.search_home);

        Glide.with(this).load(currentUser.getPhotoUrl()).into(profileImage);

        profileImage.setOnClickListener(this);
        homeButton.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.film_search);
        SearchView search = (SearchView) myActionMenuItem.getActionView();
        search.setIconifiedByDefault(false);

        adapter = new ListViewSearchAdapter(this, FirebaseData.getFilmList());
        searchResults.setAdapter(adapter);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    searchResults.clearTextFilter();
                }else{
                    adapter.filter(newText);
                }
                return true;
            }
        });

        return true;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.search_profile_picture:
                startActivity(new Intent(SearchActivity.this, UserProfileActivity.class));
                break;
            case R.id.search_home:
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }
}
