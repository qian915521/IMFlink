package com.example.welink.base;

import android.app.Application;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import until.ChatMessage;
import until.LinkUser;
import until.SocketMessage;
import until.User;


public class IMApplication extends Application {
    public  static Socket clientSocket =new Socket() ;
    public  static ObjectInputStream  in;
    public  static ObjectOutputStream out ;
    public  static  boolean  first =true ;
    public  static SocketMessage  socketMessage =new SocketMessage(); //定义全局消息
    public  static long  userId ;
    public  static String username ;
    public  static List<LinkUser> listLinkUser =new ArrayList<LinkUser>();
    public  static List<ChatMessage> listChatItem =new ArrayList<ChatMessage>();
    public  static List<ChatMessage> listChatMessage=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
