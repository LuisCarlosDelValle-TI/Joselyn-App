package com.example.joytec.network;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Body;
import com.example.joytec.models.ProductoResponse;
import com.example.joytec.models.ProductoRequest;
import java.util.Map;
import retrofit2.http.PATCH;


public interface ProductoService {
    @GET("api/productos")
    Call<List<ProductoResponse>> obtenerProductos();

    @GET("api/productos/{id}")
    Call<ProductoResponse> obtenerProductoPorId(@Path("id") int id);

    @GET("api/productos/categoria/{categoriaId}")
    Call<List<ProductoResponse>> obtenerPorCategoria(@Path("categoriaId") int categoriaId);

    @GET("api/productos/buscar/nombre/{nombre}")
    Call<List<ProductoResponse>> buscarPorNombre(@Path("nombre") String nombre);

    @POST("api/productos")
    Call<ProductoResponse> registrarProducto(@Body ProductoRequest producto);

    @PUT("api/productos/{id}")
    Call<ProductoResponse> actualizarProducto(@Path("id") int id, @Body ProductoRequest producto);

    @DELETE("api/productos/{id}")
    Call<Void> eliminarProducto(@Path("id") int id);

    @PATCH("api/productos/{id}/stock")
    Call<Void> actualizarStock(@Path("id") int id, @Body Map<String, Integer> stockBody);

    @GET("api/productos/stock/bajo")
    Call<List<ProductoResponse>> obtenerStockBajo();

    @POST("api/productos/{id}/ajuste-stock")
    Call<Void> ajustarStock(@Path("id") int id, @Body Map<String, Integer> ajusteBody);
}
