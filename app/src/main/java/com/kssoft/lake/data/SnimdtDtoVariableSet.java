package com.kssoft.lake.data;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import com.kssoft.lake.net.requests.dto.SnimdtDto;
import com.kssoft.lake.ui.activity.commit.CommitHydrologyActivity;
import com.kssoft.lake.ui.activity.commit.CommitLakeActivity;
import com.kssoft.lake.ui.activity.commit.CommitManualActivity;
import com.kssoft.lake.ui.activity.commit.CommitUrgentActivity;

import java.lang.reflect.Method;

public class SnimdtDtoVariableSet extends LocationVariableSet<SnimdtDto> {

    public SnimdtDtoVariableSet(Context context, ViewDataBinding viewDataBinding, Class<SnimdtDto> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {
        super.start();

        if (context instanceof CommitLakeActivity){
            locationBean.setXctp(0);
        }else if (context instanceof CommitHydrologyActivity){
            locationBean.setXctp(1);
        }else if (context instanceof CommitManualActivity){
            locationBean.setXctp(2);
        }else if (context instanceof CommitUrgentActivity){
            locationBean.setXctp(3);
        }
    }
}
