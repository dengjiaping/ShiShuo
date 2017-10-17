package hengai.com.shishuo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import hengai.com.shishuo.R;
import hengai.com.shishuo.ui.widget.NoScrollGridView;


/**
 * Created by pc on 2017-05-09.
 */
public class TribeActivity extends Activity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Button commentButton;
    //private TribeAdapter tribeAdapter;
    //private List<MetaNews> messageList;
    private ImageView commentImg, praiseImg;
    private LinearLayout commentLinear;
    private boolean isReply;
    private EditText commentEdit;
    private NoScrollGridView show_zan;
    private String comment = "";
    private int index_pos, index_praise_pos;
    private int index_pos_huifu_pos;
    private int index_pos_huifu;
    private int curPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tribe_2);
        //init();
        //updateData();
    }

    private void init() {
        commentEdit = (EditText) findViewById(R.id.commentEdit);
        commentLinear = (LinearLayout) findViewById(R.id.commentLinear);
        commentButton = (Button) findViewById(R.id.commentButton);

        //messageList = new ArrayList<MetaNews>();
        /*swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout_tribe);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_red_light);
        initSwipeRefresh();*/

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_tribe);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TribeActivity.this);
        //tribeAdapter = new TribeAdapter(messageList, TribeActivity.this);
        //recyclerView.setAdapter(tribeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
                int lastChildBottom = lastChildView.getBottom();
                int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);
                if (lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                    curPage++;
                    updateData();
                }
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isReply == false) {
                    if (commentEdit.getText().toString().trim().isEmpty()) {
                        Toast.makeText(TribeActivity.this, "输入内容为空", Toast.LENGTH_SHORT).show();
                    } else {
                        //PingLunMethod();
                    }
                } else {
                    if (commentEdit.getText().toString().trim().isEmpty()) {
                        Toast.makeText(TribeActivity.this, "输入内容为空", Toast.LENGTH_SHORT).show();
                    } else {
                        //HuiFuMethod();
                    }
                }
                commentEdit.setText("");
                onFocusChange(false);

            }
        });
    }


    private void initSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                curPage = 0;
                updateData();
            }
        });
    }

    private void updateData() {
       /* NewsList req = new NewsList();
        if (GlobalData.instance().getLoginUser() != null) {
            req.setUserID(GlobalData.instance().getLoginUser().getUserID());
            req.setToken(GlobalData.instance().getLoginUser().getToken());
        }
        req.setPagestart(curPage);
        BoBase<NewsListResp> bo = new BoBase<NewsListResp>();
        bo.request("/news/list", req, new NewsListResp(), new Pipe() {
            @Override
            public void complete(Object owner, Object data, int success) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (swipeRefreshLayout.isRefreshing()) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });
                } catch (NullPointerException e) {
                    return;
                }
                NewsListResp newsListResp = (NewsListResp) data;
                if (success >= Pipe.NET_SUCCESS) {

                    List<MetaNews> list = newsListResp.getNewses();
                    if (list.size() > 0) {
                        if (swipeRefreshLayout.isRefreshing()) {
                            if (!messageList.isEmpty()) {
                                messageList.clear();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (swipeRefreshLayout.isRefreshing()) {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
                            });
                        }
                        messageList.addAll(list);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tribeAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } else if (success == Pipe.SERVER_ERROR) {
                    if (null == data) {
                        Helper.showToastOnUiThread(TribeActivity.this, "更新数据失败");
                    } else {
                        if (newsListResp.getErrcode() == 1) {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "权限认证错误，请重新登录");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(TribeActivity_2.this, FirstActivity_2.class));
                                    finish();
                                }
                            });
                        } else if (newsListResp.getErrcode() == 2) {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "获取动态列表失败");
                        } else {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败");
                        }
                    }
                } else {
                    Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败，请检查网络或重试");
                }
            }
        });*/

    }

    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) commentEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (isFocus) {
                    commentLinear.setVisibility(View.VISIBLE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    commentLinear.setVisibility(View.GONE);
                    imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
                }
            }
        }, 100);
    }

   /* public class TribeAdapter extends RecyclerView.Adapter<TribeAdapter.ViewHolder> {

        //private List<MetaNews> messageList;
        private Context context;
        private AlertDialog.Builder builder;

        *//*public TribeAdapter(List<MetaNews> messageList, Context context) {
            this.messageList = messageList;
            this.context = context;
        }*//*

       *//* @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tribe_2, parent, false);
            return new ViewHolder(v);
        }*//*

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {*/

          /*  MetaNews tribeMessage = messageList.get(position);
            viewHolder.tribeMessage = tribeMessage;
            if (tribeMessage.getUser().getUserID().equals(GlobalData.instance().getLoginUser().getUserID())) {
                viewHolder.delete.setVisibility(View.VISIBLE);
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder = new AlertDialog.Builder(context);
                        builder.setTitle("删除动态").
                                setMessage("确定要删除该动态吗？").
                                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(context, "用户点击了确定按钮", Toast.LENGTH_LONG).show();
                                        deleteNews(position);
                                    }
                                }).
                                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(context, "用户点击了取消按钮", Toast.LENGTH_LONG).show();
                                    }
                                });
                        builder.create().show();
                    }
                });
            } else {
                viewHolder.delete.setVisibility(View.GONE);
            }
            if (tribeMessage.getContent() != null) {
                viewHolder.message.setText(tribeMessage.getContent());
            }
            if (tribeMessage.getUpdated() != null) {
                viewHolder.updated.setText(DateUtil.parseDateToFormal(tribeMessage.getUpdated().replace("Z", " UTC")));
            }
            if (tribeMessage != null) {
                viewHolder.commentNum.setText(tribeMessage.getCommentnums() + "");
                viewHolder.likeNum.setText(tribeMessage.getLikenums() + "");
            }
            if (null != tribeMessage.getUser()) {
                Glide.with(context)
                        .load(BoBase.BASE_URL + tribeMessage.getUser().getAvatar())
                        .centerCrop()
                        .transform(new GlideCircleTransform(context))
                        .into(viewHolder.avatar);
                viewHolder.name.setText(tribeMessage.getUser().getNickname());
            }

            if (tribeMessage != null && tribeMessage.getImages() != null && tribeMessage.getImages().size() > 0) {
                viewHolder.photoGridView.setVisibility(View.VISIBLE);
                viewHolder.photoGridView.setAdapter(new PhotoGridApapter(tribeMessage.getImages(), context));
                if (tribeMessage.getImages().size() > 0) {
                    String[] urls = new String[tribeMessage.getImages().size()];
                    for (int i = 0; i < tribeMessage.getImages().size(); i++) {
                        urls[i] = BoBase.BASE_URL + tribeMessage.getImages().get(i);
                    }
                    viewHolder.photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int positon, long id) {
                            Intent intent = new Intent(context, PhotoPagerActivity.class);
                            intent.putExtra(PhotoPagerActivity.EXTRA_PHOTO_URLS, urls);
                            intent.putExtra(PhotoPagerActivity.EXTRA_PHOTO_STATE_POSITION, positon);
                            context.startActivity(intent);
                        }
                    });
                }
            } else {
                viewHolder.photoGridView.setVisibility(View.GONE);
            }
            if (tribeMessage != null && tribeMessage.getComments() != null && tribeMessage.getComments().size() > 0) {
                viewHolder.commentNum.setText(tribeMessage.getComments().size() + "");
                viewHolder.replyList.setVisibility(View.VISIBLE);
                ReplyAdapter adapter = new ReplyAdapter(context, tribeMessage.getComments(), R.layout.item_reply_2);
                viewHolder.replyList.setAdapter(adapter);
            } else {
                viewHolder.replyList.setVisibility(View.GONE);
            }

            if (tribeMessage.getLikeusers().size() == 0) {
                viewHolder.show_praise.setVisibility(View.GONE);
            } else {
                viewHolder.show_praise.setVisibility(View.VISIBLE);
                String likeUserNames = tribeMessage.getLikeusers().get(0).getNickname();
                for (int i = 1; i < tribeMessage.getLikeusers().size(); i++) {
                    likeUserNames = likeUserNames + "," + tribeMessage.getLikeusers().get(i).getNickname();
                }
                viewHolder.likeNum.setText(tribeMessage.getLikeusers().size() + "");
                viewHolder.likeuserName.setText(likeUserNames);
            }
            viewHolder.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentEdit.setHint("");
                    index_pos = position;
                    isReply = false;
                    commentLinear.setVisibility(View.VISIBLE);
                    onFocusChange(true);
                }
            });
            viewHolder.praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    like(position);
                }
            });
            viewHolder.replyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                    index_pos_huifu = position1;
                    index_pos_huifu_pos = position;
                    isReply = true;
                    commentEdit.setHintTextColor(getResources().getColor(R.color.sign_in_edit_hint_color));
                    commentEdit.setHint("@" + messageList.get(position).getComments().get(position1).getUser().getNickname());
                    commentLinear.setVisibility(View.VISIBLE);
                    onFocusChange(true);
                }
            });

        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            super.onViewRecycled(holder);
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView avatar;
            TextView name;
            TextView likeNum;
            TextView commentNum;
            TextView message;
            TextView updated;
            NoScrollGridView photoGridView;
            MetaNews tribeMessage;
            NoScrollListView replyList;
            ImageView comment, praise;
            RelativeLayout show_praise;
            TextView likeuserName;
            TextView delete;

           *//* public ViewHolder(View itemView) {
                super(itemView);
                avatar = (ImageView) itemView.findViewById(R.id.im_tribe_avatar);
                name = (TextView) itemView.findViewById(R.id.tv_tribe_name);
                likeNum = (TextView) itemView.findViewById(R.id.tv_tribe_like_num);
                commentNum = (TextView) itemView.findViewById(R.id.tv_tribe_comment_num);
                message = (TextView) itemView.findViewById(R.id.tv_tribe_message);
                updated = (TextView) itemView.findViewById(R.id.tv_tribe_updated);
                photoGridView = (NoScrollGridView) itemView.findViewById(R.id.gv_tribe_photo);
                replyList = (NoScrollListView) itemView.findViewById(R.id.replyList);
                comment = (ImageView) itemView.findViewById(R.id.icon_tribe_comment);
                praise = (ImageView) itemView.findViewById(R.id.im_tribe_praise);
                show_praise = (RelativeLayout) itemView.findViewById(R.id.rl_show_praise);
                likeuserName = (TextView) itemView.findViewById(R.id.tv_likeuser_name);
                delete = (TextView) itemView.findViewById(R.id.tv_tribe_delete);
            }
        }
    }*/

    public void PingLunMethod() {
        /*ReplyCreate req = new ReplyCreate();
        final String commentString = commentEdit.getText().toString().trim();
        req.setNewsID(messageList.get(index_pos).getNewsID());
        req.setContent(commentString);
        req.setType("text");
        req.setToken(GlobalData.instance().getLoginUser().getToken());
        req.setUserID(GlobalData.instance().getLoginUser().getUserID());
        BoBase<NewsListResp> bo = new BoBase<NewsListResp>();
        bo.request("/news/comments/create", req, new NewsListResp(), new Pipe() {
            @Override
            public void complete(Object owner, Object data, int success) {
                NewsListResp newsListResp = (NewsListResp) data;
                if (success >= Pipe.NET_SUCCESS) {
                    List<MetaReply> replyList = new ArrayList<MetaReply>();
                    if (messageList.get(index_pos).getComments() != null) {
                        replyList.addAll(messageList.get(index_pos).getComments());
                    }
                    if (GlobalData.instance().getLoginUser() != null)
                        replyList.add(new MetaReply(GlobalData.instance().getLoginUser(), "", commentString));
                    messageList.get(index_pos).setComments(replyList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tribeAdapter.notifyDataSetChanged();
                        }
                    });
                } else if (success == Pipe.SERVER_ERROR) {
                    if (null == data) {
                        Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败");
                    } else {
                        if (newsListResp.getErrcode() == 1) {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "权限认证错误，请重新登陆");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(TribeActivity_2.this, FirstActivity_2.class));
                                    finish();
                                }
                            });
                        } else if (newsListResp.getErrcode() == 2) {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "获取动态列表失败");
                        }
                    }
                } else {
                    Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败，请检查网络或重试");
                }
            }
        });*/
    }

    public void HuiFuMethod() {
        /*ReplyCreate req = new ReplyCreate();
        final String replyString = commentEdit.getText().toString().trim();
        req.setNewsID(messageList.get(index_pos_huifu_pos).getNewsID());
        req.setContent(replyString);
        req.setType("text");
        if (GlobalData.instance().getLoginUser() != null) {
            req.setToken(GlobalData.instance().getLoginUser().getToken());
            req.setUserID(GlobalData.instance().getLoginUser().getUserID());
            req.setReplytoName(messageList.get(index_pos_huifu_pos).getComments().get(index_pos_huifu).getUsername());
        }
//        req.setReplyto(messageList.get(index_pos_huifu_pos).getComments().get(index_pos_huifu).getUsername());
        BoBase<NewsListResp> bo = new BoBase<NewsListResp>();
        bo.request("/news/comments/create", req, new NewsListResp(), new Pipe() {
            @Override
            public void complete(Object owner, Object data, int success) {
                NewsListResp newsListResp = (NewsListResp) data;
                if (success >= Pipe.NET_SUCCESS) {
                    List<MetaReply> replyList = new ArrayList<MetaReply>();
                    if (messageList.get(index_pos_huifu_pos).getComments() != null) {
                        replyList.addAll(messageList.get(index_pos_huifu_pos).getComments());
                    }
                    replyList.add(new MetaReply(GlobalData.instance().getLoginUser(), replyList.get(index_pos_huifu).getUsername(), replyString));
                    messageList.get(index_pos_huifu_pos).setComments(replyList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tribeAdapter.notifyDataSetChanged();
                        }
                    });
                } else if (success == Pipe.SERVER_ERROR) {
                    if (null == data) {
                        Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败");
                    } else {
                        if (newsListResp.getErrcode() == 1) {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "权限认证错误，请重新登陆");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(TribeActivity_2.this, FirstActivity_2.class));
                                    finish();
                                }
                            });

                        } else if (newsListResp.getErrcode() == 2) {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "获取动态列表失败");
                        }
                    }
                } else {
                    Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败，请检查网络或重试");
                }
            }
        });*/
    }

    public void like(int index_praise_pos) {
        /*LikeCreate req = new LikeCreate();
        req.setToken(GlobalData.instance().getLoginUser().getToken());
        req.setUserID(GlobalData.instance().getLoginUser().getUserID());
        req.setNewsID(messageList.get(index_praise_pos).getNewsID());
        BoBase<LikeResp> bo = new BoBase<LikeResp>();

        bo.request("/news/like", req, new LikeResp(), new Pipe() {

            @Override
            public void complete(Object owner, Object data, int success) {
                LikeResp likeResp = (LikeResp) data;
                if (success >= Pipe.NET_SUCCESS) {
                    List<MetaUser> likeUsers = new ArrayList<>();
                    if (messageList.get(index_praise_pos).getLikeusers() != null) {
                        likeUsers.addAll(messageList.get(index_praise_pos).getLikeusers());
                    }
                    if (likeResp.islike()) {
                        if (GlobalData.instance().getLoginUser() != null)
                            likeUsers.add(GlobalData.instance().getLoginUser());
                    } else {
                        for (int i = likeUsers.size() - 1; i >= 0 && GlobalData.instance().getLoginUser() != null; i--) {
                            if (likeUsers.get(i).getNickname().equals(GlobalData.instance().getLoginUser().getNickname())) {
                                likeUsers.remove(i);
                                break;
                            }
                        }
                    }
                    messageList.get(index_praise_pos).setLikeusers(likeUsers);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tribeAdapter.notifyDataSetChanged();
                        }
                    });
                } else if (success == Pipe.SERVER_ERROR) {
                    if (null == data) {
                        Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败");
                    } else {
                        if (likeResp.getErrcode() == 1) {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "权限认证错误，请重新登陆");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(TribeActivity_2.this, FirstActivity_2.class));
                                    finish();
                                }
                            });
                        } else if (likeResp.getErrcode() == 2) {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "获取动态列表失败");
                        } else {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败");
                        }
                    }
                } else {
                    Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败，请检查网络或重试");
                }
            }
        });*/
    }

    public void deleteNews(int position) {
        /*LikeCreate req = new LikeCreate();
        req.setToken(GlobalData.instance().getLoginUser().getToken());
        req.setUserID(GlobalData.instance().getLoginUser().getUserID());
        req.setNewsID(messageList.get(index_praise_pos).getNewsID());
        BoBase<NewsListResp> bo = new BoBase<NewsListResp>();

        bo.request("/news/delete", req, new NewsListResp(), new Pipe() {

            @Override
            public void complete(Object owner, Object data, int success) {
                NewsListResp newsListResp = (NewsListResp)data;
                if (success >= Pipe.NET_SUCCESS) {
                    messageList.remove(position);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tribeAdapter.notifyDataSetChanged();
                        }
                    });
                    Helper.showToastOnUiThread(TribeActivity_2.this, "删除动态成功！");
                } else if (success == Pipe.SERVER_ERROR) {
                    if (null == data) {
                        Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败");
                    } else {
                            if (newsListResp.getErrcode() == 1) {
                                Helper.showToastOnUiThread(TribeActivity_2.this, "权限认证错误，请重新登陆");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(TribeActivity_2.this, FirstActivity_2.class));
                                        finish();
                                    }
                                });

                            } else if (newsListResp.getErrcode() == 2) {
                                Helper.showToastOnUiThread(TribeActivity_2.this, "获取动态列表失败");
                            }
                        else {
                            Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败");
                        }
                    }
                } else {
                    Helper.showToastOnUiThread(TribeActivity_2.this, "更新数据失败，请检查网络或重试");
                }
            }
        });*/
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onPublishTribe(View view) {
        Intent intent = new Intent(TribeActivity.this, PublishTribeInfoActivity_2.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
