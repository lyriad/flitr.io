package com.lyriad.flitrio.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lyriad.flitrio.Activities.MainActivity;
import com.lyriad.flitrio.Classes.Film;
import com.lyriad.flitrio.Fragments.FilmFragment;
import com.lyriad.flitrio.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.Fragment;

public class ListViewSearchAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Film> filmResults;
    private ArrayList<Film> filmTitles;

    public ListViewSearchAdapter(Context context, List<Film> filmResults) {
        this.context = context;
        this.filmResults = filmResults;
        inflater = LayoutInflater.from(context);
        filmTitles = new ArrayList<>();
        filmTitles.addAll(filmResults);
    }

    @Override
    public int getCount() {
        return filmResults.size();
    }

    @Override
    public Object getItem(int position) {
        return filmResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.spinner_item, null);
            holder.filmTitle = view.findViewById(R.id.text_item_title);

            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.filmTitle.setText(filmResults.get(position).getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity parentActivity = (MainActivity) context;
                Bundle args = new Bundle();
                args.putString("Film", filmResults.get(position).getTitle());
                Fragment filmFragment = new FilmFragment();
                filmFragment.setArguments(args);
                parentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        filmFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public void filter(String query){
        query = query.toLowerCase(Locale.getDefault());
        filmResults.clear();

        if (query.length() == 0){
            filmResults.addAll(filmTitles);
        }else{
            for (Film title : filmTitles){
                if (title.getTitle().toLowerCase(Locale.getDefault()).contains(query)){
                    filmResults.add(title);
                }

                for (String cast : title.getMainCast()){
                    if (cast.toLowerCase(Locale.getDefault()).contains(query)){
                        filmResults.add(title);
                    }
                }
            }

            filmResults = removeListDuplicates(filmResults);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView filmTitle;
    }

    private List<Film> removeListDuplicates(List<Film> list){

        List<Film> newList = new ArrayList<>();

       for (Film film : list) {
            if (!newList.contains(film)) {

                newList.add(film);
            }
        }

        return newList;
    }
}
