package com.prerak.demo.dogbreads;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.prerak.demo.R;
import com.prerak.demo.dogbreads.adapter.SubDogBreadImageAdapter;
import com.prerak.demo.dogbreads.model.DogBread;
import com.prerak.demo.services.APIService;
import com.prerak.demo.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by emxcel on 1/12/17.
 */

public class SubBreadImageActivity extends AppCompatActivity {
    // variable declaration
    private RecyclerView recyclerView;
    private List<String> mList;
    private APIService mApiService;
    private SubDogBreadImageAdapter subDogBreadImageAdapter;
    String url,name,subBread;
    private TextView tv;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_main);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        tv= (TextView) findViewById(R.id.tv);
        tv.setVisibility(View.GONE);
        mList = new ArrayList<>();
        name=getIntent().getStringExtra("name");
        subBread = getIntent().getStringExtra("subBread");
        url="api/breed/"+name+"/"+subBread+"/images";
        mApiService = AppConstant.setupRetrofit(AppConstant.baseURL);
        Call<DogBread> dogBreadCall = mApiService.getSubBreadImage(url);
        Log.d("url", dogBreadCall.request().url().toString());
        dogBreadCall.enqueue(new Callback<DogBread>() {
            @Override
            public void onResponse(Call<DogBread> call, Response<DogBread> response) {
                if (response.body() != null){
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                       mList=response.body().getMessage();
                        recyclerView.setLayoutManager(new GridLayoutManager(SubBreadImageActivity.this,3));
                        recyclerView.setHasFixedSize(true);
                        subDogBreadImageAdapter= new SubDogBreadImageAdapter(SubBreadImageActivity.this,mList);
                        recyclerView.setAdapter(subDogBreadImageAdapter);
                    }
                }else {
                    Toast.makeText(SubBreadImageActivity.this, "Response null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DogBread> call, Throwable t) {

            }
        });
    }
}
