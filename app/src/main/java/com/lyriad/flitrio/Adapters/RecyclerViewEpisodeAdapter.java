package com.lyriad.flitrio.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lyriad.flitrio.Classes.Episode;
import com.lyriad.flitrio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewEpisodeAdapter extends RecyclerView.Adapter<RecyclerViewEpisodeAdapter.ViewHolder>{

    private List<Episode> adapterEpisodes;
    private Context myContext;

    public RecyclerViewEpisodeAdapter(List<Episode> adapterEpisodes, Context myContext) {
        this.adapterEpisodes = adapterEpisodes;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_episode_item, parent, false);
        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.episodeTitle.setText(adapterEpisodes.get(position).getTitle());
        holder.episodeDuration.setText(adapterEpisodes.get(position).getDuration());

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adapterEpisodes.get(position).getPlayLink()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                myContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adapterEpisodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView episodeTitle, episodeDuration;
        ImageView playButton;

        public ViewHolder(View itemView){
            super(itemView);
            episodeTitle = itemView.findViewById(R.id.episode_title);
            episodeDuration = itemView.findViewById(R.id.episode_duration);
            playButton = itemView.findViewById(R.id.episode_play);

        }

    }
}
