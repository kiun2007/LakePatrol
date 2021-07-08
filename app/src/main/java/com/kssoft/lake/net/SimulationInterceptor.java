package com.kssoft.lake.net;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSON;
import com.kssoft.lake.R;
import com.kssoft.lake.net.responses.NetListWrapper;
import com.kssoft.lake.net.responses.NetWrapper;
import com.kssoft.lake.net.responses.vo.AreaStBprp;
import com.kssoft.lake.net.responses.vo.XcLkwqB;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.interfaces.callers.PutVoidCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.JSONUtil;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static okhttp3.Protocol.HTTP_1_1;

public class SimulationInterceptor implements Interceptor {

    public static final String TAG = "SimulationInterceptor";

    private static NetWrapper createData(Object data){
        NetWrapper netWrapper = new NetWrapper();
        netWrapper.setCode("200");
        netWrapper.setData(data);
        return netWrapper;
    }

    private static<T> NetListWrapper<T> createList(Class<T> itemClz, int total, PutVoidCaller<T, Integer> caller){

        NetListWrapper<T> netListWrapper = new NetListWrapper<>();
        netListWrapper.setCode("200");

        List<T> list = new LinkedList<>();
        netListWrapper.setRows(list);
        netListWrapper.setTotal(total);

        for (int i = 0; i < total; i++) {
            try {
                T item = itemClz.newInstance();
                caller.call(item, i);
                list.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return netListWrapper;
    }

    private static Object readJson(int resId) throws IOException {
        InputStream is = ActivityApplication.getApplication().getResources().openRawResource(resId);

        byte[] buff = new byte[is.available()];
        is.read(buff);
        String json = new String(buff);
        return JSON.parse(json);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String url = request.url().url().toString();
        Response response = null;
        Response.Builder builder = new Response.Builder();
        builder.protocol(HTTP_1_1);
        builder.request(chain.request());
        builder.code(200);

        Object object = null;
        if (url.contains("/recd/add")) {
            object = createData("4938891221");
        }else if (url.contains("/task/taskStbprp")){
            object = createList(AreaStBprp.class,5,(item, index)->{
                item.setGap(15.63*index);
                item.setStcd(String.format("00%02d", index+2));
                item.setStnm("模拟数据之虚假站点" + index);
            });
        }else if (url.contains("/base/lkwqList")){
            object = createList(XcLkwqB.class, 1, (item, index)->{
                item.setAirte(-27);
                item.setAirts(5);
                item.setAlgaee(0);
                item.setAlgaes(15);
            });
        }else if (url.contains("/task/xcTaskProList")){
            object = readJson(R.raw.data);
        }

        if (object != null){
            String content = JSONUtil.toJSON(object);
            builder.body(ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), content));
            builder.message("OK");
            response = builder.build();
        }

        return response;
    }
}
