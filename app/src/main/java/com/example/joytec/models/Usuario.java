package com.example.joytec.models;

public class Usuario {
    private String username;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String password;
    private String telefono;
    private String rol;

    // Constructor para registro
    public Usuario(String username, String apellidoPaterno, String apellidoMaterno,
                   String correo, String password, String telefono, String rol) {
        this.username = username;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.rol = rol;
    }

    // Getters y setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}