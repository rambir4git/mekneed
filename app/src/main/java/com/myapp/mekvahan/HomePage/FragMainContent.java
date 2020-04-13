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

        ((TextView) mRootView.findViewById(R.id.brand)).setText("Mekvahan");
        ((TextView) mRootView.findViewById(R.id.model)).setText("Corona");
        ((TextView) mRootView.findViewById(R.id.plate_no)).setText("Stay at home");



    }

    private void setupGridApter() {

        RecyclerView recyclerView =  mRootView.findViewById(R.id.recyclerView);
        List<Tab> tabList = new ArrayList<>();


        tabList.add(new Tab(0, "General Services", R.drawable.gs_icon, 1));
            tabList.add(new Tab(1,"Repairing", R.drawable.repairing_icon,1));
            tabList.add(new Tab(2,"Wheel Care", R.mipmap.wc_icon,1));
            tabList.add(new Tab(3,"Denting & Painting", R.drawable.dp_icon,1));
            tabList.add(new Tab(4,"Car Care", R.drawable.cc_icon,1));
            tabList.add(new Tab(5,"Others", R.drawable.others_icon,1));


        TabAdapter adapter = new TabAdapter(getActivity(), tabList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

}
