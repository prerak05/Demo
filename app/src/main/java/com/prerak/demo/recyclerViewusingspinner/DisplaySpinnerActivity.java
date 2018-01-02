package com.prerak.demo.recyclerViewusingspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.prerak.demo.R;

public class DisplaySpinnerActivity extends AppCompatActivity {
    // variable declaration
    private TextView tv_display_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_spinner);

        tv_display_data= (TextView) findViewById(R.id.tv_display_data);
        tv_display_data.setText(getIntent().getStringExtra("data"));
    }
}
