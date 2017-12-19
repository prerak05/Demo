package com.prerak.demo.dogbreads.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prerak.demo.R;
import com.prerak.demo.dogbreads.DogImageActivity;
import com.prerak.demo.dogbreads.SubBreadActivity;
import com.prerak.demo.dogbreads.model.DogBread;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * Created by emxcel on 29/11/17.
 */

public class DogBreadAdapter extends RecyclerView.Adapter<DogBreadAdapter.ViewHolder> {
    // variable declaration
    private Context context;
    private List<String> mList;

    public DogBreadAdapter(Context context, List<String> mDogList) {
        this.context = context;
        this.mList = mDogList;
        System.out.println("==adapter List==" + mList);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_dog_bread.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_dog_bread;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_dog_bread = (TextView) itemView.findViewById(R.id.tv_dog_bread);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String name = mList.get(position);
            Toast.makeText(context, "name " + name, Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(context, SubBreadActivity.class);
            intent.putExtra("name",name);
            context.startActivity(intent);

        }
    }
}
