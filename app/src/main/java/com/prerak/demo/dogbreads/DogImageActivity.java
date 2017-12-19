package com.prerak.demo.dogbreads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.prerak.demo.R;
import com.prerak.demo.dogbreads.adapter.DogImageAdapter;
import com.prerak.demo.dogbreads.model.DogBread;
import com.prerak.demo.services.APIService;
import com.prerak.demo.util.AppConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class DogImageActivity extends AppCompatActivity {
    // variable declaration
    RecyclerView recyclerView;
    APIService mApiService;
    List<String> mDogImageList = new ArrayList<>();
    DogImageAdapter dogImageAdapter;
    String breadName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_image);
        breadName =  getIntent().getStringExtra("name");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mApiService = AppConstant.setupRetrofit(AppConstant.baseURL);
        final Call<DogBread> dogBreadCall= mApiService.getBreadImage(breadName);
        Log.d("url",dogBreadCall.request().url().toString());
        dogBreadCall.enqueue(new Callback<DogBread>() {
            @Override
            public void onResponse(Call<DogBread> call, Response<DogBread> response) {
                if (response.body()!= null){
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        mDogImageList = response.body().getMessage();
                        System.out.println("dog image" + mDogImageList);
                        recyclerView.setLayoutManager(new GridLayoutManager(DogImageActivity.this,3));
                        recyclerView.setHasFixedSize(true);
                        dogImageAdapter = new DogImageAdapter(DogImageActivity.this,mDogImageList);
                        recyclerView.setAdapter(dogImageAdapter);

                    }else{
                        Toast.makeText(DogImageActivity.this, "status false", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(DogImageActivity.this, "Response null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DogBread> call, Throwable t) {
                Toast.makeText(DogImageActivity.this, "response null", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
