package com.kssoft.lake.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.kssoft.lake.BR;
import com.kssoft.lake.BuildConfig;
import com.kssoft.lake.R;
import com.kssoft.lake.databinding.ActivityRootLoginBinding;
import com.kssoft.lake.net.requests.dto.LoginDto;
import com.kssoft.lake.ui.activity.personnel.HomeActivity;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.interfaces.binding.Import;
import kiun.com.bvroutine.interfaces.binding.ImportType;
import kiun.com.bvroutine.utils.SharedUtil;

/**
 * 文 件 名: Login
 * 作 者: 刘春杰
 * 创建日期: 2020/7/30 16:36
 * 说明: 登陆页面
 */
public class LoginActivity extends RequestBVActivity<ActivityRootLoginBinding> {

    public static final Class clz = LoginActivity.class;

    @Import(value = LoginDto.class, type = ImportType.Save, extra = LoginDto.TAG)
    int user = BR.user;

    @Override
    public int getViewId() {
        return R.layout.activity_root_login;
    }

    @Override
    public void initView() {
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void login(String data){
        binding.getUser().setPassword("");
        SharedUtil.saveValue(LoginDto.TAG, binding.getUser());
        startActivity(new Intent(this, HomeActivity.clz));
        finish();
    }

    public void onChangedService(View view){

        if (!BuildConfig.DEBUG){
            return;
        }

        String ip = SharedUtil.getValue("service", BuildConfig.Prefix);
        final EditText et = new EditText(this);
        et.setText(ip);
        new AlertDialog.Builder(this).setTitle("请输入服务器地址,格式http://ip:port/.")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    SharedUtil.saveValue("service", et.getText().toString());
                    String url = SharedUtil.getValue("service", BuildConfig.Prefix);
                    Toast.makeText(getContext(), "服务器地址修改为:" + url + ",手动重启后生效", Toast.LENGTH_LONG).show();
                }).setNegativeButton("取消",(dialogInterface, i) -> {
        }).setCancelable(false).show();
    }
}