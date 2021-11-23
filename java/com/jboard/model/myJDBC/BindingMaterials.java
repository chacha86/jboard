package com.jboard.model.myJDBC;

import java.util.List;

public class BindingMaterials {

    private List<Object> orderedParamList;
    private String bindingQeury;

    public BindingMaterials(List<Object> orderedParamList, String bindingQeury) {
        this.orderedParamList = orderedParamList;
        this.bindingQeury = bindingQeury;
    }

    public List<Object> getOrderedParamList() {
        return orderedParamList;
    }

    public String getBindingQeury() {
        return bindingQeury;
    }

}
