package com.lyriad.flitrio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyriad.flitrio.Classes.Season;
import com.lyriad.flitrio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewSeasonAdapter extends RecyclerView.Adapter<RecyclerViewSeasonAdapter.ViewHolder>{

    private List<Season> seasons;
    private Context myContext;
    private OnItemClickListener myListener;

    public RecyclerViewSeasonAdapter(List<Season> seasons, Context myContext) {
        this.seasons = seasons;
        this.myContext = myContext;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        myListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_season_item, parent, false);
        return (new ViewHolder(view, myListener));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(myContext).load(seasons.get(position).getSeasonImage()).into(holder.seasonImage);
        holder.seasonTitle.setText(seasons.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return seasons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView seasonImage;
        TextView seasonTitle;

        public ViewHolder(final View itemView, final OnItemClickListener listener){
            super(itemView);
            seasonImage = itemView.findViewById(R.id.recycler_season_image);
            seasonTitle = itemView.findViewById(R.id.recycler_season_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
