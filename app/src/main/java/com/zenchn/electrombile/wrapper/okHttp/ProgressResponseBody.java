package com.zenchn.electrombile.wrapper.okHttp;

import android.os.Handler;
import android.os.Looper;

import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.wrapper.callback.ProgressResponseCallback;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 作    者：wangr on 2017/3/15 17:03
 * 描    述：
 * 修订记录：
 */

public class ProgressResponseBody extends ResponseBody {

    //主线程的handler
    private Handler mHandler;

    //实际的待包装响应体
    private ResponseBody responseBody;

    //进度回调接口
    private ProgressResponseCallback callback;

    //包装完成的BufferedSource
    private BufferedSource bufferedSource;

    /**
     * 构造函数，赋值
     *
     * @param responseBody 待包装的响应体
     * @param callback     回调接口
     */
    public ProgressResponseBody(ResponseBody responseBody, ProgressResponseCallback callback) {
        this.responseBody = responseBody;
        this.callback = callback;
        this.mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    /**
     * 重写进行包装source
     *
     * @return BufferedSource
     */
    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            //包装
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    /**
     * 读取，回调进度接口
     *
     * @param source Source
     * @return Source
     */
    private Source source(Source source) {

        return new ForwardingSource(source) {

            public long bytesRead;

            //当前读取字节数
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                bytesRead = super.read(sink, byteCount);
                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //回调，如果contentLength()不知道长度，会返回-1
                LogKit.success("正在下载", "当前进度：" + (int) (bytesRead * 100 / responseBody.contentLength()) + "%");
                if (callback != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponseProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                        }
                    });
                }
                return bytesRead;
            }
        };
    }
}