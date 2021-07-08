package com.kssoft.lake.net.services;

import com.kssoft.lake.data.SamplingBase;
import com.kssoft.lake.net.requests.dto.DataDto;
import com.kssoft.lake.net.responses.NetListWrapper;
import com.kssoft.lake.net.responses.NetWrapper;

import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.interfaces.BeginLoading;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

@AutoImport(RetrofitVariableSet.class)
public interface DataService {

    /**
     * 获取采样数据.
     * @param dataDto
     * @param itemClz
     * @return
     */
    @BeginLoading
    @GET("/data/list")
    Call<NetListWrapper> dataList(@QueryMap DataDto dataDto, @Header("itemClz") String itemClz);

    //修改采样数据
    @PUT("/data/editXcLake")
    Call<NetWrapper<String>> editLakeSampling(@Body SamplingBase samplingBase);
    @PUT("/data/editXcRiver")
    Call<NetWrapper<String>> editRiverSampling(@Body SamplingBase samplingBase);
    @PUT("/data/editXcWrrb")
    Call<NetWrapper<String>> editManualSampling(@Body SamplingBase samplingBase);
    @PUT("/data/editXcWqnmisp")
    Call<NetWrapper<String>> editUrgentSampling(@Body SamplingBase samplingBase);

    /**
     * 从服务器下载文件.
     * @param url 不包含host port的路径.
     * @return
     */
    @GET
    Call<ResponseBody> downloadFile(@Url String url);

    static GetCaller<String, Call<ResponseBody>> download(DataService dataService){
        return dataService::downloadFile;
    }
}