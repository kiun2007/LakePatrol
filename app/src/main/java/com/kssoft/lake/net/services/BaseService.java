package com.kssoft.lake.net.services;

import com.kssoft.lake.data.model.XcSnimdtF;
import com.kssoft.lake.net.requests.dto.SnimdtDto;
import com.kssoft.lake.net.responses.NetListWrapper;
import com.kssoft.lake.net.responses.NetWrapper;
import com.kssoft.lake.net.responses.vo.XcLkwqB;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

@AutoImport(RetrofitVariableSet.class)
//@Builder("Test")
public interface BaseService {

    /**
     * 查询水文和人工观测预警指标
     * @param slcd 巡测线编码
     * @param stcd 测站编码
     * @return 指标结果.
     */
    @GET("/base/lkwqList")
    Call<NetListWrapper<XcLkwqB>> getLakeLimit(@Query("slcd") String slcd, @Query("stcd") String stcd);

    /**
     * 巡测时多媒体上传 包括图片和视频、音频
     * @param snimdtDto
     * @return
     */
    @POST("/mdt/upload")
    @Multipart
    Call<NetWrapper<XcSnimdtF>> uploadFile(@Part MultipartBody.Part fileUpload, @Part MultipartBody.Part thumbFileUpload, @PartMap SnimdtDto snimdtDto);
}
