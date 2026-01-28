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
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    EditText et_name, et_phone, et_password, et_confirm_password;
    Button btn_signup;
    TextView tv_login_link;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        // Get EditText from TextInputLayout
        com.google.android.material.textfield.TextInputLayout nameLayout = findViewById(R.id.til_name);
        com.google.android.material.textfield.TextInputLayout phoneLayout = findViewById(R.id.til_phone);
        com.google.android.material.textfield.TextInputLayout passwordLayout = findViewById(R.id.til_password);
        com.google.android.material.textfield.TextInputLayout confirmPasswordLayout = findViewById(R.id.til_confirm_password);
        
        if (nameLayout != null) et_name = nameLayout.getEditText();
        if (phoneLayout != null) et_phone = phoneLayout.getEditText();
        if (passwordLayout != null) et_password = passwordLayout.getEditText();
        if (confirmPasswordLayout != null) et_confirm_password = confirmPasswordLayout.getEditText();
        
        btn_signup = findViewById(R.id.btn_signup);
        tv_login_link = findViewById(R.id.tv_login_link);
        
        // Make login link clickable
        if (tv_login_link != null) {
            tv_login_link.setClickable(true);
            tv_login_link.setFocusable(true);
        }
    }

    private void setupClickListeners() {
        if (btn_signup != null) {
            btn_signup.setOnClickListener(v -> {
                if (et_name == null || et_phone == null || et_password == null || et_confirm_password == null) {
                    Toast.makeText(SignUpActivity.this, "Error: Please check all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                String name = et_name.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String confirmPassword = et_confirm_password.getText().toString().trim();

                if (validateInput(name, phone, password, confirmPassword)) {
                    // Check if phone already exists
                    String existingPhone = sharedPreferences.getString(KEY_PHONE, null);
                    if (existingPhone != null && existingPhone.equals(phone)) {
                        Toast.makeText(SignUpActivity.this, "This phone number is already registered. Please login.", Toast.LENGTH_SHORT).show();
                        navigateToLogin();
                        return;
                    }
                    
                    // Save user data
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_NAME, name);
                    editor.putString(KEY_PHONE, phone);
                    editor.putString(KEY_PASSWORD, password);
                    editor.apply();

                    Toast.makeText(SignUpActivity.this, "Sign up successful! Please login.", Toast.LENGTH_SHORT).show();
                    navigateToLogin();
                }
            });
        }

        if (tv_login_link != null) {
            tv_login_link.setOnClickListener(v -> {
                navigateToLogin();
            });
        }
    }

    private boolean validateInput(String name, String phone, String password, String confirmPassword) {
        boolean isValid = true;
        
        if (TextUtils.isEmpty(name)) {
            if (et_name != null) {
                et_name.setError("Name is required");
                et_name.requestFocus();
            }
            isValid = false;
        }

        if (TextUtils.isEmpty(phone)) {
            if (et_phone != null) {
                et_phone.setError("Phone number is required");
                et_phone.requestFocus();
            }
            isValid = false;
        } else if (phone.length() != 10) {
            if (et_phone != null) {
                et_phone.setError("Enter valid 10-digit phone number");
                et_phone.requestFocus();
            }
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            if (et_password != null) {
                et_password.setError("Password is required");
                et_password.requestFocus();
            }
            isValid = false;
        } else if (password.length() < 6) {
            if (et_password != null) {
                et_password.setError("Password must be at least 6 characters");
                et_password.requestFocus();
            }
            isValid = false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            if (et_confirm_password != null) {
                et_confirm_password.setError("Please confirm your password");
                et_confirm_password.requestFocus();
            }
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            if (et_confirm_password != null) {
                et_confirm_password.setError("Passwords do not match");
                et_confirm_password.requestFocus();
            }
            isValid = false;
        }

        return isValid;
    }

    private void navigateToLogin() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

