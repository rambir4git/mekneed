package com.myapp.mekvahan.AllBookingsDashboard.RegulaServices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.AllBookingsDashboard.BookingsHomePage;
import com.myapp.mekvahan.DialogFeedback;
import com.myapp.mekvahan.R;
import com.myapp.mekvahan.ReasonForCancellationDialog;

import java.text.ParseException;
import java.util.List;

import static com.myapp.mekvahan.AllBookingsDashboard.RegulaServices.RegularServiceDetails.REGULAR_SERVICE;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.CONTACT_MEKVAHAN;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.callIntent;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.getFormattedDate;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.getUnixTimeInMilli;

public class RegularServicesAdapter extends RecyclerView.Adapter<RegularServicesAdapter.RegularViewHolder> {

    private Context mCtx;
    private List<RegularServiceModel> mRegularServices;

    public RegularServicesAdapter(Context mCts, List<RegularServiceModel> mRegularServices) {
        this.mCtx = mCts;
        this.mRegularServices = mRegularServices;
    }

    @NonNull
    @Override
    public RegularServicesAdapter.RegularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RegularViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.recycler_view_all_services,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RegularServicesAdapter.RegularViewHolder holder, int i) {

        final RegularServiceModel service = mRegularServices.get(i);

        final int serviceStatus  = service.getServiceStatus();

        setViewServiceTypeAndStatus(serviceStatus, holder);


        holder.whole_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx, RegularServiceDetails.class);
                i.putExtra(REGULAR_SERVICE, service);
                mCtx.startActivity(i);
            }
        });

        String date="";

        try {
            String unix = ""+getUnixTimeInMilli(service.getCreatedAt())/1000L;
            date = getFormattedDate("",unix);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //createdAt = "2019-09-05 14:38:12.000000"

        String timeToDisplay = "";

        //time = 14:38

        int hr  = Integer.parseInt((service.getCreatedAt()).substring(11,13));
        int min = Integer.parseInt((service.getCreatedAt()).substring(14,16));

        if(hr>=13)
            //timeToDisplay = (hr - 12)+":"+min+" PM";
            timeToDisplay = (hr - 12) + ":" + min + " PM";

        else if(hr == 12)
            timeToDisplay = (hr)+":"+min+" PM";
        else
            timeToDisplay = (hr)+":"+min+" AM";

        holder.tv_date.setText(date);
        holder.tv_time.setText(timeToDisplay);

        holder.tv_orderId.setText("#"+service.getBookingId());
        holder.tv_model.setText(service.getVehicleModel());
        holder.tv_plate.setText(service.getLicencePlate());


        String[] s = service.getServiceName().split("!");

        holder.tv_serviceType.setText(s[0]);
        holder.tv_pin.setText("PIN : "+service.getPin());


        if(service.getIsPaymentDone() == 1){
            holder.tv_paymentCon.setTextColor(ContextCompat.getColor(mCtx,R.color.green));
            holder.iv_paymentConTick.setImageDrawable(ContextCompat.getDrawable(mCtx,R.drawable.tick_green));
        }
        else{
            holder.tv_paymentCon.setTextColor(ContextCompat.getColor(mCtx,R.color.background4));
            holder.iv_paymentConTick.setImageDrawable(ContextCompat.getDrawable(mCtx,R.drawable.tick_light));
        }


        holder.btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callIntent(mCtx, CONTACT_MEKVAHAN);

            }
        });

        holder.btn_need_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //callIntent(mCtx, CONTACT_MEKVAHAN);
                DialogFeedback feedback = new DialogFeedback();
                Bundle bundle = new Bundle();
                bundle.putString("booking_id", service.getBookingId());
                feedback.setArguments(bundle);
                feedback.show(((BookingsHomePage) mCtx).getSupportFragmentManager(), null);
            }
        });

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReasonForCancellationDialog dialog = new ReasonForCancellationDialog();
                Bundle bundle = new Bundle();
                bundle.putString("booking_id", service.getBookingId());
                bundle.putString("type", "regular");
                bundle.putString("booking_date", service.getCreatedAt());
                bundle.putString("lat", service.getLat());
                bundle.putString("lng", service.getLng());
                dialog.setArguments(bundle);
                dialog.show(((BookingsHomePage) mCtx).getSupportFragmentManager(), null);
            }
        });




    }




    @Override
    public int getItemCount() {
        return mRegularServices.size();
    }

    public class RegularViewHolder extends RecyclerView.ViewHolder {

        CardView whole_card;
        TextView tv_date, tv_time, tv_orderId;
        ImageView modelLogo, serviceLogo;
        TextView tv_model, tv_plate, tv_serviceType, tv_status, tv_pin;
        LinearLayout btn_connect;
        TextView btn_need_help, btn_remove;

        TextView tv_paymentCon;
        ImageView iv_paymentConTick;

        public RegularViewHolder(@NonNull View itemView) {
            super(itemView);

            whole_card      = itemView.findViewById(R.id.card);

            tv_date        =  itemView.findViewById(R.id.date);
            tv_time        =  itemView.findViewById(R.id.time);

            tv_orderId     =  itemView.findViewById(R.id.order_id);
            tv_model       =  itemView.findViewById(R.id.model);
            tv_plate       =  itemView.findViewById(R.id.plate_no);
            tv_serviceType =  itemView.findViewById(R.id.service_type);
            tv_status      =  itemView.findViewById(R.id.service_status);
            tv_pin         =  itemView.findViewById(R.id.pin);

            btn_connect    =  itemView.findViewById(R.id.connect);
            btn_remove     =  itemView.findViewById(R.id.remove);
            btn_need_help  =  itemView.findViewById(R.id.need_help);

            modelLogo      =  itemView.findViewById(R.id.model_logo);
            serviceLogo    =  itemView.findViewById(R.id.service_icon);


            tv_paymentCon = itemView.findViewById(R.id.payment_confirm);
            iv_paymentConTick      = itemView.findViewById(R.id.payment_confirm_tick);
        }
    }

    private void setViewServiceTypeAndStatus(int serviceStatus, RegularViewHolder holder) {

        switch (serviceStatus){

            case 1:
                holder.tv_status.setText("Awaiting Confirmation");
                holder.tv_status.setTextColor(ContextCompat.getColor(mCtx,R.color.red_dark));
                holder.btn_connect.setVisibility(View.VISIBLE);
                holder.btn_remove.setVisibility(View.VISIBLE);
                holder.btn_need_help.setVisibility(View.GONE);
                holder.tv_pin.setVisibility(View.GONE);
                break;

            case 2:
                holder.tv_status.setText("Awaiting customer pickup");
                holder.tv_status.setTextColor(ContextCompat.getColor(mCtx,R.color.yellow2));

                holder.btn_connect.setVisibility(View.VISIBLE);
                holder.btn_remove.setVisibility(View.GONE);
                holder.btn_need_help.setVisibility(View.GONE);
                holder.tv_pin.setVisibility(View.VISIBLE);

                break;

            case 3:
                holder.tv_status.setText("Awaiting partner drop off");
                holder.tv_status.setTextColor(ContextCompat.getColor(mCtx,R.color.yellow2));
                holder.btn_connect.setVisibility(View.VISIBLE);
                holder.btn_remove.setVisibility(View.GONE);
                holder.btn_need_help.setVisibility(View.GONE);
                holder.tv_pin.setVisibility(View.GONE);
                break;

            case 4:
                holder.tv_status.setText("Service in progress");
                holder.tv_status.setTextColor(ContextCompat.getColor(mCtx,R.color.yellow2));

                holder.btn_connect.setVisibility(View.VISIBLE);
                holder.btn_remove.setVisibility(View.GONE);
                holder.btn_need_help.setVisibility(View.GONE);
                holder.tv_pin.setVisibility(View.GONE);
                break;

            case 5:
                holder.tv_status.setText("Awaiting customer drop off");
                holder.tv_status.setTextColor(ContextCompat.getColor(mCtx,R.color.yellow2));

                holder.btn_connect.setVisibility(View.VISIBLE);
                holder.btn_remove.setVisibility(View.GONE);
                holder.btn_need_help.setVisibility(View.GONE);
                holder.tv_pin.setVisibility(View.VISIBLE);

                break;

            case 6:
                holder.tv_status.setText("Service Complete");
                holder.tv_status.setTextColor(ContextCompat.getColor(mCtx,R.color.green_dark));

                holder.btn_connect.setVisibility(View.GONE);
                holder.btn_remove.setVisibility(View.GONE);
                holder.btn_need_help.setVisibility(View.VISIBLE);
                holder.tv_pin.setVisibility(View.GONE);

                break;

            case 7:
                holder.tv_status.setText("Service Cancelled by partner");
                holder.tv_status.setTextColor(ContextCompat.getColor(mCtx,R.color.red_dark));
                holder.btn_connect.setVisibility(View.GONE);
                holder.btn_remove.setVisibility(View.GONE);
                holder.btn_need_help.setVisibility(View.VISIBLE);
                holder.tv_pin.setVisibility(View.GONE);

                break;

            case 8:
                holder.tv_status.setText("Service Cancelled by user");
                holder.tv_status.setTextColor(ContextCompat.getColor(mCtx,R.color.red_dark));
                holder.btn_connect.setVisibility(View.GONE);
                holder.btn_remove.setVisibility(View.GONE);
                holder.btn_need_help.setVisibility(View.VISIBLE);
                holder.tv_pin.setVisibility(View.GONE);


                break;


            case 9:
                holder.tv_status.setText("Awaiting Partner Pickup");
                holder.tv_status.setTextColor(ContextCompat.getColor(mCtx,R.color.yellow2));
                holder.btn_connect.setVisibility(View.VISIBLE);
                holder.btn_remove.setVisibility(View.GONE);
                holder.btn_need_help.setVisibility(View.GONE);
                holder.tv_pin.setVisibility(View.VISIBLE);

                break;

            default:
                holder.tv_status.setText("Pata nahi");
                holder.tv_status.setTextColor(ContextCompat.getColor(mCtx,R.color.red_dark));
                holder.btn_connect.setVisibility(View.GONE);
                holder.btn_remove.setVisibility(View.GONE);
                holder.btn_need_help.setVisibility(View.GONE);
                holder.tv_pin.setVisibility(View.GONE);

                break;
        }

    }

}
