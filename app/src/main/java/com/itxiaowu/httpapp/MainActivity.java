package com.itxiaowu.httpapp;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;


public class MainActivity extends Activity {

    private WebView webView;
    private Handler handler;
    private String url="http://api.test.yunsu.co:6088/organization/2k0r1l55i2rs5544wz5/logo-mobile";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler=new Handler();
        webView= (WebView) findViewById(R.id.wb_content);
        imageView= (ImageView) findViewById(R.id.iv_content);

//        new Thread(new HttpThread("http://www.baidu.com",webView,handler)).start();
        webView.setVisibility(View.INVISIBLE);

        HttpThread httpThread=new HttpThread(url,handler,imageView);
        httpThread.setPicture(true);
        new Thread(httpThread).start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
