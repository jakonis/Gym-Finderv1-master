package com.example.gymfinder.gymfindersearchplaces.remote;

import com.example.gymfinder.gymfindersearchplaces.model.MyPlaceDetail;
import com.example.gymfinder.gymfindersearchplaces.model.MyPlaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface IGoogleService {

    @GET
    Call<MyPlaces> getNearByPlaces(@Url String url);

    @GET
    Call<MyPlaceDetail> getPlaceDetail(@Url String url);
}
