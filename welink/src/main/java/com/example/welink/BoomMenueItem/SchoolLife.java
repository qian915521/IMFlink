package com.example.welink.BoomMenueItem;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.welink.MainActivity;
import com.example.welink.R;


public class SchoolLife extends AppCompatActivity {
    private ImageButton ToolBackMenu ;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_life);
        ToolBackMenu=(ImageButton)findViewById(R.id.back);
        ToolBackMenu.setOnClickListener(new menuButtonOnclickListener());

    }

    //菜单按钮监听处理
    private class menuButtonOnclickListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.back){
                Intent intent =new Intent().setClass(SchoolLife.this,MainActivity.class);
                startActivity(intent);
            }
        }
    }







}
