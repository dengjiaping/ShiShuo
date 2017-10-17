package hengai.com.shishuo.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hengai.com.shishuo.R;
import hengai.com.shishuo.ui.adapter.PhotoGridAdapter2;
import hengai.com.shishuo.ui.widget.NoScrollGridView;
import hengai.com.shishuo.utils.ClickFilter;
import hengai.com.shishuo.utils.UriToPathUtil;


/**
 * Created by yu on 2017-10-15.
 */
public class PublishTribeInfoActivity_2 extends Activity implements TextWatcher{
    private EditText editContent;
    private ImageView selectedPicture;
    private NoScrollGridView photoGridView;
    private static final int PHOTO_REQUEST_CAREMA = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;
    //    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private File[] files = null;
    private int fileLength = 0;
    private List<Uri> uriList = null;
    private PhotoGridAdapter2 adapter2;
    private String content = null;
    private ProgressDialog dialog;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tribe_publish_2);
        uriList = new ArrayList<Uri>();
        editContent = (EditText) this.findViewById(R.id.et_tribe_content);
        saveBtn = (Button)this.findViewById(R.id.bt_save);
        editContent.addTextChangedListener(this);
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示信息");
        dialog.setMessage("正在发布动态，请稍后......");
        findViewById(R.id.im_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    tempFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                    Uri uri = Uri.fromFile(tempFile);
                    uriList.add(uri);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
                }
            }
        });

        findViewById(R.id.im_select_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(); //开启Pictures画面Type设定为image
                intent.setType("image/*"); //使用Intent.ACTION_GET_CONTENT这个Action
                intent.setAction(Intent.ACTION_GET_CONTENT); // 取得相片后返回本画面
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        });
       saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickFilter.filter()) {
                    return;
                }
                content = editContent.getText().toString().trim();
                if (content.equals("") && uriList.size() == 0) {
                    TastyToast.makeText(PublishTribeInfoActivity_2.this,"发布内容不能为空！",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                } else {
                    dialog.show();
                    dialog.setCancelable(false);
                    if (uriList != null) {
                        files = new File[uriList.size()];
                        for (int i = 0; i < uriList.size(); i++) {
                            files[i] = new File(new UriToPathUtil().getImageAbsolutePath(PublishTribeInfoActivity_2.this, uriList.get(i)));
                        }
                    }
                    //uploadTribeInfo(); //上传数据给后台
                    TastyToast.makeText(PublishTribeInfoActivity_2.this,"发布成功！",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                saveBtn.setEnabled(true);
                Uri uri = data.getData();
                uriList.add(uri);
                photoGridView = (NoScrollGridView) this.findViewById(R.id.gv_tribe_photo);
                adapter2 = new PhotoGridAdapter2(uriList, PublishTribeInfoActivity_2.this);
                adapter2.notifyDataSetChanged();
                photoGridView.setAdapter(adapter2);
            }

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                saveBtn.setEnabled(true);
                photoGridView = (NoScrollGridView) this.findViewById(R.id.gv_tribe_photo);
                adapter2 = new PhotoGridAdapter2(uriList, PublishTribeInfoActivity_2.this);
                adapter2.notifyDataSetChanged();
                photoGridView.setAdapter(adapter2);
            }
        }
    }

    public void uploadTribeInfo() {
        /*NewsCreate req = new NewsCreate();
        if (GlobalData.instance().getLoginUser() != null) {
            req.setUserID(GlobalData.instance().getLoginUser().getUserID());
            req.setToken(GlobalData.instance().getLoginUser().getToken());
        }
        req.setContent(content);
        BoBase<NewsListResp> bo = new BoBase<NewsListResp>();
//        if (uriList != null) {
//            files = new File[uriList.size()];
//            for (int i = 0; i < uriList.size(); i++) {
//                files[i] = new File(getImageAbsolutePath(PublishTribeInfoActivity_2.this, uriList.get(i)));
//            }
//        }
        if (files.length == 0) {
            bo.request("/news/create", req, new NewsListResp(), new Pipe() {
                @Override
                public void complete(Object owner, Object data, int success) {
                    if (success >= Pipe.NET_SUCCESS) {
                        Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "部落动态发布成功！");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                    } else if (success == Pipe.SERVER_ERROR) {
                        if (null == data) {
                            Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "数据访问出错，请重试");
                        } else {
                            if (data instanceof ErrorInfo) {
                                ErrorInfo errorInfo = (ErrorInfo) data;
                                if (errorInfo.getErrcode() == 1) {
                                    Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "权限认证错误，请重新登陆");
                                } else if (errorInfo.getErrcode() == 2) {
                                    Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "课程号不存在");
                                } else if (errorInfo.getErrcode() == 3) {
                                    Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "截图上传失败");
                                }
                            } else {
                                Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "请求数据失败");
                            }
                        }
                    } else {
                        Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "请求数据失败，请检查网络或重试");
                    }
                }
            });
        } else {
            bo.request("/news/create", req, new NewsListResp(), files, new Pipe() {
                @Override
                public void complete(Object owner, Object data, int success) {
                    if (success >= Pipe.NET_SUCCESS) {
                        Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "部落动态发布成功！");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                    } else if (success == Pipe.SERVER_ERROR) {
                        if (null == data) {
                            Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "数据访问出错，请重试");
                        } else {
                            if (data instanceof ErrorInfo) {
                                ErrorInfo errorInfo = (ErrorInfo) data;
                                if (errorInfo.getErrcode() == 1) {
                                    Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "权限认证错误，请重新登陆");
                                } else if (errorInfo.getErrcode() == 2) {
                                    Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "课程号不存在");
                                } else if (errorInfo.getErrcode() == 3) {
                                    Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "截图上传失败");
                                }
                            } else {
                                Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "请求数据失败");
                            }
                        }
                    } else {
                        Helper.showToastOnUiThread(PublishTribeInfoActivity_2.this, "请求数据失败，请检查网络或重试");
                    }
                }
            });
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        saveBtn.setEnabled(true);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    public void onBackClicked(View view) {
        finish();
    }
}
