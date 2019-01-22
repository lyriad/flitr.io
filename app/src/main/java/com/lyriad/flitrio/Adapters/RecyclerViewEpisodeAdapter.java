package com.lyriad.flitrio.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lyriad.flitrio.Classes.Episode;
import com.lyriad.flitrio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewEpisodeAdapter extends RecyclerView.Adapter<RecyclerViewEpisodeAdapter.ViewHolder>{

    private List<Episode> adapterEpisodes;

    public RecyclerViewEpisodeAdapter(List<Episode> adapterEpisodes) {
        this.adapterEpisodes = adapterEpisodes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_episode_item, parent, false);
        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.episodeTitle.setText(adapterEpisodes.get(position).getTitle());
        holder.episodeDuration.setText(adapterEpisodes.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return adapterEpisodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView episodeTitle, episodeDuration;

        public ViewHolder(View itemView){
            super(itemView);
            episodeTitle = itemView.findViewById(R.id.episode_title);
            episodeDuration = itemView.findViewById(R.id.episode_duration);
        }
    }
}
