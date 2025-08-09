package com.example.joytec.network;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Body;
import com.example.joytec.models.ProductosResponse;
import com.example.joytec.models.ProductoRequest;
import java.util.Map;
import retrofit2.http.PATCH;


public interface ProductoService {
    @GET("api/productos")
    Call<List<ProductosResponse>> obtenerProductos();

    @GET("api/productos/{id}")
    Call<ProductosResponse> obtenerProductoPorId(@Path("id") int id);

    @GET("api/productos/categoria/{categoriaId}")
    Call<List<ProductosResponse>> obtenerPorCategoria(@Path("categoriaId") int categoriaId);

    @GET("api/productos/buscar/nombre/{nombre}")
    Call<List<ProductosResponse>> buscarPorNombre(@Path("nombre") String nombre);

    @POST("api/productos")
    Call<ProductosResponse> registrarProducto(@Body ProductoRequest producto);

    @PUT("api/productos/{id}")
    Call<ProductosResponse> actualizarProducto(@Path("id") int id, @Body ProductoRequest producto);

    @DELETE("api/productos/{id}")
    Call<Void> eliminarProducto(@Path("id") int id);

    @PATCH("api/productos/{id}/stock")
    Call<Void> actualizarStock(@Path("id") int id, @Body Map<String, Integer> stockBody);

    @GET("api/productos/stock/bajo")
    Call<List<ProductosResponse>> obtenerStockBajo();

    @POST("api/productos/{id}/ajuste-stock")
    Call<Void> ajustarStock(@Path("id") int id, @Body Map<String, Integer> ajusteBody);
}
