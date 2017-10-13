package hengai.com.shishuo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/10/12.
 */

public class AllWebActivity extends AppCompatActivity {
    @InjectView(R.id.webview)
    WebView mWebview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_web);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        String url=getIntent().getStringExtra("webUrl");

        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        mWebview.loadUrl(url);

    }
}
