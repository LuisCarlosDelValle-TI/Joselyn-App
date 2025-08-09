package com.example.joytec.models;

public class LoginRequest {
    private String nombre_usuario;
    private String password;

    public LoginRequest(String nombre_usuario, String password) {
        this.nombre_usuario = nombre_usuario;
        this.password = password;
    }


    public String getNombre_usuario() { return nombre_usuario; }
    public void setNombre_usuario(String nombre_usuario) { this.nombre_usuario = nombre_usuario; }

    public String getpassword() { return password; }
    public void setpassword(String password) { this.password = password; }
}