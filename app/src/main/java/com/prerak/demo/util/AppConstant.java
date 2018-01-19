package com.prerak.demo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prerak.demo.services.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emxcel on 28/11/17.
 */

public class AppConstant {
    //ToDo Variable Declaration
    public static boolean isAlreadyLoggedIn=false;
//    public static String baseURL="https://dog.ceo/";
//    public static  String baseURL = "http://10.0.2.2:8080";
    public static  String baseURL = "https://anapioficeandfire.com/";


  // Todo: Setup Retrofit
    public static APIService setupRetrofit(String url){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        APIService mApiService= retrofit.create(APIService.class);
        return mApiService;
    }

    //ToDo: Network checking method
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
