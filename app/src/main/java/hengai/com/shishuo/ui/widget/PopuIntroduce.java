package hengai.com.shishuo.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnClick;
import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/9/5.
 */

public class PopuIntroduce extends PopupWindow {

    private View mView;

    public PopuIntroduce(Context context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popu_lesson_introduce, null);
        RelativeLayout rl= (RelativeLayout) mView.findViewById(R.id.rl_introduce);
        TextView tvTitle= (TextView) mView.findViewById(R.id.tv_title);
        ImageButton imgBtn= (ImageButton) mView.findViewById(R.id.img_btn_dismiss);
        ImageView imgIntroduce= (ImageView) mView.findViewById(R.id.iv_introduce);


        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation1);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.white));
        //设置SelectPicPopupWindow弹出窗体的背景

        this.setBackgroundDrawable(dw);


    }


}
