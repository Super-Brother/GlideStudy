package com.wenchao.glidestudy;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

/**
 * @author wenchao
 * @date 2019/6/27.
 * @time 6:27
 * description：图片请求
 */
public class BitmapRequest {

    /**
     * 图片请求地址
     */
    private String url;

    /**
     * 图片加载控件
     */
    private SoftReference<ImageView> imageView;

    /**
     * 占位图片
     */
    private int resID;

    /**
     * 回调对象
     */
    private RequestListener listener;

    /**
     * 图片标识
     */
    private String urlMd5;

    private Context context;

    public BitmapRequest(Context context) {
        this.context = context;
    }

    public BitmapRequest load(String url) {
        this.url = url;
        this.urlMd5 = MD5Util.getMD5String(url);
        return this;
    }

    public BitmapRequest loading(int resID) {
        this.resID = resID;
        return this;
    }

    public BitmapRequest listener(RequestListener listener) {
        this.listener = listener;
        return this;
    }

    public void into(ImageView imageView) {
        imageView.setTag(this.urlMd5);
        this.imageView = new SoftReference<>(imageView);
        //将图片请求加入到队列中去
        RequestManager.getInstance().addBitmapRequest(this);
    }

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public int getResID() {
        return resID;
    }

    public RequestListener getListener() {
        return listener;
    }

    public String getUrlMd5() {
        return urlMd5;
    }
}
