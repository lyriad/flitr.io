package com.lyriad.flitrio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyriad.flitrio.Adapters.Models.FilmCategoryModel;
import com.lyriad.flitrio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewFilmCategoriesAdapter extends RecyclerView.Adapter<RecyclerViewFilmCategoriesAdapter.CategoriesViewHolder>{

    private List<FilmCategoryModel> categories;
    private Context myContext;

    public RecyclerViewFilmCategoriesAdapter(List<FilmCategoryModel> categories, Context myContext) {
        this.categories = categories;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_film_category_item, parent, false);
        return (new CategoriesViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.titleCategory.setText(categories.get(position).getCategoryTitle());
        holder.filmCategory.setAdapter(new RecyclerViewFilmCategoryAdapter(categories.get(position).getCategoryFilms(), myContext));
        holder.filmCategory.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder{

        TextView titleCategory;
        RecyclerView filmCategory;

        public CategoriesViewHolder(View itemView){
            super(itemView);
            titleCategory = itemView.findViewById(R.id.film_category_text);
            filmCategory = itemView.findViewById(R.id.film_category_list);
        }
    }
}
