package com.prerak.demo.recyclerViewusingspinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.prerak.demo.R;
import com.prerak.demo.recyclerViewusingspinner.adapter.MyRecyclerAdapter;

import java.util.ArrayList;

public class MainRecyclerActivity extends AppCompatActivity {
    // variable initialization
    private RecyclerView recyclerView;
    private Button btn_submit;
    private ArrayList<String> dataList = new ArrayList<>();
    private MyRecyclerAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);
        for (int i = 0; i <= 100; i++) {
            dataList.add(String.valueOf(i));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,dataList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter= new MyRecyclerAdapter(this,dataList,arrayAdapter);
        recyclerView.setAdapter(myAdapter);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainRecyclerActivity.this,DisplaySpinnerActivity.class);
                intent.putExtra("data",myAdapter.getData());
                startActivity(intent);
            }
        });


    }
}
