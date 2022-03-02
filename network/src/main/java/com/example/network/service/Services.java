package com.example.network.service;

import com.example.network.model.InspectionAPIModel;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.example.network.NetworkConstant.BASE_URL;

public interface Services {

    @POST("insertInspection")
    Observable<Response<InspectionAPIModel>> syncInspection(@Body InspectionAPIModel inspectionAPIModel);

static Services createService(){

    return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(Services.class);

}

}
