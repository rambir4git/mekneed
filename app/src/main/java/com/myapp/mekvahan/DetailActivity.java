package com.myapp.mekvahan;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        TextView details = findViewById(R.id.detail_dropdown);
        LinearLayout layout = findViewById(R.id.linear);
        for (int i = 0; i < 10; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(20, 20, 0, 20);


            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(lp);
            imageView.setId(i);
            // imageView.setPadding(2, 2, 2, 2);
            imageView.setAdjustViewBounds(true);


            imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), R.drawable.placeholder_image));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            layout.addView(imageView);
        }
        details.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        RecyclerView recyclerView = findViewById(R.id.recycler_detail);
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
            recyclerView.setAdapter(adapter);
        }


    }
}
