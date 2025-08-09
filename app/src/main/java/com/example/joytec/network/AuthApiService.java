package com.example.joytec.network;


import com.example.joytec.models.LoginRequest;
import com.example.joytec.models.LoginResponse;
import com.example.joytec.models.RegistroResponse;
import com.example.joytec.models.RegistroRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("auth/registrar")
    Call<RegistroResponse> registrar(@Body RegistroRequest request);

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}