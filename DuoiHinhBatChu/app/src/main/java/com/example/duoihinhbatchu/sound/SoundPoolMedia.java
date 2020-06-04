package com.example.duoihinhbatchu.sound;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.duoihinhbatchu.R;

public class SoundPoolMedia extends Activity
{
    static SoundPool soundPool;
    static int[] sm;

    public void InitSound() {

        int maxStreams = 1;
        Context mContext = (Context) getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(maxStreams)
                    .build();
        } else {
            soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        }

        sm = new int[3];
        // fill your sounds
        sm[0] = soundPool.load(mContext, R.raw.batdau, 1);
        sm[1] = soundPool.load(mContext, R.raw.chienthang, 1);
        sm[2] = soundPool.load(mContext, R.raw.dung, 1);

    }

    public void playSound(int sound) {

        soundPool.play(sm[sound], 1, 1, 1, 0, 1f);
    }

    public final void cleanUpIfEnd() {
        sm = null;
        soundPool.release();
        soundPool = null;
    }
}