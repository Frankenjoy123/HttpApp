package com.itxiaowu.httpapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

import org.apache.http.HttpRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by thinkpad on 2015/8/4.
 */
public class HttpThread implements  Runnable {
    private String url;
    private WebView webView;
    private Handler handler;

    private boolean isPicture;

    private ImageView imageView;

    public void setPicture(boolean isPicture) {
        this.isPicture = isPicture;
    }

    public HttpThread(String url, WebView webView, Handler handler) {
        this.url = url;
        this.webView = webView;
        this.handler = handler;
    }

    public HttpThread(String url, Handler handler, ImageView imageView) {
        this.url = url;
        this.handler = handler;
        this.imageView = imageView;
    }

    @Override
    public void run() {
        try {

            if (isPicture){
                URL httpUrl=new URL(url);
                HttpURLConnection connection= (HttpURLConnection) httpUrl.openConnection();
                connection.setReadTimeout(20000);
                connection.setRequestMethod("GET");
                InputStream in=connection.getInputStream();
                FileOutputStream fileOutputStream = null;
                File download = null;
                String fileName=String.valueOf(System.currentTimeMillis());
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    File parent= Environment.getExternalStorageDirectory();
                    download=new File(parent,fileName);

                    fileOutputStream=new FileOutputStream(download);
                    byte[] buffer=new byte[2*1024];
                    int len;
                    if (fileOutputStream!=null){

                        while ((len=in.read(buffer))>0){
                            fileOutputStream.write(buffer,0,len);
                        }
                    }

                }
                in.close();
                fileOutputStream.close();
                final Bitmap bitmap= BitmapFactory.decodeFile(download.getAbsolutePath());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
            else {
                URL httpUrl=new URL(url);
                HttpURLConnection connection= (HttpURLConnection) httpUrl.openConnection();
                connection.setReadTimeout(20000);
                connection.setRequestMethod("GET");
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                final StringBuilder sb=new StringBuilder();
                String str;
                while ((str=br.readLine())!=null){
                    sb.append(str);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadData(sb.toString(),"text/html;charset=utf-8",null);
                    }
                });

            }





        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
