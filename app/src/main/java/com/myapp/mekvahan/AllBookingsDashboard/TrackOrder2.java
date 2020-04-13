package com.myapp.mekvahan.AllBookingsDashboard;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myapp.mekvahan.CommonFiles.AppConstants;
import com.myapp.mekvahan.CommonFiles.MySingleton;
import com.myapp.mekvahan.HomePage.AppHomePage;
import com.myapp.mekvahan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.BASE;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.CUSTOMER_CARE;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.NO_OF_RETRY;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.RETRY_SECONDS;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.callIntent;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.getUnixTimeInMilli;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.sendReferalIntent;

public class TrackOrder2 extends AppCompatActivity implements OnMapReadyCallback {

    private final String TAG = getClass().getSimpleName();
    private GoogleMap mMap;

    private ProgressBar progressBar, progress;
    private TextView tv_booked_time, tv_booked_date, tv_confirmed_time, tv_conf_date, tv_pick_time, tv_pick_date, tv_picked_time, tv_picked_date,
            tv_prog_time, tv_prog_date, tv_comp_time, tv_comp_date, tv_time_drop, tv_date_drop, tv_service_canc;
    private ImageView imageView_car, imageView_tool, imageView_toolDone, imageView_carDrop, iv_confirmed, iv_pick, cancelled, booked;
    private String status = "", update = "";
    private Double latitude, longitude;
    private LatLng dest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        initView();


        clickListener();





        try {

            Bundle bundle = getIntent().getExtras();


            String booking_date = bundle.getString("booking_date");


            getBookingStatus(bundle.getString("booking_id"), bundle.getString("type"));

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dat = dateFormatter.parse(booking_date);
            long epoch = dat.getTime();

            Log.e("time ist", String.valueOf(epoch));
            SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
            timeFormatter.format(new Date(epoch));


            String format = "dd-MM-yyyy";
            String unix = "" + getUnixTimeInMilli(booking_date) / 1000L;
            Date date1 = new java.util.Date(Long.valueOf(unix) * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            String dt = sdf.format(date1);

            tv_booked_date.setText(dt);
            tv_booked_time.setText(timeFormatter.format(new Date(epoch)));


        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    private void initView() {


        tv_booked_time = findViewById(R.id.tv_bookingtime);
        tv_booked_date = findViewById(R.id.tv_bookingdate);
        tv_confirmed_time = findViewById(R.id.tv_time_confirmed);
        tv_conf_date = findViewById(R.id.tv_date_confirmed);
        tv_pick_time = findViewById(R.id.tv_time_pick);
        tv_pick_date = findViewById(R.id.tv_date_pick);
        tv_picked_time = findViewById(R.id.tv_time_picked);
        tv_picked_date = findViewById(R.id.tv_date_picked);
        tv_prog_time = findViewById(R.id.tv_time_progress);
        tv_prog_date = findViewById(R.id.tv_date_progress);
        tv_comp_time = findViewById(R.id.tv_time_complete);
        tv_comp_date = findViewById(R.id.tv_date_complete);
        tv_time_drop = findViewById(R.id.tv_time_drop);
        tv_date_drop = findViewById(R.id.tv_date_dropped);
        tv_service_canc = findViewById(R.id.tv_service_canc);

        imageView_car = findViewById(R.id.car_pick);
        imageView_tool = findViewById(R.id.tool_pick);
        imageView_toolDone = findViewById(R.id.tool_done);
        imageView_carDrop = findViewById(R.id.car_drop);
        iv_confirmed = findViewById(R.id.iv_confirmed);
        iv_pick = findViewById(R.id.iv_pick);
        cancelled = findViewById(R.id.cancelled);
        booked = findViewById(R.id.booked);

        progressBar = findViewById(R.id.progressbar1);
        progress = findViewById(R.id.progress);




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        try {
            LatLng pickup = new LatLng(-34, 151);
            if (getIntent().hasExtra("lat")) {
                latitude = Double.parseDouble(getIntent().getStringExtra("lat"));
                longitude = Double.parseDouble(getIntent().getStringExtra("lng"));
                pickup = new LatLng(latitude, longitude);
            }



            mMap.addMarker(new MarkerOptions().position(pickup).title("Pickup"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pickup));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clickListener() {

        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                startActivity(new Intent(TrackOrder2.this, AppHomePage.class));
            }
        });

        findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callIntent(TrackOrder2.this, CUSTOMER_CARE);
            }
        });

        findViewById(R.id.refer_and_earn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "Apply promocode  '" + "MekAntiCovid19" + "' while signing up in Mekpark and you will get Rs.100";

                sendReferalIntent(TrackOrder2.this, message);

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.e(TAG, "onBackPressed");

        int simpleBack = getIntent().getIntExtra("simple_back", 0);
        if (simpleBack == 1) {
            finish();
            return;
        }


        finishAffinity();
        startActivity(new Intent(this, AppHomePage.class));


    }

    private void getBookingStatus(final String bookingId, final String type) {

        progress.setVisibility(View.VISIBLE);

        String iou = BASE + "getBookingById";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, iou,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progress.setVisibility(View.GONE);
                        if (type.equals("regular")) {
                            try {

                                JSONArray jsonArray = new JSONObject(response).getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    //String object=jsonArray.getString(0);


                                    String partnerLat = "", partnerLong = "", deliveryLat = "", deliveryLong = "", pickLat = "", pickLong = "";
                                    if (!object.getString("partner_id").equals("null")) {
                                        JSONObject partnerId = object.getJSONObject("partner_id");
                                        partnerLat = partnerId.getString("latitude");
                                        partnerLong = partnerId.getString("longitude");


                                    }

                                    if (!object.getString("delivery_boy_id").equals("null")) {
                                        JSONObject delivery = object.getJSONObject("delivery_boy_id");
                                        deliveryLat = delivery.getString("latitude");
                                        deliveryLong = delivery.getString("longitude");


                                    }

                                    if (!object.getString("pickup_address_id").equals("null")) {
                                        JSONObject drop = object.getJSONObject("pickup_address_id");
                                        JSONObject data = drop.getJSONObject("data");
                                        pickLat = data.getString("lat");
                                        pickLong = data.getString("long");


                                    }

                                    if (!object.getString("drop_address_id").equals("null")) {
                                        JSONObject drop = object.getJSONObject("drop_address_id");
                                        JSONObject data = drop.getJSONObject("data");

                                        dest = new LatLng(Double.parseDouble(data.getString("lat")), Double.parseDouble(data.getString("long")));

                                    }

                                    status = object.getString("status");
                                    JSONObject details = object.getJSONObject("Vehicle Details").getJSONObject("data").getJSONObject("user_id");

                                    update = details.getString("updated_at");

                                    setProgressBarAnimation(status);
                                    //set which lat long to show
                                    switch (status) {
                                        case "booked":
                                        case "selected":
                                            setMap(status, new LatLng(Double.parseDouble(pickLat), Double.parseDouble(pickLong)));
                                            break;
                                        case "upcoming": //delivery lat long
                                        case "ongoing":
                                            setMap(status, new LatLng(Double.parseDouble(deliveryLat), Double.parseDouble(deliveryLong)));
                                            break;
                                        case "pending": //partner lat long
                                        case "complete":
                                            setMap(status, new LatLng(Double.parseDouble(partnerLat), Double.parseDouble(partnerLong)));
                                            break;
                                        case "service_complete": //drop lat long

                                            setMap(status, dest);

                                            break;


                                        default:


                                    }


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, e.toString());
                                Toast.makeText(TrackOrder2.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (type.equals("sos")) {
                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray data = object.getJSONArray("data");
                                if (data.length() > 0) {
                                    JSONObject jsonObject = data.getJSONObject(0);

                                    String partnerLat = "", partnerLong = "", delLat = "", delLong = "";
                                    if (!jsonObject.getString("partner_id").equals("null")) {
                                        JSONObject partner = jsonObject.getJSONObject("partner_id");
                                        partnerLat = partner.getString("latitude");
                                        partnerLong = partner.getString("longitude");

                                    }
                                    if (!jsonObject.getString("delivery_boy_id").equals("null")) {
                                        JSONObject partner = jsonObject.getJSONObject("delivery_boy_id");
                                        delLat = partner.getString("latitude");
                                        delLong = partner.getString("longitude");

                                    }
                                    status = jsonObject.getString("status");
                                    if (!jsonObject.getString("updated_at").equals("null")) {
                                        JSONObject updateDate = jsonObject.getJSONObject("updated_at");
                                        update = updateDate.getString("date");
                                    }

                                    setProgressBarAnimation(status);
                                    //set which lat long to show
                                    switch (status) {
                                        case "booked":
                                        case "selected":
                                            setMap(status, new LatLng(Double.parseDouble(getIntent().getStringExtra("lat")), Double.parseDouble(getIntent().getStringExtra("lng"))));
                                            break;
                                        case "upcoming": //delivery lat long
                                        case "ongoing":
                                            setMap(status, new LatLng(Double.parseDouble(delLat), Double.parseDouble(delLong)));
                                            break;
                                        case "pending": //partner lat long
                                        case "complete":
                                            setMap(status, new LatLng(Double.parseDouble(partnerLat), Double.parseDouble(partnerLong)));
                                            break;
                                        case "service_complete": //drop lat long

                                            setMap(status, dest);

                                            break;


                                        default:


                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.setVisibility(View.GONE);
                Log.e(TAG, error.toString());
                if (error instanceof AuthFailureError) {

                    Toast.makeText(TrackOrder2.this, "login problem", Toast.LENGTH_SHORT).show();
                }
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();


                header.put("Accept", "application/json");
                header.put("Authorization", AppConstants.TOKEN);


                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", type);
                params.put("booking_id", bookingId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy((RETRY_SECONDS),
                NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(TrackOrder2.this).addToRequestQueue(stringRequest);



    }


    private int getUpdate(String status) {
        int val = 0;
        switch (status) {
            case "selected": //show pickup lat long
                val = 15;
                setUpdatedTimeAndDate(update, tv_confirmed_time, tv_conf_date);
                break;
            case "upcoming": //delivery lat long
                val = 22;
                setUpdatedTimeAndDate(update, tv_pick_time, tv_pick_date);
                break;
            case "ongoing": //delivery lat long
                val = 35;
                setUpdatedTimeAndDate(update, tv_picked_time, tv_picked_date);
                break;
            case "pending": //partner lat long
                val = 45;
                setUpdatedTimeAndDate(update, tv_prog_time, tv_prog_date);
                break;
            case "service_complete": //drop lat long
                val = 55;
                setUpdatedTimeAndDate(update, tv_comp_time, tv_comp_date);
                break;
            case "complete": //partner lat long
                val = 70;
                setUpdatedTimeAndDate(update, tv_time_drop, tv_date_drop);
                break;
            case "cancelled":
                tv_service_canc.setText("Service Cancelled");
                cancelled.setImageResource(R.drawable.ic_asset_36);
                // servicecancelled();
                val = 100;
                break;
            default:
                val = 0;

        }
        return val;
    }

    private void setUpdatedTimeAndDate(String date, TextView tvTime, TextView tvDate) {
        tvDate.setVisibility(View.VISIBLE);
        tvTime.setVisibility(View.VISIBLE);
        String time = "";
        String dateDate = "";


        try {
            //date


            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dat = dateFormatter.parse(date);
            long epoch = dat.getTime();


            SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
            time = timeFormatter.format(new Date(epoch));


            String format = "dd-MM-yyyy";
            String unix = "" + getUnixTimeInMilli(date) / 1000L;
            Date date1 = new java.util.Date(Long.valueOf(unix) * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            dateDate = sdf.format(date1);
            Log.e("date", dateDate);
            Log.e("time", time);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvTime.setText(time);
        tvDate.setText(dateDate);


    }

    private void servicecancelled() {
        progressBar.getProgressDrawable().setColorFilter(getColor(R.color.red_dark), PorterDuff.Mode.SRC_IN);
        //red svg of each

        booked.setImageResource(R.drawable.red_complete);
        iv_confirmed.setImageResource(R.drawable.red_16);


        iv_pick.setImageResource(R.drawable.red_17);


        imageView_car.setImageResource(R.drawable.car_svg_red);


        imageView_tool.setImageResource(R.drawable.tool_svg_red);

        imageView_toolDone.setImageResource(R.drawable.tool_done_red);


        imageView_carDrop.setImageResource(R.drawable.cardrop_red);




    }

    private void setProgressBarAnimation(final String status) {
        int value = getUpdate(status);
        progressBar.getProgressDrawable().setColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_IN);


        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, value);

        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                Log.e("TAG", Integer.toString(val));
                Log.e("TAG", status);
                Log.e("TAG", update);


                if (val == 15) {
                    iv_confirmed.setImageResource(R.drawable.ic_asset_16);
                }
                if (val == 22) {
                    iv_pick.setImageResource(R.drawable.ic_asset_17);
                }
                if (val == 35) {
                    imageView_car.setImageResource(R.drawable.car_svg);
                }
                if (val == 45) {
                    imageView_tool.setImageResource(R.drawable.tool_svg);
                }
                if (val == 55) {
                    imageView_toolDone.setImageResource(R.drawable.tool_done_svg);
                }
                if (val == 70) {
                    imageView_carDrop.setImageResource(R.drawable.cardrop_svg);
                }
                if (val == 100) {
                    // cancelled.setImageResource(R.drawable.cross_circle2);
                    servicecancelled();
                }


            }
        });

        animation.setDuration(3000);

        animation.setInterpolator(new LinearInterpolator());

        animation.start();


    }

    private void setMap(String status, LatLng latLng) {

        switch (status) {
            case "booked":
            case "selected":
                mMap.addMarker(new MarkerOptions().position(latLng).title("Pickup"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                break;
            case "upcoming": //delivery lat long
            case "ongoing":
                break;
            case "pending": //partner lat long
            case "complete":

                mMap.addMarker(new MarkerOptions().position(latLng).title("Partner"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                break;
            case "service_complete": //drop lat long

                mMap.addMarker(new MarkerOptions().position(latLng).title("Drop"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                break;


            default:


        }

    }




}
