package com.example.joytec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.joytec.R;
import com.example.joytec.repository.AuthRepository;
public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogout;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authRepository = new AuthRepository(this);

        // Verificar si está logueado
        if (!authRepository.isLoggedIn()) {
            goToLoginActivity();
            return;
        }

        initViews();
        setupUserInfo();
    }

    private void initViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            authRepository.clearUserData();
            goToLoginActivity();
        });
    }

    private void setupUserInfo() {
        String username = authRepository.getUsername();
        String rol = authRepository.getUserRol();

        tvWelcome.setText("Bienvenido: " + username + "\nRol: " + rol);
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}