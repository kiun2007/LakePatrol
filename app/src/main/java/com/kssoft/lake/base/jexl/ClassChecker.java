package com.kssoft.lake.base.jexl;

public class ClassChecker {

    public boolean isClass(Object object, String simpleName){

        if (object == null){
            return false;
        }
        return object.getClass().getSimpleName().equals(simpleName);
    }
}
