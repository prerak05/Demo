package com.prerak.demo.services;

import com.prerak.demo.dogbreads.model.DogBread;
import com.prerak.demo.imageupload.model.Image;
import com.prerak.demo.model.Login;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by emxcel on 28/11/17.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("/portal/rest/logincheck")
    Call<Login> getLoginCall(
            @FieldMap(encoded = true) Map<String, String> params
    );
    @GET("/api/breeds/list")
    Call<DogBread> getStringCall();

    @GET("/api/breed/{breed_name}/images")
    Call<DogBread> getBreadImage(@Path(value = "breed_name", encoded = true) String breadName);

    @GET("/api/breed/{sub_breed_name}/list")
    Call<DogBread> getSubBread(@Path(value = "sub_breed_name", encoded = true) String subBreadName);

    @GET
    Call<DogBread> getSubBreadImage(@Url String url);

    @Multipart
    @POST("/images/upload")
    Call<Image> uploadImage(@Part MultipartBody.Part image);
}
