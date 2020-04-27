package com.myapp.mekvahan.AllBookingsDashboard.RegulaServices;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.AllBookingsDashboard.TrackOrder2;
import com.myapp.mekvahan.HomePage.ActionStatusAdapter;
import com.myapp.mekvahan.R;
import com.myapp.mekvahan.ReasonForCancellationDialog;

import java.util.ArrayList;
import java.util.List;

import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.CONTACT_MEKVAHAN;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.callIntent;

public class RegularServiceDetails extends AppCompatActivity {

    public final static String REGULAR_SERVICE = "regular_service";
    private final String TAG = getClass().getSimpleName();
    //Payment types
    public static final String PAYMENT_TYPE = "payment_type";
    public static final String AMOUNT_TO_PAY = "amount_to_pay";
    public static final String BUNDLE = "bundle";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_service_details);

        RegularServiceModel serviceDetails = (RegularServiceModel) getIntent().getSerializableExtra(REGULAR_SERVICE);

        setViews(serviceDetails);

    }

    private void setViews(final RegularServiceModel serviceDetails) {

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        settingPageAndServiceStatus(serviceDetails.getServiceStatus());

        findViewById(R.id.track_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegularServiceDetails.this, TrackOrder2.class)
                        .putExtra("simple_back",1)
                        .putExtra("booking_date",serviceDetails.getCreatedAt() )
                        .putExtra("type", "regular")
                        .putExtra("lat", serviceDetails.getLat())
                        .putExtra("lng", serviceDetails.getLng())
                        .putExtra("booking_id", serviceDetails.getBookingId())
                );


            }
        });


        settingVehicleAndOrderDetails(serviceDetails);

        settingPackageDetails(serviceDetails);

        settingAmountDetails(serviceDetails);


        findViewById(R.id.need_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // callIntent(RegularServiceDetails.this,CONTACT_MEKVAHAN);
                ReasonForCancellationDialog dialog = new ReasonForCancellationDialog();
                Bundle bundle = new Bundle();
                bundle.putString("booking_id", serviceDetails.getBookingId());
                bundle.putString("type", "regular");
                bundle.putString("booking_date", serviceDetails.getCreatedAt());
                bundle.putString("lat", serviceDetails.getLat());
                bundle.putString("lng", serviceDetails.getLng());

                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), null);
            }
        });


    }

    private void settingPageAndServiceStatus(int serviceStatus) {

        TextView tv_message         = findViewById(R.id.status_message);
        TextView tv_pin             = findViewById(R.id.pin);
        LinearLayout ll_payment     = findViewById(R.id.payment_layout);
        TextView tv_job_or_feeback  = findViewById(R.id.view_job_or_send_feedback);
        LinearLayout pay = findViewById(R.id.pay_options);

        String message = "";
        int colorId;

        switch (serviceStatus) {
            case 1 :
                message = "Awaiting Partner Confirmation";
                colorId = R.color.yellow;
                tv_pin.setVisibility(View.GONE);
                break;

            case 2 :
                message = "Awaiting Customer Pickup";
                colorId = R.color.yellow;
                tv_pin.setVisibility(View.VISIBLE);
                break;

            case 3 :
                message = "Awaiting Partner Drop Off";
                colorId = R.color.yellow;
                tv_pin.setVisibility(View.GONE);
                break;

            case 4 :
                message = "Service in Progress";
                colorId = R.color.yellow;
                tv_pin.setVisibility(View.GONE);
                break;

            case 5 :
                message = "Awaiting Customer Drop Off";
                colorId = R.color.yellow;
                tv_pin.setVisibility(View.VISIBLE);
                break;

            case 6 :
                message = "Service Complete";
                colorId = R.color.green;
                tv_pin.setVisibility(View.GONE);
                ll_payment.setVisibility(View.GONE);
                tv_job_or_feeback.setText("Send Feedback");
                break;

            case 7 :
                message = "Cancelled By the Partner";
                colorId = R.color.colorPrimary;
                tv_pin.setVisibility(View.GONE);
                ll_payment.setVisibility(View.GONE);
                tv_job_or_feeback.setText("Send Feedback");
                break;

            case 8 :
                message = "Cancelled By the user";
                colorId = R.color.colorPrimary;
                tv_pin.setVisibility(View.GONE);
                ll_payment.setVisibility(View.GONE);
                tv_job_or_feeback.setText("Send Feedback");
                pay.setVisibility(View.GONE);
                findViewById(R.id.need_help).setEnabled(false);
                break;

            case 9 :
                message = "Awaiting Partner Pickup";
                colorId = R.color.yellow;
                tv_pin.setVisibility(View.VISIBLE);
                break;

            default:
                message = "Pata nahi";
                colorId = R.color.yellow;
                tv_pin.setVisibility(View.GONE);
                break;
        }

        tv_message.setText(message);
        tv_message.setBackgroundColor(getColor(colorId));

    }

    private void settingVehicleAndOrderDetails(RegularServiceModel serviceDetails) {

        ((TextView)findViewById(R.id.order_id)).setText("Order #"+serviceDetails.getBookingId());
        ((TextView)findViewById(R.id.brand)).setText(serviceDetails.getVehilceCompany());
        ((TextView)findViewById(R.id.model)).setText(serviceDetails.getVehicleModel());
        ((TextView)findViewById(R.id.plate_no)).setText(serviceDetails.getLicencePlate());

        ImageView imageView = findViewById(R.id.image_vehicle);


        ((TextView)findViewById(R.id.service_location)).setText(serviceDetails.getPickupAddress());
        ((TextView)findViewById(R.id.booking_date_time)).setText(serviceDetails.getCreatedAt());
        ((TextView)findViewById(R.id.pickup_date_time)).setText(serviceDetails.getPickupDateTime());
        ((TextView)findViewById(R.id.serving_date_time)).setText(serviceDetails.getServiceDate() + "  "+serviceDetails.getServiceTime().substring(0,5));
        ((TextView)findViewById(R.id.pin)).setText("PIN : "+serviceDetails.getPin());
        findViewById(R.id.layout_make_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callIntent(RegularServiceDetails.this,CONTACT_MEKVAHAN);
            }
        });

    }

    private void settingPackageDetails(RegularServiceModel serviceDetails) {

        final ViewGroup viewGroup = findViewById(R.id.container_package_details);

        String[] s = serviceDetails.getServiceName().split("!");

        Log.e(TAG,"check2="+serviceDetails.getServiceName());



        for(int i=0;i<s.length;i++){

            final Typeface msb = ResourcesCompat.getFont(RegularServiceDetails.this,R.font.montserrat_semi_bold);

            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.item_package, null);

            TextView textView = v.findViewById(R.id.service_type);
            textView.setText(s[i]);
            textView.setTypeface(msb);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,16,0,0);

            viewGroup.addView(v);
        }

        //OnclickListener on each separate package details view
        for(int i=0;i<viewGroup.getChildCount();i++){

            //this contains arry of action having at most 15 action in each separated by !
            final List<String> actionList = serviceDetails.getActionList();

            final int finalI = i;

            viewGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Pair<String, String>> actionPair =  getActionPair(actionList.get(finalI));
                    //setActionPairDialog(actionPair);
                    setRecyclerView(actionPair, viewGroup.getChildAt(finalI));
                }
            });


        }

    }

    private void settingAmountDetails(final RegularServiceModel serviceDetails) {

        ((TextView)findViewById(R.id.sub_total)).setText(""+serviceDetails.getSubTotal());
        ((TextView)findViewById(R.id.tax)).setText(serviceDetails.getGst()+"");
        ((TextView)findViewById(R.id.additional_charges)).setText(serviceDetails.getAdditionalCharges()+"");
        ((TextView)findViewById(R.id.total)).setText(""+(serviceDetails.getSubTotal()+ serviceDetails.getGst()+ serviceDetails.getAdditionalCharges()));

        if(serviceDetails.getIsPaymentDone() == 1){
            findViewById(R.id.payment_layout).setVisibility(View.GONE);
        }
        else{
            findViewById(R.id.payment_layout).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.pay_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(serviceDetails.getIsPaymentDone() == 0 ){


                    String amount = ((TextView) findViewById(R.id.total)).getText().toString();

                    amount = (int) Double.parseDouble(amount) + "";

                    Log.e("amount", amount + "");


                    Bundle bundle = new Bundle();
                    bundle.putString(PAYMENT_TYPE, REGULAR_SERVICE);
                    bundle.putString(AMOUNT_TO_PAY, amount);
                    bundle.putString("id", serviceDetails.getId());


                }

            }
        });
    }


    private List<Pair<String, String>> getActionPair(String actions) {

        List<Pair<String, String>> pairList = new ArrayList<>();

        String[] a = actions.split("!");

        for(int i=0;i<a.length;i++){
            String[] s = a[i].split(",");

            if(s.length>=2)
                pairList.add(new Pair<>(s[0], s[s.length-1]));
        }

        return pairList;


    }


    private void setRecyclerView(List<Pair<String, String>> actionPair, View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_detail);
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(RegularServiceDetails.this));
            ActionStatusAdapter adapter = new ActionStatusAdapter(RegularServiceDetails.this, actionPair);
            recyclerView.setAdapter(adapter);

        }
    }
}
