package com.prerak.demo.music.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prerak.demo.R;
import com.prerak.demo.music.MainMusicActivity;
import com.prerak.demo.music.fragment.SongPlayFragment;
import com.prerak.demo.music.fragment.TestFragment;
import com.prerak.demo.music.model.Music;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emxcel on 9/1/18.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {
    // variable declaration
    private Context context;
    private ArrayList<Music> musicList =new ArrayList<>();

    public SongListAdapter(MainMusicActivity mainMusicActivity, ArrayList<Music> musicList) {
     this.context =mainMusicActivity;
     this.musicList = musicList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_music_sample,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(musicList.get(position).getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round).centerCrop()
                .fit().into(holder.iv_song_img);
        holder.tv_song_name.setText(musicList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_song_img;
        TextView tv_song_name;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_song_img =itemView.findViewById(R.id.iv_song_pic);
            tv_song_name = itemView.findViewById(R.id.tv_song_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
      //      ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.containt_main,new SongPlayFragment(position,musicList)).addToBackStack(null).commit();

            Intent intent = new Intent(context, TestFragment.class);
            Bundle bundle = new Bundle();
            bundle.putInt("position",position);
            bundle.putParcelableArrayList("list",musicList);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
