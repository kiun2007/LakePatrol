package com.kssoft.lake.net.services;

import com.kssoft.lake.data.model.User;
import com.kssoft.lake.net.requests.dto.Password;
import com.kssoft.lake.net.responses.NetWrapper;

import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;

@AutoImport(RetrofitVariableSet.class)
public interface UserService {

    @GET("/system/user/profile")
    Call<NetWrapper<User>> getUserInfo();

    @FormUrlEncoded
    @PUT("/system/user/profile/updatePwd")
    Call<NetWrapper<String>> resetPwd(@Body Password password);
}
