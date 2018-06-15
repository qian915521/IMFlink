package com.example.welink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.welink.R;

import java.util.List;
import java.util.Objects;

import until.LinkUser;
import until.User;

/**
 * Created by mango on 2018/5/3.
 */

public class UserItemAdapter extends ArrayAdapter<LinkUser> {
    List<LinkUser>  list ;
    LayoutInflater mInflater ;
    public UserItemAdapter(Context context  ,List<LinkUser> objects ){
        super(context,R.layout.list_user_item,objects);
        list = objects;
        mInflater=LayoutInflater.from(context);
    }


    @Override
    public View getView(int position , View convertView , ViewGroup parent){
        LinkUser user =list.get(position);
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.list_user_item,parent,false);
        }
        TextView userName=(TextView)convertView.findViewById(R.id.user_item_username);
        TextView userText=(TextView)convertView.findViewById(R.id.user_item_usertext);
        userName.setText(user.name);
        userText.setText(user.usertext);
        return convertView ;
    }

}
