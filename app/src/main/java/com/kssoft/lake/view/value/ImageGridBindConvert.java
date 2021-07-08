package com.kssoft.lake.view.value;

import com.kssoft.lake.data.model.XcSnimdtF;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.presenters.OnClickUploadPresenter;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.views.MultipleImageGrid;

public class ImageGridBindConvert extends BindConvert<MultipleImageGrid, List<XcSnimdtF>, List<XcSnimdtF>> implements MultipleImageGrid.OnDataChangedLister {

    private OnClickUploadPresenter<XcSnimdtF> presenter;
    public ImageGridBindConvert(MultipleImageGrid view) {
        super(view);
        presenter = (OnClickUploadPresenter<XcSnimdtF>) view.getUploadPresenter();
        view.setOnDataChangedLister(this);
    }

    @Override
    public List<XcSnimdtF> getValue() {

        List<XcSnimdtF> xcSnimdtFList = new LinkedList<>();
        for (XcSnimdtF xcSnimdtF : presenter.getData()){
            if (xcSnimdtF != null){
                xcSnimdtFList.add(xcSnimdtF);
            }
        }
        return xcSnimdtFList;
    }

    @Override
    public void setValue(List<XcSnimdtF> value) {
        if (!ListUtil.isEmpty(value)){

            List<XcSnimdtF> nowList = new LinkedList<>();
            nowList.addAll(value);
            if(view.isCommitMode() && nowList.get(value.size() - 1) != null){
                nowList.add(null);
            }
            presenter.setList(nowList);
        }
    }
}