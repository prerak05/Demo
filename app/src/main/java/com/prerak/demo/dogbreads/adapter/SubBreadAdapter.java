package com.prerak.demo.dogbreads.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prerak.demo.R;
import com.prerak.demo.dogbreads.SubBreadActivity;
import com.prerak.demo.dogbreads.SubBreadImageActivity;

import java.util.List;

/**
 * Created by emxcel on 1/12/17.
 */

public class SubBreadAdapter extends RecyclerView.Adapter<SubBreadAdapter.ViewHolder> {
    //variable declaration
    private Context context;
    private List<String> mList;
    private String breadName;
    public SubBreadAdapter(SubBreadActivity subBreadActivity, List<String> mSubBreadList, String sSubBread) {
        this.context = subBreadActivity;
        this.mList = mSubBreadList;
        this.breadName=sSubBread;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_subBread.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_subBread;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_subBread = itemView.findViewById(R.id.tv_dog_bread);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position= getAdapterPosition();
            String subBread= mList.get(position);
            Intent intent =new Intent(context, SubBreadImageActivity.class);
            intent.putExtra("name",breadName);
            intent.putExtra("subBread",subBread);
            context.startActivity(intent);

        }
    }
}
