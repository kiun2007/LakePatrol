package com.kssoft.lake.utils;

import androidx.databinding.ObservableField;

import com.kssoft.lake.data.model.XcAppM;
import com.kssoft.lake.services.TrailService;

import java.util.Arrays;
import java.util.List;

public class ModuleUtil {

    private List<XcAppM> moduleList;
    private TrailService trailService;
    private String inspectionType[] = new String[]{"4","M0","5","M1","6","M3","7","M2"};

    private ModuleUtil(List<XcAppM> moduleList, TrailService trailService) {
        this.moduleList = moduleList;
        this.trailService = trailService;
    }

    public boolean isShow(String id){
        if (moduleList != null){
            for (XcAppM xcAppM : moduleList){
                if (xcAppM.getMnm() != null && xcAppM.getNum().equals(id)){
                    return true;
                }
            }
        }
        return false;
    }

    public ObservableField<Boolean> isAvailable(String id){

        if (moduleList != null){
            for (XcAppM xcAppM : moduleList){
                if (xcAppM.getMcd() != null && xcAppM.getNum().equals(id) && "0".equals(xcAppM.getState())){
                    ObservableField<Boolean> observableField = new ObservableField<>();
                    observableField.set(true);

                    int index = -1;
                    if ((index = Arrays.asList(inspectionType).indexOf(id)) > -1 && trailService != null){
                        trailService.putTypeAndObservable(inspectionType[index+1].replace("M",""), observableField);
                    }
                    return observableField;
                }
            }
        }
        return new ObservableField<>(false);
    }

    public static ModuleUtil create(List<XcAppM> moduleList, TrailService trailService){
        return new ModuleUtil(moduleList, trailService);
    }
}
