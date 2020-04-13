package com.myapp.mekvahan;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.myapp.mekvahan.CommonFiles.AppConstants;
import com.myapp.mekvahan.CommonFiles.MySingleton;

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

public class JobCardActivity extends AppCompatActivity {
    ProgressBar mProgressBar;
    List<JobTableModel> jobTableModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_card);

        mProgressBar = findViewById(R.id.progress_job_cart);
        LinearLayout toolbar = findViewById(R.id.toolbar_job_card);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Job Table");
        toolbar.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        jobTableModelList = new ArrayList<>();

        fetchJobcartDetails();


    }


    private void fetchJobcartDetails() {

        Log.e("job Cart", "called : fetchJobCart");


        String iou = BASE + "jobCart";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, iou,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProgressBar.setVisibility(View.GONE);


                        Log.e("job Cart", response);
                        JSONArray array = null;
                        try {
                            array = new JSONObject(response).getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i).getJSONObject("data");
                                String id = object.getString("id");
                                String type = object.getString("type");
                                String bookingId = object.getString("booking_id");
                                String title = object.getString("service_title");
                                String des = object.getString("description");
                                String price = object.getString("service_quotation");
                                String isNegotiable = object.getString("is_negotiable");
                                String negAmount = object.getString("negotiable_amount");
                                String negStatus = object.getString("negotiation_status");
                                String settledPrice = object.getString("settled_price");
                                String expDate = object.getString("expiry_date");
                                String servStatus = object.getString("status");
                                String image1 = object.getJSONArray("image_one").getString(1);
                                String image2 = object.getJSONArray("image_two").getString(1);
                                String image3 = object.getJSONArray("image_three").getString(1);

                                jobTableModelList.add(new JobTableModel(id, type, bookingId, title, price, isNegotiable, negAmount, negStatus, des,
                                        settledPrice, servStatus, expDate, image1, image2, image3, false, false, false));

                            }

                            setRecyclerView(jobTableModelList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Log.e("job Cart", error.toString());
                Toast.makeText(JobCardActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                return super.getParams();
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy((RETRY_SECONDS),
                NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(JobCardActivity.this).addToRequestQueue(stringRequest);

    }

    private void setRecyclerView(List<JobTableModel> list) {
        RecyclerView recyclerView = findViewById(R.id.recycler_job_card);
        JobTableAdapter adapter = new JobTableAdapter(list, JobCardActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(JobCardActivity.this));
        recyclerView.setAdapter(adapter);
    }
}
