package com.wenchao.glidestudy;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author wenchao
 * @date 2019/6/27.
 * @time 7:33
 * description：
 */
public class RequestManager {

    private static RequestManager requestManager = new RequestManager();

    /**
     * 创建队列
     */
    private LinkedBlockingDeque<BitmapRequest> requestQueue = new LinkedBlockingDeque<>();

    /**
     * 创建线程数组
     */
    private BitmapDispatcher[] bitmapDispatchers;

    private RequestManager() {
        start();
    }

    private void start() {
        stop();
        statAllDispatchers();
    }

    /**
     * 提醒窗口开始服务
     */
    private void statAllDispatchers() {
        //获取手机支持的单个应用最大的线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        bitmapDispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            bitmapDispatcher.start();

            //将每个dispatcher放到数组中,方便统一管理
            bitmapDispatchers[i] = bitmapDispatcher;
        }
    }

    /**
     * 停止所有的线程
     */
    private void stop() {
        if(null != bitmapDispatchers && bitmapDispatchers.length>0){
            for(BitmapDispatcher bitmapDispatcher : bitmapDispatchers){
                if(!bitmapDispatcher.isInterrupted()){
                    bitmapDispatcher.interrupt();
                }
            }
        }
    }

    public static RequestManager getInstance() {
        return requestManager;
    }

    /**
     * 将图片请求添加到队列
     */
    public void addBitmapRequest(BitmapRequest bitmapRequest) {
        if (null == bitmapRequest) {
            return;
        }
        if (!requestQueue.contains(bitmapRequest)) {
            requestQueue.add(bitmapRequest);
        }
    }
}
