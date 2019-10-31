package com.anju.yyk.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.common.widget
 *
 * @Description 用于RecyclerView中正方形元素显示
 *
 * @Date 2019/9/10 15:06
 *
 * @modify:
 */
public class MyLayoutForGv extends RelativeLayout{

    public  MyLayoutForGv  (Context context, AttributeSet attrs,
                       int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyLayoutForGv (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLayoutForGv (Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        // 高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
