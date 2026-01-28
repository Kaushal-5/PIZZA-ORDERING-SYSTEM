package com.example.practical06;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for Order Confirmation and Coupon Application.
 */
public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    TextView tv_itemtotal, tv_gst, tv_platformfee, tv_deliveryfee, tv_total, tv_promocode, tv_final_total,
            tv_promo_status;
    Button btn_apply, btn_place_order;
    EditText et_promocode;
    LinearLayout promo_layout, coupons_container;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_PHONE = "phone";

    // Available coupons
    private static class Coupon {
        String code;
        String name;
        double discountPercent;
        double discountFixed;
        String description;

        Coupon(String code, String name, double discountPercent, double discountFixed, String description) {
            this.code = code;
            this.name = name;
            this.discountPercent = discountPercent;
            this.discountFixed = discountFixed;
            this.description = description;
        }
    }

    private List<Coupon> availableCoupons;
    private Coupon selectedCoupon;

    double tt;
    double discount = 0;
    String currentUserPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentUserPhone = sharedPreferences.getString(KEY_PHONE, "");

        initializeCoupons();
        initializeViews();
        calculateAndDisplayTotals();
        setupClickListeners();
        displayAvailableCoupons();
    }

    private void initializeCoupons() {
        availableCoupons = new ArrayList<>();
        availableCoupons.add(new Coupon("WELCOME50", "Welcome Offer", 10.0, 0, "Get 10% off on your order"));
        availableCoupons.add(new Coupon("SAVE20", "Save More", 20.0, 0, "Get 20% off on orders above ₹500"));
        availableCoupons.add(new Coupon("PIZZA100", "Flat Discount", 0, 100, "Get ₹100 off on your order"));
        availableCoupons.add(new Coupon("FIRSTORDER", "First Order", 15.0, 0, "Get 15% off on your first order"));
    }

    private void initializeViews() {
        tv_itemtotal = findViewById(R.id.tv_itemtotal);
        tv_gst = findViewById(R.id.tv_gst);
        tv_platformfee = findViewById(R.id.tv_platfromfee);
        tv_deliveryfee = findViewById(R.id.tv_deliveryfee);
        tv_total = findViewById(R.id.tv_total);
        tv_promocode = findViewById(R.id.tv_promocode);
        tv_final_total = findViewById(R.id.tv_final_total);
        tv_promo_status = findViewById(R.id.tv_promo_status);
        btn_apply = findViewById(R.id.btn_apply);
        btn_place_order = findViewById(R.id.btn_place_order);
        et_promocode = findViewById(R.id.et_promocode);
        promo_layout = findViewById(R.id.promo_layout);
        coupons_container = findViewById(R.id.coupons_container);
    }

    private void displayAvailableCoupons() {
        if (coupons_container == null)
            return;

        coupons_container.removeAllViews();

        TextView header = new TextView(this);
        header.setText("Available Coupons:");
        header.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        header.setTextSize(16f);
        header.setPadding(0, 0, 0, 12);
        header.setTypeface(null, android.graphics.Typeface.BOLD);
        coupons_container.addView(header);

        for (Coupon coupon : availableCoupons) {
            if (isCouponUsed(coupon.code)) {
                continue; // Skip already used coupons
            }

            android.widget.LinearLayout couponLayout = new LinearLayout(this);
            couponLayout.setOrientation(LinearLayout.HORIZONTAL);
            couponLayout.setPadding(16, 12, 16, 12);
            couponLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.card_background_light));
            couponLayout.setPadding(16, 16, 16, 16);
            android.view.ViewGroup.MarginLayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            ((LinearLayout.LayoutParams) params).setMargins(0, 8, 0, 8);
            couponLayout.setLayoutParams(params);

            LinearLayout textLayout = new LinearLayout(this);
            textLayout.setOrientation(LinearLayout.VERTICAL);
            textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f));

            TextView codeText = new TextView(this);
            codeText.setText(coupon.code);
            codeText.setTextColor(ContextCompat.getColor(this, R.color.accent));
            codeText.setTextSize(16f);
            codeText.setTypeface(null, android.graphics.Typeface.BOLD);
            textLayout.addView(codeText);

            TextView descText = new TextView(this);
            descText.setText(coupon.description);
            descText.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
            descText.setTextSize(12f);
            textLayout.addView(descText);

            couponLayout.addView(textLayout);

            Button applyBtn = new Button(this);
            applyBtn.setText("Apply");
            applyBtn.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            applyBtn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.accent)));
            applyBtn.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
            applyBtn.setPadding(24, 0, 24, 0);
            applyBtn.setOnClickListener(v -> {
                et_promocode.setText(coupon.code);
                applyCoupon(coupon);
            });

            couponLayout.addView(applyBtn);
            coupons_container.addView(couponLayout);
        }
    }

    private boolean isCouponUsed(String couponCode) {
        if (TextUtils.isEmpty(currentUserPhone)) {
            return false;
        }
        String key = "coupon_used_" + currentUserPhone + "_" + couponCode;
        return sharedPreferences.getBoolean(key, false);
    }

    private void markCouponAsUsed(String couponCode) {
        if (TextUtils.isEmpty(currentUserPhone)) {
            return;
        }
        String key = "coupon_used_" + currentUserPhone + "_" + couponCode;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, true);
        editor.apply();
    }

    private void calculateAndDisplayTotals() {
        String tot = getIntent().getStringExtra("total");

        if (tot != null && !tot.isEmpty()) {
            try {
                int itemTotal = Integer.parseInt(tot);

                tv_itemtotal.setText("₹" + itemTotal);

                double gst = itemTotal * 0.10;
                double deliveryFee = 60;
                double platformFee = 10;

                tv_gst.setText("₹" + String.format("%.2f", gst));
                tv_deliveryfee.setText("₹" + String.format("%.0f", deliveryFee));
                tv_platformfee.setText("₹" + String.format("%.0f", platformFee));

                tt = itemTotal + gst + deliveryFee + platformFee;
                updateFinalTotal();
            } catch (NumberFormatException e) {
                tv_itemtotal.setText("Error");
                tv_total.setText("Invalid total");
            }
        } else {
            tv_itemtotal.setText("No data");
            tv_total.setText("Error");
        }
    }

    private void setupClickListeners() {
        btn_apply.setOnClickListener(v -> {
            String promo = et_promocode.getText().toString().trim();

            if (TextUtils.isEmpty(promo)) {
                et_promocode.setError("Enter promo code");
                return;
            }

            Coupon coupon = findCouponByCode(promo);
            if (coupon != null) {
                applyCoupon(coupon);
            } else {
                tv_promo_status.setVisibility(View.VISIBLE);
                tv_promo_status.setText("Invalid promo code");
                tv_promo_status.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                Toast.makeText(this, "Invalid promo code!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_place_order.setOnClickListener(v -> {
            saveOrder();
            Toast.makeText(this, "Order placed successfully! Total: ₹" + String.format("%.2f", tt - discount),
                    Toast.LENGTH_LONG).show();
            // Go back to Menu (MainActivity) instead of just finishing, to clear stack or
            // just finish
            android.content.Intent intent = new android.content.Intent(MainActivity2.this, MainActivity.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void saveOrder() {
        if (android.text.TextUtils.isEmpty(currentUserPhone))
            return;

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm",
                java.util.Locale.getDefault());
        String currentDate = sdf.format(new java.util.Date());
        String orderId = "ORD-" + System.currentTimeMillis();
        double finalAmount = tt - discount;
        String status = "Placed";
        String items = "Pizza Order"; // ideally passed from previous intent, but for now generic

        Order newOrder = new Order(orderId, currentDate, finalAmount, status, items);

        com.google.gson.Gson gson = new com.google.gson.Gson();
        String key = "orders_" + currentUserPhone;
        String existingOrdersJson = sharedPreferences.getString(key, "[]");
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<java.util.List<Order>>() {
        }.getType();
        java.util.List<Order> orderList = gson.fromJson(existingOrdersJson, type);

        if (orderList == null)
            orderList = new java.util.ArrayList<>();
        orderList.add(0, newOrder); // Add to top

        String newOrdersJson = gson.toJson(orderList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, newOrdersJson);
        editor.apply();
    }

    private Coupon findCouponByCode(String code) {
        for (Coupon coupon : availableCoupons) {
            if (coupon.code.equalsIgnoreCase(code)) {
                return coupon;
            }
        }
        return null;
    }

    private void applyCoupon(Coupon coupon) {
        // Check if coupon already used by this user
        if (isCouponUsed(coupon.code)) {
            tv_promo_status.setVisibility(View.VISIBLE);
            tv_promo_status.setText("You have already used this coupon!");
            tv_promo_status.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
            Toast.makeText(this, "You have already used this coupon!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check minimum order value for SAVE20
        if (coupon.code.equals("SAVE20")) {
            double itemTotal = Double.parseDouble(tv_itemtotal.getText().toString().replace("₹", ""));
            if (itemTotal < 500) {
                tv_promo_status.setVisibility(View.VISIBLE);
                tv_promo_status.setText("Minimum order value ₹500 required");
                tv_promo_status.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                Toast.makeText(this, "Minimum order value ₹500 required for this coupon", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Calculate discount
        if (coupon.discountPercent > 0) {
            discount = tt * (coupon.discountPercent / 100.0);
        } else {
            discount = coupon.discountFixed;
            if (discount > tt) {
                discount = tt * 0.1; // Max discount is 10% of total if fixed amount exceeds total
            }
        }

        // Mark coupon as used
        markCouponAsUsed(coupon.code);

        // Update UI
        promo_layout.setVisibility(View.VISIBLE);
        tv_promocode.setText("-₹" + String.format("%.2f", discount));
        tv_promo_status.setVisibility(View.VISIBLE);
        tv_promo_status.setText("✓ " + coupon.name + " applied successfully!");
        tv_promo_status.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));

        // Disable promo code input
        et_promocode.setEnabled(false);
        btn_apply.setEnabled(false);
        btn_apply.setAlpha(0.5f);

        // Refresh coupon list
        displayAvailableCoupons();

        updateFinalTotal();
        Toast.makeText(this, coupon.name + " applied! Discount: ₹" + String.format("%.2f", discount),
                Toast.LENGTH_SHORT).show();
    }

    private void updateFinalTotal() {
        double finalTotal = tt - discount;
        tv_total.setText("₹" + String.format("%.2f", finalTotal));
        tv_final_total.setText("₹" + String.format("%.2f", finalTotal));
    }
}
