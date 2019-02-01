package com.lyriad.flitrio.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.lyriad.flitrio.Adapters.ListViewSearchAdapter;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    ListView searchResults;
    SearchView search;
    ListViewSearchAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchResults = view.findViewById(R.id.search_results);
        search = view.findViewById(R.id.search_query);
        search.setIconifiedByDefault(false);

        SearchView.SearchAutoComplete searchAutoComplete =
                search.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.white));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.white));

        adapter = new ListViewSearchAdapter(getActivity(), FirebaseData.getFilmList());
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


        return view;
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_view, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.film_search);
        SearchView search = (SearchView) myActionMenuItem.getActionView();
        search.setIconifiedByDefault(false);

        adapter = new ListViewSearchAdapter(getActivity(), FirebaseData.getFilmList());
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

        super.onCreateOptionsMenu(menu, inflater);
    }*/
}
