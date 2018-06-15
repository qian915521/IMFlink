package com.example.welink;

import android.util.Log;

import com.example.welink.Fragment.MessageFragment;
import com.example.welink.SQLiteDB.DbManage;
import com.example.welink.base.IMApplication;

import java.io.IOException;

import until.ChatMessage;
import until.SocketMessage;

/**
 * Created by mango on 2018/5/4.
 */

public class SendChatMessageThread extends Thread {
    private  long toId;
    private  ChatMessage chatMessage =new ChatMessage();

    public SendChatMessageThread(long toId , ChatMessage chatMessage){
        this.toId=toId;
        this.chatMessage=chatMessage;
    }

    @Override
    public void run(){
        try {
            SocketMessage socketMessage=new SocketMessage();
            socketMessage.what="chat_message";
            socketMessage.chatMessage=chatMessage;
            IMApplication.out.writeObject(socketMessage);
            DbManage.insertTable(toId,chatMessage);
            Caht_page_activity.handler.sendEmptyMessage(0x123);
            for(int i = 0 ; i <IMApplication.listChatItem.size() ; i++) {
                ChatMessage chatMessage1 =IMApplication.listChatItem.get(i);
                if(chatMessage1.id==chatMessage.toId){
                   IMApplication.listChatItem.remove(i);
                }
            }
            IMApplication.listChatItem.add(chatMessage);
            Log.d("data","lisChatItem共有"+IMApplication.listChatItem.size()+"条数据");
            MessageFragment.handler.sendEmptyMessage(0x123);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
