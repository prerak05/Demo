package com.prerak.demo.services;

import com.prerak.demo.model.Login;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by emxcel on 28/11/17.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("/portal/rest/logincheck")
    Call<Login> getLoginCall(
            @FieldMap(encoded = true) Map<String, String> params
    );

}
