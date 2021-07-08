package com.kssoft.lake.net.services;

import com.kssoft.lake.data.model.vo.DatVersion;
import com.kssoft.lake.net.responses.NetWrapper;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.net.Builder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

@AutoImport(RetrofitVariableSet.class)
@Builder("PublicMain")
public interface VersionService {

    /**
     * 获取云端版本ID.
     * @param packageName
     * @param sdkVersion
     * @param versionCode
     * @return
     */
    @FormUrlEncoded
    @POST("/system/datVersionInfo/version")
    Call<NetWrapper<DatVersion>> getNewVersion(@Field("packageName") String packageName, @Field("sdkVersion") Integer sdkVersion, @Field("versionCode") Integer versionCode);

    /**
     * 下载云端APK文件.
     * @param id apk编号.
     * @param range 断点下载开始位置 bytes={begin}-{end}.
     * @return
     */
    @Streaming
    @GET("/system/datVersionInfo/download/{id}")
    Call<ResponseBody> downloadVersion(@Path("id") String id, @Header("Range") String range);
}
