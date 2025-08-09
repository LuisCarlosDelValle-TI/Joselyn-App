package com.example.joytec.models;

public class EmpleadoRequest {
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String telefono;
    private double salario;


    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido_paterno() { return apellido_paterno; }
    public void setApellido_paterno(String apellido_paterno) { this.apellido_paterno = apellido_paterno; }

    public String getApellido_materno() { return apellido_materno; }
    public void setApellido_materno(String apellido_materno) { this.apellido_materno = apellido_materno; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
}