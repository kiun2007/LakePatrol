package com.kssoft.lake.net.services;

import com.kssoft.lake.data.model.vo.MapStateVo;
import com.kssoft.lake.net.SimulationInterceptor;
import com.kssoft.lake.net.requests.dto.RecdDto;
import com.kssoft.lake.net.requests.dto.ReportDto;
import com.kssoft.lake.net.requests.dto.XcRecdR;
import com.kssoft.lake.net.responses.NetListWrapper;
import com.kssoft.lake.net.responses.vo.AreaStBprp;
import com.kssoft.lake.net.responses.vo.ReportVo;
import com.kssoft.lake.net.responses.vo.XcTaskPro;

import java.util.Map;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.net.Builder;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

@AutoImport(RetrofitVariableSet.class)
@Builder(SimulationInterceptor.TAG)
public interface SimulationListService {

    @GET("/task/taskStbprp")
    Call<NetListWrapper<AreaStBprp>> getNearbySite(@QueryMap Map<String, Object> pagerBean);

    @GET("/recd/list")
    Call<NetListWrapper<XcRecdR>> getRecordList(@QueryMap RecdDto recdDto);

    @GET("/work/appLakeList")
    Call<NetListWrapper<ReportVo>> getReportList(@QueryMap ReportDto reportDto);

    @GET("/task/taskMapStbprp")
    Call<NetListWrapper<MapStateVo>> getMapTagging(@Query("tm") String tm);

    @GET("/task/xcTaskProList")
    Call<NetListWrapper<XcTaskPro>> xcTaskProList(@Query("stcd") String stcd, @Query("tm") String tm);

    /**
     * 获取第三方图例
     * @param tm
     * @return
     */
    @GET("map/mapYcDataList")
    Call<NetListWrapper<Object>> getOtherMapItem(@Query("tm") String tm);
}
