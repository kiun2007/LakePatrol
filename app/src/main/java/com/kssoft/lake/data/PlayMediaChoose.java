package com.kssoft.lake.data;

import android.content.Context;

import com.kssoft.lake.data.model.XcSnimdtF;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.ContextHandlerVariableSet;
import kiun.com.bvroutine.media.MediaBrowsingActivityHandler;

@AutoImport(ContextHandlerVariableSet.class)
public class PlayMediaChoose {

    private Context context;

    public PlayMediaChoose(Context context){
        this.context = context;
    }

    public void openFile(XcSnimdtF xcSnimdtF){

        if (xcSnimdtF != null && xcSnimdtF.getFlnm() != null){
            MediaBrowsingActivityHandler.openActivityNormal(context, xcSnimdtF.getFlnm());
        }
    }
}
