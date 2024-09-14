package com.android.consultaruc.entidad;

public class Historial {
    private int idConsulta;
    private String documentoConsulta;
    private String razonSocialRes;
    private String documentoRes;
    private String fecha;
    private String latitud;
    private String longitud;

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getDocumentoConsulta() {
        return documentoConsulta;
    }

    public void setDocumentoConsulta(String documentoConsulta) {
        this.documentoConsulta = documentoConsulta;
    }

    public String getRazonSocialRes() {
        return razonSocialRes;
    }

    public void setRazonSocialRes(String razonSocialRes) {
        this.razonSocialRes = razonSocialRes;
    }

    public String getDocumentoRes() {
        return documentoRes;
    }

    public void setDocumentoRes(String documentoRes) {
        this.documentoRes = documentoRes;
    }
}
