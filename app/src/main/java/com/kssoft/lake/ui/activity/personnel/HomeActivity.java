package com.kssoft.lake.ui.activity.personnel;

import com.kssoft.lake.BR;
import com.kssoft.lake.R;
import com.kssoft.lake.data.model.XcAppM;
import com.kssoft.lake.data.model.XcTaskR;
import com.kssoft.lake.databinding.ActivityHomeBinding;
import com.kssoft.lake.net.requests.dto.TaskDto;
import com.kssoft.lake.net.services.HomeService;
import com.kssoft.lake.net.services.TaskService;
import com.kssoft.lake.net.services.TestService;
import com.kssoft.lake.net.services.UserService;

import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.net.HttpException;
import kiun.com.bvroutine.utils.DateUtil;
import kiun.com.bvroutine.utils.LocationUtils;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.SharedUtil;

/**
 * 文 件 名: Home
 * 作 者: 刘春杰
 * 创建日期: 2020/7/30 20:42
 * 说明: 主页
 */
public class HomeActivity extends RequestBVActivity<ActivityHomeBinding> {

    public static final Class clz = HomeActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        startPermission(LocationUtils.PERMISSION, ()-> {
        });

        //检查地图环境是否支持外网
        rbp.addRequest(()-> rbp.callServiceData(TestService.class, s -> s.mapServer()), v -> {
        }, ex->{
            if (ex instanceof HttpException){
                SharedUtil.saveValue("InnerMap", false);
            }else if (ex instanceof SocketTimeoutException){
                SharedUtil.saveValue("InnerMap", true);
            }
        });

        addRequest(this::getBannerData, binding::setModuleList);
        addRequest(()->rbp.callServiceData(UserService.class, s -> s.getUserInfo()), v -> {
            SharedUtil.saveValue(v.getClass().getName(), v);
        });

        TaskDto taskDto = new TaskDto();
        setVariable(BR.taskDto, taskDto.listener(this::taskDtoListener));
        taskDto.setChoose(new Date());
    }

    public void taskDtoListener(TaskDto taskDto){

        String start = MCString.formatDate("yyyy-MM-dd", DateUtil.addDay(new Date(), -1));
        String end = MCString.formatDate("yyyy-MM-dd", DateUtil.addDay(new Date(), 0));
        addRequest(()->rbp.callServiceList(TaskService.class, s -> s.taskList(start, end, taskDto.getXctp()), null), taskDto::setTaskList);
    }

    private List<XcTaskR> getTaskList(){
        return null;
    }

    private List<XcAppM> getBannerData() throws Exception{
        return rbp.callServiceList(HomeService.class, s -> s.getModeList(), null);
    }
}