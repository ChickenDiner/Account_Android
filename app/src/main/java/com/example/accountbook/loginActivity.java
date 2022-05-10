package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {
    //define button
    private Button mBtnSignup;
    private Button mBtnLogin;
    private EditText mEtUser;
    private EditText mEtPassword;
    final DatabaseHelper dbHelper=new DatabaseHelper(loginActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEtUser = findViewById(R.id.et_1);
        mEtPassword = findViewById(R.id.et_2);



        //find button
        mBtnSignup = findViewById(R.id.bt_2);

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(loginActivity.this,signupActivity.class);
                startActivity(intent);

            }
        });

        mBtnLogin = findViewById(R.id.bt_1);
//        mBtnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = null;
//                intent = new Intent(MainActivity.this,SigninSuccessful.class);
//                startActivity(intent);
//
//            }
//        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mEtUser.getText().toString();
                String password = mEtPassword.getText().toString();

                SQLiteDatabase db=dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select * from information where name = ? ", new String[]{username});
                int a = cursor.getCount();
                String password2 = "";
                while (cursor.moveToNext()) {
                    password2 = cursor.getString(cursor.getColumnIndex("password"));
                }
                cursor.close();


                String ok = "Login in Successfully";
                String fail = "Password error";
                Intent intent = null;
                if(a == 0){
                    Toast.makeText(getApplicationContext(),"user not existed",Toast.LENGTH_SHORT).show();
                }
                else if(password.equals(password2)){
                    Toast.makeText(getApplicationContext(),ok,Toast.LENGTH_SHORT).show();
                    intent = new Intent(loginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast toastcenter = Toast.makeText(getApplicationContext(),fail,Toast.LENGTH_SHORT);
                    toastcenter.setGravity(Gravity.CENTER,0,0);
                    toastcenter.show();
                }
                db.close();
            }
        });

    }



}