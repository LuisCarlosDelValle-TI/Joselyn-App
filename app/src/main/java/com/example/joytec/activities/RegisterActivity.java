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
import com.example.joytec.models.Usuario;
import com.example.joytec.repository.AuthRepository;
public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etApellidoPaterno, etApellidoMaterno,
            etCorreo, etPassword, etTelefono;
    private Button btnRegister, btnGoToLogin;
    private ProgressBar progressBar;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_main);

        // Inicializar repository
        authRepository = new AuthRepository(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etApellidoPaterno = findViewById(R.id.etApellidoPaterno);
        etApellidoMaterno = findViewById(R.id.etApellidoMaterno);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        etTelefono = findViewById(R.id.etTelefono);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> {
            if (validateFields()) {
                registrar();
            }
        });

        btnGoToLogin.setOnClickListener(v -> {
            finish();
        });
    }

    private boolean validateFields() {
        String username = etUsername.getText().toString().trim();
        String apellidoPaterno = etApellidoPaterno.getText().toString().trim();
        String apellidoMaterno = etApellidoMaterno.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();

        if (username.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() ||
                correo.isEmpty() || password.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void registrar() {
        showLoading(true);

        String username = etUsername.getText().toString().trim();
        String apellidoPaterno = etApellidoPaterno.getText().toString().trim();
        String apellidoMaterno = etApellidoMaterno.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();

        Usuario usuario = new Usuario(username, apellidoPaterno, apellidoMaterno,
                correo, password, telefono, "usuario");

        authRepository.registrar(usuario, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(String message) {
                showLoading(false);
                Toast.makeText(RegisterActivity.this,
                        "Registro exitoso. Iniciando sesión...",
                        Toast.LENGTH_SHORT).show();

                // Ir a MainActivity ya que el registro automáticamente loguea
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String error) {
                showLoading(false);
                Toast.makeText(RegisterActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnRegister.setEnabled(!show);
        btnGoToLogin.setEnabled(!show);
    }
}