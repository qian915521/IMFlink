package com.example.welink.SQLiteDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.welink.base.IMApplication;

import java.util.ArrayList;
import java.util.List;

import until.ChatMessage;
import until.User;

/**
 * Created by mango on 2018/5/2.
 */

public class DbManage {
    public static SQLiteDatabase database;

    //创建数据库，命名规则为Db+用户id;
    public static  boolean createDb( ){
        String DbName="Db"+String.valueOf(IMApplication.userId);
        String path="/data/data/com.example.welink/"+DbName+".db";
        database=SQLiteDatabase.openOrCreateDatabase(path,null);
        return true ;
    }

    //创建表，表名为table+用户id;
    public  static  boolean createTable(long chatId){
        createDb();
        String tableName="chatMessageTable" +String.valueOf(chatId);
        String  createTableSql="create table if not exists " +tableName+ " ("+ "id  int(10)  not null ,name  varchar(20),content varchar(255) , time  varchar(30) , toId  int(10) , toName  varchar(255)   );" ;
        database.execSQL(createTableSql);
        database.close();
        return true ;
    }

    //增加聊天记录
    public static  boolean  insertTable(Long chatId ,ChatMessage chatMessage){
        createDb();
        String tableName = "chatMessageTable" + String.valueOf(chatId);
        String createTableSql = "create table if not exists " + tableName + " (" + "id  int(10)  not null ,name  varchar(20),content varchar(255) , time  varchar(30) , toId  int(10) ,toName  varchar(255)   );";
        database.execSQL(createTableSql);
        ChatMessage chatMessage1=new ChatMessage();
        chatMessage1=chatMessage;
        ContentValues values =new ContentValues();
        values.put("id",chatMessage1.id);
        values.put("name",chatMessage1.name);
        values.put("content",chatMessage1.content);
        values.put("time",chatMessage1.time);
        values.put("toId",chatMessage1.toId);
        values.put("toName",chatMessage1.toName);
        //String insertTableSql="insert into "+tableName+" ( id ,name ,content , time ,toId ) values (" +chatMessage.id+","+chatMessage.name+","+chatMessage.content+","+chatMessage.time +","+chatMessage.toId  +" );" ;
        // database.execSQL(insertTableSql);
        database.insert(tableName,null,values);
        database.close();
        return true ;
    }

   //查询聊天记录
    public  static List<ChatMessage> selectChatMessage(Long chatId) {
        createDb();
        List<ChatMessage> list = new ArrayList<>();
        String tableName = "chatMessageTable" + String.valueOf(chatId);
        String createTableSql = "create table if not exists " + tableName + " (" + "id  int(10)  not null ,name  varchar(20),content varchar(255) , time  varchar(30) , toId  int(10) ,toName  varchar(255)   );";
        database.execSQL(createTableSql);
        Cursor cursor = database.query(tableName, null, null, null, null, null, null);
        int count = cursor.getCount();
        Log.d("data", "共有" + count + "行数据");
        if(cursor.getCount()!=0) {
            for (cursor.moveToFirst(); !cursor.isLast(); cursor.moveToNext()) {
                Log.d("data", "正在将查询结果封装到list中");
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.id = cursor.getInt(0);
                chatMessage.name = cursor.getString(1);
                chatMessage.content = cursor.getString(2);
                chatMessage.time = cursor.getString(3);
                chatMessage.toId = cursor.getInt(4);
                chatMessage.toName = cursor.getString(5);
                list.add(chatMessage);
                Log.d("data",chatMessage.content);
            }
        }
        cursor.close();
        database.close();
        return list ;
    }




}
