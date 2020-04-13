package com.myapp.mekvahan.AllBookingsDashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.tabs.TabLayout;
import com.myapp.mekvahan.AllBookingsDashboard.RegulaServices.FragRegularServices;
import com.myapp.mekvahan.AllBookingsDashboard.RegulaServices.RegularServiceModel;
import com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions;
import com.myapp.mekvahan.CommonFiles.MySingleton;
import com.myapp.mekvahan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.BASE;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.NO_OF_RETRY;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.RETRY_SECONDS;

public class BookingsHomePage extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private TabLayout mTabLayout;

    private List<Fragment> mFragmentList;
    private List<String> titles;

    private int[] tabIcons = {
            R.drawable.gs_tab,
            R.drawable.repairing_tab,
            R.drawable.wc_tab,
            R.drawable.dp_tab,

    };

    private int[] tabIconsselect = {
            R.drawable.gs_tabselect,
            R.drawable.repairingselect_tab,
            R.drawable.wc_tabselect,
            R.drawable.dp_tabselect,

    };

    private int selected_position;


    private List<RegularServiceModel> mRegularServiceList;

    private ProgressDialog mProgressDialog;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_home_page);

        mProgressDialog = new ProgressDialog(BookingsHomePage.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);

        selected_position = getIntent().getIntExtra("viewpagerId", 0);

        titles = new ArrayList<>();

        //titles.add("All Services");
        titles.add("Regular Services");
        titles.add("Sos Services");
        titles.add("OutStation Cabs");

        mFragmentList = new ArrayList<>();

        //mFragmentList.add(new FragAllServices());
        mFragmentList.add(new FragRegularServices());
        mFragmentList.add(new FragOutStationCabs());
        mFragmentList.add(new FragOutStationCabs());

        mTabLayout = findViewById(R.id.tabs_dashboard);

        mRegularServiceList = new ArrayList<>();
        setView();
        //setViewPager();
        // mProgressDialog.show();
        fetchAllBookingsAndSetViewPager();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //mProgressDialog.show();
        // fetchAllBookingsAndSetViewPager();

    }

    private void setView() {
        ((TextView) findViewById(R.id.toolbar_title)).setText("Bookings");
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    // type -regular, sos, outstation

    private void fetchAllBookingsAndSetViewPager() {

        Log.e(TAG, "called : fetchAllBookings");
        mProgressDialog.show();

        String iou = BASE + "getBookings";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, iou,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProgressDialog.dismiss();

                        if(mRegularServiceList.size()>0)
                            mRegularServiceList.clear();


                        Log.e(TAG, response);


                        try {

                            JSONArray jsonArray = new JSONObject(response).getJSONArray("data");

                            if (jsonArray.length() == 0) {
                                Toast.makeText(BookingsHomePage.this, "No bookings Available", Toast.LENGTH_SHORT).show();
                                //launching empty
                                setViewPager();
                                return;
                            }



                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String type      = jsonObject.getString("type");
                                String bookingId = jsonObject.getString("booking_id");

                                if (type.equals("regular")) {

                                    JSONArray array = jsonObject.getJSONArray("regular_service");

                                    JSONObject rs = array.getJSONObject(0);

                                    //
                                    String id = rs.getString("id");

                                    String pickupAddress  = rs.getJSONObject("pickup_address_id").getJSONObject("data").getString("address");
                                    String lat = rs.getJSONObject("pickup_address_id").getJSONObject("data").getString("lat");
                                    String lng = rs.getJSONObject("pickup_address_id").getJSONObject("data").getString("long");

                                    String dropAddress    = rs.getJSONObject("drop_address_id").getJSONObject("data").getString("address");

                                    String serviceDate    = rs.getString("service_date");
                                    String serviceTime    = rs.getString("service_time").substring(0,5);
                                    String pickupDateTime = rs.getString("pickup_date_time").substring(0,16);


                                    String dropDateTime   = rs.getString("drop_date_time");

                                    String details        = rs.getString("details");
                                    String customerMobile = rs.getString("mobile");

                                    //String paymentDone    = rs.getString("payment");   //null if not done

                                    String stringServiceStatus = rs.getString("status");

                                    int serviceStatus = getIntServiceStatus(stringServiceStatus);

                                    if (serviceStatus == 0) {
                                        Toast.makeText(BookingsHomePage.this, "No such " + stringServiceStatus + " status", Toast.LENGTH_SHORT).show();
                                        return;
                                    }


                                    JSONObject vehicleObject = rs.getJSONObject("Vehicle Details").getJSONObject("data");

                                    String vehicleModel     = vehicleObject.getJSONObject("model_id").getString("name");
                                    String vehicleType      = vehicleObject.getJSONObject("model_id").getString("vehicle_type");
                                    String vehilceCompany   = vehicleObject.getJSONObject("model_id").getJSONArray("company_id")
                                                                .getJSONObject(0).getString("name");
                                    String vehicleImage     = vehicleObject.getJSONObject("model_id").getJSONArray("images").getString(1);
                                    String vehicleLogo      = vehicleObject.getJSONObject("model_id").getJSONArray("logo").getString(1);
                                    String licencePlate     = vehicleObject.getString("license_plate");

                                    String cod              = rs.getString("cod");
                                    String wallet           = rs.getString("wallet");
                                    String online           = rs.getString("online");


                                    if(wallet.equals("null")) wallet = "0";

                                    int isPymentDone = 0;


                                    if (Integer.parseInt(wallet) != 0 || Integer.parseInt(online) != 0) {

                                        isPymentDone = 1;
                                    }

                                    if(Integer.parseInt(cod) == 0)
                                        isPymentDone = 1;

                                    Double subTotal         = Double.valueOf(Integer.parseInt(cod)+Integer.parseInt(online)+Integer.parseInt(wallet));

                                    Double additionalCharges= 0d;
                                    Double gst              = 0d;

                                    String createdAtIncorrectUTC = rs.getJSONObject("created_at").getString("date");
                                    String createdAt = CommonVariablesFunctions.convertToLocalTimeStamp(createdAtIncorrectUTC).substring(0, 16);

                                    //Service details bacha hua hai
                                    JSONObject serviceDetails = rs.getJSONObject("service_details");
                                    String serviceName        = serviceDetails.getString("service_name");

                                    JSONObject serviceStatusObject = rs.getJSONObject("service_status");

                                    List<String> actionArray = new ArrayList<>();

                                    String actions;

//                                    String temp = serviceStatusObject.getString("action1").replaceAll("[^a-zA-Z0-9(), ]+", "");;
//                                    Log.e(TAG,"temp="+temp);

                                    actions        = serviceStatusObject.getString("action1").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action2").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action3").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action4").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action5").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action6").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action7").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action8").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action9").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action10").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action11").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action12").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action13").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action14").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                    actions       += "!" + serviceStatusObject.getString("action15").replaceAll("[^a-zA-Z0-9(), ]+", "");

                                    actionArray.add(actions);


                                    String pin = rs.getString("otp");

                                    //If there is more than 1 regular services
                                    if(jsonArray.length()>1){

                                        for(int j=1;j<array.length();j++){

                                            JSONObject rsExtra = array.getJSONObject(j);

                                            id +=  "!" + rsExtra.getString("id");
//
//                                            subTotal           += rsExtra.getJSONObject("quotation").getDouble("subtotal");
//                                            additionalCharges  += rsExtra.getJSONObject("quotation").getDouble("additional");
//                                            gst                += rsExtra.getJSONObject("quotation").getDouble("gst");

                                            serviceDetails = rsExtra.getJSONObject("service_details");
                                            serviceName    += "!" + serviceDetails.getString("service_name");

                                            serviceStatusObject = rsExtra.getJSONObject("service_status");

                                            actions        = serviceStatusObject.getString("action1").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action2").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action3").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action4").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action5").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action6").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action7").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action8").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action9").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action10").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action11").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action12").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action13").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action14").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                            actions       += "!" + serviceStatusObject.getString("action15").replaceAll("[^a-zA-Z0-9(), ]+", "");

                                            actionArray.add(actions);
                                        }

                                    }


                                    mRegularServiceList.add(new RegularServiceModel(bookingId, id, pickupAddress, lat, lng, dropAddress, serviceDate,
                                            serviceTime,pickupDateTime,dropDateTime,details, customerMobile,isPymentDone,serviceStatus,vehicleModel,
                                            vehicleType,vehilceCompany, vehicleImage,vehicleLogo, licencePlate,subTotal,additionalCharges,gst,createdAt,
                                            serviceName,pin,actionArray));

                                }

                            }

                            setViewPager();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.toString());
                            Toast.makeText(BookingsHomePage.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                Log.e(TAG, error.toString());
                Toast.makeText(BookingsHomePage.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();


                header.put("Accept", "application/json");
                header.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImIxMDhmOWM4OGJlZWRlZGRkZWRlYWZhMWNjYTRjYjYzMTEzNjQyNjE4ZTY1NGFlMjZkOGEyN2U3Yzc2MGY0YzJlZmIwNjk1Yzg2MjVjZTAzIn0.eyJhdWQiOiIxIiwianRpIjoiYjEwOGY5Yzg4YmVlZGVkZGRlZGVhZmExY2NhNGNiNjMxMTM2NDI2MThlNjU0YWUyNmQ4YTI3ZTdjNzYwZjRjMmVmYjA2OTVjODYyNWNlMDMiLCJpYXQiOjE1ODQ2MzM4MTEsIm5iZiI6MTU4NDYzMzgxMSwiZXhwIjoxNjE2MTY5ODExLCJzdWIiOiIzMDQiLCJzY29wZXMiOltdfQ.JR2S-9jhpi6EIdCzwWUIxhJLNixqnYi2nXcZRUj0FuLiHqihP1sT9N3pwIwbhrKCxoyPOr5orEbtTx7l9lRX-QzMv90oKW37GfzG1ql1giQs4B4Gh6oIEeO1Trg36fhQQltCHJa-vjCC35IcQNz_VBF_BjCQcc9vG_wHy_N8FLAseIZ2XGUXbvEeKnfE6o6waVsiSh404SbhMRolIQD3DHKfU5kV6Pz2YisHZ5299E29zf6oTLtmWnfSyuRuXhwvTFc2VOKnef991xxTZrqaUFd4A4f-D7lqKQX-R6sqXysQJtlGqOQYgc9CGIl394zGC1Q8Wc4hrti7q1ZFRwJRvchAnJ4hs_bA_-PCRxkKXwRt5MDdzlI9g9WGBJ-TnIMA46L02Kg6u7xDMYGz1GklntQcjbzApQPrRsVdoXN9e8qpGL9WGK5zgQEApffefszbkfQyfKkRkTnGD5FiUdCyRjgykF_l_rV_XOH1BdXyN6r2EetNgY7yOPOCHSe5x-qQYBe8pgycvU1JOmG5C52BSPMYgk-7E54MD1IrHc0t6_jxAYDzr_MCtSJJ045OhKx2N_qMfwutwYRKokgGvfoXtmAbKlKWDcjxe0Qg6YyaeJGQJF0RZ1eebt-K1jL0pjRTglFogXKgp94JPNWnccMK3W3I4OzoBW55VGGcc1P21AM");

                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy((RETRY_SECONDS),
                NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(BookingsHomePage.this).addToRequestQueue(stringRequest);
    }


    private void setViewPager() {


        Log.e(TAG,"called : setViewPager");

        mViewPager = findViewById(R.id.viewpager_dashboard);

        DashboardFragmentPagerAdapter adapter = new DashboardFragmentPagerAdapter(
                getSupportFragmentManager(), mFragmentList);


        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(adapter);


        mViewPager.setCurrentItem(selected_position);
        setUpCustomTabs();
        // mViewPager.addOnPageChangeListener(this);


    }

    private void setUpCustomTabs() {

        for (int i = 0; i < titles.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.custom_tabview, null);

            TextView tv = view.findViewById(R.id.tab_title);
            tv.setText(titles.get(i));

            ImageView img = view.findViewById(R.id.tab_icon);
            img.setImageResource(tabIcons[i]);
            TabLayout.Tab tab = mTabLayout.getTabAt(i);

            if (tab != null)
                tab.setCustomView(view);//set custom view
        }

        ((ImageView) mTabLayout
                .getTabAt(selected_position).getCustomView().findViewById(R.id.tab_icon))
                .setImageResource(tabIconsselect[selected_position]);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               ((ImageView) tab.getCustomView().findViewById(R.id.tab_icon)).setImageResource(tabIconsselect[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((ImageView) tab.getCustomView().findViewById(R.id.tab_icon)).setImageResource(tabIcons[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private int getIntServiceStatus(String stringServiceStatus) {

        if (stringServiceStatus.equals("booked")) return 1;
        else if (stringServiceStatus.equals("upcoming")) return 2;
        else if (stringServiceStatus.equals("ongoing")) return 3;
        else if (stringServiceStatus.equals("pending")) return 4;
        else if (stringServiceStatus.equals("customer_drop")) return 5;
        else if (stringServiceStatus.equals("complete")) return 6;
        else if (stringServiceStatus.equals("rejected")) return 7;
        else if (stringServiceStatus.equals("cancelled")) return 8;
        else if (stringServiceStatus.equals("service_complete")) return 9;


        return 0;

    }



    public List<RegularServiceModel> getRegularServiceFromParent(){
        Log.e(TAG,"called : getRegularServiceFromParent");

        for(int i=0;i<mRegularServiceList.size();i++){

        }

        return mRegularServiceList;
    }

}
