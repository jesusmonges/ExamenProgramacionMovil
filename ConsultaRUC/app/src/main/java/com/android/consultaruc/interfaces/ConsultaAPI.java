package com.android.consultaruc.interfaces;

import com.android.consultaruc.models.ConsultaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConsultaAPI {
    @GET("consulta/7788316Me0YD5d/{documento}")
    Call<ConsultaResponse> find(@Path("documento") String documento);
}

