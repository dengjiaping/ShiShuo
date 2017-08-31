package hengai.com.shishuo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bokecc.sdk.mobile.play.DWMediaPlayer;

import java.util.Map;

import hengai.com.shishuo.R;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by yu on 2017/8/27.
 */

public class VideoPlayActivity extends AppCompatActivity {
    // 默认设置为普清
    private int defaultDefinition = DWMediaPlayer.NORMAL_DEFINITION;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_video_play);
        initData();
    }

    private void initData() {
        String videoId ="123456";

        Context context = getApplicationContext();

        DWMediaPlayer player = new DWMediaPlayer();

//设置视频播放信息

        player.setVideoPlayInfo(videoId,"USERID", "API_KEY", context);

        player.prepareAsync();


        //获取清晰度列表

        Map<String,Integer> definitions = player.getDefinitions();

//获取某种清晰度对应的状态码

        int definitionCode = definitions.get("definitionInfo");

       //设置播放清晰度
        //player.setDefinition(context,defaultDefinition);
    }
}
