package com.example.welink.nav_menu_item;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.welink.MainActivity;
import com.example.welink.R;

public class MypageActivity extends AppCompatActivity {
    ImageButton ToolbackButtom ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        ToolbackButtom=(ImageButton)findViewById(R.id.back);
        ToolbackButtom.setOnClickListener(new menuButtonOnClickListener());
    }

    private class menuButtonOnClickListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.back){
                Intent intent =new Intent().setClass(MypageActivity.this, MainActivity.class);
                startActivity(intent);
            }

        }
    }
}
