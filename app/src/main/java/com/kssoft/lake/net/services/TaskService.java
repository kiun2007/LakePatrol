package com.kssoft.lake.net.services;

import com.kssoft.lake.data.model.XcTaskR;
import com.kssoft.lake.data.model.vo.MapStateVo;
import com.kssoft.lake.data.model.vo.TaskTotalVo;
import com.kssoft.lake.net.responses.NetListWrapper;
import com.kssoft.lake.net.responses.NetWrapper;
import com.kssoft.lake.net.responses.vo.TreeVo;

import java.util.List;

import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 巡测任务制定接口 服务层
 */
@AutoImport(RetrofitVariableSet.class)
public interface TaskService {

    /**
     * 查询巡测任务
     * @param stm 开始日期格式字符 yyyy-MM-dd.
     * @param etm 结束日期格式字符 yyyy-MM-dd.
     * @return
     */
    @GET("/task/list")
    Call<NetListWrapper<XcTaskR>> taskList(@Query("stm") String stm, @Query("etm") String etm, @Query("xctp") String xctp);

    @GET("task/userTree")
    Call<NetWrapper<List<TreeVo>>> userTree();

    /**
     * 新增巡测任务
     * @param xcTaskR
     * @return
     */
    @POST("/task/add")
    Call<NetWrapper<Integer>> addTask(@Body XcTaskR xcTaskR);
    
}
