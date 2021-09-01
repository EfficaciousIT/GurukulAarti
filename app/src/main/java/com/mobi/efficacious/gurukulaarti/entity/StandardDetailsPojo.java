package com.mobi.efficacious.gurukulaarti.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StandardDetailsPojo {

    @SerializedName("StandardDetails")
    @Expose
    private List<StandardDetail> standardDetails = null;

    public List<StandardDetail> getStandardDetails() {
        return standardDetails;
    }

    public void setStandardDetails(List<StandardDetail> standardDetails) {
        this.standardDetails = standardDetails;
    }

}