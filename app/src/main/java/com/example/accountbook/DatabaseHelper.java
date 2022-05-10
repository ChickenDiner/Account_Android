package com.example.accountbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper  extends SQLiteOpenHelper {
    public DatabaseHelper(Context context){super(context,"Test.db",null,1);}
    //第一个参数是上下文，第二个参数是数据库名称，
    //第三个参数是CursorFactory对象，一般设置为null，第四个参数是数据库的版本
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE information(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),password VARCHAR(20))");

    }
    //创建表 表名information 表结构 自增id，字符串姓名，int年龄
    //数据库版本发生变化时调用
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("myDeBug","数据库版本已更新");
    }
}
