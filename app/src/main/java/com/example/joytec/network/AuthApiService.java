package com.example.joytec.api;

import com.example.joytec.models.Usuario;
import com.example.joytec.models.RegistroResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("registro")
    Call<RegistroResponse> registrar(@Body Usuario usuario);
}