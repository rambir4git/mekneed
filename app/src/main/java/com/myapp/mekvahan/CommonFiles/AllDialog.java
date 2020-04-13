package com.myapp.mekvahan.CommonFiles;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.myapp.mekvahan.AllBookingsDashboard.TrackOrder2;
import com.myapp.mekvahan.HomePage.AppHomePage;
import com.myapp.mekvahan.R;

public class AllDialog {

    public static final String LOGIN_FAQ           = "login_faq";

    private final String TAG = getClass().getSimpleName();
    private Context mCtx;

    public AllDialog(Context context){
        mCtx = context;
    }


    //After successfull  booking
    public void holdUpTight(final String bookingId, final String date, final String lat, final String lng, final String type) {

        Log.e(TAG,"called : hold up tight");

        final Dialog dialog = new Dialog(mCtx);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.dialog_hold_up_tight, null);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)mCtx).getWindowManager().getDefaultDisplay().getMetrics(metrics);


        float totalScreenWidth = metrics.widthPixels - 120f;
        Log.e(TAG,"totalWidth="+totalScreenWidth);


        ImageView iv_walker = view.findViewById(R.id.image_car);
        iv_walker.animate()
                .setDuration(1000)
                .translationX(totalScreenWidth);

        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                launchParkingBookingSuccessDialog(bookingId, date, lat, lng, type);
            }
        }, 1000);
    }

    private void launchParkingBookingSuccessDialog(final String bookId, final String date, final String lat, final String lng, final String type) {

        final Dialog dialog = new Dialog(mCtx);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.dialog_booking_success, null);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BillAnimation1;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        view.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                clearingStackAndMoveToHomePage();
            }
        });

        view.findViewById(R.id.track_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                clearStackAndTrackOrder(bookId, date, lat, lng, type);

            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                clearingStackAndMoveToHomePage();
            }
        });


        dialog.show();

    }

    private void clearStackAndTrackOrder(String bookId, String date, String lat, String lng, String type) {

        Intent i = new Intent(mCtx, TrackOrder2.class);


        // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.putExtra("booking_id", bookId);
        i.putExtra("type", type);
        i.putExtra("booking_date", date);
        i.putExtra("lat", lat);
        i.putExtra("lng", lng);


        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mCtx.startActivity(i);
    }

    private void clearingStackAndMoveToHomePage(){

        Log.e(TAG,"called : clearingStackAndMoveToHomePage");

        Intent i = new Intent(mCtx, AppHomePage.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mCtx.startActivity(i);
    }

    public void holdTightOnCancellation(final String date, final String lat, final String lng, final String bookingId, final String type) {
        Log.e(TAG, "called : hold up tight");

        final Dialog dialog = new Dialog(mCtx);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.dialog_hold_up_tight, null);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.findViewById(R.id.tv_wait).setVisibility(View.GONE);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) mCtx).getWindowManager().getDefaultDisplay().getMetrics(metrics);


        float totalScreenWidth = metrics.widthPixels - 120f;
        Log.e(TAG, "totalWidth=" + totalScreenWidth);


        ImageView iv_walker = view.findViewById(R.id.image_car);
        iv_walker.animate()
                .setDuration(1000)
                .translationX(totalScreenWidth);

        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                launchBookingCancelled(date, lat, lng, bookingId, type);
            }
        }, 1000);

    }

    private void launchBookingCancelled(final String date, final String lat, final String lng, final String bookingId, final String type) {
        final Dialog dialog1 = new Dialog(mCtx, android.R.style.Theme_Translucent_NoTitleBar);
        dialog1.setContentView(R.layout.dialog_booking_cancelled);

        dialog1.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        dialog1.getWindow().setStatusBarColor(ContextCompat.getColor(mCtx, R.color.red_dark));

        TextView track = dialog1.findViewById(R.id.btn_track);
        dialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {


            }
        });
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                mCtx.startActivity(new Intent(mCtx, TrackOrder2.class).putExtra("lat", lat).putExtra("lng", lng).putExtra("booking_date", date).putExtra("booking_id", bookingId).putExtra("type", type));

            }
        });

        ImageView back = dialog1.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }


}
