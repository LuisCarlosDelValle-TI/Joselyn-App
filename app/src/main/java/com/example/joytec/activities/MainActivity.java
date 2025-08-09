package com.example.joytec.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.joytec.R;
import com.example.joytec.adapters.ProductoAdapter;
import com.example.joytec.models.Producto;
import com.example.joytec.network.ApiClient;
import com.example.joytec.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ProductoAdapter.OnItemClickListener { // 1. Implementa la interfaz

    private RecyclerView recyclerViewProductos;
    private ProductoAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Asegúrate de que este es el layout correcto

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewProductos = findViewById(R.id.recyclerViewProductos);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));

        apiService = ApiClient.getApiService();

        fetchProductos();
    }

    private void fetchProductos() {
        Call<List<Producto>> call = apiService.getProductos();

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Producto> productosList = response.body();
                    // 2. Pasa 'this' como el OnItemClickListener
                    adapter = new ProductoAdapter(productosList, MainActivity.this);
                    recyclerViewProductos.setAdapter(adapter);
                } else {
                    String errorMsg = "Error en la respuesta: " + response.message();
                    Log.e("API_CALL_ERROR", errorMsg);
                    Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                String errorMsg = "Error de conexión: " + t.getMessage();
                Log.e("API_CALL_FAILURE", errorMsg);
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 3. Implementa el método de la interfaz para manejar el clic en eliminar
    @Override
    public void onEliminarClick(Producto producto) {
        // Por ahora, solo muestra un mensaje de confirmación
        Toast.makeText(this, "Eliminar producto: " + producto.getNombre(), Toast.LENGTH_SHORT).show();
        // Aquí debes agregar la lógica para llamar a la API y eliminar el producto
        // como te mostré en la respuesta anterior para ProductosActivity.
    }
}