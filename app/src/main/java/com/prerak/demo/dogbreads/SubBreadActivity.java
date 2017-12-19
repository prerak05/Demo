package com.prerak.demo.dogbreads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.prerak.demo.R;
import com.prerak.demo.dogbreads.adapter.DogImageAdapter;
import com.prerak.demo.dogbreads.adapter.SubBreadAdapter;
import com.prerak.demo.dogbreads.model.DogBread;
import com.prerak.demo.services.APIService;
import com.prerak.demo.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubBreadActivity extends AppCompatActivity {
    // variable declaration
    private RecyclerView recyclerView;
    private List<String> mSubBreadList;
    private APIService mApiService;
    private String sSubBread;
    private SubBreadAdapter subBreadAdapter;
    private DogImageAdapter dogImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_bread);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSubBreadList = new ArrayList<>();
        sSubBread = getIntent().getStringExtra("name");
        mApiService = AppConstant.setupRetrofit(AppConstant.baseURL);
        Call<DogBread> dogBreadCall = mApiService.getSubBread(sSubBread);
        Log.d("url", dogBreadCall.request().url().toString());
        dogBreadCall.enqueue(new Callback<DogBread>() {
            @Override
            public void onResponse(Call<DogBread> call, Response<DogBread> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        if (response.body().getMessage().size() > 0) {
                            mSubBreadList = response.body().getMessage();
                            recyclerView.setLayoutManager(new LinearLayoutManager(SubBreadActivity.this));
                            recyclerView.setHasFixedSize(true);
                            subBreadAdapter = new SubBreadAdapter(SubBreadActivity.this, mSubBreadList,sSubBread);
                            recyclerView.setAdapter(subBreadAdapter);
                        } else {
                            Toast.makeText(SubBreadActivity.this, "Size 0", Toast.LENGTH_SHORT).show();
                            getBreadImage();
                        }
                    } else {
                        Toast.makeText(SubBreadActivity.this, "Response False", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(SubBreadActivity.this, "Response Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DogBread> call, Throwable t) {

            }
        });
    }

    private void getBreadImage() {
        mApiService = AppConstant.setupRetrofit(AppConstant.baseURL);
        final Call<DogBread> dogBreadCall = mApiService.getBreadImage(sSubBread);
        Log.d("url", dogBreadCall.request().url().toString());
        dogBreadCall.enqueue(new Callback<DogBread>() {
            @Override
            public void onResponse(Call<DogBread> call, Response<DogBread> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        mSubBreadList = response.body().getMessage();
                        System.out.println("dog image" + mSubBreadList);
                        recyclerView.setLayoutManager(new GridLayoutManager(SubBreadActivity.this, 3));
                        recyclerView.setHasFixedSize(true);
                        dogImageAdapter = new DogImageAdapter(SubBreadActivity.this, mSubBreadList);
                        recyclerView.setAdapter(dogImageAdapter);

                    } else {
                        Toast.makeText(SubBreadActivity.this, "status false", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SubBreadActivity.this, "Response null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DogBread> call, Throwable t) {
                Toast.makeText(SubBreadActivity.this, "response null", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
