package com.assignment.androidproject.Client;


import com.assignment.androidproject.model.ResponseModel;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequestGenerator {
/*
*url-https://api.myjson.com/bins/8j1p4
* Base Url-https://api.myjson.com
* EndPonits-bins/8j1p4
* */
@GET("s/2iodh4vg0eortkl/facts.json")
Call<ResponseModel>FetchData();
}
