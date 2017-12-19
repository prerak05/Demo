package com.prerak.demo.dogbreads.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.prerak.demo.R;
import com.prerak.demo.dogbreads.SubBreadImageActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by emxcel on 1/12/17.
 */

public class SubDogBreadImageAdapter extends RecyclerView.Adapter<SubDogBreadImageAdapter.ViewHolder> {
    // variable declaration
    private Context context;
    private List<String> mList;

    public SubDogBreadImageAdapter(SubBreadImageActivity subBreadImageActivity, List<String> mList) {
        this.context = subBreadImageActivity;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_image_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(mList.get(position)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round).centerCrop()
                .fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_dog);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            View view1= LayoutInflater.from(context).inflate(R.layout.popup_layout,null);
            final android.support.v7.app.AlertDialog forgetDialog = new android.support.v7.app.AlertDialog.Builder(context, R.style.NewDialog).create();
            forgetDialog.setView(view1);
            ImageView imageView = (ImageView) view1.findViewById(R.id.iv_dog_popup);
            Picasso.with(context).load(mList.get(position)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round).centerCrop()
                    .fit().into(imageView);
            forgetDialog.show();
        }
    }
}
