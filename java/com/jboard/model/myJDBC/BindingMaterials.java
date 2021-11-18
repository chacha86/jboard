package com.jboard.model.myJDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
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

    public void setOrderedParamList(List<Object> orderedParamList) {
        this.orderedParamList = orderedParamList;
    }

    public String getBindingQeury() {
        return bindingQeury;
    }

    public void setBindingQeury(String bindingQeury) {
        this.bindingQeury = bindingQeury;
    }
}
