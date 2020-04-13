package com.myapp.mekvahan.AllBookingsDashboard.RegulaServices;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.AllBookingsDashboard.BookingsHomePage;
import com.myapp.mekvahan.R;

import java.util.Collections;
import java.util.List;


public class FragRegularServices extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private View mRootview;

    private BookingsHomePage mActicity;


    public FragRegularServices() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootview = inflater.inflate(R.layout.frag_all_services, container, false);

        mActicity = (BookingsHomePage) getActivity();
        fetchRegularServicesList();

        return mRootview;
    }

    private void fetchRegularServicesList() {

        List<RegularServiceModel> servicesList = mActicity.getRegularServiceFromParent();

        if(servicesList.size() == 0){
            TextView tv_no_service = mRootview.findViewById(R.id.no_services);
            tv_no_service.setVisibility(View.VISIBLE);
            tv_no_service.setText("No Regular Sevice");

            return;

        }


        RecyclerView recyclerView = mRootview.findViewById(R.id.recycler_view_services);

        Collections.reverse(servicesList);

        RegularServicesAdapter adapter = new RegularServicesAdapter(getActivity(), servicesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

}
