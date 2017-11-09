package com.mobile.androidtest.pagestate;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

/**
 * author：hj
 * time: 2017/11/9 0009 11:43
 */


public class StringUtils {
    public static CharSequence formatRelativeSize(String content, float relativeSize, int start, int end) {
        if (!TextUtils.isEmpty(content) && content.length() >= start && content.length() >= end) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(content);
            ssb.setSpan(new RelativeSizeSpan(relativeSize), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            return ssb;
        } else {
            return "";
        }
    }
}
