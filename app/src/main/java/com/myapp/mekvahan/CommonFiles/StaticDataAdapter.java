package com.myapp.mekvahan.CommonFiles;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.R;

import java.util.List;

public class StaticDataAdapter extends RecyclerView.Adapter<StaticDataAdapter.ViewHolder> {

    private Context context;
    private List<StaticData> faqData;

    public StaticDataAdapter(Context context, List<StaticData> faqData) {
        this.context = context;
        this.faqData = faqData;
    }

    @NonNull
    @Override
    public StaticDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.faqitems, parent, false);
        return new StaticDataAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StaticDataAdapter.ViewHolder holder, int position) {

        final StaticData item = faqData.get(position);

        final Typeface fontHeading = ResourcesCompat.getFont(context,R.font.gotham_medium_regular);
        final Typeface fontnormal = ResourcesCompat.getFont(context,R.font.montserrat_regular);

        if (item != null)
        {
            if(item.getId().equals(1)){

                holder.mtextView.setTextColor(Color.BLACK);
                holder.mtextView.setPadding(0,0,0,0);
                holder.mtextView.setTypeface(holder.mtextView.getTypeface(), Typeface.BOLD);
                holder.mtextView.setTypeface(fontHeading);
                holder.mtextView.setTextSize(16);
                holder.mtextView.setText(item.getText());

            }

            else if(item.getId().equals(2) ){
                holder.mtextView.setTextColor(Color.GRAY);
                holder.mtextView.setTextSize(12);
                holder.mtextView.setPadding(0,0,0,0);
                holder.mtextView.setTypeface(fontnormal);
                holder.mtextView.setText(item.getText());

            }

            else if(item.getId().equals(3) ){
                holder.mtextView.setTextColor(Color.GREEN);
                holder.mtextView.setTextSize(12);
                holder.mtextView.setPadding(8,0,0,0);
                holder.mtextView.setTypeface(fontnormal);
                holder.mtextView.setText(item.getText());

            }

        }

        else {

            holder.mtextView.setTextColor(Color.RED);
        }

    }



    @Override
    public int getItemCount() {
        return faqData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public AppCompatTextView mtextView;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            mtextView = view.findViewById(R.id.faqtext);
        }
    }
}
