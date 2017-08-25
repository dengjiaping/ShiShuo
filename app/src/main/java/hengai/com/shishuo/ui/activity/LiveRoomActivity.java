package hengai.com.shishuo.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/8/24.
 */

public class LiveRoomActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_live_room);
        TextView textView= (TextView) findViewById(R.id.tv_title);
    }
}
