package com.example.joytec.activities.empleados;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.joytec.network.ApiService;
import androidx.appcompat.app.AppCompatActivity;

import com.example.joytec.R;
import com.example.joytec.models.EmpleadoResponse;
import com.example.joytec.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpleadosActivity extends AppCompatActivity {

    private LinearLayout containerEmpleados;
    private List<EmpleadoResponse> empleados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Usar el layout que me diste (copiado de item_list_clientes)
        setContentView(R.layout.fragment_item_empleado);

        initViews();
        setupClickListeners();
        cargarEmpleados();
    }

    private void initViews() {
        // Cambiar el título del layout existente
        TextView tvTitulo = findViewById(R.id.tvTituloClientes);
        if (tvTitulo != null) {
            tvTitulo.setText("Empleados");
        }

        // Cambiar el botón existente
        Button btnAgregar = findViewById(R.id.btnAgregarCliente);
        if (btnAgregar != null) {
            btnAgregar.setText("Agregar empleado");
        }

        // CORREGIR ESTA PARTE - No hagas cast a ScrollView
        containerEmpleados = findViewById(R.id.containerEmpleados); // ✅ Usa el ID correcto del layout

        // Si no encuentras el container, crear uno
        if (containerEmpleados == null) {
            // Buscar el LinearLayout principal del layout
            View rootView = findViewById(android.R.id.content);
            containerEmpleados = encontrarLinearLayout(rootView);

            if (containerEmpleados == null) {
                // Como último recurso, crear un nuevo container
                ScrollView scroll = new ScrollView(this);
                containerEmpleados = new LinearLayout(this);
                containerEmpleados.setOrientation(LinearLayout.VERTICAL);
                containerEmpleados.setPadding(16, 16, 16, 16);
                scroll.addView(containerEmpleados);
                setContentView(scroll);
            }
        }

        empleados = new ArrayList<>();
    }

    private LinearLayout encontrarLinearLayout(View parent) {
        if (parent instanceof LinearLayout) {
            LinearLayout layout = (LinearLayout) parent;
            // Verificar si tiene orientación vertical
            if (layout.getOrientation() == LinearLayout.VERTICAL) {
                return layout;
            }
        }

        if (parent instanceof android.view.ViewGroup) {
            android.view.ViewGroup group = (android.view.ViewGroup) parent;
            for (int i = 0; i < group.getChildCount(); i++) {
                LinearLayout result = encontrarLinearLayout(group.getChildAt(i));
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    private void setupClickListeners() {
        Button btnAgregar = findViewById(R.id.btnAgregarCliente);
        if (btnAgregar != null) {
            btnAgregar.setOnClickListener(v -> {
                Intent intent = new Intent(EmpleadosActivity.this, RegistroEmpleadoActivity.class);
                startActivity(intent);
            });
        }
    }

    private void cargarEmpleados() {
        Toast.makeText(this, "Cargando empleados...", Toast.LENGTH_SHORT).show();

        ApiClient.getApiService().getEmpleados().enqueue(new Callback<List<EmpleadoResponse>>() {
            @Override
            public void onResponse(Call<List<EmpleadoResponse>> call, Response<List<EmpleadoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    empleados.clear();
                    empleados.addAll(response.body());

                    mostrarEmpleados();

                    Toast.makeText(EmpleadosActivity.this,
                            "Empleados cargados: " + empleados.size(), Toast.LENGTH_SHORT).show();
                    Log.d("EmpleadosActivity", "Empleados cargados: " + empleados.size());
                } else {
                    Toast.makeText(EmpleadosActivity.this,
                            "Error al cargar empleados - Código: " + response.code(), Toast.LENGTH_LONG).show();
                    Log.e("EmpleadosActivity", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<EmpleadoResponse>> call, Throwable t) {
                Toast.makeText(EmpleadosActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("EmpleadosActivity", "Error de conexión", t);
            }
        });
    }

    private void mostrarEmpleados() {
        if (containerEmpleados == null) return;

        // Limpiar cards anteriores (mantener los primeros elementos del layout)
        int childCount = containerEmpleados.getChildCount();

        // Buscar el último CardView y eliminar desde ahí
        for (int i = childCount - 1; i >= 0; i--) {
            View child = containerEmpleados.getChildAt(i);
            if (child instanceof androidx.cardview.widget.CardView) {
                containerEmpleados.removeViewAt(i);
            }
        }

        // Si no hay empleados
        if (empleados.isEmpty()) {
            TextView tvNoData = new TextView(this);
            tvNoData.setText("No hay empleados registrados");
            tvNoData.setTextSize(16);
            tvNoData.setPadding(20, 20, 20, 20);
            containerEmpleados.addView(tvNoData);
            return;
        }

        // Agregar empleados usando fragment_item_empleado
        for (EmpleadoResponse empleado : empleados) {
            View empleadoCard = crearTarjetaEmpleado(empleado);
            containerEmpleados.addView(empleadoCard);
        }
    }

    private View crearTarjetaEmpleado(EmpleadoResponse empleado) {
        // Usar el layout fragment_item_empleado que me diste
        View cardView = LayoutInflater.from(this).inflate(R.layout.fragment_item_empleado, containerEmpleados, false);

        // ✅ AGREGAR MÁRGENES PARA SEPARAR LAS TARJETAS
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 8, 16, 8); // izquierda, arriba, derecha, abajo
        cardView.setLayoutParams(params);
        TextView tvNombre = cardView.findViewById(R.id.tvNombreEmpleado);
        TextView tvPuesto = cardView.findViewById(R.id.tvSalarioEmpleado);
        TextView tvTelefono = cardView.findViewById(R.id.tvTelefonoEmpleado);
        Button btnEditar = cardView.findViewById(R.id.btnDetallesEmpleado);
        Button btnEliminar = cardView.findViewById(R.id.btnEliminarEmpleado);

        // Configurar datos usando TUS IDs exactos
        tvNombre.setText(empleado.getNombreCompleto());
        tvPuesto.setText(empleado.getPuesto());
        tvTelefono.setText("Tel: " + empleado.getTelefono());

        // Configurar botones
        btnEditar.setOnClickListener(v -> editarEmpleado(empleado));

        btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(EmpleadosActivity.this)
                    .setTitle("Eliminar Empleado")
                    .setMessage("¿Eliminar " + empleado.getNombreCompleto() + "?")
                    .setPositiveButton("Eliminar", (dialog, which) -> eliminarEmpleado(empleado))
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        cardView.setOnClickListener(v -> editarEmpleado(empleado));

        return cardView;
    }

    private void editarEmpleado(EmpleadoResponse empleado) {
        Intent intent = new Intent(this, RegistroEmpleadoActivity.class);
        intent.putExtra("empleado_id", empleado.getId_empleado());
        intent.putExtra("modo_edicion", true);
        startActivity(intent);
    }

    private void eliminarEmpleado(EmpleadoResponse empleado) {
        Toast.makeText(this, "Eliminando empleado...", Toast.LENGTH_SHORT).show();

        ApiClient.getApiService().eliminarEmpleado(empleado.getId_empleado())
                .enqueue(new Callback<ApiService.ApiResponse<EmpleadoResponse>>() {
                    @Override
                    public void onResponse(Call<ApiService.ApiResponse<EmpleadoResponse>> call, Response<ApiService.ApiResponse<EmpleadoResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(EmpleadosActivity.this,
                                    "Empleado eliminado: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            cargarEmpleados();
                        } else {
                            Toast.makeText(EmpleadosActivity.this, "Error al eliminar empleado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiService.ApiResponse<EmpleadoResponse>> call, Throwable t) {
                        Toast.makeText(EmpleadosActivity.this, "Error de conexión al eliminar", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarEmpleados();
    }
}