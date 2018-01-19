package com.prerak.demo.realm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.prerak.demo.realm.model.User;

/**
 * Created by emxcel on 12/1/18.
 */

public class UserAdapter extends RealmRecyclerViewAdapter<User> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
