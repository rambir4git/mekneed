package com.myapp.mekvahan.Cart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.myapp.mekvahan.R;
import com.myapp.mekvahan.Rooms.CartDao;
import com.myapp.mekvahan.Rooms.MekVahanDatabase;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.myapp.mekvahan.CommonFiles.AppConstants.DETAILS;
import static com.myapp.mekvahan.CommonFiles.AppConstants.DROP_ADDRESS_ID;
import static com.myapp.mekvahan.CommonFiles.AppConstants.PICKUP_ADDRESS_ID;
import static com.myapp.mekvahan.CommonFiles.AppConstants.PICKUP_DATE_TIME;
import static com.myapp.mekvahan.CommonFiles.AppConstants.SERVICE_DATE;
import static com.myapp.mekvahan.CommonFiles.AppConstants.SERVICE_TIME;

public class CartDateAndTimePage extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private String mDateSelected;

    private TextView am,pm;
    private TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
    private LinearLayout am_timings;
    private LinearLayout pm_timings;

    private String mSelectedTiming = "12:00";
    private ProgressBar mProgressBar;

    int mSubTotal = 0;
    int mTax = 0;
    int mAdditionalCharges = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_to_pay);


        new FetchCartItems(MekVahanDatabase.getInstance(CartDateAndTimePage.this)).execute();

        //FetchSavedAddress move to onResume
//        fetchSavedAddress();

        setViews();
        setCalender();
        clickListener();

    }

    private void setViews() {

        ((TextView)findViewById(R.id.toolbar_title)).setText("");
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Timings

        am  = findViewById(R.id.am);
        pm  = findViewById(R.id.pm);

        am_timings  = findViewById(R.id.am_timings);
        pm_timings  = findViewById(R.id.pm_timings);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        t6 = findViewById(R.id.t6);
        t7 = findViewById(R.id.t7);
        t8 = findViewById(R.id.t8);
        t9  = findViewById(R.id.t9);
        t10 = findViewById(R.id.t10);
        t11 = findViewById(R.id.t11);
        t12 = findViewById(R.id.t12);


    }

    private void setCalender() {

        //Date
        final MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);

        materialCalendarView.setTitleFormatter(new DateFormatTitleFormatter(new SimpleDateFormat("MMMM yyyy")));

        mDateSelected = String.valueOf(CalendarDay.today());

        mDateSelected = formatDate(mDateSelected);

        materialCalendarView.addDecorator(new CurrentDateDecorator(this, CalendarDay.today()));



        //---add minimum date---//
        materialCalendarView.state().edit()
                .setMinimumDate(Calendar.getInstance())
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                materialCalendarView.removeDecorators();
                materialCalendarView.addDecorator(new CurrentDateDecorator(CartDateAndTimePage.this, date));

                mDateSelected = String.valueOf(date);

                mDateSelected = formatDate(mDateSelected);

                //set timing for this selected date
                timeClickListener();

            }
        });
    }

    //input = CalendarDay{2019-7-24} output = 2019-8-24
    private String formatDate(String date) {

        Log.e(TAG,"called : formatedDate");
        Log.e(TAG,"inputDate="+date);

        date =  date.substring(12,mDateSelected.length()-1);
        date = date+"-";
        String year, month, day;
        String ans="";
        int[] arr = new int[3];
        int count=0;

        for(int i=0;i<date.length();i++){
            if(date.charAt(i)== '-'){
                arr[count]= Integer.parseInt(ans);
                count++;
                ans="";
            }
            else{
                ans=ans+date.charAt(i);
            }
        }


        year = Integer.toString(arr[0]);
        month = Integer.toString(arr[1]+1);
        day = Integer.toString(arr[2]);



        date = year+"-"+month+"-"+day;

        Log.e(TAG,"outputDate="+date);


        return date;



    }


    private void clickListener() {

        Switch switchReuirePickup = findViewById(R.id.switch_require_pickup);
        final int[] requirePickup = {1};


        final TextView t1 = findViewById(R.id.add_new_address);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setText("Mek Anti Covid 19 HQ, New Delhi 01, INDIA");
            }
        });

        final TextView t2 = findViewById(R.id.add_new_address2);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setText("Mek Anti Covid 19 HQ, New Delhi 01, INDIA");
            }
        });



        switchReuirePickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    requirePickup[0] = 1;
                    findViewById(R.id.pickup_layout).setVisibility(View.VISIBLE);
                }
                else{
                    requirePickup[0] = 0;
                    findViewById(R.id.pickup_layout).setVisibility(View.GONE);

                }
            }
        });

        findViewById(R.id.book_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mSelectedTiming == null) {
                    Toast.makeText(CartDateAndTimePage.this, "Choose an appointment", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText etDes = findViewById(R.id.des);
                String des     = etDes.getText().toString().trim();

                CartDialogCheckout dialogCheckout = new CartDialogCheckout();
                Bundle bundle = new Bundle();

                //have to change the format of date and time
                //have to send address

                bundle.putInt(PICKUP_ADDRESS_ID, 0);
                bundle.putInt(DROP_ADDRESS_ID, 0);
                bundle.putString(SERVICE_DATE, mDateSelected);
                bundle.putString(SERVICE_TIME,mSelectedTiming+"");

                bundle.putString(PICKUP_DATE_TIME,mDateSelected+" "+mSelectedTiming);
                bundle.putString(DETAILS,des);

                bundle.putInt("subtotal",mSubTotal);
                bundle.putInt("tax",mTax);
                bundle.putInt("additionalCharge",mAdditionalCharges);


                dialogCheckout.setArguments(bundle);

                dialogCheckout.show( CartDateAndTimePage.this.getSupportFragmentManager(), null);

            }
        });

        timeClickListener();
    }

    private void timeClickListener(){

        am.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "";
                resetAmPmLayout(v);
            }
        });

        pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectedAmPm = PM;
                mSelectedTiming = "";
                resetAmPmLayout(v);
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "08:00";
                setTiming(v);
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "09:00";
                setTiming(v);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "10:00";
                setTiming(v);
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "11:00";
                setTiming(v);
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "12:00";
                setTiming(v);
            }
        });

        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "13:00";
                setTiming(v);
            }
        });

        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "14:00";
                setTiming(v);
            }
        });

        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "15:00";
                setTiming(v);
            }
        });

        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "16:00";
                setTiming(v);
            }
        });

        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "17:00";
                setTiming(v);
            }
        });

        t11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "18:00";
                setTiming(v);
            }
        });

        t12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedTiming = "19:00";
                setTiming(v);
            }
        });

        disableSomeTime();
    }

    private void disableSomeTime() {
        /**This function will make already past time unselectable
         * Example at 11:18am the user will not be able to select 8,9,10,11 as pickup time
         */
        resetTiming();
        am.setEnabled(true);
        pm.setEnabled(true);
        List<View> timeviews = makeTimeviewList();
        List<Date> openhrs = makeOpenhrsList();
        //Current system time
        Date currentTime = Calendar.getInstance().getTime();
        int defaultIndex = 12;
        for (int i = 0; i < 12; i++) {
            //making sure every timeview is enabled, so that it maybe disable later
            timeviews.get(i).setEnabled(true);

            //disabling certain time
            if (openhrs.get(i).before(currentTime)) {
                timeviews.get(i).setEnabled(false);
                Log.e(TAG, "disableSomeTime: " + openhrs.get(i) + " is before " + currentTime);
            } else
                defaultIndex = Math.min(i, defaultIndex);
        }
        if (defaultIndex < 4) {
            resetAmPmLayout(am);
            setTiming(timeviews.get(defaultIndex));
            setSelectedTime(defaultIndex);
        } else if (defaultIndex < 12) {
            resetAmPmLayout(pm);
            am.setEnabled(false);
            pm.setEnabled(false);
            setTiming(timeviews.get(defaultIndex));
            setSelectedTime(defaultIndex);
        } else {
            Toast.makeText(this, "Appointment is not possible on this date", Toast.LENGTH_SHORT).show();
            mSelectedTiming = null;
        }

    }

    private void setSelectedTime(int defaultIndex) {
        switch (defaultIndex) {

            case 0:
                mSelectedTiming = "08:00";
                break;
            case 1:
                mSelectedTiming = "09:00";
                break;
            case 2:
                mSelectedTiming = "10:00";
                break;
            case 3:
                mSelectedTiming = "11:00";
                break;
            case 4:
                mSelectedTiming = "12:00";
                break;
            case 5:
                mSelectedTiming = "13:00";
                break;
            case 6:
                mSelectedTiming = "14:00";
                break;
            case 7:
                mSelectedTiming = "15:00";
                break;
            case 8:
                mSelectedTiming = "16:00";
                break;
            case 9:
                mSelectedTiming = "17:00";
                break;
            case 10:
                mSelectedTiming = "18:00";
                break;
            case 11:
                mSelectedTiming = "19:00";
                break;
        }
    }

    private List<Date> makeOpenhrsList() {
        //making a list of operational hours for MEKVAHAN BUSINESS
        List<Date> openhrs = new ArrayList<>();

        //adding business hour to currently selected on calender, format yyyy-MM-dd
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (int i = 0; i < 12; i++) {
            String hour = Integer.valueOf(i + 8).toString();
            String dateString = mDateSelected + " " + hour + ":00";
            try {
                openhrs.add(inputFormat.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return openhrs;
    }

    private List<View> makeTimeviewList() {
        //adding all the timeviews in single list for easy handling
        List<View> timeviews = new ArrayList<>();
        timeviews.add(t1);
        timeviews.add(t2);
        timeviews.add(t3);
        timeviews.add(t4);
        timeviews.add(t5);
        timeviews.add(t6);
        timeviews.add(t7);
        timeviews.add(t8);
        timeviews.add(t9);
        timeviews.add(t10);
        timeviews.add(t11);
        timeviews.add(t12);
        return timeviews;
    }

    private void setTiming(View v) {
        resetTiming();
        ((TextView)v).setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.white));
        v.setBackground(getDrawable(R.drawable.red_background));

    }

    private void resetAmPmLayout(View v) {

        resetAmPm();

        v.setBackground(getDrawable(R.drawable.red_background));
        ((TextView)v).setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.white));

        if (v == findViewById(R.id.am)) {
            am.setCompoundDrawablesWithIntrinsicBounds(R.drawable.day_white, 0, 0, 0);

            resetTiming();
            am_timings.setVisibility(View.VISIBLE);
            pm_timings.setVisibility(View.GONE);
        } else {
            pm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.night_white, 0, 0, 0);

            resetTiming();
            am_timings.setVisibility(View.GONE);
            pm_timings.setVisibility(View.VISIBLE);
        }


    }

    private void resetAmPm() {
        am.setBackground(getDrawable(R.drawable.border_white));
        am.setCompoundDrawablesWithIntrinsicBounds(R.drawable.day_grey, 0, 0, 0);
        am.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));

        pm.setBackground(getDrawable(R.drawable.border_white));
        pm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.night_grey, 0, 0, 0);
        pm.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));

    }

    private void resetTiming(){

        t1.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t2.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t3.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t4.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t5.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t6.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t7.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t8.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t9.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t10.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t11.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));
        t12.setTextColor(ContextCompat.getColor(CartDateAndTimePage.this,R.color.black));

        t1.setBackground(getDrawable(R.drawable.border_white));
        t2.setBackground(getDrawable(R.drawable.border_white));
        t3.setBackground(getDrawable(R.drawable.border_white));
        t4.setBackground(getDrawable(R.drawable.border_white));

        t5.setBackground(getDrawable(R.drawable.border_white));
        t6.setBackground(getDrawable(R.drawable.border_white));
        t7.setBackground(getDrawable(R.drawable.border_white));
        t8.setBackground(getDrawable(R.drawable.border_white));

        t9.setBackground(getDrawable(R.drawable.border_white));
        t10.setBackground(getDrawable(R.drawable.border_white));
        t11.setBackground(getDrawable(R.drawable.border_white));
        t12.setBackground(getDrawable(R.drawable.border_white));
    }


    private class CurrentDateDecorator implements DayViewDecorator {

        private Drawable highlightDrawable;
        private Context context;
        private CalendarDay currentdate;

        public CurrentDateDecorator(Context context, CalendarDay currentdate) {
            this.context = context;
            highlightDrawable = this.context.getDrawable(R.drawable.shape_back_circle);
            this.currentdate = currentdate;

        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(currentdate);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(highlightDrawable);
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.5f));
        }
    }



    class FetchCartItems extends AsyncTask<Void,Void,Void> {

        private final CartDao cartDao;
        private List<CartTable> cartTableList;

        public FetchCartItems(MekVahanDatabase instance) {
            cartDao = instance.getCartDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartTableList = cartDao.getAllMyCartItems();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            setPrices(cartTableList);
        }
    }

    private void setPrices(List<CartTable> cartList) {

        TextView tv_subTotal   = findViewById(R.id.subtotal);

        for(int i=0;i<cartList.size();i++){
            mSubTotal            += Integer.parseInt(cartList.get(i).getSubtotal());
            mTax                 += Double.parseDouble(cartList.get(i).getGst());
            mAdditionalCharges   += Integer.parseInt(cartList.get(i).getAddiCharges());
        }

        tv_subTotal.setText(mSubTotal+"");

    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.book_now).setEnabled(true);

    }
}
