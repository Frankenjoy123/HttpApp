package com.itxiaowu.httpapp;

import android.os.Handler;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by thinkpad on 2015/8/4.
 */
public class RegisterThread implements Runnable{
    private String name;
    private String age;
    private String url;

    private TextView tv_result;
    private Handler handler;

    public RegisterThread(String url, String name, String age) {
        this.url = url;
        this.name = name;
        this.age = age;
    }

    public void setTv_result(TextView tv_result) {
        this.tv_result = tv_result;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        doGet();
//        doPost();
    }

    private void doGet(){
        try {
            try {
                url+="?name="+ URLEncoder.encode(name,"utf-8")+"&age="+age;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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
            if (tv_result!=null&&handler!=null){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText(sb.toString());
                    }
                });
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doPost(){
        try {
            URL httpUrl=new URL(url);
            HttpURLConnection connection= (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(20000);
            OutputStream outputStream=connection.getOutputStream();
            String params="name="+name+"&age="+age;
            outputStream.write(params.getBytes());
            BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final StringBuilder sb=new StringBuilder();
            String str;
            while ((str=br.readLine())!=null){
                sb.append(str);
            }
            if (tv_result!=null&&handler!=null){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText(sb.toString());
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
