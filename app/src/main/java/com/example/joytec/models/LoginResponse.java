package com.example.joytec.models;

public class LoginResponse {
    private String mensaje;
    private Usuario usuario;  // este es otro modelo que tú ya tienes
    private String token;

    // Constructor vacío requerido por Retrofit
    public LoginResponse() {}

    // Getters y setters
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
