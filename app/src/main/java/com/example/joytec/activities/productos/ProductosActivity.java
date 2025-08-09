package com.example.joytec.activities.productos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joytec.R;
import com.example.joytec.models.ProductoResponse;
import com.example.joytec.network.ApiClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProductos;
    private FloatingActionButton fabAgregarProducto;
    private List<ProductoResponse> productos;
    private ProductosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_productos);

        initViews();
        setupRecyclerView();
        setupClickListeners();
        cargarProductos();
    }

    private void initViews() {
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos);
        fabAgregarProducto = findViewById(R.id.fab_agregar_producto);
        productos = new ArrayList<>();
    }

    private void setupRecyclerView() {
        adapter = new ProductosAdapter(productos);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProductos.setAdapter(adapter);
    }

    private void setupClickListeners() {
        fabAgregarProducto.setOnClickListener(v -> {
            Intent intent = new Intent(ProductosActivity.this, RegistroProductoActivity.class);
            startActivity(intent);
        });
    }

    private void cargarProductos() {
        Toast.makeText(this, "Cargando productos...", Toast.LENGTH_SHORT).show();

        ApiClient.getApiService().getProductos().enqueue(new Callback<List<ProductoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductoResponse>> call, Response<List<ProductoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productos.clear();
                    productos.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    Toast.makeText(ProductosActivity.this,
                            "Productos cargados: " + productos.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductosActivity.this,
                            "Error al cargar productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductoResponse>> call, Throwable t) {
                Toast.makeText(ProductosActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarProductos();
    }

    // Adapter interno
    private class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder> {
        private List<ProductoResponse> productos;

        public ProductosAdapter(List<ProductoResponse> productos) {
            this.productos = productos;
        }

        @Override
        public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item_producto, parent, false);
            return new ProductoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductoViewHolder holder, int position) {
            ProductoResponse producto = productos.get(position);
            holder.bind(producto);
        }

        @Override
        public int getItemCount() {
            return productos.size();
        }

        class ProductoViewHolder extends RecyclerView.ViewHolder {
            TextView tvNombre, tvPrecio;
            Button btnEditar, btnEliminar;

            public ProductoViewHolder(View itemView) {
                super(itemView);
                tvNombre = itemView.findViewById(R.id.tvNombreProducto);
                tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
                btnEditar = itemView.findViewById(R.id.btnEditar);
                btnEliminar = itemView.findViewById(R.id.btnEliminar);
            }

            public void bind(ProductoResponse producto) {
                tvNombre.setText(producto.getNombre());
                tvPrecio.setText("$" + producto.getPrecio());

                btnEditar.setOnClickListener(v -> {
                    Intent intent = new Intent(ProductosActivity.this, RegistroProductoActivity.class);
                    intent.putExtra("producto_id", producto.getId_producto());
                    intent.putExtra("modo_edicion", true);
                    startActivity(intent);
                });

                btnEliminar.setOnClickListener(v -> {
                    new AlertDialog.Builder(ProductosActivity.this)
                            .setTitle("Eliminar Producto")
                            .setMessage("¿Eliminar " + producto.getNombre() + "?")
                            .setPositiveButton("Eliminar", (dialog, which) -> eliminarProducto(producto))
                            .setNegativeButton("Cancelar", null)
                            .show();
                });

                itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(ProductosActivity.this, RegistroProductoActivity.class);
                    intent.putExtra("producto_id", producto.getId_producto());
                    intent.putExtra("modo_edicion", true);
                    startActivity(intent);
                });
            }
        }
    }

    private void eliminarProducto(ProductoResponse producto) {
        ApiClient.getApiService().eliminarProducto(producto.getId_producto())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ProductosActivity.this,
                                    "Producto eliminado", Toast.LENGTH_SHORT).show();
                            cargarProductos();
                        } else {
                            Toast.makeText(ProductosActivity.this,
                                    "Error al eliminar producto", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ProductosActivity.this,
                                "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}