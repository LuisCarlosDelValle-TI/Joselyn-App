package com.example.joytec.activities.productos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.joytec.R;
import com.example.joytec.models.ProductoRequest;
import com.example.joytec.models.ProductoResponse;
import com.example.joytec.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroProductoActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private EditText etNombre, etPrecio, etStockMin, etExistencias;
    private Spinner spinnerMaterial, spinnerCategoria;
    private Button btnGuardar;

    private boolean modoEdicion = false;
    private int productoId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_producto);

        initViews();
        checkEditMode();
        setupClickListeners();
    }

    private void initViews() {
        tvTitulo = findViewById(R.id.tvTituloRegistrarProductos);
        etNombre = findViewById(R.id.etNombre);
        etPrecio = findViewById(R.id.etPrecio);
        etStockMin = findViewById(R.id.etStockMin);
        etExistencias = findViewById(R.id.etExistencias);
        spinnerMaterial = findViewById(R.id.spinnerMaterial);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnGuardar = findViewById(R.id.btnGuardar);
    }

    private void checkEditMode() {
        modoEdicion = getIntent().getBooleanExtra("modo_edicion", false);
        productoId = getIntent().getIntExtra("producto_id", 0);

        if (modoEdicion && productoId > 0) {
            tvTitulo.setText("Editar Producto");
            btnGuardar.setText("Actualizar Producto");
            cargarDatosProducto();
        }
    }

    private void setupClickListeners() {
        btnGuardar.setOnClickListener(v -> {
            if (validarCampos()) {
                if (modoEdicion) {
                    actualizarProducto();
                } else {
                    crearProducto();
                }
            }
        });
    }

    private boolean validarCampos() {
        if (etNombre.getText().toString().trim().isEmpty()) {
            etNombre.setError("El nombre es requerido");
            return false;
        }
        if (etPrecio.getText().toString().trim().isEmpty()) {
            etPrecio.setError("El precio es requerido");
            return false;
        }
        return true;
    }

    private ProductoRequest crearProductoRequest() {
        ProductoRequest request = new ProductoRequest();
        request.setNombre(etNombre.getText().toString().trim());
        request.setDescripcion(spinnerMaterial.getSelectedItem().toString()); // Usar material como descripción
        request.setPrecio(Double.parseDouble(etPrecio.getText().toString().trim()));
        request.setStock(Integer.parseInt(etExistencias.getText().toString().trim()));
        request.setId_categoria(1); // Por defecto
        return request;
    }

    private void crearProducto() {
        ProductoRequest request = crearProductoRequest();

        ApiClient.getApiService().crearProducto(request).enqueue(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistroProductoActivity.this,
                            "Producto creado exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegistroProductoActivity.this,
                            "Error al crear producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductoResponse> call, Throwable t) {
                Toast.makeText(RegistroProductoActivity.this,
                        "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarProducto() {
        ProductoRequest request = crearProductoRequest();

        ApiClient.getApiService().actualizarProducto(productoId, request)
                .enqueue(new Callback<ProductoResponse>() {
                    @Override
                    public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegistroProductoActivity.this,
                                    "Producto actualizado exitosamente", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegistroProductoActivity.this,
                                    "Error al actualizar producto", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductoResponse> call, Throwable t) {
                        Toast.makeText(RegistroProductoActivity.this,
                                "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cargarDatosProducto() {
        ApiClient.getApiService().getProducto(productoId).enqueue(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductoResponse producto = response.body();
                    etNombre.setText(producto.getNombre());
                    etPrecio.setText(String.valueOf(producto.getPrecio()));
                    etExistencias.setText(String.valueOf(producto.getStock()));
                }
            }

            @Override
            public void onFailure(Call<ProductoResponse> call, Throwable t) {
                Toast.makeText(RegistroProductoActivity.this,
                        "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}