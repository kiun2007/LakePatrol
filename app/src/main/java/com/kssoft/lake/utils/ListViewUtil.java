package com.kssoft.lake.utils;

import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.presenters.list.NetListProvider;

public class ListViewUtil {

    public static void refresh(ListHandler listHandler){
        if (listHandler.getTag() instanceof NetListProvider){
            ((NetListProvider) listHandler.getTag()).refresh();
        }
    }
}
