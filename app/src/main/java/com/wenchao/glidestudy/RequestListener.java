package com.wenchao.glidestudy;

import android.graphics.Bitmap;

/**
 * @author wenchao
 * @date 2019/6/27.
 * @time 6:38
 * description：
 */
public interface RequestListener {

    boolean onSuccess(Bitmap bitmap);

    boolean onFailure();
}
