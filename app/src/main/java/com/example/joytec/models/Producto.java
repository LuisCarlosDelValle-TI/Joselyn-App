package com.example.joytec.models;

public class Producto {

        private int id_producto;
        private String nombre;
        private double precio;
        private int stock_minimo;
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

        public double getPrecio() {
                return precio;
        }

        public void setPrecio(double precio) {
                this.precio = precio;
        }

        public int getStock_minimo() {
                return stock_minimo;
        }

        public void setStock_minimo(int stock_minimo) {
                this.stock_minimo = stock_minimo;
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