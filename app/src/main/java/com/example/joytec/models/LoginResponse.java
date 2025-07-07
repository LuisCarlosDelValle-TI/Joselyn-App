package com.example.joytec.models;

public class LoginResponse {
    private String status;
    private String token;
    private UserData data;

    public String getStatus() { return status; }
    public String getToken() { return token; }
    public UserData getData() { return data; }

    public static class UserData {
        private UserInfo usuario;
        public UserInfo getUsuario() { return usuario; }
    }

    public static class UserInfo {
        private int id;
        private String username;
        private String rol;

        public int getId() { return id; }
        public String getUsername() { return username; }
        public String getRol() { return rol; }
    }
}