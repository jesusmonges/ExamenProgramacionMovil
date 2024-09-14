package com.android.consultaruc.interfaces;

import com.android.consultaruc.models.CantConsultas;
import com.android.consultaruc.models.ConsultaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VerificarConsultas {
    @GET("Consultas/verificar/7788316Me0YD5d/1/1")
    Call<CantConsultas> find(
            @Query("ipDispositivo") String ipDispositivo,
            @Query("fecha") String fecha
    );

}
