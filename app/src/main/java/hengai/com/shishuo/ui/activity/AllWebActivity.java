package hengai.com.shishuo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
        //settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebview.loadUrl(url);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            // 返回上一页面
            mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
