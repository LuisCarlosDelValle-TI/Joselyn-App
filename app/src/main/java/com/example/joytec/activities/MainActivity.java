package com.example.joytec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.joytec.R;
import com.example.joytec.repository.AuthRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogout;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authRepository = new AuthRepository(this);

        if (!authRepository.isLoggedIn()) {
            goToLoginActivity();
            return;
        }

        initViews();
        setupUserInfo();
        probarApi();
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

    // Método para probar tu API
    private void probarApi() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.1.52:3001") // Cambia por la URL de tu API
                .build();

        new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    Log.e("API_TEST", "Error: " + response.code());
                } else {
                    Log.d("API_TEST", "Respuesta: " + response.body().string());
                }
            } catch (Exception e) {
                Log.e("API_TEST", "Excepción: " + e.getMessage());
            }
        }).start();
    }
}