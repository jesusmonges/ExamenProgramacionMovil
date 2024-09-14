package com.android.consultaruc.models;

public class Datos {
    private String TIPO;
    private String DOCUMENTO;
    private String RAZON_SOCIAL;

    // Getter y Setter para 'TIPO'
    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    // Getter y Setter para 'DOCUMENTO'
    public String getDOCUMENTO() {
        return DOCUMENTO;
    }

    public void setDOCUMENTO(String DOCUMENTO) {
        this.DOCUMENTO = DOCUMENTO;
    }

    // Getter y Setter para 'RAZON_SOCIAL'
    public String getRAZON_SOCIAL() {
        return RAZON_SOCIAL;
    }

    public void setRAZON_SOCIAL(String RAZON_SOCIAL) {
        this.RAZON_SOCIAL = RAZON_SOCIAL;
    }

    public Datos(String TIPO, String DOCUMENTO, String RAZON_SOCIAL) {
        this.TIPO = TIPO;
        this.DOCUMENTO = DOCUMENTO;
        this.RAZON_SOCIAL = RAZON_SOCIAL;
    }
}
