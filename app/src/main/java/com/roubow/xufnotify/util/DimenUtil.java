package com.roubow.xufnotify.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Xuf on 2015/9/3.
 */
public class DimenUtil {

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
