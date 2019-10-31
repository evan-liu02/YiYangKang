package com.anju.yyk.common.widget.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.anju.yyk.common.R;
import com.anju.yyk.common.R2;
import com.anju.yyk.common.impl.ToolbarListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wcs
 * @Package com.leo.common.widget.toolbar
 * @Description 自定义toolbar
 * @Date 2019/4/29 15:58
 * @modify:
 */
public class NormalToolbar extends Toolbar {

    @BindView(R2.id.common_tv_left)
    TextView commonTvLeft;
    @BindView(R2.id.common_tv_topic)
    TextView commonTvTopic;
    @BindView(R2.id.common_tv_right)
    TextView commonTvRight;
    @BindView(R2.id.common_iv_left)
    ImageView commonIvLeft;
    @BindView(R2.id.common_iv_right)
    ImageView commonIvRight;

    private Unbinder mUnBinder;
    private String titleText;
    private ToolbarListener mToolbarListener;

    public NormalToolbar(Context context) {
        super(context);
        init(context, null);
    }

    public NormalToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NormalToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_toolbar, this, true);
        mUnBinder = ButterKnife.bind(this, view);

        //很重要
        setContentInsetsRelative(0, 0);

        // Load attributes
//        @SuppressLint("CustomViewStyleable")
//        final TypedArray a = getContext().obtainStyledAttributes(
//                attrs, R.styleable.common_ToolbarControl, 0, 0);
//        titleText = a.getString(R.styleable.common_ToolbarControl_common_titleText);
//        commonTvTopic.setText(titleText);
//
//        a.recycle();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY)
            mUnBinder.unbind();
        this.mUnBinder = null;
    }

    public void setTopic(String topicStr){
        if (commonTvTopic != null){
            commonTvTopic.setText(topicStr);
        }
    }

    public void setTopicColor(int color){
        if (commonTvTopic != null){
            commonTvTopic.setTextColor(color);
        }
    }

    public void setLeftText(String leftStr){
        if (commonTvLeft != null){
            commonTvLeft.setText(leftStr);
        }
    }

    public void setRightText(String rightStr){
        if (commonTvRight != null){
            commonTvRight.setText(rightStr);
        }
    }

    public void setLeftIvSrc(int imgRes){
        if (commonIvLeft != null){
            commonIvLeft.setImageResource(imgRes);
        }
    }

    public void setRightIvSrc(int imgRes){
        if (commonIvRight != null){
            commonIvRight.setImageResource(imgRes);
        }
    }

    public void ivIsShow(boolean leftIsShow, boolean rightIsShow){
        if (leftIsShow){
            commonIvLeft.setVisibility(VISIBLE);
        }else {
            commonIvLeft.setVisibility(GONE);
        }

        if (rightIsShow){
            commonIvRight.setVisibility(VISIBLE);
        }else {
            commonIvRight.setVisibility(GONE);
        }
    }

    public void hide(){
        this.setVisibility(View.GONE);
    }

    public void setToolbarListener(ToolbarListener listener){
        this.mToolbarListener = listener;
    }

    @OnClick({R2.id.common_tv_left, R2.id.common_tv_right, R2.id.common_iv_left, R2.id.common_iv_right})
    public void onViewCliecked(View view){
        if (mToolbarListener != null){
            if (view.getId() == R.id.common_tv_left){
                mToolbarListener.leftTvListener();
            }else if (view.getId() == R.id.common_tv_right){
                mToolbarListener.rightTvListener();
            }else if (view.getId() == R.id.common_iv_left){
                mToolbarListener.leftIvListener();
            }else if (view.getId() == R.id.common_iv_right){
                mToolbarListener.rightIvListener();
            }
        }
    }

    public String getTitleText(){
        return titleText;
    }
}