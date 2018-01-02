package com.prerak.demo.recyclerViewwithcheckbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.prerak.demo.R;

/**
 * Created by emxcel on 2/1/18.
 */

public class DisplayCheckboxActivity extends AppCompatActivity {
    //variable declaration
    TextView tv_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_checkbox);
        tv_data = (TextView) findViewById(R.id.tv_data);
        Intent intent = getIntent();
        if (intent != null) {
            tv_data.setText(intent.getStringExtra("data"));
        }
    }
}
