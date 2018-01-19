package com.prerak.demo.realm.adapter;

import android.content.Context;

import com.prerak.demo.realm.model.User;

import io.realm.RealmResults;

/**
 * Created by emxcel on 12/1/18.
 */

public class RealmUserAdapter extends RealmModelAdapter<User> {

    public RealmUserAdapter(Context context, RealmResults<User> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
