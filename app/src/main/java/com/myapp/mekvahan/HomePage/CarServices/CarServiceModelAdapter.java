package com.myapp.mekvahan.HomePage.CarServices;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.Cart.CartTable;
import com.myapp.mekvahan.CommonFiles.AppConstants;
import com.myapp.mekvahan.HomePage.DialogServiceDetail;
import com.myapp.mekvahan.HomePage.ServiceModel;
import com.myapp.mekvahan.R;
import com.myapp.mekvahan.Rooms.CartDao;
import com.myapp.mekvahan.Rooms.MekVahanDatabase;

import java.util.List;
import java.util.StringTokenizer;

import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.isInteger;

public class CarServiceModelAdapter extends RecyclerView.Adapter<CarServiceModelAdapter.ServiceViewHolder> {

    private Context mCtx;
    private List<ServiceModel> mServiceModel;
    private final String TAG = getClass().getSimpleName();
    private boolean serviceStatus;


    public CarServiceModelAdapter(Context mCtx, List<ServiceModel> mServiceModel) {
        this.mCtx = mCtx;
        this.mServiceModel = mServiceModel;
    }

    private int[] repairing = {
            R.drawable.comprehensive__checkup, R.drawable.break_disk_pad__replacement, R.drawable.ac_check, R.drawable.ac_gas_refill,
            R.drawable.ac_service, R.drawable.clutch_check, R.drawable.battery_replacement, R.drawable.other_diagnosis,
            R.drawable.scanning,
    };

    private int[] general_service = {
            R.drawable.primary_service, R.drawable.standard, R.drawable.comprehensive,

    };
    private int[] wheel_care = {
            R.drawable.wheel_allignment, R.drawable.wheel_balancing, R.drawable.wheel_allignment_and_balancing, R.drawable.tyre_replacement
    };

    private int[] denting_painting = {
            R.drawable.bonnet, R.drawable.bumper_rear,
            R.drawable.quarter_pannel_left, R.drawable.quarter_pannel_right,
            R.drawable.running_board_left, R.drawable.running_board_right, R.drawable.fender_left,
            R.drawable.fender_right, R.drawable.door_front_left,
            R.drawable.door_front_right, R.drawable.door_rear_left,
            R.drawable.door_rear_right, R.drawable.dikky,
            R.drawable.bonnet, R.drawable.roof,
            R.drawable.full_body

    };

    private int[] car_care = {
            R.drawable.car_wash, R.drawable.interior_dry_cleaning,
            R.drawable.exterior_car_rubbing, R.drawable.complete__car_detaling,
            R.drawable.teflon_coating, R.drawable.nano_coating,
    };

    private int[] others = {
            R.drawable.other_mechanical,
            R.drawable.other_electrical
    };






    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ServiceViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.row_service_model,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceViewHolder holder, int position) {


        final ServiceModel service = mServiceModel.get(position);

        String service_type = null;
        int service_icon = R.drawable.about_icon;

        String str= service.getCategory();

        if(str.contains("repairing")){
            holder.img.setImageResource(repairing[position]);
        }

        if(str.contains("general_services")){
            holder.img.setImageResource(general_service[position]);
        }

        if(str.contains("wheel_care")){
            holder.img.setImageResource(wheel_care[position]);
        }

        if(str.contains("denting_painting")){
            holder.img.setImageResource(denting_painting[position]);
        }
        if(str.contains("car_care")){
            holder.img.setImageResource(car_care[position]);
        }
        if(str.contains("others")){
            holder.img.setImageResource(others[position]);
        }

        if ((service.getServiceName().equals("Primary Service") || service.getServiceName().equals("Comprehensive Service") || service.getServiceName().equals("Standard Service")) && service.isAddedToCart()) {
            serviceStatus=true;
            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(mCtx).edit();
            editor.putBoolean(service.getServiceName(), true);
            editor.putBoolean("key", serviceStatus);
            editor.commit();
        }




        if (service.getServiceName().equals(AppConstants.ServiceTypes.primary_service.getServicetype())) {
            service_type = AppConstants.ServiceTypes.primary_service.getServicetype();
           // service_icon = AppConstants.ServiceTypes.primary_service.getService_icon();
        } else if (service.getServiceName().equals(AppConstants.ServiceTypes.standard_service.getServicetype())) {
            service_type = AppConstants.ServiceTypes.standard_service.getServicetype();
          //  service_icon = AppConstants.ServiceTypes.standard_service.getService_icon();
        } else if (service.getServiceName().equals(AppConstants.ServiceTypes.Comprehensive_service.getServicetype())) {
            service_type = AppConstants.ServiceTypes.Comprehensive_service.getServicetype();
           // service_icon = AppConstants.ServiceTypes.Comprehensive_service.getService_icon();
        } else if (service.getServiceName().equals(AppConstants.ServiceTypes.wheel_alignment_balancing.getServicetype())) {
            service_type = AppConstants.ServiceTypes.wheel_alignment_balancing.getServicetype();
           // service_icon = AppConstants.ServiceTypes.wheel_alignment_balancing.getService_icon();
        } else if (service.getServiceName().equals(AppConstants.ServiceTypes.comprehensive_checkup.getServicetype())) {
            service_type = AppConstants.ServiceTypes.comprehensive_checkup.getServicetype();
           // service_icon = AppConstants.ServiceTypes.comprehensive_checkup.getService_icon();
        } else {
            service_type = service.getServiceName();
           // service_icon = AppConstants.ServiceTypes.standard_service.getService_icon();
        }

        holder.service.setText(service_type);
       // holder.img.setImageResource(service_icon);

        if (service.getPriorLine1().isEmpty())
            holder.txtview_priorline1.setVisibility(View.GONE);
        else {
            StringTokenizer tokens = new StringTokenizer(service.getPriorLine1(), "\\\n");
            holder.txtview_priorline1.setText(tokens.nextToken());
        }


        String[] s = service.getWhen().split("BREAKNEWLINE");

        //Remove views before adding in android
        holder.layout_when_container.removeAllViews();

        for (int i = 0; i < s.length; i++) {
            View view = LayoutInflater.from(mCtx).inflate(R.layout.item_when_conditions, holder.layout_when_container, false);
            ((TextView) view.findViewById(R.id.textview_condition)).setText(s[i].trim());
            holder.layout_when_container.addView(view);
        }




        holder.amount.setText(service.getSubtotal());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogServiceDetail detailHomepage = new DialogServiceDetail();

                Bundle bundle = new Bundle();
                bundle.putSerializable("serviceDetail", service);
                detailHomepage.setArguments(bundle);

                detailHomepage.show( ( (FragmentActivity)mCtx).getSupportFragmentManager(), null);

            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG,"total = "+service.getSubtotal());

                if(!isInteger(service.getSubtotal()) || !isInteger(service.getTotal())){
                    Toast.makeText(mCtx, "Service can't be added",Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor editor= sharedPreferences.edit();
                if(service.isAddedToCart()){


                    new RemoveFromCart(MekVahanDatabase.getInstance(mCtx),service, holder).execute(service);
                    resetToDefault(service, holder.getAdapterPosition());
                    return;
                }



                if(service.getCategory().contains("general_services")){
                    serviceStatus=sharedPreferences.getBoolean("key", false);
                    Log.e("servicestatus", String.valueOf(serviceStatus));

                    if(!serviceStatus){
                        new AddToCart(MekVahanDatabase.getInstance(mCtx), service, holder).execute(service);

                        serviceStatus=true;
                        editor.putBoolean("key", serviceStatus);
                        editor.putBoolean(service.getServiceName(), true);
                        editor.commit();


                    }
                    else if(serviceStatus) {


                        Toast.makeText(v.getContext(), "Only One Particular General service should be selected", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(service.getCategory().contains("repairing")){
                    new AddToCart(MekVahanDatabase.getInstance(mCtx), service, holder).execute(service);
                }
                else if(service.getCategory().contains("wheel_care")){
                    if(service.getServiceName().equals("Wheel Alignment") && sharedPreferences.getBoolean("Comprehensive Service", false)){
                        Toast.makeText(v.getContext(), "This Service Is Already Included in your Selected Service", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    if(service.getServiceName().equals("Wheel Balancing") && sharedPreferences.getBoolean("Comprehensive Service", false)){
                        Toast.makeText(v.getContext(), "This Service Is Already Included in your Selected Service", Toast.LENGTH_SHORT).show();


                        return;
                    }
                    if(service.getServiceName().equals("Wheel Alignment and Balancing") && sharedPreferences.getBoolean("Comprehensive Service", false)){
                        Toast.makeText(v.getContext(), "This Service Is Already Included in your Selected Service", Toast.LENGTH_SHORT).show();


                        return;
                    }

                    new AddToCart(MekVahanDatabase.getInstance(mCtx), service, holder).execute(service);
                }
                else if(service.getCategory().contains("denting_painting")){
                    new AddToCart(MekVahanDatabase.getInstance(mCtx), service, holder).execute(service);
                }else if(service.getCategory().contains("car_care")){
                    if(service.getServiceName().equals("Car Wash") && (sharedPreferences.getBoolean("Primary Service", false) || sharedPreferences.getBoolean("Standard Service", false) || sharedPreferences.getBoolean("Comprehensive Service", false))){

                        Toast.makeText(v.getContext(), "This Service Is Already Included in your Selected Service", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    if(service.getServiceName().equals("Interior Dry Cleaning") && (sharedPreferences.getBoolean("Primary Service", false) || sharedPreferences.getBoolean("Standard Service", false) || sharedPreferences.getBoolean("Comprehensive Service", false))){

                        Toast.makeText(v.getContext(), "This Service Is Already Included in your Selected Service", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    new AddToCart(MekVahanDatabase.getInstance(mCtx), service, holder).execute(service);
                }else if(service.getCategory().contains("others")){
                    new AddToCart(MekVahanDatabase.getInstance(mCtx), service, holder).execute(service);
                }
            }
        });

        if(service.isAddedToCart()){
            holder.add.setBackgroundColor(ContextCompat.getColor(mCtx,R.color.green));
            holder.add.setTextColor(ContextCompat.getColor(mCtx,R.color.white));
            holder.add.setText("REMOVE");
        }

        else {
            holder.add.setBackground(ContextCompat.getDrawable(mCtx,R.drawable.shape_border));
            holder.add.setTextColor(ContextCompat.getColor(mCtx,R.color.colorPrimary));
            holder.add.setText("ADD");
        }


    }
    private void resetToDefault(ServiceModel serviceModel, int selectedPosition){

        serviceModel=mServiceModel.get(selectedPosition);
        serviceStatus=false;
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(mCtx);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean("key", serviceStatus);
        editor.putBoolean(serviceModel.getServiceName(), false);
        editor.commit();




    }


    @Override
    public int getItemCount() {
        return mServiceModel.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        public TextView txtview_priorline1, service, amount, rs_icon;
        public Button add;
        public ImageView img;
        public LinearLayout layout_when_container;

        RelativeLayout card;

        public ServiceViewHolder(@NonNull View view) {
            super(view);

            layout_when_container = view.findViewById(R.id.layout_when_container);

            txtview_priorline1 =  view.findViewById(R.id.txtview_priorline1);
            img                =  view.findViewById(R.id.img_service_icon);
            service            =  view.findViewById(R.id.txt_primaryservice);
            amount             =  view.findViewById(R.id.amount);
            add                =  view.findViewById(R.id.add);
            card               =  view.findViewById(R.id.card);
        }
    }

    class AddToCart extends AsyncTask<ServiceModel,Void,Void> {

        private final CartDao cartDao;
        private ServiceViewHolder holder;
        private ServiceModel service;

        private List<CartTable> cartTableList;

        public AddToCart(MekVahanDatabase instance,ServiceModel ser, ServiceViewHolder h) {
            cartDao = instance.getCartDao();
            holder = h;
            service = ser;
        }


        @Override
        protected Void doInBackground(ServiceModel... serviceModels) {

            ServiceModel service = serviceModels[0];

            CartTable cartTable = new CartTable(0, service.getId(),1, service.getServiceName(),service.getAction1(),
                    service.getAction2(), service.getAction3(),service.getAction4(),service.getAction5(),
                    service.getAction6(),service.getAction7(),service.getAction8(),service.getAction9(),
                    service.getAction10(),service.getAction11(),service.getAction12(),service.getAction13(),
                    service.getAction14(),service.getAction15(),service.getTotal(),service.getSubtotal(),
                    service.getAddiCharges(), service.getGst(), service.getStrikedOutAmount(), 1, 0);
            cartDao.insert(cartTable);

            cartTableList = cartDao.getAllMyCartItems();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            holder.add.setBackgroundColor(ContextCompat.getColor(mCtx,R.color.green));
            holder.add.setTextColor(ContextCompat.getColor(mCtx,R.color.white));
            holder.add.setText("REMOVE");

            setupCartBadgeAndCard(cartTableList);

            service.setAddedToCart(true);


        }
    }

    class RemoveFromCart extends AsyncTask<ServiceModel,Void,Void> {

        private final CartDao cartDao;
        private ServiceViewHolder holder;
        private ServiceModel service;

        private List<CartTable> cartTableList;

        public RemoveFromCart(MekVahanDatabase instance,ServiceModel ser, ServiceViewHolder h) {
            cartDao = instance.getCartDao();
            holder = h;
            service = ser;
        }


        @Override
        protected Void doInBackground(ServiceModel... serviceModels) {

            ServiceModel service = serviceModels[0];
            cartDao.deleteByServiceId(service.getId());
            cartTableList = cartDao.getAllMyCartItems();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            holder.add.setBackground(ContextCompat.getDrawable(mCtx,R.drawable.shape_border));
            holder.add.setTextColor(ContextCompat.getColor(mCtx,R.color.colorPrimary));
            holder.add.setText("ADD");

            setupCartBadgeAndCard(cartTableList);

            service.setAddedToCart(false);


        }
    }



    private void setupCartBadgeAndCard(List<CartTable> cartTableList) {

        ((CarServicesHomePage)mCtx).setupCartBadge(cartTableList.size());

        if(cartTableList.size() == 0){
            ((Activity)mCtx).findViewById(R.id.to_cart).setVisibility(View.GONE);
            return;
        }

        ((Activity)mCtx).findViewById(R.id.to_cart).setVisibility(View.VISIBLE);

        TextView tv_itemInCart   = ((Activity)mCtx).findViewById(R.id.item_in_cart);
        TextView tv_cartSubTotal = ((Activity)mCtx).findViewById(R.id.cart_sub_total);

        int cartSubTotal = 0;

        for(int i=0;i<cartTableList.size();i++)
            cartSubTotal += Integer.parseInt(cartTableList.get(i).getSubtotal());

        if(cartTableList.size()==1)
            tv_itemInCart.setText("1 item in cart");
        else
            tv_itemInCart.setText(cartTableList.size()+" items in cart");

        tv_cartSubTotal.setText("\u20B9 "+cartSubTotal);

    }


}
