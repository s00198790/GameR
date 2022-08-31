package com.mad.gamer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.gamer.R;
import com.mad.gamer.model.Model;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.Holder> {
    Context context;
    List<Model> modelList;

    public ScoreAdapter(Context context, List<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_score, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvNumber.setText("" + (position + 1));
        holder.tvName.setText(modelList.get(position).getName());
        holder.tvScore.setText(String.valueOf(modelList.get(position).getScore()));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvName, tvScore;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvName = itemView.findViewById(R.id.tv_name);
            tvScore = itemView.findViewById(R.id.tv_score);
        }
    }
}
