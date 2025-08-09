package com.example.joytec.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joytec.R;
import com.example.joytec.adapters.ProductoAdapter;
import com.example.joytec.network.ApiService;
import com.example.joytec.network.RetrofitClient;
import com.example.joytec.models.ProductoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListProductosFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductoAdapter adapter;
    private ApiService apiService;

    public ItemListProductosFragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_item_list_productos, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        cargarProductos();

        return view;
    }

    private void cargarProductos() {
        apiService.getProductos().enqueue(new Callback<List<ProductoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductoResponse>> call, Response<List<ProductoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new ProductoAdapter(getContext(), response.body()); // Agregar context
                    adapter.setOnProductoClickListener(new ProductoAdapter.OnProductoClickListener() {
                        @Override
                        public void onEditarClick(ProductoResponse producto) {
                            Toast.makeText(getContext(), "Editar: " + producto.getNombre(), Toast.LENGTH_SHORT).show();
                            // Aquí podrías abrir la actividad de edición
                        }

                        @Override
                        public void onEliminarClick(ProductoResponse producto) {
                            eliminarProducto(producto.getId_producto());
                        }

                        @Override
                        public void onDetallesClick(ProductoResponse producto) {
                            Toast.makeText(getContext(), "Detalles de: " + producto.getNombre(), Toast.LENGTH_SHORT).show();
                            // Aquí podrías abrir un fragmento o actividad con los detalles completos
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductoResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminarProducto(int idProducto) {
        apiService.eliminarProducto(idProducto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                    cargarProductos(); // Recarga la lista
                } else {
                    Toast.makeText(getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo al eliminar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
