package com.myapp.mekvahan.CommonFiles;
import com.myapp.mekvahan.R;

public class AppConstants {

    //Sos service id
    public static final int FUEL_SERVICE_ID     = 1;
    public static final int TYRE_SERVICE_ID     = 3;
    public static final int BATTER_SERVICE_ID   = 6 ;
    public static final int TOWING_SERVICE_ID   = 7;
    public static final int KEY_SERVICE_ID      = 8;
    public static final int ONSITE_SERVICE_ID   = 9;

    public static final int CHAUFFEUR_SERVICE_ID   = 10;


    public static final String TYRE_REPAIR      = "tyre_repair";
    public static final String TYRE_REPLACEMENT = "tyre_replacement";

    public static final String BATTERY_JUMPSTART   = "battery_repair";
    public static final String BATTERY_REPLACEMENT = "battery_replacement";

    public static final String MECHANICAL_FAULTS       = "mechanical_fault";
    public static final String ELECTRICAL_FAULT        = "electrical_fault";
    public static final String HORN_MALFUNCTION        = "horn_malfunctioning";
    public static final String WIPER_BLADE_REPLACEMENT = "wiper_blade_replacement";

    public static final String MANUAL    = "manual";
    public static final String AUTOMATIC = "automatic";

    public static final String PETROL        = "petrol";
    public static final String DIESEL        = "deasel";  //this is the spelling used in database
    public static final String CNG           = "cng";

    public static final String VEHICLE_TO_VEHICLE   = "vehicle_to_vehicle";
    public static final String CRANE_TOWING         = "crane_towing";
    public static final String FLAT_BELT_TOWING     = "flat_belt_towing";



    //SOS API PARAMETER

    public static final String SELECTED_SERVICE = "selected_service";    // 1,3,6,7,8,9
    public static final String CONTACT_NO       = "contactNo";
    public static final String LATUTIDE         = "latitude";
    public static final String LONGITUDE        = "longitude";
    public static final String ADDRESS          = "address";

    public static final String FUEL_AMOUNT      = "fuel_amount";        //100,
    public static final String FUEL_TYPE        = "fuel_type";          //petrol, deasel
    public static final String TOWING_TYPE      = "towing_type";        //vehicle_to_vehicle,crane_towing,flat_belt_towing
    public static final String TOWING_DISTANCE  = "towing_distance";    // 8
    public static final String KEY_RECOVERY     = "key_recovery";       //0,1

    public static final String ONSITE_ASSISTANCE= "onsite_assistance"; //
    public static final String BATTERY          = "battery";           // battery_jumpstart. battery_replacement
    public static final String TYRE             = "tyre";              // tyre_repair, tyre_replacement
    public static final String DETAILS          = "details";
    public static final String COUPON           = "coupon";
    public static final String CHAUFFEUR_DISTANCE = "chauffeur_distance";      // 3,4

    public static final String COD              = "cod";                   // 0,1
    public static final String VEHICLE_TYPE     = "vehicle_type";          // Car, Bike
    public static final String MODEL_ID         = "model_id";
    public static final String DROP_ADDRESS     = "drop_address";

    public static final String SERVICE_TYPE     = "service_type";
    public static final String VEHILCE_NO       = "vehicleNo";
    public static final String BRAND            = "brand";
    public static final String MODEL            = "model";
    public static final String FUEL_QUANTITY    = "fuel_quantity";

    public static final String WALLET     = "wallet";
    public static final String ONLINE     = "online";
    public static final String BOOKING_ID = "booking_id";


    public static final String AMOUNT_TO_PAY = "amount_to_pay";
    public static final String PAYMENT_MERCHANT_NAME = "MekVahan";


    //Regular service booking api


    public static final String SERVICE_ID = "service_id";
    public static final String PICKUP_ADDRESS_ID = "pickup_address_id";
    public static final String DROP_ADDRESS_ID = "drop_address_id";
    public static final String SERVICE_DATE = "service_date";
    public static final String SERVICE_TIME= "service_time";
    public static final String PICKUP_DATE_TIME = "pickup_date_time";


    //Payment types
    public static final String PAYMENT_TYPE = "payment_type";
    public static final String BUNDLE = "bundle";

    public static final String REGULAR_SERVICE = "regular_service";
    public static final String SOS_PAYMENT = "sos_payment";



    public enum AddressType {
        Home, Office, Others
    }


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

        public int getService_icon() {
            return service_icon;
        }
    }

    public enum PrefTags {
        DefaultVehicleId
    }

}
