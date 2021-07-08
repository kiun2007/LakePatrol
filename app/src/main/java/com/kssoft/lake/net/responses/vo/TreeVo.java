package com.kssoft.lake.net.responses.vo;

import android.annotation.SuppressLint;

import com.kssoft.lake.R;

import java.util.LinkedList;
import java.util.List;
import kiun.com.bvroutine.interfaces.TreeItem;
import kiun.com.bvroutine.utils.ListUtil;

public class TreeVo implements TreeItem {

    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    private List<TreeVo> children;

    private boolean isLoading = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeVo> getChildren() {
        return children;
    }

    public boolean isChildrenEmpty(){
        return ListUtil.isEmpty(children);
    }

    public void setChildren(List<TreeVo> children) {
        this.children = children;
    }

    @SuppressLint("DefaultLocale")
    public String getTitleOfLevel(int level){

        int count = children == null ? 0 : children.size();
        if (count > 0){
            if (level == 0){
                return String.format("(%d个部门)",count);
            }else if (level == 1){
                return String.format("(%d名人员)",count);
            }
        }
        return "";
    }

    @Override
    public int itemLayoutId() {
        return R.layout.item_root_person;
    }

    @Override
    public boolean withChild(int parentLevel) {
        return parentLevel < 1;
    }

    @Override
    public List<?> itemList() {
        if (!isLoading){
            isLoading = true;
            return children;
        }
        return new LinkedList<>();
    }

    @Override
    public String toString() {
        return label;
    }
}
