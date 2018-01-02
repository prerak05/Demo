package com.prerak.demo.recyclerViewwithcheckbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.prerak.demo.R;
import com.prerak.demo.recyclerViewwithcheckbox.model.Student;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by emxcel on 1/9/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    //variable declaration
    private List<Student> studentList;
    private Context context;
    private List<Student> image;
    public String url = "https://vignette.wikia.nocookie.net/thehungergames/images/9/95/100x100.png/revision/latest?cb=20130823021501";
    public MyAdapter(Context context, List<Student> studentList, List<Student> image) {
        this.studentList = studentList;
        this.context= context;
        this.image= image;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_recycler_checkbox_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
        holder.tvName.setText(studentList.get(position).getName());
        holder.tvEmailId.setText(studentList.get(position).getEmailID());
        holder.chkSelected.setChecked(studentList.get(position).isSelected());
        holder.chkSelected.setTag(studentList.get(position));
        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox= (CheckBox) view;
                studentList.get(pos).setSelected(checkBox.isChecked());
            }
        });
      //  holder.imageView.setImageResource(Integer.parseInt(image.get(position).getImg()));
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher_round)
                .resize(80,80).error(R.mipmap.ic_launcher).into(holder.imageView);
        Log.d("url",url);
//  Picasso.with(context).load("https://static.pexels.com/photos/257360/pexels-photo-257360.jpeg").placeholder(R.mipmap.ic_launcher_round).centerCrop()
//                .fit().into(holder.imageView);
//
//        Log.d("img","https://static.pexels.com/photos/257360/pexels-photo-257360.jpeg");
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvEmailId;
        public CheckBox chkSelected;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvEmailId = (TextView) itemView.findViewById(R.id.tvEmailId);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
            imageView=(ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public List<Student> getStudent(){
        return studentList;
    }
}
