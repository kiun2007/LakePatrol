package com.kssoft.lake.net.services;

import com.kssoft.lake.data.model.vo.MapStateVo;
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
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

@AutoImport(RetrofitVariableSet.class)
//@Builder(SimulationInterceptor.TAG)
public interface ListService {

    /**
     * 获取附近站点信息.
     * @param pagerBean
     * @return
     */
    @GET("/task/taskStbprp")
    Call<NetListWrapper<AreaStBprp>> getNearbySite(@QueryMap Map<String, Object> pagerBean);

    @GET("/recd/list")
    Call<NetListWrapper<XcRecdR>> getRecordList(@QueryMap RecdDto recdDto);

    @GET("/work/appLakeList")
    Call<NetListWrapper<ReportVo>> getReportList(@QueryMap ReportDto reportDto);

    @GET("/task/taskMapStbprp")
    Call<NetListWrapper<MapStateVo>> getMapTagging(@Query("tm") String tm);

    /**
     * 查询站点当天采样属性
     * @param stcd 站点编码
     * @param tm 采样日期
     * @return 采样样本数据.
     */
    @GET("/task/xcTaskProList")
    Call<NetListWrapper<XcTaskPro>> xcTaskProList(@Query("stcd") String stcd, @Query("tm") String tm, @Query("rdcd") String rdcd, @Query("id") String id);

    /**
     * 获取第三方图例
     * @param tm
     * @return
     */
    @GET("map/mapYcDataList")
    Call<NetListWrapper<Object>> getOtherMapItem(@Query("tm") String tm);
}
