package com.example.joytec.models;

public class RegistroRequest {
    private String nombre_usuario;
    private String contraseña;
    private String correo;
    private Integer id_empleado;

    // Constructor
    public RegistroRequest(String nombre_usuario, String contraseña, String correo, Integer id_empleado) {
        this.nombre_usuario = nombre_usuario;
        this.contraseña = contraseña;
        this.correo = correo;
        this.id_empleado = id_empleado;
    }

    // Getters
    public String getNombre_usuario() { return nombre_usuario; }
    public String getContraseña() { return contraseña; }
    public String getCorreo() { return correo; }
    public Integer getId_empleado() { return id_empleado; }

    // Setters
    public void setNombre_usuario(String nombre_usuario) { this.nombre_usuario = nombre_usuario; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setId_empleado(Integer id_empleado) { this.id_empleado = id_empleado; }
}
