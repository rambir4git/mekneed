package com.myapp.mekvahan.AllBookingsDashboard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.myapp.mekvahan.R;

public class FragAllServices extends Fragment {


    public FragAllServices() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_all_services, container, false);
    }

}
