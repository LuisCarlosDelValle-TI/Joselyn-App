package com.example.joytec.activities.empleados;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.joytec.R;
import com.example.joytec.models.EmpleadoRequest;
import com.example.joytec.models.EmpleadoResponse;
import com.example.joytec.network.ApiClient;
import com.example.joytec.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroEmpleadoActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private EditText etNombre, etApellidoPaterno, etApellidoMaterno, etTelefono, etSalario;
    private Button btnGuardarEmpleado;

    private boolean modoEdicion = false;
    private int empleadoId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empleado);

        initViews();
        checkEditMode();
        setupClickListeners();
    }

    private void initViews() {
        tvTitulo = findViewById(R.id.tvTitulo);
        etNombre = findViewById(R.id.etNombre);
        etApellidoPaterno = findViewById(R.id.etApellido);
        etApellidoMaterno = findViewById(R.id.etApellido); // Usar el mismo campo
        etTelefono = findViewById(R.id.etTelefono);
        etSalario = findViewById(R.id.etSalario);
        btnGuardarEmpleado = findViewById(R.id.btnGuardar);
    }

    private void checkEditMode() {
        modoEdicion = getIntent().getBooleanExtra("modo_edicion", false);
        empleadoId = getIntent().getIntExtra("empleado_id", 0);

        if (modoEdicion && empleadoId > 0) {
            tvTitulo.setText("Editar Empleado");
            btnGuardarEmpleado.setText("Actualizar");
            cargarDatosEmpleado();
        } else {
            tvTitulo.setText("Registrar Empleado");
            btnGuardarEmpleado.setText("Guardar");
        }
    }

    private void setupClickListeners() {
        btnGuardarEmpleado.setOnClickListener(v -> {
            if (validarCampos()) {
                if (modoEdicion) {
                    actualizarEmpleado();
                } else {
                    crearEmpleado();
                }
            }
        });

        Button btnCancelar = findViewById(R.id.btnCancelar);
        if (btnCancelar != null) {
            btnCancelar.setOnClickListener(v -> finish());
        }
    }

    private boolean validarCampos() {
        if (etNombre.getText().toString().trim().isEmpty()) {
            etNombre.setError("El nombre es requerido");
            etNombre.requestFocus();
            return false;
        }
        if (etApellidoPaterno.getText().toString().trim().isEmpty()) {
            etApellidoPaterno.setError("El apellido es requerido");
            etApellidoPaterno.requestFocus();
            return false;
        }
        if (etTelefono.getText().toString().trim().isEmpty()) {
            etTelefono.setError("El teléfono es requerido");
            etTelefono.requestFocus();
            return false;
        }
        if (etSalario.getText().toString().trim().isEmpty()) {
            etSalario.setError("El salario es requerido");
            etSalario.requestFocus();
            return false;
        }

        try {
            Double.parseDouble(etSalario.getText().toString().trim());
        } catch (NumberFormatException e) {
            etSalario.setError("Salario inválido");
            etSalario.requestFocus();
            return false;
        }

        return true;
    }

    private EmpleadoRequest crearEmpleadoRequest() {
        EmpleadoRequest request = new EmpleadoRequest();
        request.setNombre(etNombre.getText().toString().trim());
        request.setApellido(etApellidoPaterno.getText().toString().trim()); // Solo un apellido
        request.setTelefono(etTelefono.getText().toString().trim());
        request.setEmail(findViewById(R.id.etEmail) != null ?
                ((EditText) findViewById(R.id.etEmail)).getText().toString().trim() : "");
        request.setPuesto(findViewById(R.id.etPuesto) != null ?
                ((EditText) findViewById(R.id.etPuesto)).getText().toString().trim() : "Empleado");
        request.setSalario(Double.parseDouble(etSalario.getText().toString().trim()));
        return request;
    }

    private void crearEmpleado() {
        EmpleadoRequest request = crearEmpleadoRequest();
        Toast.makeText(this, "Creando empleado...", Toast.LENGTH_SHORT).show();

        ApiClient.getApiService().crearEmpleado(request)
                .enqueue(new Callback<ApiService.ApiResponse<EmpleadoResponse>>() {
                    @Override
                    public void onResponse(Call<ApiService.ApiResponse<EmpleadoResponse>> call,
                                           Response<ApiService.ApiResponse<EmpleadoResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String mensaje = response.body().getMessage();
                            if (mensaje == null || mensaje.isEmpty()) {
                                mensaje = "Empleado creado exitosamente";
                            }
                            Toast.makeText(RegistroEmpleadoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                            Log.d("RegistroEmpleado", "Empleado creado");
                            finish();
                        } else {
                            Toast.makeText(RegistroEmpleadoActivity.this,
                                    "Error al crear empleado", Toast.LENGTH_SHORT).show();
                            Log.e("RegistroEmpleado", "Error: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiService.ApiResponse<EmpleadoResponse>> call, Throwable t) {
                        Toast.makeText(RegistroEmpleadoActivity.this,
                                "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("RegistroEmpleado", "Error de conexión", t);
                    }
                });
    }

    private void actualizarEmpleado() {
        EmpleadoRequest request = crearEmpleadoRequest();
        Toast.makeText(this, "Actualizando empleado...", Toast.LENGTH_SHORT).show();

        ApiClient.getApiService().actualizarEmpleado(empleadoId, request)
                .enqueue(new Callback<ApiService.ApiResponse<EmpleadoResponse>>() {
                    @Override
                    public void onResponse(Call<ApiService.ApiResponse<EmpleadoResponse>> call,
                                           Response<ApiService.ApiResponse<EmpleadoResponse>> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegistroEmpleadoActivity.this,
                                    "Empleado actualizado exitosamente", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegistroEmpleadoActivity.this,
                                    "Error al actualizar empleado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiService.ApiResponse<EmpleadoResponse>> call, Throwable t) {
                        Toast.makeText(RegistroEmpleadoActivity.this,
                                "Error de conexión al actualizar", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cargarDatosEmpleado() {
        ApiClient.getApiService().getEmpleadoPorId(empleadoId)
                .enqueue(new Callback<EmpleadoResponse>() {
                    @Override
                    public void onResponse(Call<EmpleadoResponse> call, Response<EmpleadoResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            EmpleadoResponse empleado = response.body();
                            etNombre.setText(empleado.getNombre());
                            etApellidoPaterno.setText(empleado.getApellido());
                            etTelefono.setText(empleado.getTelefono());
                            etSalario.setText(String.valueOf(empleado.getSalario()));

                            // Campos opcionales
                            EditText etEmail = findViewById(R.id.etEmail);
                            if (etEmail != null) {
                                etEmail.setText(empleado.getEmail() != null ? empleado.getEmail() : "");
                            }

                            EditText etPuesto = findViewById(R.id.etPuesto);
                            if (etPuesto != null) {
                                etPuesto.setText(empleado.getPuesto() != null ? empleado.getPuesto() : "");
                            }
                        } else {
                            Toast.makeText(RegistroEmpleadoActivity.this,
                                    "Error al cargar datos del empleado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EmpleadoResponse> call, Throwable t) {
                        Toast.makeText(RegistroEmpleadoActivity.this,
                                "Error de conexión al cargar empleado", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}