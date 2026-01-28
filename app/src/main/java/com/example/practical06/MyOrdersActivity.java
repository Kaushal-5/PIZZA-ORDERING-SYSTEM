package com.example.practical06;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying User's Order History.
 */
public class MyOrdersActivity extends AppCompatActivity {

    private static final String TAG = "MyOrdersActivity";
    RecyclerView rvOrders;
    TextView tvNoOrders;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_orders);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        rvOrders = findViewById(R.id.rv_orders);
        tvNoOrders = findViewById(R.id.tv_no_orders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loadOrders();
    }

    private void loadOrders() {
        String phone = sharedPreferences.getString(KEY_PHONE, "");
        if (phone.isEmpty()) {
            tvNoOrders.setVisibility(View.VISIBLE);
            rvOrders.setVisibility(View.GONE);
            return;
        }

        String key = "orders_" + phone;
        String ordersJson = sharedPreferences.getString(key, "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Order>>() {
        }.getType();
        List<Order> orderList = gson.fromJson(ordersJson, type);

        if (orderList == null || orderList.isEmpty()) {
            tvNoOrders.setVisibility(View.VISIBLE);
            rvOrders.setVisibility(View.GONE);
        } else {
            tvNoOrders.setVisibility(View.GONE);
            rvOrders.setVisibility(View.VISIBLE);
            OrderAdapter adapter = new OrderAdapter(orderList);
            rvOrders.setAdapter(adapter);
        }
    }
}
