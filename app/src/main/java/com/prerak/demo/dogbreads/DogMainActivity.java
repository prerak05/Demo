package com.prerak.demo.dogbreads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.prerak.demo.R;
import com.prerak.demo.dogbreads.adapter.DogBreadAdapter;
import com.prerak.demo.dogbreads.model.DogBread;
import com.prerak.demo.services.APIService;
import com.prerak.demo.util.AppConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogMainActivity extends AppCompatActivity {
    // variable declaration
    TextView tv_bread;
    APIService mApiService;
    RecyclerView mRecyclerView;
    List<String> mDogList = new ArrayList<>();
    DogBreadAdapter dogBreadAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mApiService= AppConstant.setupRetrofit(AppConstant.baseURL);
        Call<DogBread>  stringCall=mApiService.getStringCall();
        Log.d("url",stringCall.request().url().toString());
        stringCall.enqueue(new Callback<DogBread>() {
            @Override
            public void onResponse(Call<DogBread> call, Response<DogBread> response) {
                if (response.body() != null){
                   if (response.body().getStatus().equalsIgnoreCase("success")){
                       if (response.body().getMessage().size() > 0){
                           mDogList = response.body().getMessage();
                           mRecyclerView.setLayoutManager(new GridLayoutManager(DogMainActivity.this,3));
                           mRecyclerView.setHasFixedSize(true);
                           dogBreadAdapter =new DogBreadAdapter(DogMainActivity.this,mDogList);
                           System.out.println("===list===" + mDogList);
                           mRecyclerView.setAdapter(dogBreadAdapter);
                       }
                   }else {
                       Toast.makeText(DogMainActivity.this," "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }else {
                    Toast.makeText(DogMainActivity.this, "Response null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DogBread> call, Throwable t) {
                Toast.makeText(DogMainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
