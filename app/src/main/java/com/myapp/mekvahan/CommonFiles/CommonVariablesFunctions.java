package com.myapp.mekvahan.CommonFiles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CommonVariablesFunctions {

    private static final String TAG = "CoomanVarAndFun";

    public static final int RETRY_SECONDS = 10000;
    public static final int NO_OF_RETRY = 2;

    public static final String BASE = "https://mekvahan.com/api/";
    public static final String BASE_USER = BASE + "user/";


    public static final String KEY_CAR  = "Car";
    public static final String KEY_BIKE = "Bike";

    public static final String LOCATION_NOT_FOUND = "location_not_found";

    public static final String CUSTOMER_CARE    = "7838878899";
    public static final String CONTACT_MEKVAHAN = "+918920286986";

    public static final String OTP_KEY = "192785A1sPOm0965a584ea5";

    public static final String TODAY        = "Today";
    public static final String TOMMARROW    = "Tommarrow";

    public static final String BASE_URL_FROM_MEKPARK = "https://mekpark.com/mani14/user/";

    public static final int GPS_REQUEST = 100;
    public static final String TIME_FORMAT  = "hh:mm a";

    @SuppressLint("ResourceAsColor")


    public static void sendEmailIntent(Context context){

        String email_id = "support@mekpark.com";

        String email_subject = "Feedback for mekPark App";


        Intent emailIntent =  new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto",email_id,null));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, email_subject);


        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if(isIntentSafe)
            context.startActivity(Intent.createChooser(emailIntent,"Send email via ..."));
        else
            Toast.makeText(context,"Email can't be send from here", Toast.LENGTH_SHORT).show();


    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void callIntent(Context context, String phoneNumber){

        String phone = phoneNumber;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        context.startActivity(intent);

    }

    //CHANGE INCORRECT IST TIMESTAMP TO LOCAL TIMESTAMP
    public static String convertToLocalTimeStamp(String ISTTimeStamp) {

        /**
         * I NOTICED A 12:30 HOURS EXTRA OFFSET IN TIMESTAMP from UTC which is not IST, HENCE Correcting THE SAME
         **/

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSSSS");
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = inputFormat.parse(ISTTimeStamp);
            date.setTime(date.getTime() - 45000000); // 12.5 hours in milliseconds
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSSSS");
        outputFormat.setTimeZone(TimeZone.getDefault());
        return outputFormat.format(date);
    }


    // Input - unix in sec
    // Output - Format : 06:45 PM
    public static String getFormattedTime(String TAG, String s) {

        String time = "NA";
        Date date;
        SimpleDateFormat sdf = new java.text.SimpleDateFormat(TIME_FORMAT);

        try {
            Long unix   = Long.valueOf(s);
            date        = new java.util.Date(unix*1000L);

            time        = sdf.format(date);


        }catch (Exception e){
            Log.e(TAG,"Exception cought while converting time : "+e.toString());

        }

        return time;

    }

    // Input - unix in sec
    // Output - Format : 1st Mar, 2019
    public static String getFormattedDate(String TAG, String unix) {

        String formatedstring = "NA";

        SimpleDateFormat sdf = new SimpleDateFormat("d");

        Long longUnix = null;

        try {

            longUnix = Long.valueOf(unix);
            String date = sdf.format(new Date(longUnix * 1000L));

            if (date.endsWith("1") && !date.endsWith("11"))
                sdf = new SimpleDateFormat("d'st' MMM, yyyy");
            else if (date.endsWith("2") && !date.endsWith("12"))
                sdf = new SimpleDateFormat("d'nd' MMM, yyyy");
            else if (date.endsWith("3") && !date.endsWith("13"))
                sdf = new SimpleDateFormat("d'rd' MMM, yyyy");
            else
                sdf = new SimpleDateFormat("d'th' MMM, yyyy");

        }catch (Exception e){
            Log.e(TAG,"Exception cought while converting time : "+e.toString());
        }
        formatedstring = sdf.format(new Date(longUnix*1000L));


        return formatedstring;
    }


    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    //imput - 2019-08-25 09:53
    //output - inUmixMilli
    public  static long getUnixTimeInMilli(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    //Input 2019-08-27
    //Output unixInSec
    public  static long getStartOfDayInMillis(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * @param date the date in the format "yyyy-MM-dd"
     */
    public static long getEndOfDayInMillis(String date) throws ParseException {
        return getStartOfDayInMillis(date) + (24 * 60 * 60 * 1000);
    }


    public static String getDate(String str){
        Date date = null;
        try {

            date = new SimpleDateFormat("yyyy-MM-dd").parse(str);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEE, dd MMM").format(date);

    }

    public static String  getTime(String str){
        String dateString3 = str;
        String time="";
        //old format
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try{
            Date date3 = sdf.parse(dateString3);
            //new format
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
            //formatting the given time to new format with AM/PM

            time=sdf2.format(date3);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return time;
    }



    public static void sendReferalIntent(Context context,String message){

        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT,message);
        i.setType("text/plain");

        context.startActivity(i);

    }

    public static void showGoogleForms(Context context) {
        Uri uri = Uri.parse("https://forms.gle/vdMdjKdW8xzjX6kd9");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

}
