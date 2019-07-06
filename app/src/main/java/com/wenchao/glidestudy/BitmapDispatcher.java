package com.wenchao.glidestudy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author wenchao
 * @date 2019/6/27.
 * @time 6:48
 * description：
 */
public class BitmapDispatcher extends Thread {

    private Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 创建一个阻塞队列
     */
    private LinkedBlockingDeque<BitmapRequest> requestQueue;

    public BitmapDispatcher(LinkedBlockingDeque requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                //从队列中获取图片请求
                BitmapRequest br = requestQueue.take();
                //设置占位图片
                showLoadingImage(br);
                //加载图片
                Bitmap bitmap = findBitmap(br);
                //将图片显示到imageview
                showImageView(br, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageView(final BitmapRequest br, final Bitmap bitmap) {
        if(null != bitmap && null != br.getImageView() && br.getUrlMd5().equals(br.getImageView().getTag())){
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    if(null != br.getListener()){
                        RequestListener listener = br.getListener();
                        listener.onSuccess(bitmap);
                    }
                }
            });
        }
    }

    private Bitmap findBitmap(BitmapRequest br) {
        return downloadBitmap(br.getUrl());
    }

    private Bitmap downloadBitmap(String path) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            //创建一个url对象
            URL url = new URL(path);
            //然后使用HttpURLConnection通过URL去开始读取数据
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private void showLoadingImage(BitmapRequest br) {
        if (br.getResID() > 0 && null != br.getImageView()) {
            final int resId = br.getResID();
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId);
                }
            });
        }
    }

}
