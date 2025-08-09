package com.example.joytec.repository;
import com.example.joytec.network.ProductoService;
import retrofit2.Call;
import java.util.List;
import com.example.joytec.models.ProductosResponse;
import com.example.joytec.models.ProductoRequest;
import com.example.joytec.network.RetrofitClient;

public class ProductoRepository {
    private ProductoService productoService;

    public ProductoRepository() {
        productoService = RetrofitClient.getRetrofitInstance().create(ProductoService.class);
    }

    public Call<List<ProductosResponse>> obtenerProductos() {
        return productoService.obtenerProductos();
    }

    public Call<ProductosResponse> registrarProducto(ProductoRequest request) {
        return productoService.registrarProducto(request);
    }

    public Call<Void> eliminarProducto(int id) {
        return productoService.eliminarProducto(id);
    }
}
