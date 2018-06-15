package com.example.welink;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.welink.BoomMenueItem.ExerciseActivity;
import com.example.welink.BoomMenueItem.GameActivity;
import com.example.welink.BoomMenueItem.MoreActivity;
import com.example.welink.BoomMenueItem.MusicbarActivity;
import com.example.welink.BoomMenueItem.PiaoActivity;
import com.example.welink.BoomMenueItem.SchoolLife;
import com.example.welink.BoomMenueItem.TaogeqiuActivity;
import com.example.welink.BoomMenueItem.TravelworldActivity;
import com.example.welink.BoomMenueItem.WenkuActivity;
import com.example.welink.Fragment.ExploreFragment;
import com.example.welink.Fragment.LinkmanFragment;
import com.example.welink.Fragment.MessageFragment;
import com.example.welink.nav_menu_item.MyexploreActivity;
import com.example.welink.nav_menu_item.MypageActivity;
import com.example.welink.nav_menu_item.PayActivity;
import com.example.welink.nav_menu_item.SettingActivity;
import com.example.welink.nav_menu_item.WenjianActivity;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    View headerView;
    ImageButton write_explore ,search_add ,freash;
    TextView toolBartextView;
    BoomMenuButton boomMenuButton;
    ImageButton menu;
    private View currentBtn;//标记 当前按钮view
    ImageButton messgeMenu ,linkmanMenu,exploreMenu ;
     MessageFragment messageFragment  ;
    LinkmanFragment  linkmanFragment ;
    ExploreFragment exploreFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取ToolBar中TextView组件//获取ToolBar中的menu按钮及监听此按钮
        search_add=(ImageButton)findViewById(R.id.layout_search_add);
        search_add.setOnClickListener(new ToolBarMenuOnClickListener());
        write_explore=(ImageButton)findViewById(R.id.layout_write_explore) ;
        write_explore.setOnClickListener(new ToolBarMenuOnClickListener());
        menu=(ImageButton)findViewById(R.id.menu) ;
        freash=(ImageButton)findViewById(R.id.layout_tongzhi);
        freash.setOnClickListener(new ToolBarMenuOnClickListener());
        menu.setOnClickListener(menulistener);
        toolBartextView =(TextView)findViewById(R.id.toolBarTextView) ;
        //获取三个Fragment
        messageFragment=(MessageFragment) getFragmentManager().findFragmentById(R.id.layout_fragmeng_message) ;
        linkmanFragment=(LinkmanFragment) getFragmentManager().findFragmentById(R.id.layout_fragment_linkmanFragment);
        exploreFragment=(ExploreFragment) getFragmentManager().findFragmentById(R.id.layout_fragment_explore);
        //获取底部三个菜单按钮(实际为获取外部的布局
        messgeMenu=(ImageButton) findViewById(R.id.layout_messageMenu) ;
        messgeMenu.setOnClickListener(new bottomMenulistener());
        linkmanMenu=(ImageButton) findViewById(R.id.layout_linkMenu);
        linkmanMenu.setOnClickListener(new bottomMenulistener());
        exploreMenu=(ImageButton)findViewById(R.id.layout_exploreMenu);
        exploreMenu.setOnClickListener(new bottomMenulistener());
        messgeMenu.performClick();
        //获取nav_header头部布局中的组件
         navigationView=(NavigationView)findViewById(R.id.navigation);
         navigationView.setItemIconTintList(null);
         headerView = navigationView.getHeaderView(0);
        ImageView userImage=(ImageView) headerView.findViewById(R.id.userImage);//获取用户头像
        TextView userText=(TextView)headerView.findViewById(R.id.userName) ;//获取用户名称
        //nav_menu选项设置监听器
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new naviListener());
        //boomMenuButton组件的获取及其相关事件的处理
        boomMenuButton = (BoomMenuButton) findViewById(R.id.bmb);
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            final TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        //boomMenuButton组件中按钮单击跳转到不同页面
                        public void onBoomButtonClick(int index) {
                          switch (index){
                              case 0 : Intent intent0 =new Intent();
                                       intent0.setClass(MainActivity.this,SchoolLife.class);
                                        startActivity (intent0);break;
                              case 1 :  Intent intent1 =new Intent();
                                        intent1.setClass(MainActivity.this,TravelworldActivity.class);
                                        startActivity(intent1); break;
                              case 2 :  Intent intent2 =new Intent();
                                         intent2.setClass(MainActivity.this,MusicbarActivity.class);
                                         startActivity(intent2); break;
                              case 3 :  Intent intent3 =new Intent();
                                         intent3.setClass(MainActivity.this,WenkuActivity.class);
                                          startActivity(intent3); break;
                              case 7:  Intent intent4 =new Intent();
                                          intent4.setClass(MainActivity.this,PiaoActivity.class);
                                           startActivity(intent4); break;
                              case 4:  Intent intent5 =new Intent();
                                            intent5.setClass(MainActivity.this,ExerciseActivity.class);
                                            startActivity(intent5); break;
                              case 5:  Intent intent6 =new Intent();
                                             intent6.setClass(MainActivity.this,GameActivity.class);
                                             startActivity(intent6); break;
                              case 6:  Intent intent7 =new Intent();
                                             intent7.setClass(MainActivity.this,TaogeqiuActivity.class);
                                              startActivity(intent7); break;
                              case 8 :  Intent intent8 =new Intent();
                                              intent8.setClass(MainActivity.this,MoreActivity.class);
                                              startActivity(intent8); break;

                          }
                        }
                    })
                    .normalImageRes(getImageResource())
                    .normalText(getext());
            boomMenuButton.addBuilder(builder);
        }
        Intent intentThree = new Intent(this,MainService.class);
        startService(intentThree);
    }



    //ToolBar中的menu按钮的点击事件实现
    View.OnClickListener menulistener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DrawerLayout drawerLayout;
            drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
            drawerLayout.openDrawer(navigationView);
        }
    };
    class  ToolBarMenuOnClickListener implements View.OnClickListener{

        public void onClick(View view) {
           if(view.getId()==R.id.layout_search_add){
                 Intent intent=new Intent().setClass(MainActivity.this,SearchAddFriend.class);
                 startActivity(intent);
           }
            if(view.getId()==R.id.layout_write_explore){
                Intent intent=new Intent().setClass(MainActivity.this,WriteExplore.class);
                startActivity(intent);
            }
            if(view.getId()==R.id.layout_tongzhi){

            }
        }
    }

    //bootomMnenu中按钮点击事件的监听
    class  bottomMenulistener implements View.OnClickListener{
        public void onClick(View view) {
            // 切换Fragment
            switch (view.getId()) {
                case R.id.layout_messageMenu://消息
                    getFragmentManager().beginTransaction()
                            .show(messageFragment)
                            .hide(linkmanFragment)
                            .hide(exploreFragment)
                            .commit();
                    setButton(view);
                    freash.setVisibility(View.VISIBLE);
                    write_explore.setVisibility(View.GONE);
                    search_add.setVisibility(View.GONE);
                    toolBartextView.setText("消息");
                    findViewById(R.id.messageMenubg).setAlpha((float) 0.7);
                    findViewById(R.id.linkMenubg).setAlpha(1);
                    findViewById(R.id.exploreMenubg).setAlpha(1);
                    break;
                case R.id.layout_linkMenu://联系人
                    getFragmentManager().beginTransaction()
                            .hide(messageFragment)
                            .show(linkmanFragment)
                            .hide(exploreFragment)
                            .commit();
                    setButton(view);
                    freash.setVisibility(View.GONE);
                    write_explore.setVisibility(View.GONE);
                    search_add.setVisibility(View.VISIBLE);
                    toolBartextView.setText("通讯录");
                    findViewById(R.id.messageMenubg).setAlpha(1);
                    findViewById(R.id.linkMenubg).setAlpha((float) 0.7);
                    findViewById(R.id.exploreMenubg).setAlpha(1);
                    break;
                case R.id.layout_exploreMenu://发现
                    getFragmentManager().beginTransaction()
                            .hide(messageFragment)
                            .hide(linkmanFragment)
                            .show(exploreFragment)
                            .commit();
                    setButton(view);
                    freash.setVisibility(View.GONE);
                    write_explore.setVisibility(View.VISIBLE);
                    search_add.setVisibility(View.GONE);
                    toolBartextView.setText("发现");
                    findViewById(R.id.messageMenubg).setAlpha(1);
                    findViewById(R.id.linkMenubg).setAlpha(1);
                    findViewById(R.id.exploreMenubg).setAlpha((float) 0.7);
                    break;
                default:
                    break;
            }
        }
    }

    //nav_menu选项监听处理事件
    private class naviListener implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
         if(menuItem.getItemId()==R.id.nav_mainpage){
             Intent intent=new Intent().setClass(MainActivity.this, MypageActivity.class);
             startActivity(intent);
         }
            if(menuItem.getItemId()==R.id.nav_explore){
                Intent intent=new Intent().setClass(MainActivity.this, MyexploreActivity.class);
                startActivity(intent);
            }
            if(menuItem.getItemId()==R.id.nav_pay){
                Intent intent=new Intent().setClass(MainActivity.this, PayActivity.class);
                startActivity(intent);
            }
            if(menuItem.getItemId()==R.id.nav_cloud){
                Intent intent=new Intent().setClass(MainActivity.this, WenjianActivity.class);
                startActivity(intent);
            }
            if(menuItem.getItemId()==R.id.menu_setting){
                Intent intent=new Intent().setClass(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
            if(menuItem.getItemId()==R.id.menu_exit){
                System.exit(0);
            }

         return true;
        }

    }

    //与boomMenuButton组件的获取及其相关事件的处理有关的方法及变量开始
    private static int index = 0;
    static String getext() {
        if (index >= text.length) index = 0;
        return text[index++];

    }
    private static String [] text = new String[]{"校园生活","旅行世界","音乐台","文库","运动","小游戏","淘个球","漂流瓶","更多活动"};
    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }
    private static int[] imageResources = new int[]{
            R.drawable.school_page,
            R.drawable.trave_page,
            R.drawable.music_page,
            R.drawable.wen_page,
            R.drawable.sport_page,
            R.drawable.pai_page,
            R.drawable.tao_page,
            R.drawable.piao_page,
            R.drawable.more_page,
    };
    //与boomMenuButton组件的获取及其相关事件的处理有关的方法及变量结束

    private void setButton(View v) {
        if (currentBtn != null && currentBtn.getId() != v.getId()) {
            currentBtn.setEnabled(true);
        }
        v.setEnabled(false);
        currentBtn = v;
    }

}
