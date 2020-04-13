package com.myapp.mekvahan.HomePage.CarServices;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.myapp.mekvahan.AllBookingsDashboard.BookingsHomePage;
import com.myapp.mekvahan.Cart.CartHomePage;
import com.myapp.mekvahan.Cart.CartTable;
import com.myapp.mekvahan.CommonFiles.AppConstants;
import com.myapp.mekvahan.CommonFiles.MySingleton;
import com.myapp.mekvahan.HomePage.ServiceModel;
import com.myapp.mekvahan.MenuModel;
import com.myapp.mekvahan.R;
import com.myapp.mekvahan.Rooms.CartDao;
import com.myapp.mekvahan.Rooms.MekVahanDatabase;
import com.myapp.mekvahan.SignupAndLogin.Login.LoginHomePage;

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

public class CarServicesHomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();

    private TabLayout mTabLayout;


    private List<Fragment> mFragmentList;
    private List<String> titles ;

    private ProgressBar mProgressBar;
    boolean serviceStatus;

    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    private List<MenuModel> headerList = new ArrayList<>();
    private HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    private int lastExpandedPosition = -1;
    HashMap<String, String> mUserInfo;

    private int[] tabIcons = {
            R.drawable.gs_tab,
            R.drawable.repairing_tab,
            R.drawable.wc_tab,
            R.drawable.dp_tab,
            R.drawable.cc_tab,
            R.drawable.others_tab
    };

    private int[] tabIconsselect = {
            R.drawable.gs_tabselect,
            R.drawable.repairingselect_tab,
            R.drawable.wc_tabselect,
            R.drawable.dp_tabselect,
            R.drawable.cc_tabselect,
            R.drawable.others_tabselect
    };

    private int selected_position;

    private List<ServiceModel> mCarAllServiceList = new ArrayList<>();

    private TextView textCartItemCount;
    int mCartItemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchHomePage();
    }


    private void launchHomePage(){

        setContentView(R.layout.activity_car_services_home_page);

        mProgressBar = findViewById(R.id.progress_bar);
        selected_position = getIntent().getIntExtra("viewpagerId",0);

        setNavigationDrawer();

        titles = new ArrayList<>();

        titles.add("General Service");
        titles.add("Repairing");
        titles.add("Wheel Care");
        titles.add("Denting Painting");
        titles.add("Car Care");
        titles.add("Others");

        mFragmentList = new ArrayList<>();

        for(int i=1;i<=6;i++){

            Bundle bundle = new Bundle();
            bundle.putInt("flag",i);
            Fragment fragment = new FragCarService();
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }




        mTabLayout  = findViewById(R.id.tabs_car_services);

        //First fetching items from cart so that it can be known if it is already added to cart or not
        // then fetching all service for car from api
        new FetchCartItems(MekVahanDatabase.getInstance(CarServicesHomePage.this)).execute();
    }



    private void setNavigationDrawer(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.dots_3);
        toolbar.setOverflowIcon(drawable);

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(

                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(false);
        Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.m_1, CarServicesHomePage.this.getTheme());
        toggle.setHomeAsUpIndicator(icon);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(CarServicesHomePage.this);

        // Set Navigation Header
        View headerView      =  navigationView.getHeaderView(0);

        ImageView iv_profile =  headerView.findViewById(R.id.profile_pic);
        TextView tv_name     =  headerView.findViewById(R.id.name);
        TextView tv_mobile   =  headerView.findViewById(R.id.mobile);


        iv_profile.setImageResource(R.drawable.profile_placehoder);

    }

    private void clickListener() {

        //Search to be implemented
        findViewById(R.id.search_text).setEnabled(false);

        findViewById(R.id.to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarServicesHomePage.this, CartHomePage.class));
            }
        });
    }

    private void fetchCarAllServices(final List<CartTable> cartTableList) {

        Log.e(TAG,"called : fetchCarGeneralServices");
        mProgressBar.setVisibility(View.VISIBLE);

        String iou = BASE + "regularCarService";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, iou,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProgressBar.setVisibility(View.GONE);

                        Log.e(TAG,response);

                        try {

                            JSONArray jsonArray = new JSONObject(response).getJSONArray("data");

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject obj = jsonArray.getJSONObject(i);

                                int id = obj.getInt("id");

                                String priorLine1   = obj.getString("prior_line1");
                                String category     = obj.getString("category");
                                String priorLine2   = obj.getString("prior_line2");
                                String serviceName  = obj.getString("service_name");
                                String serviceId    = obj.getString("service_id");
                                String description  = obj.getString("description");
                                String what         = obj.getString("what");
                                String when         = obj.getString("when");

                                //Removing everything except alphabets, numbers and comma

                                String action1 = obj.getString("action1").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action2 = obj.getString("action2").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action3 = obj.getString("action3").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action4 = obj.getString("action4").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action5 = obj.getString("action5").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action6 = obj.getString("action6").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action7 = obj.getString("action7").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action8 = obj.getString("action8").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action9 = obj.getString("action9").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action10 = obj.getString("action10").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action11 = obj.getString("action11").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action12 = obj.getString("action12").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action13 = obj.getString("action13").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action14 = obj.getString("action14").replaceAll("[^a-zA-Z0-9(), ]+", "");
                                String action15 = obj.getString("action15").replaceAll("[^a-zA-Z0-9(), ]+", "");


                                String total       = obj.getString("TotalCharge");
                                String subTotal    = obj.getString("SubTotalCharge");
                                String addiCharges = obj.getString("AddtionalCharge");
                                String gst         = obj.getString("GstCharge");

                                //Striked out amount is greater by 20%
                                //String strikedOutAmount = String.valueOf(Integer.parseInt(total) + (int)(Integer.parseInt(total)*.2));

                                String strikedOutAmount = "0";

                                String catagory  = obj.getString("category");


                                int flag;

                                if(catagory.equals("general_services"))
                                    flag = 1;
                                else if (catagory.equals("repairing"))
                                    flag = 2;
                                else if (catagory.equals("wheel_care"))
                                    flag = 3;
                                else if (catagory.equals("denting_painting"))
                                    flag = 4;
                                else if (catagory.equals("car_care"))
                                    flag = 5;
                                else
                                    flag = 6;

                                mCarAllServiceList.add(new ServiceModel(id,category,priorLine1, priorLine2,serviceName,serviceId,
                                        description, what, when,action1,action2,action3,action4,action5,action6,action7,
                                        action8,action9,action10,action11,action12,action13,action14,action15,
                                        total,subTotal,addiCharges,gst,strikedOutAmount,flag));

                            }

                            if(cartTableList.size() != 0)
                                setToCartCard(cartTableList);

                            //Checking if service is added to cart or not

                            for(int i=0;i<cartTableList.size();i++)
                            {
                                CartTable cartItem =  cartTableList.get(i);

                                for(int j=0;j<mCarAllServiceList.size();j++){
                                    ServiceModel serviceModel = mCarAllServiceList.get(j);

                                    if(cartItem.getServiceId() == serviceModel.getId())
                                        serviceModel.setAddedToCart(true);
                                }


                            }

                            setViewPager();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG,e.toString());
                            Toast.makeText(CarServicesHomePage.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Log.e(TAG,error.toString());

                if (error instanceof AuthFailureError) {

                    Toast.makeText(CarServicesHomePage.this, "token problem", Toast.LENGTH_SHORT).show();
                }
            }
        }){

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();

                header.put("Accept","application/json");
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
        MySingleton.getInstance(CarServicesHomePage.this).addToRequestQueue(stringRequest);
    }

    public List<ServiceModel>  getCarService(int flag){

        List<ServiceModel> tempList = new ArrayList<>();

        for(int i=0;i<mCarAllServiceList.size();i++){
            ServiceModel serviceModel = mCarAllServiceList.get(i);
            if(serviceModel.getFlag() == flag)
                tempList.add(serviceModel);
        }

        return tempList;
    }

    private void setViewPager(){

        ViewPager viewPager  = findViewById(R.id.viewpager_car_services);

        CarServicesFragmentPagerAdapter adapter = new CarServicesFragmentPagerAdapter(
                getSupportFragmentManager(),mFragmentList);
        mTabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(selected_position);

        setUpCustomTabs();

    }

    private void setUpCustomTabs() {

        for (int i = 0; i < titles.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.custom_tabview, null);

            TextView tv = view.findViewById(R.id.tab_title);
            tv.setText(titles.get(i));

            ImageView img   =  view.findViewById(R.id.tab_icon);
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

        clickListener();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
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

            fetchCarAllServices(cartTableList);

            mCartItemCount = cartTableList.size();
        }
    }

    private void setToCartCard(List<CartTable> cartTableList) {

        findViewById(R.id.to_cart).setVisibility(View.VISIBLE);

        TextView tv_itemInCart   = findViewById(R.id.item_in_cart);
        TextView tv_cartSubTotal = findViewById(R.id.cart_sub_total);

        int cartSubTotal = 0;

        for(int i=0;i<cartTableList.size();i++)
            cartSubTotal += Integer.parseInt(cartTableList.get(i).getSubtotal());

        if(cartTableList.size()==1)
            tv_itemInCart.setText("1 item in cart");
        else
            tv_itemInCart.setText(cartTableList.size()+" items in cart");

        tv_cartSubTotal.setText("\u20B9 "+cartSubTotal);
    }


    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel(0, "Bookings", false, true, R.mipmap.bookings);
        headerList.add(menuModel);
        headerList.add(new MenuModel(1, "Logout", false, true, R.drawable.logout));

    }

    private void populateExpandableList() {

        expandableListAdapter = new com.myapp.mekvahan.ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (id == 0)
                    startActivity(new Intent(CarServicesHomePage.this, BookingsHomePage.class));
                else
                    startActivity(new Intent(CarServicesHomePage.this, LoginHomePage.class));
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_service, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_cart);

        View actionView   = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupCartBadge(mCartItemCount);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });


        return true;
    }

    public void setupCartBadge(int cartCount) {

        Log.e(TAG,"called : setupCartBadge");

        if (textCartItemCount != null) {
            if (cartCount == 0)
                textCartItemCount.setVisibility(View.GONE);
            else {
                textCartItemCount.setText(String.valueOf(Math.min(cartCount, 99)));
                textCartItemCount.setVisibility(View.VISIBLE);
            }
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart:
                startActivity(new Intent(CarServicesHomePage.this, CartHomePage.class));
                return true;
            case R.id.menu_refresh:
                finish();
                overridePendingTransition(0,0);
                startActivity(getIntent());
                overridePendingTransition(0,0);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onBackPressed() {


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }
}
