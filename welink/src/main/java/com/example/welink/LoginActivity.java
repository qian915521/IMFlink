package com.example.welink;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.welink.base.Constant;
import com.example.welink.base.IMApplication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import until.SocketMessage;
import until.User;

import static com.example.welink.base.IMApplication.clientSocket;
import static com.example.welink.base.IMApplication.in;
import static com.example.welink.base.IMApplication.out;

public class LoginActivity extends AppCompatActivity {
    Button LoginButton ;
    TextView Register ;
    EditText  account ;
    EditText  password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);
         account =(EditText) findViewById(R.id.account);
         password=(EditText)findViewById(R.id.password);
         LoginButton=(Button)findViewById(R.id.login);
         LoginButton.setOnClickListener(new LoginButtonOnClickListener());
         Register=(TextView) findViewById(R.id.register);
         Register.setOnClickListener(new RegisterOnClickListener());
    }

    Handler handler =new Handler(){
        final PopupWindow popupWindow = new PopupWindow();
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0x123){
                //登陆等待条
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.popup,null);
                popupWindow.setContentView(view);
                popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
                //登陆等待条结束
                findViewById(R.id.login_activity_layout).setAlpha((float)0.1);
            }
            else if(msg.what==0x124){
                popupWindow.dismiss();
            }
            else if(msg.what==0x125){
                Toast.makeText(LoginActivity.this,"账号和密码不能为空",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==0x126){
                Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
            }
            findViewById(R.id.login_activity_layout).setAlpha((float)1);
        }
    };


    //登陆按钮监听事件
    class LoginButtonOnClickListener implements View.OnClickListener {
        public void onClick(View v){
             Thread  loginThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    if(!TextUtils.isEmpty(account.getText())&&!TextUtils.isEmpty(password.getText())) {
                        long accountNumber =Long.parseLong(account.getText().toString());
                        String  passwordString =password.getText().toString();
                        handler.sendEmptyMessage(0x123);
                        try {
                            SocketMessage message =new SocketMessage();
                            message.what="login";
                            User user =new User();
                            user.id=accountNumber ;
                            user.password=passwordString;
                            message.user=user ;
                            IMApplication.out.writeObject(message);
                            IMApplication.out.flush();
                            SocketMessage m;
                            m=(SocketMessage)IMApplication.in.readObject();
                            if("login_back".equals(m.what)){
                                if("success".equals(m.state)){
                                    IMApplication.userId =m.user.id;
                                    IMApplication.username=m.user.name ;
                                    Intent intent  =new Intent();
                                    intent.setClass(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    handler.sendEmptyMessage(0x124);
                                    finish();
                                }
                                if("faild".equals(m.state)){
                                    handler.sendEmptyMessage(0x124);
                                    handler.sendEmptyMessage(0x126);
                                }
                            }

                        } catch (IOException  e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0x124);
                    }
                    else {
                        handler.sendEmptyMessage(0x125);
                    }
                }
            });
            loginThread.start();
        }
    }


    //注册文本框监听事件（强制转换为按钮）
      class  RegisterOnClickListener implements View.OnClickListener{
        public void onClick(View v){
            finish();
            Intent intent  =new Intent();
            intent.setClass(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }

}
