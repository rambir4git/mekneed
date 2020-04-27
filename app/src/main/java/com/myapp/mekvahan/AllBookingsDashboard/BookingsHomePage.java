package com.myapp.mekvahan.AllBookingsDashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.myapp.mekvahan.AllBookingsDashboard.RegulaServices.FragRegularServices;
import com.myapp.mekvahan.AllBookingsDashboard.RegulaServices.RegularServiceModel;
import com.myapp.mekvahan.R;

import java.util.ArrayList;
import java.util.List;

public class BookingsHomePage extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private TabLayout mTabLayout;

    private List<Fragment> mFragmentList;
    private List<String> titles;

    private int[] tabIcons = {
            R.drawable.ic_asset_7,
            R.drawable.ic_asset_9,
            R.drawable.ic_asset_12,
            R.drawable.ic_asset_14,
            R.drawable.ic_asset_16,
            R.drawable.ic_asset_18
    };

    private int[] tabIconsselect = {
            R.drawable.ic_asset_7,
            R.drawable.ic_asset_9,
            R.drawable.ic_asset_12,
            R.drawable.ic_asset_14,
            R.drawable.ic_asset_16,
            R.drawable.ic_asset_18
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
        titles.add("Grocery & Staples");
        titles.add("Fruit & Vegetables");
        titles.add("Household Items");
        titles.add("Dairy Essentials");
        titles.add("Medicines");
        titles.add("Others");

        mFragmentList = new ArrayList<>();

        //mFragmentList.add(new FragAllServices());
        mFragmentList.add(new FragRegularServices());
        mFragmentList.add(new FragOutStationCabs());
        mFragmentList.add(new FragOutStationCabs());
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
        String action = "(abcd,zyz)";
        List<String> actionArray = new ArrayList<>();
        actionArray.add(action);


        mRegularServiceList.add(new RegularServiceModel("1584181143", "123", "pickupAddress", "28.653980184766546", "36.12154154", "dropAddress", "2020-04-15",
                "10:00:00","2020-04-15 10:00:00","2020-04-15 13:00:00","buy only fresh items", "9812345678",1,1,"MekGroceries",
                "Anti conrona","Healthy Groceries", "","", "Stay home and eat fresh",700.00,0.00,0.00,"2020-04-14 22:49:05.000000",
                "Groceries and Staples","1100",actionArray));

        mRegularServiceList.add(new RegularServiceModel("1584184511", "1245", "pickupAddress", "28.653980184766546", "36.12154154", "dropAddress", "2020-04-15",
                "10:00:00","2020-04-15 10:00:00","2020-04-15 13:00:00","buy only fresh items", "9812345678",1,1,"MekGroceries",
                "Anti conrona","Healthy Groceries", "","", "Stay home and eat fresh",7845.00,0.00,0.00,"2020-04-14 22:49:05.000000",
                "Groceries and Staples","1100",actionArray));

        mRegularServiceList.add(new RegularServiceModel("1584187605", "123", "pickupAddress", "28.653980184766546", "36.12154154", "dropAddress", "2020-04-15",
                "10:00:00","2020-04-15 10:00:00","2020-04-15 13:00:00","buy only fresh items", "9812345678",1,1,"MekGroceries",
                "Anti conrona","Healthy Groceries", "","", "Stay home and eat fresh",12.00,0.00,0.00,"2020-04-14 22:49:05.000000",
                "Groceries and Staples","1100",actionArray));

        mRegularServiceList.add(new RegularServiceModel("1584181143", "123", "pickupAddress", "28.653980184766546", "36.12154154", "dropAddress", "2020-04-15",
                "10:00:00","2020-04-15 10:00:00","2020-04-15 13:00:00","buy only fresh items", "9812345678",1,1,"MekGroceries",
                "Anti conrona","Healthy Groceries", "","", "Stay home and eat fresh",963.00,0.00,0.00,"2020-04-14 22:49:05.000000",
                "Groceries and Staples","1100",actionArray));

     setViewPager();

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
