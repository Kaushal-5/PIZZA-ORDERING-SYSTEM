package com.example.practical06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {
    // TextViews for all items
    TextView tv_mexican_qty, tv_mexican_total;
    TextView tv_cheesypizza_qty, tv_cheesypizza_total;
    TextView tv_italian_qty, tv_italian_total;
    TextView tv_margherita_qty, tv_margherita_total;
    TextView tv_pepperoni_qty, tv_pepperoni_total;
    TextView tv_veggie_qty, tv_veggie_total;
    TextView tv_cart_total;
    
    // Buttons
    Button btn_proceed_checkout;
    Button btn_mexican_plus, btn_mexican_minus;
    Button btn_cheesypizza_plus, btn_cheesypizza_minus;
    Button btn_italian_plus, btn_italian_minus;
    Button btn_margherita_plus, btn_margherita_minus;
    Button btn_pepperoni_plus, btn_pepperoni_minus;
    Button btn_veggie_plus, btn_veggie_minus;
    
    // Card views for visibility control
    View card_mexican, card_cheesypizza, card_italian;
    View card_margherita, card_pepperoni, card_veggie;
    
    // Quantities
    int mexicanQty = 0, cheesypizzaQty = 0, italianQty = 0;
    int margheritaQty = 0, pepperoniQty = 0, veggieQty = 0;
    
    // Prices
    int mexicanPrice = 300, cheesypizzaPrice = 450, italianPrice = 760;
    int margheritaPrice = 350, pepperoniPrice = 420, veggiePrice = 380;
    
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        initializeViews();
        receiveIntentData();
        setupQuantityControls();
        updateCartItems();

        // Proceed to checkout button
        btn_proceed_checkout.setOnClickListener(v -> {
            Intent checkoutIntent = new Intent(CartActivity.this, MainActivity2.class);
            checkoutIntent.putExtra("total", String.valueOf(total));
            startActivity(checkoutIntent);
        });
    }

    private void initializeViews() {
        // Mexican Pizza
        tv_mexican_qty = findViewById(R.id.tv_mexican_qty);
        tv_mexican_total = findViewById(R.id.tv_mexican_total);
        btn_mexican_plus = findViewById(R.id.btn_mexican_plus);
        btn_mexican_minus = findViewById(R.id.btn_mexican_minus);
        card_mexican = findViewById(R.id.card_mexican);
        
        // Cheesy Pizza
        tv_cheesypizza_qty = findViewById(R.id.tv_cheesypizza_qty);
        tv_cheesypizza_total = findViewById(R.id.tv_cheesypizza_total);
        btn_cheesypizza_plus = findViewById(R.id.btn_cheesypizza_plus);
        btn_cheesypizza_minus = findViewById(R.id.btn_cheesypizza_minus);
        card_cheesypizza = findViewById(R.id.card_cheesypizza);
        
        // Italian Pizza
        tv_italian_qty = findViewById(R.id.tv_italian_qty);
        tv_italian_total = findViewById(R.id.tv_italian_total);
        btn_italian_plus = findViewById(R.id.btn_italian_plus);
        btn_italian_minus = findViewById(R.id.btn_italian_minus);
        card_italian = findViewById(R.id.card_italian);
        
        // Margherita Pizza
        tv_margherita_qty = findViewById(R.id.tv_margherita_qty);
        tv_margherita_total = findViewById(R.id.tv_margherita_total);
        btn_margherita_plus = findViewById(R.id.btn_margherita_plus);
        btn_margherita_minus = findViewById(R.id.btn_margherita_minus);
        card_margherita = findViewById(R.id.card_margherita);
        
        // Pepperoni Pizza
        tv_pepperoni_qty = findViewById(R.id.tv_pepperoni_qty);
        tv_pepperoni_total = findViewById(R.id.tv_pepperoni_total);
        btn_pepperoni_plus = findViewById(R.id.btn_pepperoni_plus);
        btn_pepperoni_minus = findViewById(R.id.btn_pepperoni_minus);
        card_pepperoni = findViewById(R.id.card_pepperoni);
        
        // Veggie Supreme
        tv_veggie_qty = findViewById(R.id.tv_veggie_qty);
        tv_veggie_total = findViewById(R.id.tv_veggie_total);
        btn_veggie_plus = findViewById(R.id.btn_veggie_plus);
        btn_veggie_minus = findViewById(R.id.btn_veggie_minus);
        card_veggie = findViewById(R.id.card_veggie);
        
        // Total and Checkout
        tv_cart_total = findViewById(R.id.tv_cart_total);
        btn_proceed_checkout = findViewById(R.id.btn_proceed_checkout);
    }

    private void receiveIntentData() {
        Intent intent = getIntent();
        mexicanQty = intent.getIntExtra("mexican_qty", 0);
        cheesypizzaQty = intent.getIntExtra("cheesypizza_qty", 0);
        italianQty = intent.getIntExtra("italian_qty", 0);
        margheritaQty = intent.getIntExtra("margherita_qty", 0);
        pepperoniQty = intent.getIntExtra("pepperoni_qty", 0);
        veggieQty = intent.getIntExtra("veggie_qty", 0);
        total = intent.getIntExtra("total", 0);
    }

    private void setupQuantityControls() {
        // Mexican Pizza
        btn_mexican_plus.setOnClickListener(v -> {
            mexicanQty++;
            updateCartItems();
        });
        btn_mexican_minus.setOnClickListener(v -> {
            if (mexicanQty > 0) {
                mexicanQty--;
                updateCartItems();
            }
        });

        // Cheesy Pizza
        btn_cheesypizza_plus.setOnClickListener(v -> {
            cheesypizzaQty++;
            updateCartItems();
        });
        btn_cheesypizza_minus.setOnClickListener(v -> {
            if (cheesypizzaQty > 0) {
                cheesypizzaQty--;
                updateCartItems();
            }
        });

        // Italian Pizza
        btn_italian_plus.setOnClickListener(v -> {
            italianQty++;
            updateCartItems();
        });
        btn_italian_minus.setOnClickListener(v -> {
            if (italianQty > 0) {
                italianQty--;
                updateCartItems();
            }
        });

        // Margherita Pizza
        btn_margherita_plus.setOnClickListener(v -> {
            margheritaQty++;
            updateCartItems();
        });
        btn_margherita_minus.setOnClickListener(v -> {
            if (margheritaQty > 0) {
                margheritaQty--;
                updateCartItems();
            }
        });

        // Pepperoni Pizza
        btn_pepperoni_plus.setOnClickListener(v -> {
            pepperoniQty++;
            updateCartItems();
        });
        btn_pepperoni_minus.setOnClickListener(v -> {
            if (pepperoniQty > 0) {
                pepperoniQty--;
                updateCartItems();
            }
        });

        // Veggie Supreme
        btn_veggie_plus.setOnClickListener(v -> {
            veggieQty++;
            updateCartItems();
        });
        btn_veggie_minus.setOnClickListener(v -> {
            if (veggieQty > 0) {
                veggieQty--;
                updateCartItems();
            }
        });
    }

    private void updateCartItems() {
        // Mexican Pizza
        updateItem(mexicanQty, mexicanPrice, tv_mexican_qty, tv_mexican_total, card_mexican);

        // Cheesy Pizza
        updateItem(cheesypizzaQty, cheesypizzaPrice, tv_cheesypizza_qty, tv_cheesypizza_total, card_cheesypizza);

        // Italian Pizza
        updateItem(italianQty, italianPrice, tv_italian_qty, tv_italian_total, card_italian);

        // Margherita Pizza
        updateItem(margheritaQty, margheritaPrice, tv_margherita_qty, tv_margherita_total, card_margherita);

        // Pepperoni Pizza
        updateItem(pepperoniQty, pepperoniPrice, tv_pepperoni_qty, tv_pepperoni_total, card_pepperoni);

        // Veggie Supreme
        updateItem(veggieQty, veggiePrice, tv_veggie_qty, tv_veggie_total, card_veggie);

        // Calculate and display total
        total = mexicanQty * mexicanPrice +
                cheesypizzaQty * cheesypizzaPrice +
                italianQty * italianPrice +
                margheritaQty * margheritaPrice +
                pepperoniQty * pepperoniPrice +
                veggieQty * veggiePrice;
        
        tv_cart_total.setText("₹" + total);
        
        // Enable/disable checkout button
        btn_proceed_checkout.setEnabled(total > 0);
        btn_proceed_checkout.setAlpha(total > 0 ? 1.0f : 0.5f);
    }

    private void updateItem(int qty, int price, TextView qtyView, TextView totalView, View cardView) {
        qtyView.setText(String.valueOf(qty));
        int itemTotal = qty * price;
        totalView.setText("₹" + itemTotal);
        
        if (qty == 0) {
            cardView.setVisibility(View.GONE);
        } else {
            cardView.setVisibility(View.VISIBLE);
        }
    }
}
