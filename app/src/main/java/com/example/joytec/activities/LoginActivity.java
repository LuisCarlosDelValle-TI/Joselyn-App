package com.example.joytec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.joytec.R;
import com.example.joytec.models.LoginResponse;
import com.example.joytec.repository.AuthRepository;
public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnGoToRegister;
    private ProgressBar progressBar;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        // Inicializar repository
        authRepository = new AuthRepository(this);

        // Verificar si ya está logueado
        if (authRepository.isLoggedIn()) {
            goToMainActivity();
            return;
        }

        initViews();
        setupListeners();
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            login(username, password);
        });

        btnGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login(String username, String password) {
        showLoading(true);

        authRepository.login(username, password, new AuthRepository.LoginCallback() {
            @Override
            public void onSuccess(LoginResponse response) {
                showLoading(false);
                Toast.makeText(LoginActivity.this,
                        "Bienvenido " + response.getData().getUsuario().getUsername(),
                        Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }

            @Override
            public void onError(String error) {
                showLoading(false);
                Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!show);
        btnGoToRegister.setEnabled(!show);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}