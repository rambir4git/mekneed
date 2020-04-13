package com.myapp.mekvahan;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.myapp.mekvahan.CommonFiles.AppConstants;
import com.myapp.mekvahan.CommonFiles.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.BASE;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.NO_OF_RETRY;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.RETRY_SECONDS;

public class JobTableAdapter extends RecyclerView.Adapter<JobTableAdapter.JobHolder> {

    List<JobTableModel> list;
    Context context;
    ProgressBar mProgressBar;
    String negotiatedPrice;

    public JobTableAdapter(List<JobTableModel> listt, Context mCtx) {
        this.list = listt;
        this.context = mCtx;
    }

    @NonNull
    @Override
    public JobHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_table_item, viewGroup, false);
        mProgressBar = view.findViewById(R.id.progress_job_table);
        return new JobHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobTableAdapter.JobHolder holder, int i) {
        final JobTableModel model = list.get(holder.getAdapterPosition());
        holder.bookingId.setText("#" + model.bookingId);
        final View view = LayoutInflater.from(context).inflate(R.layout.job_pending, holder.newServAdded, false);

            TextView title = view.findViewById(R.id.tv_service_name);
            title.setText(model.title);

            TextView price = view.findViewById(R.id.tv_original_price);
        price.setText("\u20b9 " + model.priceOriginal);
        negotiatedPrice = model.priceOriginal;

            TextView details = view.findViewById(R.id.tv_details);

        ImageView smily = view.findViewById(R.id.smily_neg_status);

        if (model.isNegotiable.equals("1")) {
            smily.setImageResource(R.drawable.ic_smily_meh);
            final View requestPrice = view.findViewById(R.id.req_price);


            ((TextView) requestPrice.findViewById(R.id.tv_original_price)).setText("\u20b9 " + model.priceOriginal);
            final Double miniPrice = Double.parseDouble(model.priceOriginal) * 0.9;
            String min = "*Minimum quoted price \u20b9 " + miniPrice;
            ((TextView) requestPrice.findViewById(R.id.min_price)).setText(min);

            final EditText new_price = requestPrice.findViewById(R.id.et_now_quoted);

            if (model.isConfirmed) {
                requestPrice.findViewById(R.id.btn_confirm).setEnabled(false);
                ((Button) requestPrice.findViewById(R.id.btn_confirm)).setText("Price Negotiated");
            }

            requestPrice.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (new_price.getText().toString().equals("")) {
                        Toast.makeText(context, "Please enter amount", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Double.parseDouble(new_price.getText().toString().trim()) < miniPrice) {
                        Toast.makeText(context, "Please enter amount greater or equal to minimum quoted price", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    model.isConfirmed = true;
                    negotiatedPrice = new_price.getText().toString().trim();
                    setNegotiationAmount(model.bookingId, new_price.getText().toString().trim(), ((Button) requestPrice.findViewById(R.id.btn_confirm)));
                }
            });


            smily.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (requestPrice.getVisibility() == View.VISIBLE) {
                        requestPrice.setVisibility(View.GONE);
                    } else {
                        requestPrice.setVisibility(View.VISIBLE);

                    }
                }
            });


        } else {
            smily.setImageResource(0);
        }

        if (model.isRejected || model.isAccepted) {
            view.findViewById(R.id.btn_layout).setVisibility(View.GONE);
        }

        view.findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNegotiationStatus(model.bookingId, negotiatedPrice, "approved", ((LinearLayout) view.findViewById(R.id.btn_layout)));
                model.isAccepted = true;

            }
        });
        view.findViewById(R.id.btn_reject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNegotiationStatus(model.bookingId, negotiatedPrice, "rejected", ((LinearLayout) view.findViewById(R.id.btn_layout)));
                model.isRejected = true;
            }
        });


            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogDetails dialogDetails = new DialogDetails();
                    Bundle bundle = new Bundle();


                    bundle.putString("name", model.title);
                    bundle.putString("des", model.description);
                    bundle.putString("subtotal", model.priceOriginal);
                    bundle.putString("newTotal", model.settledPrice);
                    bundle.putString("image1", model.imageOne);
                    bundle.putString("image2", model.imageTwo);
                    bundle.putString("image3", model.imageThree);
                    bundle.putString("type", model.type);
                    bundle.putString("id", model.id);
                    dialogDetails.setArguments(bundle);


                    dialogDetails.show(((AppCompatActivity) context).getSupportFragmentManager(), "Details");
                }
            });

        holder.newServAdded.addView(view);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class JobHolder extends RecyclerView.ViewHolder {
        TextView details;
        TextView bookingId;
        LinearLayout newServAdded;
        LinearLayout btnLayout;

        public JobHolder(View itemView) {
            super(itemView);
            details = itemView.findViewById(R.id.tv_details);
            bookingId = itemView.findViewById(R.id.tv_order_no);
            newServAdded = itemView.findViewById(R.id.ll_added);
            btnLayout = itemView.findViewById(R.id.btn_layout);
        }

    }

    private void getTimeDiff(String expiryTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("");
    }


    private void setNegotiationAmount(final String bookingId, final String amount, final Button confirm) {
        Log.e("job table adapter", "called :setNegotiationAmount");
        mProgressBar.setVisibility(View.VISIBLE);
        final String URL = BASE + "jobCart/set_negotiation_amount";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressBar.setVisibility(View.GONE);
                Log.e("job cart", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                confirm.setEnabled(false);
                confirm.setText("Price Negotiated");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Log.e("job cart", error.toString());
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("amount", amount);
                params.put("booking_id", bookingId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", "application/json");
                header.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImIxMDhmOWM4OGJlZWRlZGRkZWRlYWZhMWNjYTRjYjYzMTEzNjQyNjE4ZTY1NGFlMjZkOGEyN2U3Yzc2MGY0YzJlZmIwNjk1Yzg2MjVjZTAzIn0.eyJhdWQiOiIxIiwianRpIjoiYjEwOGY5Yzg4YmVlZGVkZGRlZGVhZmExY2NhNGNiNjMxMTM2NDI2MThlNjU0YWUyNmQ4YTI3ZTdjNzYwZjRjMmVmYjA2OTVjODYyNWNlMDMiLCJpYXQiOjE1ODQ2MzM4MTEsIm5iZiI6MTU4NDYzMzgxMSwiZXhwIjoxNjE2MTY5ODExLCJzdWIiOiIzMDQiLCJzY29wZXMiOltdfQ.JR2S-9jhpi6EIdCzwWUIxhJLNixqnYi2nXcZRUj0FuLiHqihP1sT9N3pwIwbhrKCxoyPOr5orEbtTx7l9lRX-QzMv90oKW37GfzG1ql1giQs4B4Gh6oIEeO1Trg36fhQQltCHJa-vjCC35IcQNz_VBF_BjCQcc9vG_wHy_N8FLAseIZ2XGUXbvEeKnfE6o6waVsiSh404SbhMRolIQD3DHKfU5kV6Pz2YisHZ5299E29zf6oTLtmWnfSyuRuXhwvTFc2VOKnef991xxTZrqaUFd4A4f-D7lqKQX-R6sqXysQJtlGqOQYgc9CGIl394zGC1Q8Wc4hrti7q1ZFRwJRvchAnJ4hs_bA_-PCRxkKXwRt5MDdzlI9g9WGBJ-TnIMA46L02Kg6u7xDMYGz1GklntQcjbzApQPrRsVdoXN9e8qpGL9WGK5zgQEApffefszbkfQyfKkRkTnGD5FiUdCyRjgykF_l_rV_XOH1BdXyN6r2EetNgY7yOPOCHSe5x-qQYBe8pgycvU1JOmG5C52BSPMYgk-7E54MD1IrHc0t6_jxAYDzr_MCtSJJ045OhKx2N_qMfwutwYRKokgGvfoXtmAbKlKWDcjxe0Qg6YyaeJGQJF0RZ1eebt-K1jL0pjRTglFogXKgp94JPNWnccMK3W3I4OzoBW55VGGcc1P21AM");

                return header;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy((RETRY_SECONDS * 1000),
                NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    private void setNegotiationStatus(final String bookingId, final String settledPrice, final String status, final LinearLayout btns) {
        Log.e("job table adapter", "called :setNegotiationStatus");
        mProgressBar.setVisibility(View.VISIBLE);
        final String URL = BASE + "jobCart/set_status_user";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressBar.setVisibility(View.GONE);
                Log.e("neg status", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                btns.setVisibility(View.GONE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Log.e("job cart", error.toString());
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("settled_price", settledPrice);
                params.put("booking_id", bookingId);
                params.put("status", status);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", "application/json");
                header.put("Authorization", AppConstants.TOKEN);
                return header;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy((RETRY_SECONDS * 1000),
                NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


}

