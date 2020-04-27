package com.myapp.mekvahan.HomePage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;


public class FragMainContent extends Fragment {

    private final String TAG = "FragMainContent";
    private View mRootView;

    public FragMainContent() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView =  inflater.inflate(R.layout.fragment_fram_car_services_home_page, container, false);


        setupGridApter();
        setupVehicleLayout();

        return mRootView;

    }

    private void setupVehicleLayout() {

        final ImageView iv_vehicle = mRootView.findViewById(R.id.image_vehicle);
        final ProgressBar imageProgressBar =  mRootView.findViewById(R.id.progress_bar_image);

        imageProgressBar.setVisibility(GONE);
        ((TextView) mRootView.findViewById(R.id.brand)).setText("Hygienically Packaged");
        ((TextView) mRootView.findViewById(R.id.model)).setText("Safely Delivered");
        ((TextView) mRootView.findViewById(R.id.plate_no)).setText("Daily Home Essentials");



    }

    private void setupGridApter() {

        RecyclerView recyclerView =  mRootView.findViewById(R.id.recyclerView);
        List<Tab> tabList = new ArrayList<>();


        tabList.add(new Tab(0, "Grocery & Staples", R.drawable.ic_asset_7, 1));
            tabList.add(new Tab(1,"Fruit & Vegetables", R.drawable.ic_asset_9,1));
            tabList.add(new Tab(2,"Household Items", R.drawable.ic_asset_12,1));
            tabList.add(new Tab(3,"Dairy Essentials", R.drawable.ic_asset_14,1));
            tabList.add(new Tab(4,"Medicines", R.drawable.ic_asset_16,1));
            tabList.add(new Tab(5,"Others", R.drawable.ic_asset_18,1));


        TabAdapter adapter = new TabAdapter(getActivity(), tabList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

}
