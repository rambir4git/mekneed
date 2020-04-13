package com.myapp.mekvahan.HomePage;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.R;

import java.util.List;

public class ActionStatusAdapter extends RecyclerView.Adapter<ActionStatusAdapter.MyViewHolder> {

    private Context context;
    private List<Pair<String,String>> mActionPairList;

    public ActionStatusAdapter(Context context, List<Pair<String, String>> mActionPairList) {
        this.mActionPairList = mActionPairList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_action_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pair<String, String> pair = mActionPairList.get(holder.getAdapterPosition());
        holder.name.setText(pair.first);
        holder.itemaction.setText("");

        if (position % 2 == 0) {
            holder.itemview.setBackgroundColor(ContextCompat.getColor(context, R.color.service_action_even));
        }

        //Log.e("TAG","status="+pair.first + pair.second);

        if(pair.second.equals("1"))
            holder.completed.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.completed_icon));
        else
            holder.completed.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.tick_light));
    }

    @Override
    public int getItemCount() {
        return mActionPairList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView itemaction;
        public LinearLayout itemview;
        public ImageView completed;

        public MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            itemaction = view.findViewById(R.id.itemaction);
            itemview = view.findViewById(R.id.itemview);
            completed = view.findViewById(R.id.action_done);
        }
    }
}


