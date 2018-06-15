package com.example.welink;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.welink.SQLiteDB.DbManage;
import com.example.welink.adapter.chatPageMessageItemAdapter;
import com.example.welink.base.IMApplication;

import java.util.ArrayList;
import java.util.List;

import until.ChatMessage;

public class Caht_page_activity extends AppCompatActivity {
    public static  long    chatId ;
    public  static String  chatName ;
    public  static chatPageMessageItemAdapter chatMessageItemAdapter ;
    public   ListView chatpagelistView ;
    public  ImageButton  sendMessageButton  ,backButton;
    public   EditText contentTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caht_page_activity);
        IMApplication.listChatMessage.clear();
        Intent intent = getIntent();
        chatId=intent.getLongExtra("chatId",00000);
        chatName=intent.getStringExtra("chatName");
        TextView toolbarText=(TextView)findViewById(R.id.toolbar_text);
        toolbarText.setText(chatName);
        contentTextView=(EditText) findViewById(R.id.message_text);
        chatpagelistView=(ListView)findViewById(R.id.chat_list_view);
        IMApplication.listChatMessage= DbManage.selectChatMessage(chatId);
        chatMessageItemAdapter=new chatPageMessageItemAdapter(this,IMApplication.listChatMessage);
        chatpagelistView.setAdapter(chatMessageItemAdapter);
        backButton=(ImageButton)findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Caht_page_activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sendMessageButton=(ImageButton)findViewById(R.id.send_message_button);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=contentTextView.getText().toString();
                if(!content.equals("")){
                    ChatMessage chatMessage=new ChatMessage();
                    chatMessage.id=IMApplication.userId;
                    chatMessage.name=IMApplication.username;
                    chatMessage.content=content;
                    chatMessage.toId=chatId;
                    chatMessage.toName=chatName;
                    IMApplication.listChatMessage.add(chatMessage);
                    SendChatMessageThread sendChatMessageThread=new SendChatMessageThread(chatId,chatMessage);
                    sendChatMessageThread.start();
                    contentTextView.setText("");
                }
                else {
                    Toast.makeText(Caht_page_activity.this,"发送容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public  static Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg){
        if(msg.what==0x123){
            chatMessageItemAdapter.notifyDataSetChanged();
        }
        }

    };
}
