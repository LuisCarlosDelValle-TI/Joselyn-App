package com.example.joytec.models;

public class Producto {

        private int id_producto;
        private String nombre;
        private String descripcion;
        private double precio;
        private int stock;
        private int existencias;
        private int id_categoria;
        private String nombre_categoria;
        private String nombre_material;

        public int getId_producto() {
                return id_producto;
        }

        public void setId_producto(int id_producto) {
                this.id_producto = id_producto;
        }

        public String getNombre() {
                return nombre;
        }

        public void setNombre(String nombre) {
                this.nombre = nombre;
        }

        public String getDescripcion() {
                return descripcion;
        }

        public void setDescripcion(String descripcion) {
                this.descripcion = descripcion;
        }
        public double getPrecio() {
                return precio;
        }

        public void setPrecio(double precio) {
                this.precio = precio;
        }

        public int getStock() {
                return stock;
        }

        public void setStock(int stock) {
                this.stock = stock;
        }

        public int getExistencias() {
                return existencias;
        }

        public void setExistencias(int existencias) {
                this.existencias = existencias;
        }

        public int getId_categoria() {
                return id_categoria;
        }

        public void setId_categoria(int id_categoria) {
                this.id_categoria = id_categoria;
        }

        public String getNombre_categoria() {
                return nombre_categoria;
        }

        public void setNombre_categoria(String nombre_categoria) {
                this.nombre_categoria = nombre_categoria;
        }

        public String getNombre_material() {
                return nombre_material;
        }

        public void setNombre_material(String nombre_material) {
                this.nombre_material = nombre_material;
        }
}