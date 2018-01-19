package com.prerak.demo.got.model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.prerak.demo.R;
import com.prerak.demo.got.model.adapter.BookAdapter;
import com.prerak.demo.services.APIService;
import com.prerak.demo.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {
    // variable declaration
    private RecyclerView recyclerView;
    private APIService mApiService;
    BookAdapter mBookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_book);
        mApiService = AppConstant.setupRetrofit(AppConstant.baseURL);
        Call<Parent> mainModelCall = mApiService.getBookModelCall();
        Log.d("url", mainModelCall.request().url().toString());
        mainModelCall.enqueue(new Callback<Parent>() {
            @Override
            public void onResponse(Call<Parent> call, Response<Parent> response) {
                Toast.makeText(BookActivity.this, "success", Toast.LENGTH_SHORT).show();
                if (response.body() != null) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(BookActivity.this));
                    recyclerView.setHasFixedSize(true);
                    mBookAdapter = new BookAdapter(BookActivity.this,response.body().getMainModels());
                    recyclerView.setAdapter(mBookAdapter);
                }
            }

            @Override
            public void onFailure(Call<Parent> call, Throwable t) {
                Toast.makeText(BookActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
