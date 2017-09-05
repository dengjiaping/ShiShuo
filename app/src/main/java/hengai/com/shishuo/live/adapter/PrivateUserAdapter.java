package hengai.com.shishuo.live.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;


import butterknife.ButterKnife;
import butterknife.InjectView;
import hengai.com.shishuo.R;
import hengai.com.shishuo.live.module.PrivateUser;
import hengai.com.shishuo.live.view.HeadView;
import hengai.com.shishuo.utils.EmojiUtil;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class PrivateUserAdapter extends RecyclerView.Adapter<PrivateUserAdapter.PrivateUserViewHolder> {

    private Context mContext;
    private ArrayList<PrivateUser> mPrivateUsers;
    private LayoutInflater mInflater;

    public PrivateUserAdapter(Context context) {
        mPrivateUsers = new ArrayList<>();
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 添加数据
     */
    public void add(PrivateUser privateUser) {
        int index = -1;
        for (PrivateUser user : mPrivateUsers) {
            if (user.getId().equals(privateUser.getId())) {
                index = mPrivateUsers.indexOf(user);
                break;
            }
        }
        if (index != -1) {
            mPrivateUsers.remove(index);
        }
        mPrivateUsers.add(0, privateUser);
        notifyDataSetChanged();
    }

    public ArrayList<PrivateUser> getPrivateUsers() {
        return mPrivateUsers;
    }

    @Override
    public PrivateUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrivateUserViewHolder(mInflater.inflate(R.layout.private_user_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(PrivateUserViewHolder holder, int position) {
        PrivateUser privateUser = mPrivateUsers.get(position);
        holder.mUserName.setText(privateUser.getName());
        SpannableString ss = new SpannableString(privateUser.getMsg());
        holder.mContent.setText(EmojiUtil.parseFaceMsg(mContext, ss));
        holder.mTime.setText(privateUser.getTime());
        Glide.with(mContext).
                load(privateUser.getAvatar()).
                placeholder(R.drawable.chatuser_head_icon).
                into(holder.mHeadIcon);
        if (privateUser.isRead()) {
            holder.mHeadIcon.clearNew();
        } else {
            holder.mHeadIcon.updateNew();
        }
    }

    @Override
    public int getItemCount() {
        return mPrivateUsers.size();
    }

    final class PrivateUserViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.id_private_user_head)
        HeadView mHeadIcon;
        @InjectView(R.id.id_private_time)
        TextView mTime;
        @InjectView(R.id.id_private_user_name)
        TextView mUserName;
        @InjectView(R.id.id_private_msg)
        TextView mContent;

        PrivateUserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
