package com.example.welink;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.welink.Fragment.LinkmanFragment;
import com.example.welink.base.IMApplication;

import java.io.IOException;

import until.LinkUser;
import until.SocketMessage;
import until.User;

public class SearchAddFriend extends AppCompatActivity {
   private ImageButton ToolBackMenu ;
    private Button searchButton , sureadd;
    private EditText searchInput;
    public  static LinkUser user =new LinkUser();
    private static   LinearLayout adduserinformation ;private static RelativeLayout noadduserinformation ;
    private  SocketMessage socketMessage =new SocketMessage();
    private  SocketMessage socketMessage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_add_friend);
        user=null;
        socketMessage.state="";
        ToolBackMenu=(ImageButton)findViewById(R.id.back);
        searchButton=(Button)findViewById(R.id.btn_search);
        sureadd=(Button)findViewById(R.id.addSure);
        adduserinformation=(LinearLayout)findViewById(R.id.add_user_information) ;
        noadduserinformation=(RelativeLayout)findViewById(R.id.no_add_user_information) ;
        adduserinformation.setVisibility(View.GONE);
        noadduserinformation.setVisibility(View.GONE);
        searchInput=(EditText)findViewById(R.id.search_newfriend_input) ;
        sureadd.setOnClickListener(new ButtonOnclickListener());
        searchButton.setOnClickListener(new ButtonOnclickListener());
        ToolBackMenu.setOnClickListener(new ButtonOnclickListener());
    }



    //菜单按钮监听处理
   private class ButtonOnclickListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.back){
                Intent intent =new Intent().setClass(SearchAddFriend.this,MainActivity.class);
                startActivity(intent);
            }
            if(v.getId()==R.id.btn_search){
                if(searchInput.getText().toString().equals("")){
                    Toast.makeText(SearchAddFriend.this,"请输入检索条件",Toast.LENGTH_SHORT).show();
                }
                else{
                    socketMessage=new SocketMessage();
                    socketMessage.id=IMApplication.userId;
                    socketMessage.state="";
                    socketMessage.what="search_friend";
                    socketMessage.user.id=Long.parseLong(searchInput.getText().toString());
                    Thread search_friend_thread=new Thread(runnable);
                    search_friend_thread.start();
                    searchInput.setText("");
                }
            }
            if(v.getId()==R.id.addSure){
                socketMessage2=new SocketMessage();
                socketMessage2.user.id=socketMessage.user.id;
                socketMessage2.id=IMApplication.userId;
                socketMessage2.what="search_friend";
                socketMessage2.state="sureadd";
                socketMessage2.linkUser=user;
                Thread search_friend_thread2=new Thread(runnable2);
                search_friend_thread2.start();
                searchInput.setText("");
                Log.d("data","本程序用户为"+socketMessage2.user.id);
                Log.d("data","本程序用户为"+socketMessage2.linkUser.name);
            }
        }
    }

    public  static Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg){
            if(msg.what==0x123){
                noadduserinformation.setVisibility(View.GONE);
                user=(LinkUser)msg.getData().getSerializable("user");
                LinkmanFragment.handler.sendEmptyMessage(0x123);
                adduserinformation.setVisibility(View.VISIBLE);
                TextView username=(TextView)adduserinformation.findViewById(R.id.add_user_name);
                username.setText(user.name);
                TextView userage=(TextView)adduserinformation.findViewById(R.id.add_user_age);
                userage.setText(String.valueOf(user.age));
                Log.d("data",String.valueOf(user.age));
                TextView useraddress=(TextView)adduserinformation.findViewById(R.id.add_user_address);
                useraddress.setText(user.address);
                Log.d("data",user.address);
            }
            if(msg.what==0x124){
                adduserinformation.setVisibility(View.GONE);
                noadduserinformation.setVisibility(View.VISIBLE);
            }

        }

    };


    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try {
                Log.d("data","本程序用户为"+socketMessage.user.id);
                if(socketMessage.linkUser!=null){
                    Log.d("data","本程序用户为wo"+socketMessage.what);
                    Log.d("data","本程序用户为wo"+socketMessage.state);
                    Log.d("data","本程序用户为wo"+socketMessage.linkUser.name);
                }
                IMApplication.out.writeObject(socketMessage);
                IMApplication.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } ;

    Runnable runnable2=new Runnable() {
        @Override
        public void run() {
            try {
                Log.d("data","本程序用户为"+socketMessage.user.id);
                if(socketMessage.linkUser!=null){
                    Log.d("data","本程序用户为wo"+socketMessage.what);
                    Log.d("data","本程序用户为wo"+socketMessage.state);
                    Log.d("data","本程序用户为wo"+socketMessage.linkUser.name);
                }
                IMApplication.out.writeObject(socketMessage2);
                IMApplication.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } ;

}
