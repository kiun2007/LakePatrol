package com.kssoft.lake.net.services;

import com.kssoft.lake.data.model.XcAppM;
import com.kssoft.lake.data.model.XcAppPic;
import com.kssoft.lake.net.responses.NetListWrapper;

import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@AutoImport(RetrofitVariableSet.class)
public interface HomeService {

    @GET("/app/picList")
    Call<NetListWrapper<XcAppPic>> getBannerImages(@Query("key") String key);

    @GET("/app/mList")
    Call<NetListWrapper<XcAppM>> getModeList();
}
