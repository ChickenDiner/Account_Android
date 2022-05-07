package com.example.accountbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.accountbook.ui.Calculate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.accountbook.backend.AccountInquiry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.sql.Date;
import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {
    FloatingActionButton add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** 初始化数据库
          + by hrl 1711326
         */
        AccountInquiry accountInquiry = new AccountInquiry(MainActivity.this);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_charts)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        /**
         * 悬浮按钮点击事件
         * by gw 1711324
         */
        add_btn=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        //获取当前时间
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        //Date date = new Date(System.currentTimeMillis());

        //tv_month.setText("Date获取当前日期时间");
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calculate.class);
                startActivity(intent);
            }
        });
    }

}
