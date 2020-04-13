package com.myapp.mekvahan;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

public class DialogDetails extends DialogFragment {
    private final String TAG = DialogDetails.this.getTag();
    View mRootView;
    public static final String PAYMENT_TYPE = "payment_type";
    public static final String AMOUNT_TO_PAY = "amount_to_pay";
    public static final String BUNDLE = "bundle";



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        dialog.getWindow().getAttributes().windowAnimations = R.style.animationSlideRightToRight;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        dialog.getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.red_dark));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.detail_service, container, false);
        LinearLayout toolbar = mRootView.findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Details");

        TextView name = mRootView.findViewById(R.id.tv_service_name);
        name.setText(getArguments().getString("name"));

        TextView description = mRootView.findViewById(R.id.tv_description);
        description.setText(getArguments().getString("des"));

        TextView total = mRootView.findViewById(R.id.value_total);
        //abhi ke liye ye same hai change this
        total.setText("\u20b9" + getArguments().getString("subtotal"));

        TextView subTotal = mRootView.findViewById(R.id.value_subtotal);
        subTotal.setText("\u20b9" + getArguments().getString("subtotal"));

        //hardcoded
        TextView addCharges = mRootView.findViewById(R.id.value_charges);
        addCharges.setText("\u20b9" + "100");

        mRootView.findViewById(R.id.toolbar).findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mRootView.findViewById(R.id.pay_job_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "yet to implement", Toast.LENGTH_SHORT).show();
            }
        });

        return mRootView;
    }
}
