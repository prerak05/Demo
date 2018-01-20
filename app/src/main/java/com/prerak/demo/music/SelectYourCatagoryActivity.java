package com.prerak.demo.music;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.prerak.demo.R;
import com.prerak.demo.music.adapter.RecyclerViewDataAdapter;
import com.prerak.demo.music.model.SectionDataModel;
import com.prerak.demo.music.model.SingleItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SelectYourCatagoryActivity extends AppCompatActivity {
    // variable declaration
    private RecyclerView recyclerView;
    ArrayList<SectionDataModel> allSampleData;
    List<String> arrayOfTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_your_catagory);
        allSampleData = new ArrayList<>();
        //  createDummyData();
        loadJSONFromAsset();
        createJsonToArray();

        Log.e("arrayOfTitle : ", arrayOfTitle + "");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }


    public void createJsonToArray() {
        arrayOfTitle = new ArrayList<>();
        allSampleData = new ArrayList<>();
        try {
            JSONObject obj = loadJSONFromAsset();
            if (obj != null) {
                JSONArray m_jArry = obj.getJSONArray("data");
                for (int i = 0; i < m_jArry.length(); i++) {

                    SectionDataModel sectionDataModel = new SectionDataModel();

                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    Iterator<String> iter = jo_inside.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        arrayOfTitle.add(key);

                        sectionDataModel.setHeaderTitle(key);

                        ArrayList<SingleItemModel> allItemsInSection = new ArrayList<>();
                        JSONArray m_jArry1 = jo_inside.getJSONArray(key);
                        for (int j = 0; j < m_jArry1.length(); j++) {
                            JSONObject jaSubData = m_jArry1.getJSONObject(j);
                            allItemsInSection.add(new SingleItemModel(jaSubData.getString("id").toString(), jaSubData.getString("name").toString(), jaSubData.getString("imageURL").toString()));
                        }
                        sectionDataModel.setAllItemsInSection(allItemsInSection);
                    }


                    allSampleData.add(sectionDataModel);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject loadJSONFromAsset() {
        String json = null;
        JSONObject obj = null;
        try {
            InputStream is = getAssets().open("mainlist.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            obj = new JSONObject(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
