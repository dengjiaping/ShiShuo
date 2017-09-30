package hengai.com.shishuo.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;

import hengai.com.shishuo.R;
import hengai.com.shishuo.ui.widget.SmartRefreshWebLayout;
import hengai.com.shishuo.utils.DownloadController;

/**
 * Created by yu on 2017/9/20.
 */

public class BaseWebActivity extends AppCompatActivity {

    protected AgentWeb mAgentWeb;
    private AlertDialog mAlertDialog;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web);
        mLinearLayout = (LinearLayout) this.findViewById(R.id.container);

        long p = System.currentTimeMillis();
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .setReceivedTitleCallback(mCallback)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecutityType(AgentWeb.SecurityType.strict)
                .setWebLayout(new SmartRefreshWebLayout(this))
                .createAgentWeb()//
                .ready()
                .go(getUrl());
        long n = System.currentTimeMillis();

    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
            //       Log.i("Info","progress:"+newProgress);
        }
    };
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };

    public String getUrl() {
        //https://m.jd.com/
        return "http://x.iyoukiss.com/qbexamsess_ent.html?sesscode=6rq1zovb&checkopen=fDjeG2Tz1JEhZtERm4btxsz1NUCfvBrBsf5uhmvsLx4eeFSc&from=groupmessage";
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {

        }
    };

    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DownloadController.setBackDownload(true);
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DownloadController.setBackDownload(false);
                        finish();
                    }
                }).setTitle("您确定要关闭该页面吗？")
                .create();

        dialog.show();
    }

   /* private void showDialog() {
        if (mAlertDialog == null)
            mAlertDialog = new AlertDialog.Builder(this)
                    .setTitle("您确定要关闭该页面吗?")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null)
                                mAlertDialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null)
                                mAlertDialog.dismiss();
                            BaseWebActivity.this.finish();
                        }
                    }).create();
        mAlertDialog.show();

    }
*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            showDialog();
        }
       /* return super.onKeyDown(keyCode, event);*/
        return false;
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("Info", "result:" + requestCode + " result:" + resultCode);
        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
