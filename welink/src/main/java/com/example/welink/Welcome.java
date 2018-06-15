package com.example.welink;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.welink.base.Constant;
import com.example.welink.base.IMApplication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import static com.example.welink.base.IMApplication.clientSocket;
import static com.example.welink.base.IMApplication.in;
import static com.example.welink.base.IMApplication.out;

public class Welcome extends AppCompatActivity {
    private ImageView mImageView;//标志图
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = this;
        findView();
        Thread  connectThread =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketAddress remoteAddr=new InetSocketAddress(Constant.IM_HOST,Constant.IM_PORT);
                    IMApplication.clientSocket.connect(remoteAddr);
                    IMApplication.out =new ObjectOutputStream(IMApplication.clientSocket.getOutputStream());
                    IMApplication.in =new ObjectInputStream(IMApplication.clientSocket.getInputStream());
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    Thread.sleep(3000);
                    IMApplication.first=false;
                    startActivity(intent);
                    finish();
                   } catch (IOException |InterruptedException e) {
                     handler.sendEmptyMessage(0x127);
                   }
            }
          });
        if(IMApplication.first) {
            connectThread.start();
        }else{
                  findView();
                  Intent intent = new Intent(mContext, MainActivity.class);
                  startActivity(intent);
                  finish();
        }
    }

    Handler handler =new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x127) {
                Toast.makeText(Welcome.this,"网络错误，请检查后重启应用",Toast.LENGTH_SHORT).show();
            }
        }
    };
    //---------------------------------------------------
    private void findView() {
        mImageView = (ImageView) findViewById(R.id.iv_welcome);
    }

}
