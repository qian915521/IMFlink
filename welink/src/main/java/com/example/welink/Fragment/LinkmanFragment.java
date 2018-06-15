package com.example.welink.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.welink.Caht_page_activity;
import com.example.welink.Linkman;
import com.example.welink.MainActivity;
import com.example.welink.R;
import com.example.welink.adapter.UserItemAdapter;
import com.example.welink.base.IMApplication;

import java.util.ArrayList;
import java.util.List;

import until.LinkUser;
import until.User;


/**
 * 联系人页
 */
public class LinkmanFragment extends Fragment {
    public static ListView LinkManListView ;
    private View mView;
    public  static UserItemAdapter userItemAdapter ;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                         // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_link, container, false);
        LinkManListView=(ListView)view.findViewById(R.id.LinkManListView);
        userItemAdapter=new UserItemAdapter(this.getContext(),IMApplication.listLinkUser);
        LinkManListView.setAdapter(userItemAdapter);
        LinkManListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinkUser touser=IMApplication.listLinkUser.get(position);
                Intent intent =new Intent() ;
                intent.putExtra("chatId",touser.id);
                intent.putExtra("chatName",touser.name);
                intent.setClass(getActivity(),Caht_page_activity.class);
                startActivity(intent);
            }
        });
        return view ;
    }

    public static Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg){
            if(msg.what==0x123){
                Log.d("data","执行0x123");
                LinkmanFragment.userItemAdapter.notifyDataSetChanged();
            }
        }
    };

}
