package com.myapp.mekvahan.HomePage;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;
import com.myapp.mekvahan.AllBookingsDashboard.BookingsHomePage;
import com.myapp.mekvahan.Cart.CartHomePage;
import com.myapp.mekvahan.Cart.CartTable;
import com.myapp.mekvahan.CommonFiles.AppConstants;
import com.myapp.mekvahan.CommonFiles.MySingleton;
import com.myapp.mekvahan.Interfaces.MyListener;
import com.myapp.mekvahan.MenuModel;
import com.myapp.mekvahan.R;
import com.myapp.mekvahan.Rooms.CartDao;
import com.myapp.mekvahan.Rooms.MekVahanDatabase;
import com.myapp.mekvahan.SignupAndLogin.Login.LoginHomePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.NO_OF_RETRY;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.RETRY_SECONDS;

public class AppHomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MyListener {

    private final String TAG = this.getClass().getSimpleName();

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    private CardView mMenuMyVehicleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launchHomePage();

    }

    private void launchHomePage() {

        Log.e(TAG,"launchHomePage");

        setContentView(R.layout.app_home_page);

        mMenuMyVehicleLayout = findViewById(R.id.menu_my_vehicle_layout);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://mekvahan.com/api/mekvahan_auth_token", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(AppConstants.TOKEN.equals("Bearer ")){
                    response=response.replace("\"","");
                    AppConstants.TOKEN+=response;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AppHomePage.this, "Token api problem", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy((RETRY_SECONDS),
                NO_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(AppHomePage.this).addToRequestQueue(stringRequest);

        setNavigationDrawer();
        clickListener();

    }


    private void clickListener() {

        //Search to be implemented
        findViewById(R.id.search_text).setEnabled(false);

        findViewById(R.id.search_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuMyVehicleLayout.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuMyVehicleLayout.setVisibility(View.GONE);
                startActivity(new Intent(AppHomePage.this, CartHomePage.class));
            }
        });

        findViewById(R.id.select_vehicle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMenuMyVehicleLayout.getVisibility() == View.VISIBLE)
                    mMenuMyVehicleLayout.setVisibility(View.GONE);
                else
                    mMenuMyVehicleLayout.setVisibility(View.VISIBLE);


            }
        });



    }


    private void setNavigationDrawer(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expandableListView = findViewById(R.id.expandableListView);

        prepareMenuData();
        populateExpandableList();

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(

                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(false);
        Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.m_1,AppHomePage.this.getTheme());
        toggle.setHomeAsUpIndicator(icon);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuMyVehicleLayout.setVisibility(View.GONE);
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set Navigation Header
        View headerView      =  navigationView.getHeaderView(0);

        ImageView iv_profile =  headerView.findViewById(R.id.profile_pic);
        TextView tv_name     =  headerView.findViewById(R.id.name);
        TextView tv_mobile   =  headerView.findViewById(R.id.mobile);

        iv_profile.setImageResource(R.drawable.profile_placehoder);



        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AppHomePage.this,Profile.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        tv_name.setText("Helpline");
        tv_mobile.setText("7838878899");

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
                    startActivity(new Intent(AppHomePage.this, BookingsHomePage.class));
                else
                    startActivity(new Intent(AppHomePage.this, LoginHomePage.class));
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {

        mMenuMyVehicleLayout.setVisibility(View.GONE);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void updatePage() {
        loadFragment();
    }


    @Override
    protected void onResume() {
        super.onResume();
        new FetchCartItems(MekVahanDatabase.getInstance(AppHomePage.this)).execute();
        loadFragment();
    }

    public void loadFragment(){
        replaceFragment(new FragMainContent());
    }

    public void replaceFragment(Fragment fragment) {

        Log.e(TAG, "replaceFragment");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_frame_layout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
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
            setupCartBadge(cartTableList.size());
        }
    }

    public void setupCartBadge(int cartCount) {

        Log.e(TAG,"called : setupCartBadge");

        TextView tv_cart_badge = findViewById(R.id.cart_badge);

        if (cartCount == 0)
            tv_cart_badge.setVisibility(View.GONE);
        else {
            tv_cart_badge.setText(String.valueOf(Math.min(cartCount, 99)));
            tv_cart_badge.setVisibility(View.VISIBLE);

        }

    }

}


