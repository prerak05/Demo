package com.prerak.demo.music.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.prerak.demo.R;
import com.prerak.demo.music.Utilities;
import com.prerak.demo.music.model.Music;
import com.prerak.demo.music.service.MusicService;
import com.prerak.demo.music.util.StorageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emxcel on 10/1/18.
 */

public class TestFragment extends AppCompatActivity {

    // variable declaration
    public static final String Broadcast_PLAY_NEW_AUDIO = "com.prerak.demo.music.fragment.PlayNewAudio";
    private View songPlayView;
    private ImageButton btnPlay;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnPlaylist;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private ImageView iv_song_image;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    int position;
    ArrayList<Music> musicList = new ArrayList<>();
    private MusicService player;
    boolean serviceBound = false;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private Utilities utils;
    private Handler mHandler = new Handler();
    private BroadcastReceiver mReceiver;
    String songName;
    boolean pause = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_song_play);
        // initialization method
        init();

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            position = bundle.getInt("position");
            musicList = bundle.getParcelableArrayList("list");
        } else {
            Toast.makeText(player, "data null", Toast.LENGTH_SHORT).show();
        }
        playAudio(musicList.get(position).getId());

        songTitleLabel.setText(musicList.get(position).getName());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestFragment.this, MusicService.class);
                intent.putExtra("action", "forward");
                player.onBind(intent);
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestFragment.this, MusicService.class);
                intent.putExtra("action", "backward");
                player.onBind(intent);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pause == true) {
                    Intent intent = new Intent(TestFragment.this, MusicService.class);
                    intent.putExtra("action", "pause");
                    player.onBind(intent);
                    btnPlay.setImageResource(R.drawable.btn_play);
                    pause = false;
                } else {
                    Intent intent = new Intent(TestFragment.this, MusicService.class);
                    intent.putExtra("action", "play");
                    player.onBind(intent);
                    btnPlay.setImageResource(R.drawable.btn_pause);
                    pause = true;
                }
            }
        });
    }


    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;
            btnPlay.setImageResource(R.drawable.btn_pause);
            pause = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    private void playAudio(int media) {
        //Check is service is active
        if (!serviceBound) {
            Intent playerIntent = new Intent(this, MusicService.class);
            playerIntent.putExtra("media", musicList.get(position).getUrl());
            playerIntent.putExtra("position", position);
            playerIntent.putParcelableArrayListExtra("list", musicList);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            sendBroadcast(broadcastIntent);
            btnPlay.setImageResource(R.drawable.btn_pause);
            pause = true;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (new StorageUtil(this).loadAudioIndex() == 0) {
            btnPlay.setImageResource(R.drawable.btn_play);
        } else if (new StorageUtil(this).loadAudioIndex() == 1) {
            btnPlay.setImageResource(R.drawable.btn_pause);
        }
        IntentFilter intentFilter = new IntentFilter(
                "android.intent.action.MAIN");

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent

                if (intent != null) {
                    songName = intent.getStringExtra("songName");
                    Toast.makeText(context, "songName" + songName, Toast.LENGTH_SHORT).show();

                    songTitleLabel.setText(songName);
                    if (intent.getStringExtra("pause") != null) {
                        pause = false;
                        btnPlay.setImageResource(R.drawable.btn_play);
                        songTitleLabel.setText(intent.getStringExtra("songName"));
                    }else if (intent.getStringExtra("play")!= null){
                        pause =true;
                        btnPlay.setImageResource(R.drawable.btn_play);
                        songTitleLabel.setText(intent.getStringExtra("songName"));
                    }
                }
                //log our message value
//                Log.i("InchooTutorial", msg_for_me);
            }
        };
        //registering our receiver
        this.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //unregister our receiver
        this.unregisterReceiver(this.mReceiver);
    }

    private void init() {
        // All player buttons
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        iv_song_image = (ImageView) findViewById(R.id.iv_song_pic);
    }
}
