package com.example.practical06;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for User Login.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText et_phone, et_password;
    Button btn_login;
    TextView tv_signup_link;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Check if already logged in
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            navigateToMain();
            return;
        }

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_signup_link = findViewById(R.id.tv_signup_link);
    }

    private void setupClickListeners() {
        btn_login.setOnClickListener(v -> {
            String phone = et_phone.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            if (validateInput(phone, password)) {
                // Check if user exists and credentials match
                String storedPhone = sharedPreferences.getString(KEY_PHONE, null);
                String storedPassword = sharedPreferences.getString("password", null);

                if (storedPhone == null) {
                    Toast.makeText(LoginActivity.this, "Please sign up first!", Toast.LENGTH_SHORT).show();
                    Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(signUpIntent);
                    return;
                }

                if (phone.equals(storedPhone) && password.equals(storedPassword)) {
                    // Save login state
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEY_IS_LOGGED_IN, true);
                    editor.putString(KEY_PHONE, phone);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    navigateToMain();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid phone or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_signup_link.setOnClickListener(v -> {
            Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);
        });
    }

    private boolean validateInput(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            et_phone.setError("Phone number is required");
            et_phone.requestFocus();
            return false;
        }

        if (phone.length() != 10) {
            et_phone.setError("Enter valid 10-digit phone number");
            et_phone.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            et_password.setError("Password is required");
            et_password.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            et_password.setError("Password must be at least 6 characters");
            et_password.requestFocus();
            return false;
        }

        return true;
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public static void logout(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.remove(KEY_PHONE);
        editor.apply();
    }
}

