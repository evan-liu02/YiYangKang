package com.anju.yyk.main.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.anju.yyk.common.utils.TimeUtil;
import com.anju.yyk.common.utils.klog.KLog;
import com.anju.yyk.main.R;
import com.anju.yyk.main.widget.calendarview.CustomWeekBar;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class DatePickerDialog extends DialogFragment implements View.OnClickListener, CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener {

    public Activity mContext;


    private TextView mMonthDayTv;
    private TextView mYearTv;
    private TextView mLunarTv;
    private TextView mCurrentDayTv;
    private FrameLayout mCurrentDayFl;
    private CalendarView mCalendarView;
    private TextView mConfirmBtn;
    private TextView mCancelBtn;

    /** 当前选择的年月日，xxxx-xx-xx*/
    private String mCurSelectedDate = "";
    /** 今日日期，用于比对日历选择的日期，是否是当天*/
    private String mTodayDate = "";

    private DateCallback mCallback;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    private void onAttachToContext(Context context) {
        mContext = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.home_DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.home_dialog_datepicker, container);
        mMonthDayTv = view.findViewById(R.id.tv_month_day);
        mYearTv = view.findViewById(R.id.tv_year);
        mLunarTv = view.findViewById(R.id.tv_lunar);
        mCurrentDayTv = view.findViewById(R.id.tv_current_day);
        mCurrentDayFl = view.findViewById(R.id.fl_current);
        mCalendarView = view.findViewById(R.id.calendarview);
        mConfirmBtn = view.findViewById(R.id.tv_confirm);
        mCancelBtn = view.findViewById(R.id.tv_cancel);
        initCalendar();
        initListener();
        return view;
    }

    private void initCalendar(){
        String date = String.format(getString(R.string.common_month_day_text), mCalendarView.getCurMonth()
                , mCalendarView.getCurDay());
        mMonthDayTv.setText(date);
        mYearTv.setText(String.valueOf(mCalendarView.getCurYear()));
        mLunarTv.setText(R.string.common_today);
        mCurrentDayTv.setText(String.valueOf(mCalendarView.getCurDay()));
        mCalendarView.setWeekBar(CustomWeekBar.class);
        // 获得默认当前的日期
        mCurSelectedDate = TimeUtil.getCurrentDate();
        // 获得当天日期
        mTodayDate = TimeUtil.getCurrentDate();
    }

    private void initListener(){
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mCurrentDayFl.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fl_current){
            mCalendarView.scrollToCurrent();
        }else if (v.getId() == R.id.tv_confirm){
            if (mCallback != null)
                mCallback.onSelectDate(mCurSelectedDate);
            dismiss();
        }else if (v.getId() == R.id.tv_cancel){
            dismiss();
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mCurSelectedDate = TimeUtil.getFormatTime(TimeUtil.FORMAT_YYYY_MM_DD, calendar.getTimeInMillis());
        KLog.d("选择的日期：" + mCurSelectedDate);
        String tempDate = TimeUtil.getFormatTime(TimeUtil.FORMAT_YYYY_MM_DD, calendar.getTimeInMillis());
        if (!mCurSelectedDate.equals(tempDate)){
            // 与上次选择日期并非同一天，则请求列表接口，并更新日期显示
            mLunarTv.setVisibility(View.VISIBLE);
            mYearTv.setVisibility(View.VISIBLE);
            String date = String.format(getString(R.string.common_month_day_text), calendar.getMonth(), calendar.getDay());
            mMonthDayTv.setText(date);
            mYearTv.setText(String.valueOf(calendar.getYear()));
            if (mTodayDate.equals(mCurSelectedDate)){
                mLunarTv.setText(R.string.common_today);
            }else {
                mLunarTv.setText(calendar.getLunar());
            }

            mCurSelectedDate = tempDate;
        }
    }

    @Override
    public void onYearChange(int year) {
        mMonthDayTv.setText(String.valueOf(year));
    }

    public void setCallback(DateCallback callback){
        this.mCallback = callback;
    }

    public interface DateCallback{
        void onSelectDate(String date);
    }
}
