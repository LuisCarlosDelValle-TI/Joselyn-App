package com.example.joytec.activities.productos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.joytec.activities.productos.FormularioProductoActivity;
import com.example.joytec.R;
import com.example.joytec.adapters.ProductoAdapter;
import com.example.joytec.models.Producto;
import com.example.joytec.network.ApiClient;
import com.example.joytec.network.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosActivity extends AppCompatActivity implements ProductoAdapter.OnItemClickListener {

    private RecyclerView recyclerViewProductos;
    private ProductoAdapter adapter;
    private ApiService apiService;
    private FloatingActionButton fabAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewProductos = findViewById(R.id.recyclerViewProductos);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));


        fabAddProduct = findViewById(R.id.fabAddProduct);
        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductosActivity.this, FormularioProductoActivity.class);
                startActivity(intent);
            }
        });

        apiService = ApiClient.getApiService();

        fetchProductos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProductos();
    }

    private void fetchProductos() {
        Call<List<Producto>> call = apiService.getProductos();

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Producto> productosList = response.body();
                    adapter = new ProductoAdapter(productosList, ProductosActivity.this);
                    recyclerViewProductos.setAdapter(adapter);
                } else {
                    String errorMsg = "Error en la respuesta: " + response.message();
                    Log.e("API_CALL_ERROR", errorMsg);
                    Toast.makeText(ProductosActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                String errorMsg = "Error de conexión: " + t.getMessage();
                Log.e("API_CALL_FAILURE", errorMsg);
                Toast.makeText(ProductosActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEliminarClick(Producto producto) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que quieres eliminar el producto " + producto.getNombre() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    eliminarProducto(producto);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void eliminarProducto(Producto producto) {
        Call<Void> call = apiService.deleteProducto(producto.getId_producto());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductosActivity.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();

                    fetchProductos();
                } else {
                    Toast.makeText(ProductosActivity.this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProductosActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}