package com.myapp.mekvahan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.DetailViewHolder> {


    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new DetailViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_detail_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder detailViewHolder, int i) {
        if (detailViewHolder.getAdapterPosition() % 2 == 0) {
            detailViewHolder.itemView.setBackground(ContextCompat.getDrawable(detailViewHolder.itemView.getContext(), R.drawable.rectangular_background2));
        } else {
            detailViewHolder.itemView.setBackground(ContextCompat.getDrawable(detailViewHolder.itemView.getContext(), R.drawable.rectangular_background));
        }
    }


    @Override
    public int getItemCount() {
        return 4;
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView subcat;
        private ImageView status;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            subcat = itemView.findViewById(R.id.service_subcategory);
            status = itemView.findViewById(R.id.action_done);
        }
    }
}
