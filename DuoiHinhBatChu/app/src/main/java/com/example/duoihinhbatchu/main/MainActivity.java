package com.example.duoihinhbatchu.main;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.duoihinhbatchu.database.MyDatabase;
import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.sound.SoundPoolMedia;

public class MainActivity extends AppCompatActivity {
    private final int NUMBER_OF_SIMULTANEOUS_SOUNDS = 5;
    Button btPlay,btMusic;

    SoundPoolMedia soundPoolMedia = new SoundPoolMedia();
    public SoundPool soundPool;
    private AudioManager audioManager;

    private static final int streamType = AudioManager.STREAM_MUSIC;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        // Đối tượng AudioManager sử dụng để điều chỉnh âm lượng.
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        // Chỉ số âm lượng hiện tại của loại luồng nhạc cụ thể (streamType).
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);

        soundPool= new SoundPool(NUMBER_OF_SIMULTANEOUS_SOUNDS, AudioManager.STREAM_MUSIC, 100);

        // Chỉ số âm lượng tối đa của loại luồng nhạc cụ thể (streamType).
        float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(streamType);


        // Cho phép thay đổi âm lượng các luồng kiểu 'streamType' bằng các nút
        // điều khiển của phần cứng.
        this.setVolumeControlStream(streamType);



        // bắt sự kiện nút play
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, Manhinh_game.class);
                startActivity(i);

            }
        });
        // bắt sự kiện nút bật nhạc

        btMusic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                   soundPool.play(soundPool.load(MainActivity.this,R.raw.batdau,1),1,1,1,0,1f);
            }
        });
    }
    


    private void init(){
        btPlay = findViewById(R.id.buttonPlay);
        btMusic = findViewById(R.id.buttonMusic);


    }
}
