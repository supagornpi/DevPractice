package com.supagorn.devpractice.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;


import com.supagorn.devpractice.R;

import java.util.ArrayList;

/**
 * Created by apple on 12/14/2016 AD.
 */

public class SpannableText {

    public static Spannable setClickableSpan(final Activity activity, int text, String[] textIndex,
                                             final OnClickListener onClickListener) {
        String textStr = activity.getResources().getString(text);
        SpannableString wordtoSpan = new SpannableString(textStr);

        int position = 0;
        for (String textIndexStr : textIndex) {

            int start = textStr.indexOf(textIndexStr);
            int end = start + textIndexStr.length();

            final int finalPosition = position;
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    if (onClickListener == null) {
                        return;
                    }
                    onClickListener.onClick(textView, finalPosition);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(activity.getResources().getColor(R.color.color_white));
                    ds.setUnderlineText(true);
                }
            };
            // index
            if (start > 0) {
                wordtoSpan.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                position++;
            }
        }
        return wordtoSpan;
    }

    public static Spannable getSpanColor(Context context, String text, ArrayList<String> textIndex) {
        Spannable wordtoSpan = new SpannableString(text);

        for (String textIndexStr : textIndex) {

            int start = text.indexOf(textIndexStr);
            int end = start + textIndexStr.length();
            // index
            if (start >= 0) {
                wordtoSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue))
                        , start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return wordtoSpan;
    }

    public interface OnClickListener {
        void onClick(View textView, int position);
    }
}
