package com.kssoft.lake;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.kssoft.lake.data.model.XcSnimdtF;
import com.kssoft.lake.net.responses.NetWrapper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;

import kiun.com.bvroutine.base.jexl.proxy.Enhancer;
import kiun.com.bvroutine.base.jexl.proxy.MethodInterceptor;
import kiun.com.bvroutine.base.jexl.proxy.MethodProxy;
import kiun.com.bvroutine.utils.HTTPUtil;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    public class But {
        private String cbc;

        public void setCbc(String cbc) {
            this.cbc = cbc;
        }
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.kssoft.lake", appContext.getPackageName());

        Enhancer enhancer = new Enhancer(appContext);
        enhancer.setSuperclass(But.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object object, Object[] args, MethodProxy methodProxy) throws Exception {
                Object obj = methodProxy.invokeSuper(object, args);
                return obj;
            }
        });
        But test = (But) enhancer.create();
        test.setCbc("fsdf");

        int a = 0;
    }

    @Test
    public void useTest(){
        String json = "{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"searchValue\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"dataScope\":null,\"params\":{},\"fcd\":\"WXB86F243FD2E1\",\"rdcd\":null,\"stcd\":null,\"tm\":null,\"flnm\":\"/profile/hufan/2020/09/11/a00f7f8ba32d086ad0210ef73d8f31eb.png\",\"flsz\":0.0,\"lgtd\":0.0,\"lttd\":0.0,\"flext\":null,\"sbp\":null,\"ftp\":null,\"xctp\":null,\"stnm\":null},\"num\":1}";

        String json2 = "{\"searchValue\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"dataScope\":null,\"params\":{},\"fcd\":\"WXB86F243FD2E1\",\"rdcd\":null,\"stcd\":null,\"tm\":null,\"flnm\":\"/profile/hufan/2020/09/11/a00f7f8ba32d086ad0210ef73d8f31eb.png\",\"flsz\":0.0,\"lgtd\":0.0,\"lttd\":0.0,\"flext\":null,\"sbp\":null,\"ftp\":null,\"xctp\":null,\"stnm\":null}";

        XcSnimdtF xcSnimdtF = JSONObject.parseObject(json2, XcSnimdtF.class);
        Object object = JSONObject.parse(json);
        Type type = new ParameterizedTypeImpl(new Type[]{XcSnimdtF.class}, null, NetWrapper.class);
        NetWrapper netWrapper = JSONObject.parseObject(json, type);

        int a = 0;
    }
}
