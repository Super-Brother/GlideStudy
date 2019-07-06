package com.wenchao.glidestudy;

import android.content.Context;

/**
 * @author wenchao
 * @date 2019/6/28.
 * @time 23:33
 * description：
 */
public class WeGlide {

    public static BitmapRequest with(Context context) {
        return new BitmapRequest(context);
    }
}
