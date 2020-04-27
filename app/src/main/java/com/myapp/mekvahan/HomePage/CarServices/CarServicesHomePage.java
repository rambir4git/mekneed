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

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.myapp.mekvahan.AllBookingsDashboard.BookingsHomePage;
import com.myapp.mekvahan.Cart.CartHomePage;
import com.myapp.mekvahan.Cart.CartTable;
import com.myapp.mekvahan.HomePage.ServiceModel;
import com.myapp.mekvahan.MenuModel;
import com.myapp.mekvahan.R;
import com.myapp.mekvahan.Rooms.CartDao;
import com.myapp.mekvahan.Rooms.MekVahanDatabase;
import com.myapp.mekvahan.SignupAndLogin.Login.LoginHomePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        titles.add("Grocery & Staples");
        titles.add("Fruit & Vegetables");
        titles.add("Household Items");
        titles.add("Dairy Essentials");
        titles.add("Medicines");
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

        mCarAllServiceList.add(new ServiceModel(1,"grocery_staples","Key Feature", "","Family Farm Chana Dal","chana daal",
                "Pure and natural premium-quality graded grains BREAKNEWLINE" +
                        "Enriched with the goodness of protein and essential nutrients BREAKNEWLINE" +
                        "Easily digestible and extremely healthy for diabetics BREAKNEWLINE" +
                        "Rich source of dietary fibre BREAKNEWLINE" +
                        "Ideal to prepare a variety of cuisines like salads, curries, soups, dal preparations, sweets and also few rice dishes",
                "Come & Take away eligible BREAKNEWLINE" +
                "Free delivery eligible BREAKNEWLINE" +
                "Contact free delivery eligible BREAKNEWLINE" +
                "Low cost guarantee BREAKNEWLINE" +
                "20 points inspection BREAKNEWLINE"+
                "2 days return eligible",
                "Pure and natural premium-quality graded grains BREAKNEWLINE" +
                "Enriched with the goodness of protein and essential nutrients BREAKNEWLINE" +
                "Easily digestible and extremely healthy for diabetics BREAKNEWLINE" +
                "Rich source of dietary fibre BREAKNEWLINE" +
                "Ideal to prepare a variety of cuisines like salads, curries, soups, dal preparations, sweets and also few rice dishes","Carbs,47g","Dietary Fiber,11g","Fat,3g","Protein,25g","Sodium,35mg","Cholesterol,0mg","Calcium,1%","Iron,10%","Vitamin A,0%","Vitamin C,0%","","","","","",
                "72","72","0","0","72",1));

        mCarAllServiceList.add(new ServiceModel(2,"grocery_staples","Key Feature", "","Chakki Atta 10 KG","atta",
                "Picked from the finest wheat fields BREAKNEWLINE" +
                        "100% whole wheat Atta BREAKNEWLINE" +
                        "Get the super quality taste BREAKNEWLINE" +
                        "Imparts a good aroma and a fluffy lookBREAKNEWLINE" +
                        "Also helps prevent weight gain",
                "Come & Take away eligible BREAKNEWLINE" +
                        "Free delivery eligible BREAKNEWLINE" +
                        "Contact free delivery eligible BREAKNEWLINE" +
                        "Low cost guarantee BREAKNEWLINE" +
                        "20 points inspection BREAKNEWLINE"+
                        "2 days return eligible",
                "Pure and natural premium-quality graded grains BREAKNEWLINE" +
                        "Enriched with the goodness of protein and essential nutrients BREAKNEWLINE" +
                        "Easily digestible and extremely healthy for diabetics BREAKNEWLINE" +
                        "Rich source of dietary fibre BREAKNEWLINE" +
                        "Ideal to prepare a variety of cuisines like salads, curries, soups, dal preparations, sweets and also few rice dishes","Carbs,47g","Dietary Fiber,11g","Fat,3g","Protein,25g","Sodium,35mg","Cholesterol,0mg","Calcium,1%","Iron,10%","Vitamin A,0%","Vitamin C,0%","","","","","",
                "308","308","0","0","308",1));

        mCarAllServiceList.add(new ServiceModel(3,"grocery_staples","Key Feature", "","Fortune Premium Kachi Ghani Pure Mustard Oil (Bottle)","mustard oil",
                "Made from finest Mustard seeds BREAKNEWLINE" +
                        "Offers strong aroma and pungency that will spice up your cooking BREAKNEWLINE" +
                        "Acts as a digestive aid in neutralising toxins BREAKNEWLINE" +
                        "Adds an authentic taste and flavour to curries and vegetables BREAKNEWLINE",
                "Come & Take away eligible BREAKNEWLINE" +
                        "Free delivery eligible BREAKNEWLINE" +
                        "Contact free delivery eligible BREAKNEWLINE" +
                        "Low cost guarantee BREAKNEWLINE" +
                        "20 points inspection BREAKNEWLINE"+
                        "2 days return eligible",
                "Pure and natural premium-quality graded grains BREAKNEWLINE" +
                        "Enriched with the goodness of protein and essential nutrients BREAKNEWLINE" +
                        "Easily digestible and extremely healthy for diabetics BREAKNEWLINE" +
                        "Rich source of dietary fibre BREAKNEWLINE" +
                        "Ideal to prepare a variety of cuisines like salads, curries, soups, dal preparations, sweets and also few rice dishes","Carbs,47g","Dietary Fiber,11g","Fat,3g","Protein,25g","Sodium,35mg","Cholesterol,0mg","Calcium,1%","Iron,10%","Vitamin A,0%","Vitamin C,0%","","","","","",
                "115","115","0","0","115",1));

        mCarAllServiceList.add(new ServiceModel(4,"grocery_staples","Key Feature", "","Family Farm Broken Mogra Basmati","rice",
                "Carefully selected for the highest quality BREAKNEWLINE" +
                        "Rich aroma and taste BREAKNEWLINE" +
                        "Widely used in cooking for an authentic Indian taste BREAKNEWLINE" +
                        "A perfect fit for everyday consumption BREAKNEWLINE" ,
                "Come & Take away eligible BREAKNEWLINE" +
                        "Free delivery eligible BREAKNEWLINE" +
                        "Contact free delivery eligible BREAKNEWLINE" +
                        "Low cost guarantee BREAKNEWLINE" +
                        "20 points inspection BREAKNEWLINE"+
                        "2 days return eligible",
                "Pure and natural premium-quality graded grains BREAKNEWLINE" +
                        "Enriched with the goodness of protein and essential nutrients BREAKNEWLINE" +
                        "Easily digestible and extremely healthy for diabetics BREAKNEWLINE" +
                        "Rich source of dietary fibre BREAKNEWLINE" +
                        "Ideal to prepare a variety of cuisines like salads, curries, soups, dal preparations, sweets and also few rice dishes","Carbs,47g","Dietary Fiber,11g","Fat,3g","Protein,25g","Sodium,35mg","Cholesterol,0mg","Calcium,1%","Iron,10%","Vitamin A,0%","Vitamin C,0%","","","","","",
                "680","680","0","0","680",1));




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
