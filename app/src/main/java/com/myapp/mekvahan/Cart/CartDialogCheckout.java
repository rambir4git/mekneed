package com.myapp.mekvahan.Cart;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.CommonFiles.AllDialog;
import com.myapp.mekvahan.HomePage.ActionPairAdapter;
import com.myapp.mekvahan.R;
import com.myapp.mekvahan.Rooms.CartDao;
import com.myapp.mekvahan.Rooms.MekVahanDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.myapp.mekvahan.CommonFiles.AppConstants.SERVICE_DATE;
import static com.myapp.mekvahan.CommonFiles.AppConstants.SERVICE_ID;
import static com.myapp.mekvahan.CommonFiles.AppConstants.SERVICE_TIME;

public class CartDialogCheckout extends DialogFragment {

    private View mRootView;
    private final String TAG = getClass().getSimpleName();

    private ProgressDialog mProgressDialog;

    private int mSubTotal, mAdditional;
    private int mIsCouponApplied = 0;


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

        mRootView   = inflater.inflate(R.layout.dialog_checkout, container, false);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please wait...");

        String servingDate  = getArguments().getString(SERVICE_DATE);
        String servingTime  = getArguments().getString(SERVICE_TIME);

        mSubTotal           = getArguments().getInt("subtotal",0);
        mAdditional         = getArguments().getInt("additionalCharge",0);


        setViews(servingDate + " "+servingTime);

        setPricing(0,0);
        new FetchCartItems(MekVahanDatabase.getInstance(getActivity())).execute();


        return mRootView;
    }

    private void clickListener() {

        mRootView.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroyView();
            }
        });

        mRootView.findViewById(R.id.cod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                sendRegularServiceToDbWithCod();

            }
        });
    }

    private void setPricing(int discountPercentage, int maxDiscount) {

        int subTotalToSet = mSubTotal;
        int discount = 0;

        if(discountPercentage!=0){
            discount = (discountPercentage * mSubTotal)/100;
            if(discount>maxDiscount)
                discount = maxDiscount;
            mIsCouponApplied = 1;

        }

        subTotalToSet = subTotalToSet - discount;

        int gst = (int) (.18*(subTotalToSet+mAdditional));

        ((TextView)mRootView.findViewById(R.id.subtotal)).setText(subTotalToSet+"");
        ((TextView) mRootView.findViewById(R.id.tax)).setText(gst + "");
        ((TextView)mRootView.findViewById(R.id.additional_charges)).setText(mAdditional+"");

        ((TextView) mRootView.findViewById(R.id.total)).setText((subTotalToSet + mAdditional) + " ");

        setPromoTextView(discount);

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

    private void setViews(String servingTime) {
        ((TextView)mRootView.findViewById(R.id.toolbar_title)).setText("Checkout");
        ((TextView)mRootView.findViewById(R.id.service_time)).setText(servingTime);
    }



    private void setPromoTextView(int discount) {

        TextView tv_promocode = mRootView.findViewById(R.id.promocode);

        if(mIsCouponApplied == 1) {
            tv_promocode.setText("Promocode applied : "+"\u20B9 "+discount+" Off");
            tv_promocode.setTextColor(ContextCompat.getColor(getActivity(),R.color.green));
            tv_promocode.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.cross_circle,0);

        }

        else {
            tv_promocode.setText("% Apply Coupon code");
            tv_promocode.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
            tv_promocode.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_right,0);

        }
    }



    private void sendRegularServiceToDbWithCod() {

        new AllDialog(getActivity()).holdUpTight("0000", "2020-04-14 12:12:20.000", "HQ", "HQ", "Covid19");
    }


    @Override
    public void onResume() {
        super.onResume();
        enableButtons();
    }

    private void enableButtons(){
        mRootView.findViewById(R.id.back_btn).setEnabled(true);
        mRootView.findViewById(R.id.promocode).setEnabled(true);
        mRootView.findViewById(R.id.pay_now).setEnabled(true);
        mRootView.findViewById(R.id.cod).setEnabled(true);
    }

    class FetchCartItems extends AsyncTask<Void,Void,Void> {

        private final CartDao cartDao;
        List<CartTable> cartList;

        public FetchCartItems(MekVahanDatabase instance) {
            cartDao = instance.getCartDao();
            cartList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartList = cartDao.getAllMyCartItems();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            String serviceId = "";


            for(int i=0;i<cartList.size()-1;i++){
                serviceId = serviceId + cartList.get(i).getServiceId()+",";

            }
            serviceId = serviceId + cartList.get(cartList.size()-1).getServiceId();

            getArguments().putString(SERVICE_ID,serviceId);
            setActions(cartList);
            // getServiceActions(serviceId);


            clickListener();
        }
    }


    private void setActions(final List<CartTable> cartList) {
        final ViewGroup viewGroup = mRootView.findViewById(R.id.container_package_details);
        for (int i = 0; i < cartList.size(); i++) {
            final Typeface msb = ResourcesCompat.getFont(getActivity(), R.font.montserrat_semi_bold);

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.item_package, null);

            TextView textView = v.findViewById(R.id.service_type);
            textView.setText(cartList.get(i).getServiceName());
            textView.setTypeface(msb);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 16, 0, 0);

            viewGroup.addView(v);
        }


        for (int i = 0; i < viewGroup.getChildCount(); i++) {


            //this contains arry of action having at most 15 action in each separated by !
            // final List<String> actionList = serviceDetails.getActionList();

            final int finalI = i;

            viewGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Pair<String, String>> actionPair = prepareActionLayout(cartList.get(finalI));
                    Log.e("onclick", String.valueOf(actionPair));
                    //setActionPairDialog(actionPair);
                    setRecyclerView(actionPair, viewGroup.getChildAt(finalI));
                }
            });


        }


    }


    private List<Pair<String, String>> prepareActionLayout(CartTable cartTable) {

        List<Pair<String, String>> pairList = new ArrayList<>();

        Pair<String, String> pair = getPair(cartTable.getAction1());
        if (pair != null) pairList.add(pair);


        Pair<String, String> pair2 = getPair(cartTable.getAction2());
        if (pair2 != null) pairList.add(pair2);

        Pair<String, String> pair3 = getPair(cartTable.getAction3());
        if (pair3 != null) pairList.add(pair3);

        Pair<String, String> pair4 = getPair(cartTable.getAction4());
        if (pair4 != null) pairList.add(pair4);

        Pair<String, String> pair5 = getPair(cartTable.getAction5());
        if (pair5 != null) pairList.add(pair5);

        Pair<String, String> pair6 = getPair(cartTable.getAction6());
        if (pair6 != null) pairList.add(pair6);

        Pair<String, String> pair7 = getPair(cartTable.getAction7());
        if (pair7 != null) pairList.add(pair7);

        Pair<String, String> pair8 = getPair(cartTable.getAction8());
        if (pair8 != null) pairList.add(pair8);

        Pair<String, String> pair9 = getPair(cartTable.getAction9());
        if (pair9 != null) pairList.add(pair9);

        Pair<String, String> pair10 = getPair(cartTable.getAction10());
        if (pair10 != null) pairList.add(pair10);

        Pair<String, String> pair11 = getPair(cartTable.getAction11());
        if (pair11 != null) pairList.add(pair11);

        Pair<String, String> pair12 = getPair(cartTable.getAction12());
        if (pair12 != null) pairList.add(pair12);

        Pair<String, String> pair13 = getPair(cartTable.getAction13());
        if (pair13 != null) pairList.add(pair13);

        Pair<String, String> pair14 = getPair(cartTable.getAction14());
        if (pair14 != null) pairList.add(pair14);

        Pair<String, String> pair15 = getPair(cartTable.getAction15());
        if (pair15 != null) pairList.add(pair15);

        return pairList;


    }


    private Pair<String, String> getPair(String action) {

        String[] s = action.split(",");
        if (s.length >= 2)
            return new Pair<>(s[0], s[s.length - 1]);

        return null;


    }

    private void setRecyclerView(List<Pair<String, String>> actionPairList, View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_detail);
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ActionPairAdapter adapter = new ActionPairAdapter(getActivity(), actionPairList);
            recyclerView.setAdapter(adapter);

        }
    }


}



