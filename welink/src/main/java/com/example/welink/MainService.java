package com.example.welink;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.welink.Fragment.LinkmanFragment;
import com.example.welink.Fragment.MessageFragment;
import com.example.welink.SQLiteDB.DbManage;
import com.example.welink.base.IMApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import until.ChatMessage;
import until.LinkUser;
import until.SocketMessage;
import until.User;

public class MainService extends Service {
    public MainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent ,int flags ,int startId){
        super.onStartCommand(intent, flags, startId);
        Thread thread =new Thread(runnable);
        thread.start();
        return START_STICKY ;
    }


    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while(true)
            {
                try{
                    Log.d("data","主线程正在运行");
                    SocketMessage socketmessage=new SocketMessage();
                    socketmessage=(SocketMessage) IMApplication.in.readObject();
                    Log.d("data",socketmessage.what);

                    //联系人信息处理
                    if(socketmessage.what.equals("back_linktable")){
                        Log.d("data","联系人处理开始");
                        LinkUser[]  linkTable =socketmessage.linkTable ;
                        for(int i=0 ;i<Arrays.asList(linkTable).size();i++){
                            LinkUser linkUser =Arrays.asList(linkTable).get(i);
                            IMApplication.listLinkUser.add(linkUser);
                        }
                        LinkmanFragment.handler.sendEmptyMessage(0x123);
                        Log.d("data","联系人处理结束");
                    }

                    //消息信息处理
                    if(socketmessage.what.equals("chat_message")){
                        ChatMessage chatMessage =new ChatMessage() ;
                        chatMessage=socketmessage.chatMessage ;
                        DbManage.insertTable(chatMessage.id ,chatMessage);
                        if(chatMessage.id==Caht_page_activity.chatId){
                            IMApplication.listChatMessage.add(chatMessage);
                            Caht_page_activity.handler.sendEmptyMessage(0x123);
                        }
                        //判断在会话列表中是否存在
                        for(int i = 0 ; i <IMApplication.listChatItem.size() ; i++) {
                            ChatMessage chatMessage1 =IMApplication.listChatItem.get(i);
                            if(chatMessage1.id==chatMessage.id){ //存在则修改其值
                                IMApplication.listChatItem.remove(i);
                            }
                               IMApplication.listChatItem.add(chatMessage);
                               MessageFragment.handler.sendEmptyMessage(0x123);
                        }
                    }

                    //添加好友处理
                    if(socketmessage.what.equals("search_friend_back")){
                        if(socketmessage.linkUser!=null){
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("user",socketmessage.linkUser);
                            Message message=new Message();
                            message.what=0x123;
                            message.setData(bundle);
                            SearchAddFriend.handler.sendMessage(message);
                        }
                        if(socketmessage.user==null){
                            SearchAddFriend.handler.sendEmptyMessage(0x124);
                        }
                    }
                    IMApplication.socketMessage=socketmessage;
                } catch (ClassNotFoundException |IOException e) {
                    e.printStackTrace();
                    Log.d("data","主线程出现错误");
                }

            }
        }
    };

}
