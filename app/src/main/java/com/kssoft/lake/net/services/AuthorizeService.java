package com.kssoft.lake.net.services;

import com.kssoft.lake.net.requests.dto.LoginDto;
import com.kssoft.lake.net.responses.vo.LoginVO;

import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@AutoImport(RetrofitVariableSet.class)
public interface AuthorizeService {

    @POST("/mobileLogin")
    @Headers({"login:.AppLogin"})
    Call<LoginVO> login(@Body LoginDto loginDto);
}