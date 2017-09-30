package hengai.com.shishuo.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/9/28.
 */

public class PopuWritenSubject extends PopupWindow {
    private View mView;
    public PopuWritenSubject(Context context,View.OnClickListener itemsOnclick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popu_writen_subject, null);
        TextView tvOne= (TextView) mView.findViewById(R.id.tv_jyzh);
        TextView tvTwo= (TextView) mView.findViewById(R.id.tv_xk);

        tvOne.setOnClickListener(itemsOnclick);
        tvTwo.setOnClickListener(itemsOnclick);

        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.Animation1);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.white));
        //设置SelectPicPopupWindow弹出窗体的背景

        this.setBackgroundDrawable(dw);
    }
}
