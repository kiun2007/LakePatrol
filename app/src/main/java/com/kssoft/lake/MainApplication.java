package com.kssoft.lake;

import android.content.Intent;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.kssoft.lake.base.jexl.ClassChecker;
import com.kssoft.lake.base.jexl.NumberChecker;
import com.kssoft.lake.base.jexl.SqlUtil;
import com.kssoft.lake.net.SimulationInterceptor;
import com.kssoft.lake.net.cacheline.CacheLineInterceptor;
import com.kssoft.lake.net.cacheline.settings.MainCacheLine;
import com.kssoft.lake.services.TrailService;
import com.kssoft.lake.view.FlowRadioGroup;
import com.kssoft.lake.view.value.EditTextLimitBindConvert;
import com.kssoft.lake.view.value.RadioGroupLimitBindConvert;
import com.province.libcacheline.CacheSettings;
import com.province.libcacheline.utils.JexlUtil;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.JsContext;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.SearchPackage;
import kiun.com.bvroutine.base.binding.BindConvertBridge;
import kiun.com.bvroutine.base.binding.BindConvertImport;
import kiun.com.bvroutine.base.binding.value.RadioGroupBindConvert;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.SharedUtil;

import static kiun.com.bvroutine.base.jexl.RuntimeContext.runTime;
import static kiun.com.bvroutine.net.ServiceGenerator.MAIN;

@BindConvertImport("com.kssoft.lake.view.value")
@SearchPackage({"com.province.libcacheline.body"})
public class MainApplication extends ActivityApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG){
            //Debug动态服务器地址
            ServiceGenerator.putBuild(new CacheLineInterceptor(), SharedUtil.getValue("service", BuildConfig.Prefix), MAIN);
        }else{
            //配置主服务构建器地址前缀.
            ServiceGenerator.putBuild(new CacheLineInterceptor(), BuildConfig.Prefix, MAIN);
        }

        //配置无需登陆的服务器地址
        ServiceGenerator.putBuild(BuildConfig.Prefix, "PublicMain");
        //配置虚拟数据.
        ServiceGenerator.putBuild(new SimulationInterceptor(), BuildConfig.Prefix, SimulationInterceptor.TAG);
        //配置测试服务器.
        ServiceGenerator.putBuild(String.format("http://%s", BuildConfig.Local), "Test");

        //测试地图使用外网还是内网服务器
        ServiceGenerator.putBuild(null,"http://218.94.6.92:6080/", "MapTest", null, builder -> {
            // 连接超时时间
            builder.connectTimeout(5, TimeUnit.SECONDS);
            // 连接写入超时时间
            builder.writeTimeout(5, TimeUnit.SECONDS);
            // 链接读取超时时间
            builder.readTimeout(5, TimeUnit.SECONDS);
        });

        //配置列表默认空白页面提示和布局.
        ListHandler.configNormal().empty("找不到数据").loading("正在为您加载数据...").layout(R.layout.list_error_normal);

        //配置运行时验证工具类.
        runTime().set("numberChecker", new NumberChecker());
        runTime().set("classChecker", new ClassChecker());

        //配置转化包.
        BindConvertBridge.set(FlowRadioGroup.class, RadioGroupLimitBindConvert.class);
        BindConvertBridge.set(AppCompatEditText.class, EditTextLimitBindConvert.class);

        startService(new Intent(this, TrailService.class));

        CacheSettings.initSettings(getApplicationContext(), "", new MainCacheLine());

        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        QbSdk.initX5Environment(this,null);

        //Jexl配置
        JexlUtil.addUtilObject("sqlUtil", new SqlUtil());
    }
}
