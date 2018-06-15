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
import com.example.welink.R;
import com.example.welink.adapter.ChatItemAdapter;
import com.example.welink.adapter.UserItemAdapter;
import com.example.welink.base.IMApplication;

import until.ChatMessage;
import until.User;


/**
 * 联系人页
 */

public class MessageFragment extends Fragment {
    public static ListView MessageFragmentListView ;
    public  static ChatItemAdapter chatItemAdapter ;
    private Context mContext;
    private View mView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_message, container, false);
        MessageFragmentListView=(ListView)view.findViewById(R.id.messageFragmentListView);
        chatItemAdapter=new ChatItemAdapter(this.getContext(), IMApplication.listChatItem);
        MessageFragmentListView.setAdapter(chatItemAdapter);
        MessageFragmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatMessage chatMessage =IMApplication.listChatItem.get(position);
                Intent intent =new Intent() ;
                Log.d("data","从MessageFragment跳转到Caht_page_activity");
                long chatId =chatMessage.toId;
                String chatName =chatMessage.toName ;
                intent.putExtra("chatId",chatId);
                intent.putExtra("chatName",chatName);
                intent.setClass(getActivity(),Caht_page_activity.class);
                startActivity(intent);
            }
        });
        return view ;
    }

    public  static Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg){
            if(msg.what==0x123){
                Log.d("data","更新chatItemAdapter");
                chatItemAdapter .notifyDataSetChanged();
            }
        }

    };



}