package com.example.welink.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.welink.R;
import com.example.welink.base.IMApplication;

import java.util.List;

import until.ChatMessage;
import until.User;

/**
 * Created by mango on 2018/5/4.
 */

public class ChatItemAdapter extends ArrayAdapter<ChatMessage> {
    List<ChatMessage> list ;
    LayoutInflater mInflater ;
    public ChatItemAdapter(Context context , List<ChatMessage> objects ){
        super(context, R.layout.list_chat_item,objects);
        list = objects;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public View getView(int position , View convertView , ViewGroup parent){
        ChatMessage chatMessage =list.get(position);
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.list_chat_item,parent,false);
        }
        Log.d("data","执行到ChatItemAdapter");
        TextView userName=(TextView)convertView.findViewById(R.id.chat_item_username);
        TextView userText=(TextView)convertView.findViewById(R.id.chat_item_usertext);
        if(chatMessage.id!= IMApplication.userId) {
            userName.setText(chatMessage.name);
            userText.setText(chatMessage.content);
        }
        else {
            userName.setText(chatMessage.toName);
            userText.setText(chatMessage.content);
        }
        return convertView ;
    }

}
