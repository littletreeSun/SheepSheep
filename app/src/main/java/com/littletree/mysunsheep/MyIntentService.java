package com.littletree.mysunsheep;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * @ProjectName: MySunSheep
 * @Package: com.littletree.mysunsheep
 * @ClassName: MyIntentService
 * @Author: littletree
 * @CreateDate: 2022/11/7/007 16:36
 */
public class MyIntentService extends Service {
    public MyIntentService() {
    }

    //创建播放器对象
    private MediaPlayer player;
    private int media_position;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取MainActivity中 按钮的点击类型：根据不同类型处理不同事件
        String action = intent.getStringExtra("action");
        if ("play".equals(action)) {
            //播放
            playMusic();
        } else if ("stop".equals(action)) {
            //停止
            stopMusic();
        } else if ("pause".equals(action)) {
            //暂停
            pauseMusic();
        } else if ("resume".equals(action)) {
            //恢复
            resumeMusic();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 播放音乐
     */
    public void playMusic() {
        if (player == null ) {
            player= MediaPlayer.create(this,R.raw.voice_background);
            //是否循环播放
            player.setLooping(true);
        }
        player.start();
    }

    /**
     * 暂停播放
     */
    public void pauseMusic() {
        if (player != null && player.isPlaying()) {
            player.pause();
            media_position = player.getCurrentPosition();
        }
    }

    /**
     * 恢复播放
     */
    public void resumeMusic() {
        if (player != null) {
            player.seekTo(media_position);
            player.start();
        }
    }

    /**
     * 停止播放
     */
    public void stopMusic() {
        if (player != null) {
            player.stop();
            player.reset();//重置
            player.release();//释放
            player = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在服务死亡之前停止音乐
        stopMusic();
    }
}