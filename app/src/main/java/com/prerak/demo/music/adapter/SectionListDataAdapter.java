package com.prerak.demo.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prerak.demo.R;
import com.prerak.demo.music.model.SingleItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by emxcel on 20/1/18.
 */

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {
    // variable declaration
    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;

    public SectionListDataAdapter(Context mContext, ArrayList singleSectionItems) {
        this.mContext = mContext;
        this.itemsList = singleSectionItems;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleItemRowHolder(LayoutInflater.from(mContext).inflate(R.layout.list_single_card, parent, false));
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {
        SingleItemModel singleItem = itemsList.get(position);

        holder.tvTitle.setText(singleItem.getName());
        Log.e("image",singleItem.getUrl());
        Glide.with(mContext)
                .load(singleItem.getUrl())
                .placeholder(R.drawable.image5)
                .error(R.drawable.image5)
                .into(holder.itemImage);
//        Picasso.with(mContext).load(singleItem.getUrl()).placeholder(R.drawable.image5).error(R.drawable.image5).into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView tvTitle;
        protected ImageView itemImage;
        public SingleItemRowHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) itemView.findViewById(R.id.itemImage);

        }
    }
}
