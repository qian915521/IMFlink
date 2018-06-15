package com.example.welink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class WriteExplore extends AppCompatActivity {
    ImageButton toolBackMenu ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_explore);
        toolBackMenu=(ImageButton)findViewById(R.id.back);
        toolBackMenu.setOnClickListener(new menuButtonOnclickListener());
    }

    private class menuButtonOnclickListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.back){
                Intent intent =new Intent().setClass(WriteExplore.this,MainActivity.class);
                startActivity(intent);
            }

        }
    }
}
