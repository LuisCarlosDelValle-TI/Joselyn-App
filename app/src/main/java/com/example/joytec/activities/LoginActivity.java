package com.example.joytec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.joytec.R;
import com.example.joytec.activities.productos.ProductosActivity;
import com.example.joytec.api.AuthApiService;
import com.example.joytec.models.LoginRequest;
import com.example.joytec.models.LoginResponse;
import com.example.joytec.repository.AuthRepository; // AGREGAR ESTA IMPORTACIÓN
import retrofit2.*;

import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, buttonRegister;
    private ProgressBar progressBar;
    private AuthApiService apiService;
    private AuthRepository authRepository; // AGREGAR ESTA LÍNEA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // AGREGAR ESTA LÍNEA
        authRepository = new AuthRepository(this);

        // Si ya está logueado, ir directo al MainActivity
        if (authRepository.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, ProductosActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progressBar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.52:3001/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(AuthApiService.class);

        btnLogin.setOnClickListener(v -> iniciarSesion());

        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ProductosActivity.class);
            startActivity(intent);
        });
    }

    private void iniciarSesion() {
        String usuario = etUsername.getText().toString().trim();
        String contrasena = etPassword.getText().toString().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        LoginRequest request = new LoginRequest(usuario, contrasena);

        Call<LoginResponse> call = apiService.login(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    // GUARDAR LOS DATOS DEL USUARIO
                    authRepository.saveUserData(
                            loginResponse.getToken() != null ? loginResponse.getToken() : "default_token",
                            usuario, // El username que ingresó el usuario
                            loginResponse.getRol() != null ? loginResponse.getRol() : "user" // Rol por defecto si no viene
                    );

                    Toast.makeText(LoginActivity.this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();

                    // Redirige al dashboard
                    Intent intent = new Intent(LoginActivity.this, ProductosActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
