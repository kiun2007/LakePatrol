package com.kssoft.lake.net.services;


import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.net.Builder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

@AutoImport(RetrofitVariableSet.class)
//此配置将跳过验证机制.
@Builder("PublicMain")
public interface DownloadService {

    /**
     * 从服务器下载文件.
     * @param url 不包含host port的路径.
     * @return
     */
    @GET
    Call<ResponseBody> downloadFile(@Url String url);

    static GetCaller<String, Call<ResponseBody>> download(DownloadService dataService){
        return dataService::downloadFile;
    }
}