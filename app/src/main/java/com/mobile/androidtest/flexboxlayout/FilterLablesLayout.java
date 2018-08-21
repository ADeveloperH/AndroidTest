package com.mobile.androidtest.flexboxlayout;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.mobile.androidtest.R;

import java.util.List;

import static com.google.android.flexbox.AlignItems.FLEX_START;
import static com.google.android.flexbox.FlexDirection.ROW;
import static com.google.android.flexbox.FlexWrap.WRAP;
import static com.google.android.flexbox.JustifyContent.SPACE_AROUND;

/**
 * @author huangjian
 * @create 2018/8/21 0021
 * @Description
 */
public class FilterLablesLayout extends FlexboxLayout {
    private final Context context;
    private int columns = 3;
    private int childHeight;
    private float childWidthPercent = 0.28f;

    private int rowMarginTop;

    public FilterLablesLayout(Context context) {
        this(context, null);
    }

    public FilterLablesLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterLablesLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        setAlignContent(SPACE_AROUND);
        setAlignItems(FLEX_START);
        setFlexDirection(ROW);
        setFlexWrap(WRAP);
        setJustifyContent(SPACE_AROUND);
        setShowDivider(SHOW_DIVIDER_NONE);
        childHeight = dp2px(context, 36);
        rowMarginTop = dp2px(context, 12);
    }

    public void setLabelsData(@NonNull List<String> labelsList) {
        if (labelsList == null || labelsList.size() <= 0 || columns <= 0) {
            return;
        }

        while (labelsList.size() % columns != 0) {
            labelsList.add("");
        }

        if (getChildCount() > 0) {
            removeAllViews();
        }
        int row = 1;
        for (int i = 0; i < labelsList.size(); i++) {
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            String text = labelsList.get(i);
            textView.setText(text);
            textView.setTextColor(0xff333333);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            textView.setBackgroundResource(R.drawable.item_flexbox_gray);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, childHeight);
            layoutParams.setFlexBasisPercent(childWidthPercent);
            if (i != 0 && i % columns == 0) {
                row++;
                layoutParams.setWrapBefore(true);
            }
            if (row > 1) {
                layoutParams.topMargin = rowMarginTop;
            }

            if (TextUtils.isEmpty(text)) {
                textView.setVisibility(View.INVISIBLE);
            }

            if (textView.getVisibility() == View.VISIBLE) {
                final int finalI = i;
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSelect(finalI);
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(finalI);
                        }
                    }
                });
            }
            addView(textView, layoutParams);
        }
        setSelect(0);
    }

    private void setSelect(int position) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                TextView textView = (TextView) getChildAt(i);
                if (i == position) {
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundColor(0xffF87C2A);
                } else {
                    textView.setTextColor(0xff333333);
                    textView.setBackgroundColor(0xffF5F5F5);
                }
            }
        }
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private int dp2px(Context context, int dip) {
        // dp和px的转换关系比例值
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
