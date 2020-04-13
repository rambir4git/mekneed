package com.myapp.mekvahan.Cart;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.R;
import com.myapp.mekvahan.Rooms.CartDao;
import com.myapp.mekvahan.Rooms.MekVahanDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartHomePage extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private List<CartTable> mCartList;
    private SwipeController swipeController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_home_page);

        ((TextView)findViewById(R.id.toolbar_title)).setText("My Cart");

        mCartList = new ArrayList<>();

        fetchCartItem();
        clickListener();

    }

    private void clickListener() {

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.continue_shopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.proceed).setEnabled(false);
                startActivity(new Intent(CartHomePage.this, CartDateAndTimePage.class));
            }
        });
    }

    private void fetchCartItem() {
        Log.e(TAG,"called, fetchCartItems");
        new FetchCartItems(MekVahanDatabase.getInstance(CartHomePage.this)).execute();
    }

    class FetchCartItems extends AsyncTask<Void,Void,Void> {

        private final CartDao cartDao;

        public FetchCartItems(MekVahanDatabase instance) {
            cartDao = instance.getCartDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mCartList.clear();
            mCartList = cartDao.getAllMyCartItems();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if(mCartList.size() == 0){
                findViewById(R.id.empty_cart).setVisibility(View.VISIBLE);
                findViewById(R.id.payment_layout).setVisibility(View.GONE);
            }
            else {
                findViewById(R.id.empty_cart).setVisibility(View.GONE);
                findViewById(R.id.payment_layout).setVisibility(View.VISIBLE);
            }

            RecyclerView recyclerView = findViewById(R.id.recycler_view_carts);
            recyclerView.setLayoutManager(new LinearLayoutManager(CartHomePage.this));
            final CartTableAdapter adapter = new CartTableAdapter(CartHomePage.this, mCartList);
            recyclerView.setAdapter(adapter);

            swipeController = new SwipeController(new SwipeControllerActions() {
                @Override
                public void onRightClicked(int position) {
                    Log.e(TAG,"Right swiped "+position);
                    CartTable cartItem =  adapter.mCartList.get(position);

                    Log.e(TAG,"Removing item with service id "+cartItem.getServiceId()+" from cart(room)");
                    new RemoveFromCart(MekVahanDatabase.getInstance(CartHomePage.this), cartItem.getServiceId()).execute();

                    adapter.mCartList.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, adapter.getItemCount());

                    if(adapter.mCartList.size() == 0){
                        findViewById(R.id.empty_cart).setVisibility(View.VISIBLE);
                        findViewById(R.id.payment_layout).setVisibility(View.GONE);
                    }
                }
            }, CartHomePage.this);

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
            itemTouchhelper.attachToRecyclerView(recyclerView);

            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    swipeController.onDraw(c);
                }
            });

            setPrices();
        }
    }

    private void setPrices() {

        Log.e(TAG,"cartSize="+mCartList.size());

        TextView tv_subTotal = findViewById(R.id.cart_subtotal);
        TextView tv_shipping = findViewById(R.id.shipping_charges);
        TextView tv_total    = findViewById(R.id.cart_total);
        TextView tv_textItems    = findViewById(R.id.text_items);

        if(mCartList.size() == 1)
            tv_textItems.setText("One item added in cart");
        else
            tv_textItems.setText(mCartList.size()+" items added in cart");

        int subTotal = 0;

        for(int i=0;i<mCartList.size();i++){
            Log.e(TAG,"sub"+subTotal+"");
            subTotal += Integer.parseInt(mCartList.get(i).getSubtotal());
        }

        int shipping = 0;
        int total = subTotal+shipping;

        if(shipping == 0)
            tv_shipping.setText("Free");
        else
            tv_shipping.setText(shipping+"");


        tv_subTotal.setText(subTotal+"");
        tv_total.setText(total+"");
    }

    class RemoveFromCart extends AsyncTask<Void,Void,Void> {

        private final CartDao cartDao;
        private int serviceId;

        public RemoveFromCart(MekVahanDatabase instance, int id) {
            cartDao = instance.getCartDao();
            serviceId = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartDao.deleteByServiceId(serviceId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            for(int i=0;i<mCartList.size();i++){
                if(mCartList.get(i).getServiceId() == serviceId) {
                    mCartList.remove(i);
                    break;

                }
            }

            setPrices();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.proceed).setEnabled(true);
    }
}
