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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

public class DialogFeedback extends DialogFragment {
    private final String TAG = DialogFeedback.this.getTag();
    View mRootView;
    float rate;

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
        mRootView = inflater.inflate(R.layout.feedback, container, false);
        TextView title = mRootView.findViewById(R.id.toolbar_title);
        title.setText("Feedback");

        final RatingBar stars = mRootView.findViewById(R.id.rating_bar);
        rate = stars.getRating();

        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating = rate;
            }
        });

        final EditText et = mRootView.findViewById(R.id.et_message);
        ImageView back = mRootView.findViewById(R.id.back_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button submit = mRootView.findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et.getText().equals("") && rate != 0) {
                    sendFeedback(v, getArguments().getString("booking_id"), rate, et.getText().toString().trim());

                } else if (et.getText().equals("")) {
                    Toast.makeText(getActivity(), "Please describe your problem a bit", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please rate us out of 5", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return mRootView;
    }

    private void sendFeedback(final View view, final String id, final float star, final String message) {
        Toast.makeText(getActivity(), "feedback sent", Toast.LENGTH_SHORT).show();
    }

}
