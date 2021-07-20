package com.kssoft.lake.ui.activity.mine;

import android.view.View;
import android.widget.Toast;

import com.kssoft.lake.R;
import com.kssoft.lake.databinding.ActivityMinePwdBinding;
import com.kssoft.lake.net.requests.dto.Password;

import kiun.com.bvroutine.base.RequestBVActivity;

public class PwdChangeActivity extends RequestBVActivity<ActivityMinePwdBinding> {
    public static final Class clz = PwdChangeActivity.class;

    public PwdChangeActivity() {
    }

    public int getViewId() {
        return R.layout.activity_mine_pwd;
    }

    public void initView() {
        ((ActivityMinePwdBinding)this.binding).setPassword(new Password());
    }

    public void onPasswordChange(View var1) {
        Toast.makeText(this, "密码修改成功", Toast.LENGTH_LONG).show();
        this.finish();
    }
}
