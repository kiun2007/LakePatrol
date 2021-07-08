package com.kssoft.lake.net.services;

import com.kssoft.lake.data.model.commit.XcLakeR;
import com.kssoft.lake.data.model.commit.XcRiverR;
import com.kssoft.lake.data.model.commit.XcWqnmispR;
import com.kssoft.lake.data.model.commit.XcWrrbR;
import com.kssoft.lake.net.requests.dto.RepCkDto;
import com.kssoft.lake.net.responses.NetWrapper;
import com.kssoft.lake.net.responses.vo.XcTaskPro;

import java.util.List;

import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 填报服务接口.
 */
@AutoImport(RetrofitVariableSet.class)
public interface CommitService {

    /**
     * 湖泛巡查上报数据
     * @param xcLakeR
     * @return
     */
    @POST("/data/addXcLake")
    Call<NetWrapper<Integer>> addXcLake(@Body XcLakeR xcLakeR);

    /**
     * 水文巡查上报数据.
     * @param xcRiverR
     * @return
     */
    @POST("/data/addXcRiver")
    Call<NetWrapper<Integer>> addXcRiver(@Body XcRiverR xcRiverR);

    /**
     * 新增人工观测数据.
     * @param xcWrrbR
     * @return
     */
    @POST("/data/addXcWrrb")
    Call<NetWrapper<Integer>> addXcWrrb(@Body XcWrrbR xcWrrbR);

    /**
     * 新增应急监测数据.
     * @param wqnmispR
     * @return
     */
    @POST("/data/addXcWqnmisp")
    Call<NetWrapper<Integer>> addXcWqnmisp(@Body XcWqnmispR wqnmispR);


    @POST("/work/addReportP")
    Call<NetWrapper<Integer>> addReportPerson(@Body RepCkDto wqnmispR);

    /**
     * 修改上报数据.
     * @param taskProList
     * @return
     */
    @POST("/data/editProData")
    Call<NetWrapper<Integer>> editProData(@Body List<XcTaskPro> taskProList);
}
