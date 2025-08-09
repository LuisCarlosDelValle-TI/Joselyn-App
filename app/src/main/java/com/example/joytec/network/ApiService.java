package com.example.joytec.network;

import com.example.joytec.models.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // ============ PRODUCTOS ============
    @GET("productos")
    Call<List<ProductoResponse>> getProductos();

    @GET("productos/{id}")
    Call<ProductoResponse> getProductoPorId(@Path("id") int id);

    @POST("productos")
    Call<ProductoResponse> crearProducto(@Body ProductoRequest producto);

    @PUT("productos/{id}")
    Call<ProductoResponse> actualizarProducto(@Path("id") int id, @Body ProductoRequest producto);

    @DELETE("productos/{id}")
    Call<Void> eliminarProducto(@Path("id") int id);
    @GET("productos/{id}")
    Call<ProductoResponse> getProducto(@Path("id") int id);

    @GET("productos/buscar/nombre/{nombre}")
    Call<List<ProductoResponse>> buscarProductosPorNombre(@Path("nombre") String nombre);

    // ============ EMPLEADOS ============
    @GET("empleados")
    Call<List<EmpleadoResponse>> getEmpleados();

    @GET("empleados/{id}")
    Call<EmpleadoResponse> getEmpleadoPorId(@Path("id") int id);

    @POST("empleados")
    Call<ApiResponse<EmpleadoResponse>> crearEmpleado(@Body EmpleadoRequest empleado);

    @PUT("empleados/{id}")
    Call<ApiResponse<EmpleadoResponse>> actualizarEmpleado(@Path("id") int id, @Body EmpleadoRequest empleado);

    @DELETE("empleados/{id}")
    Call<ApiResponse<EmpleadoResponse>> eliminarEmpleado(@Path("id") int id);

    // ============ CATEGORÍAS ============
    @GET("categorias")
    Call<List<CategoriaResponse>> getCategorias();

    // Clase para respuestas con mensaje (como empleados)
    class ApiResponse<T> {
        private String message;
        private T empleado;
        private T data;

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public T getEmpleado() { return empleado; }
        public void setEmpleado(T empleado) { this.empleado = empleado; }
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }
    }
}