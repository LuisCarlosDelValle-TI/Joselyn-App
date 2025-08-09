package com.example.joytec.repository;

import com.example.joytec.models.Usuario;
import com.example.joytec.models.RegistroRequest;
import android.content.Context;
import android.content.SharedPreferences;
import com.example.joytec.models.LoginResponse;
import com.example.joytec.network.AuthApiService;
import com.example.joytec.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.joytec.models.RegistroResponse;

public class AuthRepository {
    private static final String PREFS_NAME = "auth_prefs";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ROL = "user_rol";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences sharedPreferences;

    public AuthRepository(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // AGREGAR ESTE MÉTODO QUE FALTABA
    public void saveUserData(String token, String username, String rol) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_ROL, rol);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    // Interfaz para el callback de login
    public interface LoginCallback {
        void onSuccess(LoginResponse response);
        void onError(String error);
    }

    public void clearUserData() {
        sharedPreferences.edit().clear().apply();
    }

    public void login(String username, String password, LoginCallback callback) {
        // Aquí deberías hacer la llamada a la API, esto es solo un ejemplo simulado:
        if ("admin".equals(username) && "admin".equals(password)) {
            LoginResponse response = new LoginResponse();
            // Configura el objeto response según tu modelo
            callback.onSuccess(response);
        } else {
            callback.onError("Credenciales incorrectas");
        }
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public String getUserRol() {
        return sharedPreferences.getString(KEY_USER_ROL, "");
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, "");
    }

    public interface AuthCallback {
        void onSuccess(String message);
        void onError(String error);
    }

    public void registrar(Usuario usuario, AuthCallback callback) {
        AuthApiService apiService = ApiClient.getClient().create(AuthApiService.class);

        // Convertimos Usuario a RegistroRequest
        RegistroRequest request = new RegistroRequest(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getCorreo(),
                null
        );

        Call<RegistroResponse> call = apiService.registrar(request);

        call.enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Error en el registro");
                }
            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}