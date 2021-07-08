package com.kssoft.lake.ui.activity.mine;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.kssoft.lake.R;
import com.kssoft.lake.base.presenter.imp.VersionUpdatePresenter;
import com.kssoft.lake.databinding.ActivityMineBinding;

import kiun.com.bvroutine.BuildConfig;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.utils.AppUtil;
import kiun.com.bvroutine.utils.SharedUtil;

public class MineActivity extends RequestBVActivity<ActivityMineBinding> {
    public static final Class clz = MineActivity.class;

    public MineActivity() {
    }

    public int getViewId() {
        return R.layout.activity_mine;
    }

    public void initView() {
        ((ActivityMineBinding)this.binding).setVersionUpdate(new VersionUpdatePresenter(this));
        ((ActivityMineBinding)this.binding).setPackageInfo(AppUtil.getPackageInfo(this.getContext()));
    }

    public void onChangedService(View view){
        String ip = SharedUtil.getValue("service", BuildConfig.Prefix);
        final EditText et = new EditText(this);
        et.setText(ip);
        new AlertDialog.Builder(this).setTitle("请输入服务器地址,格式http://ip:port/.")
                .setIcon(R.mipmap.ic_update_version)
                .setView(et)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    SharedUtil.saveValue("service", et.getText().toString());
                    String url = SharedUtil.getValue("service", BuildConfig.Prefix);
                    Toast.makeText(getContext(), "服务器地址修改为:" + url + ",手动重启后生效", Toast.LENGTH_LONG).show();
                }).setNegativeButton("取消",(dialogInterface, i) -> {
        }).setCancelable(false).show();
    }
}
