package com.myapp.mekvahan.AllBookingsDashboard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.myapp.mekvahan.R;

public class FragOutStationCabs extends Fragment {


    private  View mRootview;

    public FragOutStationCabs() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootview =  inflater.inflate(R.layout.frag_all_services, container, false);

        TextView tv_no_service = mRootview.findViewById(R.id.no_services);
        tv_no_service.setVisibility(View.VISIBLE);
        tv_no_service.setText("To be implemented");

        return mRootview;
    }

}
