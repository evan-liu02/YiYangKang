package com.anju.yyk.main.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anju.yyk.main.R;

public class IconReplacementSpan extends ReplacementSpan {

    private Context context;

    private int backgroundColorId;
    private float backgroundHeight;
    private float backgroundWidth;
    private float backgroundRadius;
    private Paint backgroundPaint;

    private String text;
    private int textColorId;
    private float textSize;
    private float textMarginRight;
    private Paint textPaint;

    public IconReplacementSpan(Context context, int backgroundColorId, int textColorId, String text) {
        // Do not allow empty text.
        if (TextUtils.isEmpty(text)) {
            return;
        }

        // Init instance variable value.
        this.context = context.getApplicationContext();

        DisplayMetrics displayMetrics = this.context.getResources().getDisplayMetrics();

        // Init text related variable value.
        this.text = text;
        this.textColorId = textColorId;
        this.textMarginRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, displayMetrics);
//        this.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, displayMetrics);
        this.textSize = this.context.getResources().getDimension(R.dimen.sp_14);

        // Init background related variable value.
        this.backgroundColorId = backgroundColorId;
//        this.backgroundHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25f, displayMetrics);
        this.backgroundHeight = this.context.getResources().getDimension(R.dimen.dp_20);
        this.backgroundWidth = this.calculateBackgroundWidth(text+"", displayMetrics, this.textSize) * 1.4f;
//        this.backgroundRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, displayMetrics);
        this.backgroundRadius = this.context.getResources().getDimension(R.dimen.dp_10);

        // Init background paint.
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setColor(this.backgroundColorId);
        this.backgroundPaint.setStyle(Paint.Style.FILL);
        this.backgroundPaint.setAntiAlias(true);

        // Init text paint.
        this.textPaint = new TextPaint();
        this.textPaint.setColor(this.textColorId);
        this.textPaint.setTextSize(this.textSize);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
    }

    /* Return the background width after replace text. */
    @Override
    public int getSize(@NonNull Paint paint, CharSequence charSequence, @IntRange(from = 0) int start, @IntRange(from = 0) int end,
                       @Nullable Paint.FontMetricsInt fontMetricsInt) {
        return (int) (this.backgroundWidth + this.textMarginRight);
    }

    /*Draw the span component in the canvas.*/
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end,
                     float x, int top, int y, int bottom, @NonNull Paint paint) {

        // Draw background.
        Paint.FontMetrics backgroundFontMetrics = this.backgroundPaint.getFontMetrics();
        float textHeight = backgroundFontMetrics.descent - backgroundFontMetrics.ascent;
        //Calculate draw background y coordinate.
        float backgroundStartY = y + (textHeight - this.backgroundHeight) / 2 + backgroundFontMetrics.ascent - 20;

        RectF backgroundRect = new RectF(x, backgroundStartY, x + this.backgroundWidth, backgroundStartY + this.backgroundHeight);
        canvas.drawRoundRect(backgroundRect, this.backgroundRadius, this.backgroundRadius, this.backgroundPaint);

        // Draw text.
        Paint.FontMetrics textFontMetrics = textPaint.getFontMetrics();
        float textRectHeight = textFontMetrics.bottom - textFontMetrics.top;
        canvas.drawText(this.text, x + this.backgroundWidth / 2, backgroundStartY + (this.backgroundHeight - textRectHeight) / 2 - textFontMetrics.top, textPaint);
    }

    /* Calculate and return background width.*/
    private float calculateBackgroundWidth(String text, DisplayMetrics displayMetrics, float textSize) {
        if (text.length() > 1) {
            Paint textPaint = new Paint();
            textPaint.setTextSize(textSize);
            Rect textRect = new Rect();
            // calculate textRect
            textPaint.getTextBounds(text, 0, text.length(), textRect);
            // Get context resource padding value.
            float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, displayMetrics);
            int textWidth = textRect.width();
            return textWidth + padding * 2;
        } else {
            // If has only one character, then return background width is equal to height.
            return this.backgroundHeight;
        }
    }
}
