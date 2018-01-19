package com.prerak.demo.realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.prerak.demo.R;
import com.prerak.demo.realm.adapter.UserAdapter;
import com.prerak.demo.realm.controler.RealmController;
import com.prerak.demo.realm.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmMainActivity extends AppCompatActivity {
    // variable declaration
    private RecyclerView recyclerView;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_main);
        recyclerView = findViewById(R.id.recyclerView_realm);
        //get realm instance
        this.realm = RealmController.with(this).getRealm();

        setupRecycler();

        // refresh the realm instance
        RealmController.with(this).refresh();
    }

    private void setupRecycler() {

    }

    /*public void setRealmAdapter(RealmResults<User> books) {

        UserAdapter realmAdapter = new UserAdapter(this.getApplicationContext(), books, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }*/
}
