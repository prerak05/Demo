package com.prerak.demo.music.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prerak.demo.R;
import com.prerak.demo.music.MainMusicActivity;
import com.prerak.demo.music.model.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emxcel on 9/1/18.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    // variable declaration
    private Context context;
    private List<Music> musicList = new ArrayList<>();
    MediaPlayer mPlayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private boolean playPause;
    private boolean isPaused = false;
    int posi = 0;

    public MusicAdapter(MainMusicActivity mainMusicActivity, List<Music> musicList) {
        this.context = mainMusicActivity;
        this.musicList = musicList;
        // Initialize a new media player instance
        mPlayer = new MediaPlayer();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_music_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        mPlayer.reset();
        holder.tv_song_name.setText(musicList.get(position).getName());
        holder.iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iv_play.setVisibility(View.GONE);
                holder.iv_pause.setVisibility(View.VISIBLE);

                int secondPosition = position;
                Toast.makeText(context, "second position= " + secondPosition, Toast.LENGTH_SHORT).show();

                if (mPlayer.isPlaying()) {
                    return;
                }
                if (mPlayer != null && !mPlayer.isPlaying() && isPaused) {
                    mPlayer.start();
                }

                if (posi != secondPosition) {
                    mPlayer.reset();
                    // Set the media player audio stream type
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    //Try to play music/audio from url
                    try {
                        // Set the audio data source
                        mPlayer.setDataSource(musicList.get(position).getUrl());

                        // Prepare the media player
                        mPlayer.prepare();
                        // Start playing audio from http url
                        mPlayer.start();

                        // Inform user for audio streaming
                        Toast.makeText(context, "Playing", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        // Catch the exception
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }

                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(context, "End", Toast.LENGTH_SHORT).show();
                            holder.iv_play.setEnabled(true);
                            holder.iv_play.setVisibility(View.VISIBLE);
                            holder.iv_pause.setVisibility(View.GONE);
                        }
                    });
                }

                mPlayer.reset();
                // Set the media player audio stream type
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //Try to play music/audio from url
                try {
                    // Set the audio data source
                    mPlayer.setDataSource(musicList.get(position).getUrl());
                    // Prepare the media player
                    mPlayer.prepare();
                    // Start playing audio from http url
                    mPlayer.start();

                    // Inform user for audio streaming
                    Toast.makeText(context, "Playing", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    // Catch the exception
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }

                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(context, "End", Toast.LENGTH_SHORT).show();
                        holder.iv_play.setEnabled(true);
                        holder.iv_play.setVisibility(View.VISIBLE);
                        holder.iv_pause.setVisibility(View.GONE);
                    }
                });
            }

        });

        holder.iv_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iv_pause.setVisibility(View.GONE);
                holder.iv_play.setVisibility(View.VISIBLE);
                mPlayer.pause();
                isPaused = true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_song_name;
        ImageView iv_play, iv_pause;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_song_name = itemView.findViewById(R.id.tv_song_name);
            iv_play = itemView.findViewById(R.id.iv_play);
            iv_pause = itemView.findViewById(R.id.iv_pause);
            iv_play.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int potion = getAdapterPosition();

        }
    }
}
