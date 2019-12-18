package com.assignment.androidproject.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.assignment.androidproject.Client.ApiRequestGenerator;
import com.assignment.androidproject.model.ResponseModel;
import com.assignment.androidproject.model.SingleResponseModel;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends AndroidViewModel {
    /*
     * Mutable Live Data */
    MutableLiveData<String> apiObserver = new MutableLiveData<>();

    /*
     * Observer for List
     * */
    private MutableLiveData<List<SingleResponseModel>> ListObserver = new MutableLiveData<>();

    /*
     * Constructor
     * */
    public MainViewModel(@NonNull Application application) {
        super(application);
    }



    public void fetchData() {

        /*
         * OkHttp Logging Interceptor
         * */
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /*
         * Connect HttpLoggingInterceptor to OkHttp first
         * */
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        /*
         * This is how to create the Retrofit's Builder*/
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.baseUrl("https://dl.dropboxusercontent.com/");
        retrofitBuilder.client(okHttpClient);

        /* create the retrofit using builder*/
        Retrofit retrofit = retrofitBuilder.build();

        /*Create the instances of apirequestgenrator using retrofit object*/
        ApiRequestGenerator apiRequestGenerator = retrofit.create(ApiRequestGenerator.class);

        Call<ResponseModel> requestCall = apiRequestGenerator.FetchData();
        /*Perform actual call*/
        requestCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel responseBody = response.body();

                if (responseBody == null) {
                    apiObserver.postValue("reponse body is null");
                    return;
                }
                    apiObserver.postValue(responseBody.getTitle());

                    /*
                     * Post the List
                     * */
                    ListObserver.postValue(responseBody.getRows());


            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<String> getApiObserver() {
        return apiObserver;
    }

    public LiveData<List<SingleResponseModel>> getMovieListObserver() {
        return ListObserver;
    }
}
