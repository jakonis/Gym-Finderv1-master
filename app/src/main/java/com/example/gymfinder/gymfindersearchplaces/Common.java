package com.example.gymfinder.gymfindersearchplaces;

import com.example.gymfinder.gymfindersearchplaces.model.Results;
import com.example.gymfinder.gymfindersearchplaces.remote.IGoogleService;
import com.example.gymfinder.gymfindersearchplaces.remote.RetrofitClient;

public class Common {

    private static final String GOOGLE_API_URL="https://maps.googleapis.com/";
    public static Results currentResult;

    public static IGoogleService getGoogleService(){
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleService.class);
    }
}
