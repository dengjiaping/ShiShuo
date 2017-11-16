package hengai.com.shishuo.ui.activity.myactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/11/16.
 */

public class MyUploadedVideo extends AppCompatActivity {

    private Context mCtx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myuploaded_video);
        mCtx = getApplicationContext();

    }
}
