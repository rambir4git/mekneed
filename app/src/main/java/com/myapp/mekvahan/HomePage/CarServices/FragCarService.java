package com.myapp.mekvahan.HomePage.CarServices;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.myapp.mekvahan.HomePage.ServiceModel;
import com.myapp.mekvahan.R;

import java.util.List;


public class FragCarService extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private View mRootView;
    private SwipeRefreshLayout mSwipeToRefresh;
    private CarServicesHomePage mActivity;

    private int flag;

    public FragCarService() {}


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        flag = getArguments().getInt("flag");

        Log.e(TAG,"flag="+flag);

        mActivity = (CarServicesHomePage) getActivity();
        fetchCarGeneralServicesFromParent(flag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_car_service1, container, false);
        mSwipeToRefresh = mRootView.findViewById(R.id.swipe_to_refresh);

        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchCarGeneralServicesFromParent(flag);
            }
        });

        return mRootView;
    }

    private void fetchCarGeneralServicesFromParent(int flag) {

        mSwipeToRefresh.setRefreshing(true);

        List<ServiceModel> serviceModelList = mActivity.getCarService(flag);
        setAdapter(serviceModelList);

        mSwipeToRefresh.setRefreshing(false);

    }

    private void setAdapter(List<ServiceModel> serviceModelList) {

        RecyclerView recyclerView = mRootView.findViewById(R.id.recycler_view_car_services);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        CarServiceModelAdapter adapter = new CarServiceModelAdapter(getActivity(), serviceModelList);
        recyclerView.setAdapter(adapter);
    }

}
