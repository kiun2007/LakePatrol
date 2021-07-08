package com.kssoft.lake.net.services;

import com.kssoft.lake.net.requests.dto.RecdDto;
import com.kssoft.lake.net.requests.dto.XcRecdR;
import com.kssoft.lake.net.requests.dto.XcTrailR;
import com.kssoft.lake.net.responses.NetListWrapper;
import com.kssoft.lake.net.responses.NetWrapper;

import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

@AutoImport(RetrofitVariableSet.class)
//@Builder(SimulationInterceptor.TAG) //配置为虚拟数据,#see AutoImport自动导入服务.
public interface RecordService{

    /**
     * 创建巡测记录.
     * @param xcRecdR 记录。
     * @return 记录ID.
     */
    @POST("/recd/add")
    Call<NetWrapper<XcRecdR>> startRecord(@Body XcRecdR xcRecdR);

    /**
     *
     * @param xcRecdR
     * @return
     */
    @PUT("/recd/edit")
    Call<NetWrapper<Integer>> editRecord(@Body XcRecdR xcRecdR);

    /**
     * 添加轨迹记录.
     * @param xcTrailR
     * @return
     */
    @POST("/recd/addTrail")
    Call<NetWrapper<String>> addTrail(@Body XcTrailR xcTrailR);

    /**
     * 获取轨迹记录.
     * @param recdDto
     * @return
     */
    @GET("/recd/appTraillist")
    Call<NetListWrapper<XcTrailR>> getTrailList(@QueryMap RecdDto recdDto);
}
