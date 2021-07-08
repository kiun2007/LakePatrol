package com.kssoft.lake.net.services;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.net.Builder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

@AutoImport(RetrofitVariableSet.class)
@Builder("MapTest")
public interface TestService {

    @GET("arcgis/rest/services/jssl_vector_map/MapServer")
    Call<ResponseBody> mapServer();
}
