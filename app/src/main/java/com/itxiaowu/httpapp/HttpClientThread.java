package com.itxiaowu.httpapp;

import android.os.Handler;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2015/8/5.
 */
public class HttpClientThread implements Runnable {
    private String url;
    private String name;
    private String age;

    private TextView tv_result;
    private Handler handler;

    public void setTv_result(TextView tv_result) {
        this.tv_result = tv_result;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public HttpClientThread(String url) {
        this.url = url;
    }

    public HttpClientThread(String name, String url, String age) {
        this.name = name;
        this.url = url;
        this.age = age;
    }

    @Override
    public void run() {
//        doHttpClientGet();
        doHttpClientPost();
    }

    private void doHttpClientGet(){
        HttpClient client=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        try {
            HttpResponse response=client.execute(httpGet);
            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                HttpEntity entity=response.getEntity();
                final String s=EntityUtils.toString(entity);
                if (tv_result!=null&&handler!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_result.setText(s);
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doHttpClientPost(){

        try {
            HttpClient client=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(url);
            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair("name",name));
            params.add(new BasicNameValuePair("age",age));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response=client.execute(httpPost);
            if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                HttpEntity entity=response.getEntity();
                final String s=EntityUtils.toString(entity);
                if (tv_result!=null&&handler!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_result.setText(s);
                        }
                    });
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }
}
