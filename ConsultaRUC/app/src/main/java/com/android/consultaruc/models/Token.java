package com.android.consultaruc.models;

public class Token {
    private String idUsuario;
    private String token;

    public Token(String idUsuario, String token) {
        this.idUsuario = idUsuario;
        this.token = token;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}