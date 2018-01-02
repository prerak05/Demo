package com.prerak.demo.recyclerViewusingspinner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.prerak.demo.R;
import com.prerak.demo.recyclerViewusingspinner.MainRecyclerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emxcel on 2/1/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    // variable declaration
    private Context context;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list;
    private List<Integer> selectedItemPosition = new ArrayList<>();
    private String data;

    public MyRecyclerAdapter(MainRecyclerActivity mainRecyclerActivity, ArrayList<String> dataList, ArrayAdapter<String> arrayAdapter) {
      this.context =mainRecyclerActivity;
      this.list= dataList;
      this.arrayAdapter= arrayAdapter;
        for (int i=0; i<=15; i++){
            selectedItemPosition.add(0);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_recycler_lauout,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.sp_data.setAdapter(arrayAdapter);
        holder.sp_data.setSelection(selectedItemPosition.get(position));
        holder.sp_data.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItemPosition.remove(position);
                selectedItemPosition.add(position,i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Spinner sp_data;
        public ViewHolder(View itemView) {
            super(itemView);
            sp_data= itemView.findViewById(R.id.sp_data);
        }
    }

    public String getData(){
        data="";
        for (int i=0; i<15; i++){
            data+=list.get(selectedItemPosition.get(i)) + "\n";
        }
        return data;
    }
}
