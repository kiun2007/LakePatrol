package com.kssoft.lake;

import android.content.Intent;

import com.kssoft.lake.net.responses.vo.LoginVO;
import com.kssoft.lake.services.TrailService;
import com.kssoft.lake.ui.activity.LoginActivity;
import com.tencent.smtt.sdk.WebView;

import java.util.Map;

import kiun.com.bvroutine.net.LoginInterceptor;
import kiun.com.bvroutine.utils.SharedUtil;
import okhttp3.Headers;

public class AppLogin extends LoginInterceptor<MainApplication> {

    public AppLogin(MainApplication activityApplication) {
        super(activityApplication);
        authorizeToken = SharedUtil.getValue("token", "");
    }

    @Override
    public String getAuthorizeToken() {
        return authorizeToken;
    }

    @Override
    public void refineToken(Headers headers, String body) {
        LoginVO loginVO = (LoginVO) getBody(body, LoginVO.class);
        authorizeToken = loginVO.getToken();
        SharedUtil.saveValue("token", authorizeToken);
    }

    @Override
    public Map<String, String> getHeader() {
        headers.put("Authorization", authorizeToken);
        return headers;
    }

    @Override
    public void clear() {
        if (clearToken()){
            SharedUtil.saveValue("token", "");

            Intent intent = new Intent(application.getTop(), LoginActivity.class);

            application.stopService(new Intent(application, TrailService.class));
            application.getTop().startActivity(intent);
            //退出登陆操作.
            application.finish(LoginActivity.class);
        }
    }
}
