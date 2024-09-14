package com.android.consultaruc.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InsertarConsulta {
    @FormUrlEncoded
    @POST("Consultas/guardar")  // Ajusta el endpoint según corresponda
    Call<ResponseBody> guardar(
            @Field("idUsuario") String idUsuario,
            @Field("ipDispositivo") String ipDispositivo
    );
}