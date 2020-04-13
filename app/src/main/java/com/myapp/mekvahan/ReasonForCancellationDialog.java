package com.myapp.mekvahan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.myapp.mekvahan.CommonFiles.AllDialog;

public class ReasonForCancellationDialog extends DialogFragment {
    private View mRootView;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_reason_for_cancellation, container, false);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please wait...");

        Button cancel = mRootView.findViewById(R.id.cancel);
        Button dont = mRootView.findViewById(R.id.dont_cancel);
        ImageView back = mRootView.findViewById(R.id.back_btn);


        final String bookingId = getArguments().getString("booking_id");
        final String type = getArguments().getString("type");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AllDialog(getActivity()).holdTightOnCancellation(getArguments().getString("booking_date"), getArguments().getString("lat"), getArguments().getString("lng"), bookingId, type);

            }
        });
        dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final RadioButton rb1 = mRootView.findViewById(R.id.radio1);
        final RadioButton rb2 = mRootView.findViewById(R.id.radio2);
        final RadioButton rb3 = mRootView.findViewById(R.id.radio3);
        final RadioButton rb4 = mRootView.findViewById(R.id.radio4);
        final RadioButton rb5 = mRootView.findViewById(R.id.radio5);
        final RadioButton rb6 = mRootView.findViewById(R.id.radio6);

        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb1.isChecked()) {
                    rb1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.background4));
                } else {
                    rb1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
            }
        });
        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb2.isChecked()) {
                    rb2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.background4));
                } else {
                    rb2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
            }
        });
        rb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb3.isChecked()) {
                    rb3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.background4));
                } else {
                    rb3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
            }
        });
        rb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb4.isChecked()) {
                    rb4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.background4));
                } else {
                    rb4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
            }
        });
        rb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb5.isChecked()) {
                    rb5.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.background4));
                } else {
                    rb5.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
            }
        });
        rb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb6.isChecked()) {
                    rb6.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.background4));
                } else {
                    rb6.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
            }
        });

        return mRootView;

    }

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


}
