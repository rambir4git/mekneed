package com.myapp.mekvahan.HomePage;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.R;
import com.myapp.mekvahan.Rooms.MekVahanDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DialogServiceDetail extends DialogFragment {

    private View mRootView;

    private final String TAG = getClass().getSimpleName();

    private ServiceModel mServiceDetail;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        dialog.getWindow().getAttributes().windowAnimations = R.style.animationSlideRightToRight;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView   = inflater.inflate(R.layout.dialog_service_detail, container, false);
        setViews();
        new FetchDefaultVehcile(MekVahanDatabase.getInstance(getActivity())).execute();
        prepareDescriptionLayout();
        prepareWhatLayout();
        prepareActionLayout();
        clickListener();

        return mRootView;
    }


    @Override
    public void onDestroyView() {
        Log.e(TAG, "OnDestroView1");
        super.onDestroyView();
        assert getFragmentManager() != null;
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        ft.remove(this);
        ft.commit();
    }

    private void setViews() {

        mServiceDetail = (ServiceModel) getArguments().getSerializable("serviceDetail");

        ((TextView)mRootView.findViewById(R.id.toolbar_title)).setText("Product Detail");
        ((TextView)mRootView.findViewById(R.id.service_name)).setText(mServiceDetail.getServiceName());

        ((TextView)mRootView.findViewById(R.id.sub_total)).setText(mServiceDetail.getSubtotal());

//        TextView tv_strikedOutSubTotal = mRootView.findViewById(R.id.striked_out_sub_total);
//        tv_strikedOutSubTotal.setText(mServiceDetail.getStrikedOutAmount()+"");
//        tv_strikedOutSubTotal.setPaintFlags(tv_strikedOutSubTotal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    private void clickListener() {
        mRootView.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroyView();
            }
        });

        //View is also commented out in design

//        mRootView.findViewById(R.id.add_now).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


    }

    class FetchDefaultVehcile extends AsyncTask<Void,Void,Void> {

        private int defaultVehielId;

        public FetchDefaultVehcile(MekVahanDatabase instance) {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setupVehicleLayout();
        }
    }

    private void setupVehicleLayout() {



        TextView txtview_selected_vehicle_brand, txtview_selected_vehicle_model, txtview_selected_vehicle_platenum;
        ImageView imgview_selected_vehicle;

        imgview_selected_vehicle          = mRootView.findViewById(R.id.imgview_selected_vehicle);
        txtview_selected_vehicle_brand    = mRootView.findViewById(R.id.txtview_selected_vehicle_brand);
        txtview_selected_vehicle_model    = mRootView.findViewById(R.id.txtview_selected_vehicle_model);
        txtview_selected_vehicle_platenum = mRootView.findViewById(R.id.txtview_selected_vehicle_platenum);



    }


    private void prepareDescriptionLayout() {

        LinearLayout layout_description_container = mRootView.findViewById(R.id.layout_description_container);

        String[] s = mServiceDetail.getDes().split("BREAKNEWLINE");

        for (int i = 0; i < s.length; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_description, layout_description_container, false);
            ((TextView) view.findViewById(R.id.textview_condition)).setText(s[i].trim());

            layout_description_container.addView(view);
        }

    }

    private void prepareWhatLayout() {


        LinearLayout layout_service_what = mRootView.findViewById(R.id.layout_service_what);


        String[] s = mServiceDetail.getWhat().split("BREAKNEWLINE");

        for (int i = 0; i < s.length; i++) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_description, layout_service_what, false);
            ((TextView) view.findViewById(R.id.textview_condition)).setText(s[i].trim());

            layout_service_what.addView(view);
        }



    }


    private void prepareActionLayout() {

        List<Pair<String,String>> pairList = new ArrayList<>();

        Pair<String, String> pair = getPair(mServiceDetail.getAction1());
        if(pair!=null) pairList.add(pair);


        pair = getPair(mServiceDetail.getAction2());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction3());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction4());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction5());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction6());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction7());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction8());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction9());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction10());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction11());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction12());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction13());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction14());
        if(pair!=null) pairList.add(pair);
        pair = getPair(mServiceDetail.getAction15());
        if(pair!=null) pairList.add(pair);


        RecyclerView recyclerView = mRootView.findViewById(R.id.rc_servicetype);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ActionPairAdapter adapter = new ActionPairAdapter(getActivity(), pairList);
        recyclerView.setAdapter(adapter);

    }

    private Pair<String, String> getPair(String action) {

        String[] s = action.split(",");
        if(s.length>=2)
            return new Pair<>(s[0], s[s.length-1]);

        return null;


    }


}
