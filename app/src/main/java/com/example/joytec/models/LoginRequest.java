package com.example.joytec.models;

public class LoginRequest {
    private String nombre_usuario;
    private String contraseña;

    public LoginRequest(String nombre_usuario, String contraseña) {
        this.nombre_usuario = nombre_usuario;
        this.contraseña = contraseña;
    }

    // Getters y Setters
    public String getNombre_usuario() { return nombre_usuario; }
    public void setNombre_usuario(String nombre_usuario) { this.nombre_usuario = nombre_usuario; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
}