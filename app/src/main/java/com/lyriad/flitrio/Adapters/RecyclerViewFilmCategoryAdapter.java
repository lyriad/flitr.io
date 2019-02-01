package com.lyriad.flitrio.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lyriad.flitrio.Activities.MainActivity;
import com.lyriad.flitrio.Classes.Film;
import com.lyriad.flitrio.Fragments.FilmFragment;
import com.lyriad.flitrio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewFilmCategoryAdapter extends RecyclerView.Adapter<RecyclerViewFilmCategoryAdapter.CategoryViewHolder>{

    private List<Film> adapterFilms;
    private Context context;

    public RecyclerViewFilmCategoryAdapter(List<Film> adapterFilms, Context context) {
        this.adapterFilms = adapterFilms;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_film_poster, parent, false);
        return (new CategoryViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        Glide.with(context).load(adapterFilms.get(position).getPosterUrl()).into(holder.filmPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity parentActivity = (MainActivity) context;
                Bundle args = new Bundle();
                args.putString("Film", adapterFilms.get(position).getTitle());
                Fragment filmFragment = new FilmFragment();
                filmFragment.setArguments(args);
                parentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        filmFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return adapterFilms.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView filmPoster;

        public CategoryViewHolder(View itemView){
            super(itemView);
            filmPoster = itemView.findViewById(R.id.recycler_film_image);
        }
    }
}
