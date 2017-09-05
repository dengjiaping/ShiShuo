package hengai.com.shishuo.ui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hengai.com.shishuo.R;

/**
 * Created by yu on 2017/9/5.
 */

public class PopuSelect extends PopupWindow {
    private View mView;
    public PopuSelect(Context context, View.OnClickListener itemsOnclick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popu_select, null);
        RadioButton rbAll= (RadioButton) mView.findViewById(R.id.rb_all);
        RadioButton rbSpeak= (RadioButton) mView.findViewById(R.id.rb_speak);
        RadioButton rbRehearsal= (RadioButton) mView.findViewById(R.id.rb_rehearsal);
        RadioButton rbStructured= (RadioButton) mView.findViewById(R.id.rb_structured);
        RadioButton rbDefense= (RadioButton) mView.findViewById(R.id.rb_defense);

        rbAll.setOnClickListener(itemsOnclick);
        rbDefense.setOnClickListener(itemsOnclick);
        rbSpeak.setOnClickListener(itemsOnclick);
        rbRehearsal.setOnClickListener(itemsOnclick);
        rbStructured.setOnClickListener(itemsOnclick);

        rbAll.setChecked(true);
        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
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
