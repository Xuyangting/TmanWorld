package com.tencent.tmanapp.bgm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.tencent.tmanapp.R;
import com.tencent.tmanapp.util.Config;

public class BgmService extends Service {
    private MediaPlayer mediaPlayer;
    private boolean isStop=true;

    public BgmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //在此方法中服务被创建
    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer==null){
            mediaPlayer=new MediaPlayer();

            //为播放器添加播放完成时的监听器
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d(Config.tag, "bgm complete");
                }
            });
        }
    }

    /**
     * 在此方法中，可以执行相关逻辑，如耗时操作
     * @param intent :由Activity传递给service的信息，存在intent中
     * @param flags ：规定的额外信息
     * @param startId ：开启服务时，如果有规定id，则传入startid
     * @return 返回值规定此startservice是哪种类型，粘性的还是非粘性的
     *          START_STICKY:粘性的，遇到异常停止后重新启动，并且intent=null
     *          START_NOT_STICKY:非粘性，遇到异常停止不会重启
     *          START_REDELIVER_INTENT:粘性的，重新启动，并且将Context传递的信息intent传递
     * 此方法是唯一的可以执行很多次的方法
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getIntExtra("type",-1)){
            case Config.PLAY_MUSIC:
                Log.d(Config.tag, "start bgm");
                if (isStop){
                    //重置 media player
                    mediaPlayer.reset();
                    //将需要播放的资源与之绑定
                    mediaPlayer=MediaPlayer.create(this, R.raw.bgm);
                    //开始播放
                    mediaPlayer.start();
                    //是否循环播放
                    mediaPlayer.setLooping(false);
                    isStop=false;
                }else {
                    mediaPlayer.start();
                }
                break;
            case Config.PAUSE_MUSIC:
                Log.d(Config.tag, "pause bgm");
                //播放器不为空，并且正在播放
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case Config.STOP_MUSIC:
                Log.d(Config.tag, "stop bgm");
                //停止之后要开始播放音乐
                mediaPlayer.stop();
                isStop=true;
                break;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
