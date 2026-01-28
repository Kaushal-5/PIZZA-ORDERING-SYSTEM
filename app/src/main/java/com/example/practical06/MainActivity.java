package com.example.practical06;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Main Activity handling the menu and ordering process.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    // Buttons
    Button btn_checkout;
    Button btn_mexican_plus, btn_mexican_minus;
    Button btn_cheesypizza_plus, btn_cheesypizza_minus;
    Button btn_italian_plus, btn_italian_minus;
    Button btn_margherita_plus, btn_margherita_minus;
    Button btn_pepperoni_plus, btn_pepperoni_minus;
    Button btn_veggie_plus, btn_veggie_minus;

    // Quantity TextViews
    TextView et_mexican_main, et_italian_main, et_cheesypizza_main;
    TextView et_margherita_main, et_pepperoni_main, et_veggie_main;
    TextView tv_total_main;

    // Quantities
    int count_mexican = 0, count_italian = 0, count_cheesypizza = 0;
    int count_margherita = 0, count_pepperoni = 0, count_veggie = 0;

    // Prices
    int mexicanPrice = 300, cheesypizzaPrice = 450, italianPrice = 760;
    int margheritaPrice = 350, pepperoniPrice = 420, veggiePrice = 380;

    int total = 0;

    @Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        EdgeToEdge.enable(this);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Check login status
        if (!sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // Setup Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Menu");
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        initializeViews();
        setupClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_my_orders) {
            Intent intent = new Intent(MainActivity.this, MyOrdersActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.menu_logout) {
            LoginActivity.logout(sharedPreferences);
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeViews() {
        // Mexican Pizza
        btn_mexican_plus = findViewById(R.id.btn_mexican_add);
        btn_mexican_minus = findViewById(R.id.btn_mexican_minus);
        et_mexican_main = findViewById(R.id.et_mexican_main);

        // Cheesy Pizza
        btn_cheesypizza_plus = findViewById(R.id.btn_cheesypizza_add);
        btn_cheesypizza_minus = findViewById(R.id.btn_chessypizza_minus);
        et_cheesypizza_main = findViewById(R.id.et_cheesypizza_main);

        // Italian Pizza
        btn_italian_plus = findViewById(R.id.btn_italian_plus);
        btn_italian_minus = findViewById(R.id.btn_italian_minus);
        et_italian_main = findViewById(R.id.et_italian_main);

        // Margherita Pizza
        btn_margherita_plus = findViewById(R.id.btn_margherita_add);
        btn_margherita_minus = findViewById(R.id.btn_margherita_minus);
        et_margherita_main = findViewById(R.id.et_margherita_main);

        // Pepperoni Pizza
        btn_pepperoni_plus = findViewById(R.id.btn_pepperoni_add);
        btn_pepperoni_minus = findViewById(R.id.btn_pepperoni_minus);
        et_pepperoni_main = findViewById(R.id.et_pepperoni_main);

        // Veggie Supreme
        btn_veggie_plus = findViewById(R.id.btn_veggie_add);
        btn_veggie_minus = findViewById(R.id.btn_veggie_minus);
        et_veggie_main = findViewById(R.id.et_veggie_main);

        // Checkout and Total
        btn_checkout = findViewById(R.id.btn_checkout);
        tv_total_main = findViewById(R.id.tv_total_main);
    }

    private void setupClickListeners() {
        // Mexican Pizza
        btn_mexican_plus.setOnClickListener(v -> {
            count_mexican++;
            et_mexican_main.setText(String.valueOf(count_mexican));
            calculateTotal();
        });

        btn_mexican_minus.setOnClickListener(v -> {
            if (count_mexican > 0) {
                count_mexican--;
                et_mexican_main.setText(String.valueOf(count_mexican));
                calculateTotal();
            }
        });

        // Cheesy Pizza
        btn_cheesypizza_plus.setOnClickListener(v -> {
            count_cheesypizza++;
            et_cheesypizza_main.setText(String.valueOf(count_cheesypizza));
            calculateTotal();
        });

        btn_cheesypizza_minus.setOnClickListener(v -> {
            if (count_cheesypizza > 0) {
                count_cheesypizza--;
                et_cheesypizza_main.setText(String.valueOf(count_cheesypizza));
                calculateTotal();
            }
        });

        // Italian Pizza
        btn_italian_plus.setOnClickListener(v -> {
            count_italian++;
            et_italian_main.setText(String.valueOf(count_italian));
            calculateTotal();
        });

        btn_italian_minus.setOnClickListener(v -> {
            if (count_italian > 0) {
                count_italian--;
                et_italian_main.setText(String.valueOf(count_italian));
                calculateTotal();
            }
        });

        // Margherita Pizza
        btn_margherita_plus.setOnClickListener(v -> {
            count_margherita++;
            et_margherita_main.setText(String.valueOf(count_margherita));
            calculateTotal();
        });

        btn_margherita_minus.setOnClickListener(v -> {
            if (count_margherita > 0) {
                count_margherita--;
                et_margherita_main.setText(String.valueOf(count_margherita));
                calculateTotal();
            }
        });

        // Pepperoni Pizza
        btn_pepperoni_plus.setOnClickListener(v -> {
            count_pepperoni++;
            et_pepperoni_main.setText(String.valueOf(count_pepperoni));
            calculateTotal();
        });

        btn_pepperoni_minus.setOnClickListener(v -> {
            if (count_pepperoni > 0) {
                count_pepperoni--;
                et_pepperoni_main.setText(String.valueOf(count_pepperoni));
                calculateTotal();
            }
        });

        // Veggie Supreme
        btn_veggie_plus.setOnClickListener(v -> {
            count_veggie++;
            et_veggie_main.setText(String.valueOf(count_veggie));
            calculateTotal();
        });

        btn_veggie_minus.setOnClickListener(v -> {
            if (count_veggie > 0) {
                count_veggie--;
                et_veggie_main.setText(String.valueOf(count_veggie));
                calculateTotal();
            }
        });

        // Checkout Button
        btn_checkout.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CartActivity.class);
            i.putExtra("mexican_qty", count_mexican);
            i.putExtra("cheesypizza_qty", count_cheesypizza);
            i.putExtra("italian_qty", count_italian);
            i.putExtra("margherita_qty", count_margherita);
            i.putExtra("pepperoni_qty", count_pepperoni);
            i.putExtra("veggie_qty", count_veggie);
            i.putExtra("total", total);
            startActivity(i);
        });
    }

    private void calculateTotal() {
        total = count_mexican * mexicanPrice +
                count_cheesypizza * cheesypizzaPrice +
                count_italian * italianPrice +
                count_margherita * margheritaPrice +
                count_pepperoni * pepperoniPrice +
                count_veggie * veggiePrice;
        tv_total_main.setText("₹" + total);

        // Enable/disable checkout button
        btn_checkout.setEnabled(total > 0);
        if (total > 0) {
            btn_checkout.setAlpha(1.0f);
        } else {
            btn_checkout.setAlpha(0.5f);
        }
    }
}
