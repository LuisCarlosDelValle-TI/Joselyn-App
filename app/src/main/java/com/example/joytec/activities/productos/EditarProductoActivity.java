package com.example.joytec.activities.productos;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joytec.R;
import com.example.joytec.models.Categoria;
import com.example.joytec.models.Producto;
import com.example.joytec.network.ApiClient;
import com.example.joytec.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarProductoActivity extends AppCompatActivity {

    private EditText etNombre, etDescripcion, etPrecio, etStock;
    private Spinner spnCategoria;
    private Button btnActualizar;
    private ApiService apiService;
    private int productoId;

    private List<Categoria> categoriasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        apiService = ApiClient.getApiService();

        etNombre = findViewById(R.id.etNombre);
        etPrecio = findViewById(R.id.etPrecio);
        etStock = findViewById(R.id.etStock);
        spnCategoria = findViewById(R.id.spnCategoria);
        btnActualizar = findViewById(R.id.btnActualizar);

        cargarSpinnersConDatosEstaticos();

        productoId = getIntent().getIntExtra("PRODUCTO_ID", -1);
        if (productoId != -1) {
            cargarDatosProducto(productoId);
        } else {
            Toast.makeText(this, "Error: No se encontró el ID del producto", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnActualizar.setOnClickListener(v -> guardarCambios());
    }

    private void cargarSpinnersConDatosEstaticos() {
        categoriasList.add(new Categoria(1, "Anillo"));
        categoriasList.add(new Categoria(2, "Pulsera"));
        categoriasList.add(new Categoria(3, "Collar"));
        categoriasList.add(new Categoria(4, "Arete"));

        List<String> nombresCategorias = new ArrayList<>();
        for (Categoria categoria : categoriasList) {
            nombresCategorias.add(categoria.getNombre_categoria());
        }
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombresCategorias);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoria.setAdapter(adapterCategorias);
    }

    private void cargarDatosProducto(int id) {
        apiService.getProductoById(id).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Producto producto = response.body();

                    etNombre.setText(producto.getNombre());
                    etPrecio.setText(String.valueOf(producto.getPrecio()));
                    etStock.setText(String.valueOf(producto.getStock()));

                    setCategoriaSpinnerSelection(spnCategoria, categoriasList, producto.getId_categoria());
                } else {
                    Toast.makeText(EditarProductoActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(EditarProductoActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarCambios() {
        Producto productoActualizado = new Producto();
        productoActualizado.setId_producto(productoId);
        productoActualizado.setNombre(etNombre.getText().toString());
        productoActualizado.setDescripcion("");
        productoActualizado.setPrecio(Double.parseDouble(etPrecio.getText().toString()));
        productoActualizado.setStock(Integer.parseInt(etStock.getText().toString()));

        int selectedCategoriaPosition = spnCategoria.getSelectedItemPosition();
        productoActualizado.setId_categoria(categoriasList.get(selectedCategoriaPosition).getId_categoria());

        apiService.actualizarProducto(productoId, productoActualizado).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarProductoActivity.this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditarProductoActivity.this, "Error al actualizar: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(EditarProductoActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCategoriaSpinnerSelection(Spinner spinner, List<Categoria> list, int idCategoria) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId_categoria() == idCategoria) {
                spinner.setSelection(i);
                return;
            }
        }
    }
}