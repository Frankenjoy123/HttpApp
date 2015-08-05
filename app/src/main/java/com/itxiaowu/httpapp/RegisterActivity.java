package com.itxiaowu.httpapp;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class RegisterActivity extends ActionBarActivity {
    private EditText et_name;
    private EditText et_age;
    private Button btn_register;

    private TextView tv_result;

    private String url="http://192.168.1.5:8080/HttpServe/MyServlet";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        handler=new Handler();
        et_name= (EditText) findViewById(R.id.et_name);
        et_age= (EditText) findViewById(R.id.et_age);
        btn_register= (Button) findViewById(R.id.btn_register);

        tv_result= (TextView) findViewById(R.id.tv_result);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RegisterThread registerThread=new RegisterThread(url,et_name.getText().toString(),et_age.getText().toString());
//                registerThread.setHandler(handler);
//                registerThread.setTv_result(tv_result);
//                new Thread(registerThread).start();


//                try {
//                    url+="?name="+ URLEncoder.encode(et_name.getText().toString(),"utf-8")+"&age="+et_age.getText().toString();
//                } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            HttpClientThread thread=new HttpClientThread(url);
//            thread.setTv_result(tv_result);
//            thread.setHandler(handler);
//            new Thread(thread).start();

                HttpClientThread thread=new HttpClientThread(et_name.getText().toString(),url,et_age.getText().toString());
                thread.setTv_result(tv_result);
                thread.setHandler(handler);
                new Thread(thread).start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
