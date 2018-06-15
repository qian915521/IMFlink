package com.example.welink;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.welink.base.IMApplication;

import java.io.IOException;
import java.net.Socket;

import until.SocketMessage;

public class RegisterActivity extends AppCompatActivity {
    ImageButton RegisterBack ;
    Button registerButton ;
    EditText userid ,userpost, userpassword,usersurepassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton=(Button)findViewById(R.id.register);
        registerButton.setOnClickListener(new onClickListener());
        RegisterBack=(ImageButton)findViewById(R.id.back);
        userid=(EditText)findViewById(R.id.userid);
        userpost=(EditText)findViewById(R.id.postmail);
        userpassword=(EditText)findViewById(R.id.password);
        usersurepassword=(EditText)findViewById(R.id.checkpassword);
        RegisterBack.setOnClickListener( new onClickListener());
    }

    class  onClickListener implements View.OnClickListener{

        public void onClick(View v){
            if(v.getId()==R.id.back) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            if(v.getId()==R.id.register){
                if(!TextUtils.isEmpty(userid.getText())&&!TextUtils.isEmpty(userpassword.getText())&&!TextUtils.isEmpty(usersurepassword.getText())){
                    if(usersurepassword.getText().toString().equals(userpassword.getText().toString())){
                        Thread registThread =new Thread(new Runnable() {
                            @Override
                            public void run() {
                                registerHandle.sendEmptyMessage(0x125);
                                SocketMessage  message   =new SocketMessage();
                                message.what="regist";
                                message.user.id=Long.parseLong(userid.getText().toString());
                                message.user.password=userpassword.getText().toString();
                                message.user.sex=1;message.user.age=18;message.user.state=1;
                                message.user.name="WeLink";message.user.usertext="我叫WeLink";
                                message.user.address="湖北-武汉";message.user.question="无";message.user.answer="无";message.user.headImage=String.valueOf(message.user.id);
                                message.user.mails=userpost.getText().toString();
                                message.user.linktable=Long.parseLong(userid.getText().toString());
                                try {
                                    IMApplication.out.writeObject(message);
                                    SocketMessage  back_message;
                                    back_message=(SocketMessage)IMApplication.in.readObject();
                                    if(back_message.what.equals("regist_bcak")){
                                        if(back_message.state.equals("success")){
                                            Intent intent =new Intent();
                                            intent.setClass(RegisterActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            registerHandle.sendEmptyMessage(0x124);
                                            finish();
                                        }
                                        else{
                                            registerHandle.sendEmptyMessage(0x123);
                                        }
                                    }
                                } catch (IOException |ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        registThread.start();
                    }else{
                        Toast.makeText(RegisterActivity.this,"两次输入的密码不同",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this,"注册条件不能为空",Toast.LENGTH_SHORT).show();
                }
            }
     }
    }


    Handler registerHandle =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Toast.makeText(RegisterActivity.this,"注册失败，请重新选择账号",Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 0x124) {
                Toast.makeText(RegisterActivity.this,"注册成功，已进入主页",Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 0x125) {
                Toast.makeText(RegisterActivity.this,"正在注册，请稍后",Toast.LENGTH_SHORT).show();
            }
        }
    };

}
