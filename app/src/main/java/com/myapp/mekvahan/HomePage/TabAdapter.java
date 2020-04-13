package com.myapp.mekvahan.HomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.HomePage.CarServices.CarServicesHomePage;
import com.myapp.mekvahan.R;

import java.util.List;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.TabViewHolder> {

    private Context mCtx;
    private List<Tab> mTabList;

    CardView mMenuMyVehicleLayout;

    public TabAdapter(Context mCtx, List<Tab> mTabList) {
        this.mCtx = mCtx;
        this.mTabList = mTabList;

        mMenuMyVehicleLayout = ((Activity)mCtx).findViewById(R.id.menu_my_vehicle_layout);
    }

    @NonNull
    @Override
    public TabAdapter.TabViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TabViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.row_home_tab,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TabAdapter.TabViewHolder holder, int position) {

        final Tab tab = mTabList.get(position);

        holder.categoryNameTv.setText(tab.getTitle());
        int icon = tab.getIcons();
        holder.categoryIv.setImageResource(icon);
        holder.parentCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuMyVehicleLayout.setVisibility(View.GONE);

                int tabType = tab.getTabType();
                int tabId   = tab.getId();

                Intent i;
                    i = new Intent(mCtx, CarServicesHomePage.class);

                i.putExtra("viewpagerId",tabId);
                mCtx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTabList.size();
    }

    public class TabViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryIv;
        TextView categoryNameTv;
        CardView parentCv;

        public TabViewHolder(@NonNull View view) {
            super(view);

            categoryIv = view.findViewById(R.id.image);
            categoryNameTv = view.findViewById(R.id.title);
            parentCv = view.findViewById(R.id.card);
        }
    }
}
