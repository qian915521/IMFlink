package com.example.welink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.welink.Caht_page_activity;
import com.example.welink.R;
import com.example.welink.base.IMApplication;

import java.util.List;

import until.ChatMessage;
import until.User;

/**
 * Created by mango on 2018/5/4.
 */

public class chatPageMessageItemAdapter extends ArrayAdapter<ChatMessage> {
    List<ChatMessage> list ;
    LayoutInflater mInflater ;
    public chatPageMessageItemAdapter(Context context , List<ChatMessage> objects ){
        super(context, R.layout.list_chat_message_left,objects);
        list = objects;
        mInflater=LayoutInflater.from(context);
    }

    public View getView(int position , View convertView , ViewGroup parent) {
        ChatMessage chatMessage =list.get(position);
        // if(convertView==null){
            if(chatMessage.id== IMApplication.userId){
                convertView=mInflater.inflate(R.layout.list_chat_message_right,parent,false);
            }
            else {
                convertView=mInflater.inflate(R.layout.list_chat_message_left,parent,false);
            }
        //  }
        TextView  chatText=(TextView)convertView.findViewById(R.id.chat_user_text);
        chatText.setText(chatMessage.content);
        return  convertView ;
    }
}
