package com.littletree.mysunsheep.customview;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.littletree.mysunsheep.R;

/**
 * @ProjectName: MySunSheep
 * @Package: com.littletree.mysunsheep.customview
 * @ClassName: PlayVoice
 * @Author: littletree
 * @CreateDate: 2022/11/3/003 17:50
 * 声音
 */
public class PlayVoice {
    private static MediaPlayer clickMediaPlayer;

    public static void playClickVoice(Context context){
        try {
            if (null ==clickMediaPlayer){
                clickMediaPlayer= MediaPlayer.create(context, R.raw.voice_click);
            }
            clickMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //销毁声音
    public  static void destoryVoice(){
        if (null!= clickMediaPlayer){
            clickMediaPlayer.stop();
            clickMediaPlayer.release();
            clickMediaPlayer = null;
        }
    }
}
