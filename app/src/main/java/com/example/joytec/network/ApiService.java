package com.example.joytec.network;

import com.example.joytec.models.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiService {
    @GET("api/productos")
    Call<List<Producto>> getProductos();

    @DELETE("api/productos/{id_producto}")
    Call<Void> deleteProducto(@Path("id_producto") int idProducto);
    @GET("/api/categorias")
    Call<List<Categoria>> getCategorias();

    @POST("/api/productos")
    Call<Producto> crearProducto(@Body Producto producto);

    @GET("/api/productos/{id}")
    Call<Producto> getProductoById(@Path("id") int id);

    @PUT("/api/productos/{id}")
    Call<Producto> actualizarProducto(@Path("id") int id, @Body Producto producto);
}