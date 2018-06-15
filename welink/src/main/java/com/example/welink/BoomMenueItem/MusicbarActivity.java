package com.example.welink.BoomMenueItem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.welink.MainActivity;
import com.example.welink.R;

public class MusicbarActivity extends AppCompatActivity {
    private ImageButton ToolBackMenu ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicbar);
        ToolBackMenu=(ImageButton)findViewById(R.id.back);
        ToolBackMenu.setOnClickListener(new menuButtonOnclickListener());
    }
    //菜单按钮监听处理
    private class menuButtonOnclickListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.back){
                Intent intent =new Intent().setClass(MusicbarActivity.this,MainActivity.class);
                startActivity(intent);

            }
        }
    }
}
