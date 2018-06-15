package com.example.welink.BoomMenueItem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.welink.MainActivity;
import com.example.welink.R;

public class TaogeqiuActivity extends AppCompatActivity {
    private ImageButton ToolBackMenu ;
    ImageSwitcher imageSwitch ;
    int currentIndex =0 ;
    int[] images = {R.drawable.head_nav_bg,R.drawable.head_nav_bg2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taogeqiu);
        ToolBackMenu=(ImageButton)findViewById(R.id.back);
        ToolBackMenu.setOnClickListener(new menuButtonOnclickListener());
        imageSwitch = (ImageSwitcher)findViewById(R.id.imageSwitch);

        imageSwitch.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                // makeView返回的是当前需要显示的ImageView控件，用于填充进ImageSwitcher中
                return new ImageView(TaogeqiuActivity.this);
            }
        });

        imageSwitch.postDelayed(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                imageSwitch.setImageResource(images[currentIndex]);
                if(currentIndex ==(images.length - 1))
                    currentIndex = 0;
                else
                    currentIndex++;
                imageSwitch.postDelayed(this,1000);
            }
        },2000);


    }
    //菜单按钮监听处理
    private class menuButtonOnclickListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.back){
                Intent intent =new Intent().setClass(TaogeqiuActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
