package com.mobi.efficacious.gurukulaarti.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LibraryDetailPojo {

    @SerializedName("LibraryDetail")
    @Expose
    private List<LibraryDetail> libraryDetail = null;

    public List<LibraryDetail> getLibraryDetail() {
        return libraryDetail;
    }

    public void setLibraryDetail(List<LibraryDetail> libraryDetail) {
        this.libraryDetail = libraryDetail;
    }

}
