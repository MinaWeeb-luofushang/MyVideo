package com.example.myvideo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.base.BasePager;
import com.example.pager.AudioPager;
import com.example.pager.NetAudioPager;
import com.example.pager.NetVideoPager;
import com.example.pager.VideoHomePager;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private FrameLayout fl_main_content;
    private RadioGroup rg_main_tg;

    //页面集合
    private ArrayList<BasePager> basePagers;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        fl_main_content=findViewById(R.id.fl_main_content);
        rg_main_tg=findViewById(R.id.rg_main_tg);

        basePagers = new ArrayList<>();
        basePagers.add(new VideoHomePager(this));//本地视频
        basePagers.add(new AudioPager(this));//本地引用
        basePagers.add(new NetAudioPager(this));//网络音乐
        basePagers.add(new NetVideoPager(this));//网络视频

        //导航栏选中的监听
        rg_main_tg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_main_tg.check(R.id.rb_video_home_tv);//默认选中第一个

    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            switch (checkedId){
                default:
                    position = 0;
                    break;
                case R.id.rb_video_tv:
                    position = 1;
                    break;
                case R.id.rb_video_internet:
                    position = 2;
                    break;
                case R.id.rb_video_myself:
                    position = 3;
                    break;
            }

            setFragent();
        }
    }

    //把页面添加进入
    private void setFragent() {
        //获取FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction ft = manager.beginTransaction();
        System.out.println(manager+"============"+ft);
        //事务替换
        ft.replace(R.id.fl_main_content,new Fragment());
        //提交事务
        ft.commit();

    }
    //继承Fragment 添加视图 重写onCreateView
    public class MyFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            BasePager basePager = getBasePager();
            if (basePager!=null){
                return basePager.rootview;
            }
            return null;
        }
    }
    //根据位置得到对应页面
    private BasePager getBasePager() {
        BasePager basePager = basePagers.get(position);
        if (basePager!=null&&basePager.isInitDate){
            basePager.initData();//请求或者绑定数据
            basePager.isInitDate = true;//不用重复加载，调用一次后不会再调用第二次
        }
        return basePager;
    }
    //测试创建数据库
    private class MyDataDbHelper extends SQLiteOpenHelper{


        public MyDataDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public SQLiteDatabase getReadableDatabase() {
            return super.getReadableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
