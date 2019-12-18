
package com.assignment.androidproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rows")
    @Expose
    private List<SingleResponseModel> rows = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SingleResponseModel> getRows() {
        return rows;
    }

    public void setRows(List<SingleResponseModel> rows) {
        this.rows = rows;
    }

}
