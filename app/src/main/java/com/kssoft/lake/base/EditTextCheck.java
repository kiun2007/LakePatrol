package com.kssoft.lake.base;

import java.util.List;

import kiun.com.bvroutine.data.VerifyBase;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.utils.JexlUtil;
import kiun.com.bvroutine.utils.ListUtil;

public class EditTextCheck extends VerifyBase<List,Boolean> {

    public EditTextCheck(Object object, Verify verify) {
        super(object, verify, 1000);
    }

    public Boolean extra(Boolean def,Object item) {
        if (verify.extra().isEmpty()){
            return def;
        }
        return JexlUtil.run(verify.extra(), "that", object, "fieldName", fieldName, "item", item);
    }

    @Override
    protected boolean verifyValue(List value) {
        if (!ListUtil.isEmpty(value)){
            for (Object item : value){
                if (extra(true, item)){
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
