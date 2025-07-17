package com.example.joytec.api;

import com.example.joytec.models.Usuario;
import com.example.joytec.models.LoginRequest;
import com.example.joytec.models.LoginResponse;
import com.example.joytec.models.RegistroResponse;
import com.example.joytec.models.RegistroRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("auth/registrar")  // Ruta para registro
    Call<RegistroResponse> registrar(@Body RegistroRequest request);

    @POST("auth/login")      // Ruta para login
    Call<LoginResponse> login(@Body LoginRequest request);
}