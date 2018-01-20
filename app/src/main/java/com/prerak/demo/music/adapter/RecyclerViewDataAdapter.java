package com.prerak.demo.music.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prerak.demo.R;
import com.prerak.demo.music.SelectYourCatagoryActivity;
import com.prerak.demo.music.model.SectionDataModel;

import java.util.ArrayList;

/**
 * Created by emxcel on 20/1/18.
 */

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.holder> {
    // variable declaration
    private ArrayList<SectionDataModel> dataList;
    private Context mContext;

    public RecyclerViewDataAdapter(SelectYourCatagoryActivity selectYourCatagoryActivity, ArrayList<SectionDataModel> allSampleData) {
    this.mContext = selectYourCatagoryActivity;
    this.dataList =allSampleData;
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(mContext).inflate(R.layout.raw_category_list,parent,false));
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        final String sectionName = dataList.get(position).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(position).getAllItemsInSection();
        holder.itemTitle.setText(sectionName);
        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);
        holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_view_list.setAdapter(itemListDataAdapter);


        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "click event on more, "+sectionName , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;
        protected Button btnMore;

        public holder(View itemView) {
            super(itemView);
            this.itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) itemView.findViewById(R.id.recycler_view_list);
            this.btnMore= (Button) itemView.findViewById(R.id.btnMore);

        }
    }
}
