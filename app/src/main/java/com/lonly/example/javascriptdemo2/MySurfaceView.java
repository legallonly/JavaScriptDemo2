package com.lonly.example.javascriptdemo2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lonly on 2017/10/26.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder surfaceHolder;
    private Context context;
    private Canvas canvas;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    };

    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }
    /**
     * 映射对象
     * 里面的方法必须使用@JavascriptInterface注解
     */
    public class MyObject{
        @JavascriptInterface
        public void drawCircle(int x, int y, int r){
            MySurfaceView.this.drawCircle(x,y,r);
        }
        @JavascriptInterface
        public void drawLine(int x1, int y1, int x2, int y2){
            MySurfaceView.this.drawLine(x1,y1,x2,y2);
        }
        @JavascriptInterface
        public void post(){
            handler.sendEmptyMessage(0);
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        canvas = surfaceHolder.lockCanvas();

        WebView mWebView = new WebView(context);
        WebSettings mWebSetting = mWebView.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        //第一个参数：要映射的对象  第二哥参数：映射对象的名字
        mWebView.addJavascriptInterface(new MyObject(),"Pencil");

        try {
            InputStream is = getResources().openRawResource(R.raw.deaw);
            byte[] buffer = new byte[1024];

            int count = is.read(buffer);
            String drawJavaScript = new String(buffer, 0, count, "utf-8");
            mWebView.loadDataWithBaseURL(null,drawJavaScript, "text/html", "utf-8", null);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void drawCircle(int x, int y, int r){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(x,y,r,paint);
    }
    public void drawLine(int x1, int y1, int x2, int y2){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.YELLOW);
        canvas.drawLine(x1,y1,x2,y2,paint);
    }
}
