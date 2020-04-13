package com.myapp.mekvahan.CommonFiles;
import com.myapp.mekvahan.R;

public class AppConstants {
    public static final String DETAILS          = "details";


    public static final String SERVICE_ID = "service_id";
    public static final String PICKUP_ADDRESS_ID = "pickup_address_id";
    public static final String DROP_ADDRESS_ID = "drop_address_id";
    public static final String SERVICE_DATE = "service_date";
    public static final String SERVICE_TIME= "service_time";
    public static final String PICKUP_DATE_TIME = "pickup_date_time";

    public static String TOKEN="Bearer ";


    public enum ServiceTypes {
        primary_service("Primary Service", R.drawable.services_icon),
        standard_service("Standard Service", R.drawable.ss_icon),
        Comprehensive_service("Comprehensive Service", R.drawable.cs_icon),
        wheel_alignment_balancing("Wheel Alignment Balancing", R.drawable.services_icon),
        comprehensive_checkup("Comprehensive Checkup", R.drawable.services_icon);

        private String servicetype;
        private int service_icon;

        ServiceTypes(String servicetype, int service_icon) {
            this.servicetype = servicetype;
            this.service_icon = service_icon;
        }

        public String getServicetype() {
            return servicetype;
        }
    }



}
