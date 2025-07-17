package com.example.joytec.network;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;
import com.example.joytec.api.AuthApiService;

public class NetworkConfig {
    // Cambia esta IP por la IP de tu servidor
    private static final String BASE_URL = "http://192.168.1.100:3001/api/";

    private static Retrofit retrofit;
    private static AuthApiService authApiService;

    public static AuthApiService getAuthApiService() {
        if (authApiService == null) {
            authApiService = getRetrofitInstance().create(AuthApiService.class);
        }
        return authApiService;
    }

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Interceptor para logging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Cliente OkHttp
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            // Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl("192.168.1.52:3001/api/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}