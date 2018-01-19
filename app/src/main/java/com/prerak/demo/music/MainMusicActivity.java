package com.prerak.demo.music;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prerak.demo.R;
import com.prerak.demo.music.adapter.MusicAdapter;
import com.prerak.demo.music.adapter.SongListAdapter;
import com.prerak.demo.music.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MainMusicActivity extends AppCompatActivity {
    // variable declaration
    private RecyclerView recyclerView;
    private ArrayList<Music> musicList = new ArrayList<>();
    private Music music;
    private MusicAdapter musicAdapter;
    private SongListAdapter songListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_music);

        // add data in list
        music = new Music(1,"Dil Diyan Gallan","https://dl.jatt.link/hd.jatt.link/74ee50e5422b1409305bdec7691a87a2/inmzv/Dil Diyan Gallan-(SwagyJatt.CoM).mp3","https://static.jatt.link/thumbs/100_100/efgai.jpg");
        musicList.add(music);

        Music music1= new Music(2,"Swage Se Swagat","https://dl.jatt.link/hd.jatt.link/f6845447ed4a45c3033a32860e5df6a1/fimzv/Swag Se Swagat-(SwagyJatt.CoM).mp3","https://static.jatt.link/thumbs/100_100/ntrai.jpg");
        musicList.add(music1);

        Music music2 = new Music(3,"Ban Ja Rani","http://file.mp3sun.com/siteuploads/files/sfd3/1107/Ban%20Ja%20Meri%20Rani%20Tumhari%20Sulu_320-(Mp3Sun.Com).mp3","http://lq.djring.com/covers/56513.jpg");
        musicList.add(music2);

        Music music3 = new Music(4,"Dil Chori","https://downpwnew.com/upload_file/5570/6757/Latest%20Bollywood%20Hindi%20Mp3%20Songs%20-%202017/Dil%20Chori%20-%20Yo%20Yo%20Honey%20Singh%20Mp3%20Song/Dil%20Chori%20-%20Yo%20Yo%20Honey%20Singh%20320Kbps.mp3","https://downpwnew.com/upload_file/5570/6757/Latest%20Bollywood%20Hindi%20Mp3%20Songs%20-%202017/Dil%20Chori%20-%20Yo%20Yo%20Honey%20Singh%20Mp3%20Song/thumb-Dil%20Chori%20-%20Yo%20Yo%20Honey%20Singh%20320Kbps.jpg");
        musicList.add(music3);

        Music music4 = new Music(5,"Mere Rashke Qamar","http://lq.djring.com/48/480483/Mere Rashke Qamar - Arijit Singh (DJJOhAL.Com).mp3","http://lq.djring.com/covers/56513.jpg");
        musicList.add(music4);

        Music music5 = new Music(6,"Chalti Hai Kya 9 Se 12","https://downpwnew.com/upload_file/5570/6757/Latest%20Bollywood%20Hindi%20Mp3%20Songs%20-%202017/Judwaa%202%20%282017%29%20Hindi%20Movie%20Mp3%20Songs/01%20Chalti%20Hai%20Kya%209%20Se%2012%20-%20Judwaa%202%20-%20190Kbps.mp3","http://lq.djring.com/covers/56513.jpg");
        musicList.add(music5);

        Music music6 = new Music(7,"Pallo Latke","http://file.mp3sun.com/siteuploads/files/sfd4/1782/Pallo%20Latke%20Shaadi%20Mein%20Zaroor%20Aana_320-(Mp3Sun.Com).mp3","http://file.mp3sun.com/siteuploads/thumb/sft4/1782_6.jpg");
        musicList.add(music6);

        Music music7 = new Music(8,"Mahebooba","http://file.mp3sun.com/siteuploads/files/sfd9/4196/Mehbooba%20Fukrey%20Returns_320-(Mp3Sun.Com).mp3","http://file.mp3sun.com/siteuploads/thumb/sft9/4196_6.jpg");
        musicList.add(music7);

        Music music8 = new Music(9,"Ghoomar","http://file.mp3sun.com/siteuploads/files/sfd4/1805/Ghoomar%20Padmavati_320-(Mp3Sun.Com).mp3","https://downpwnew.com/upload_file/5570/Top%20Mp3%20Songs%20Bollywood%20Pop/Bollywood%20Top%20Mp3%20Songs%202017/thumb-Ghoomar%20-%20Padmavati%20320Kbps.jpg");
        musicList.add(music8);

        Music music9 = new Music(10,"Main Tera Boyfriend","https://downpwnew.com/upload_file/5570/6757/Latest%20Bollywood%20Hindi%20Mp3%20Songs%20-%202017/Raabta%20%282017%29%20Hindi%20Movie%20Mp3%20Songs/05%20Main%20Tera%20Boyfriend%20-%20Raabta%20%28Arijit%20Singh%29%20320Kbps.mp3","http://file.mp3sun.com/siteuploads/thumb/sft4/1782_6.jpg");
        musicList.add(music9);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_music);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
          recyclerView.setLayoutManager(new GridLayoutManager(MainMusicActivity.this,2));
        recyclerView.setHasFixedSize(true);
//        musicAdapter = new MusicAdapter(MainMusicActivity.this,musicList);
         songListAdapter = new SongListAdapter(MainMusicActivity.this,musicList);
        recyclerView.setAdapter(songListAdapter);

    }
}
