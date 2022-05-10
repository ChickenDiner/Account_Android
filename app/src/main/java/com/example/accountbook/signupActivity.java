package com.example.accountbook;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signupActivity extends AppCompatActivity {
    private Button mBtnsinup;
    private EditText mEtUser;
    private EditText mEtPassword;
    private EditText mEtPassword2;
    final DatabaseHelper dbHelper=new DatabaseHelper(signupActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mEtUser = findViewById(R.id.et_signup1);
        mEtPassword = findViewById(R.id.signup2);
        mEtPassword2 = findViewById(R.id.signup3);
        mBtnsinup = findViewById(R.id.btsignup);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        mBtnsinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                String username = mEtUser.getText().toString();
                String password = mEtPassword.getText().toString();
                String password2 = mEtPassword2.getText().toString();

                Cursor cursor = db.rawQuery("select * from information where name = ? ", new String[]{username});
                int a = cursor.getCount();
                cursor.close();
                if(a != 0){
                    Toast.makeText(getApplicationContext(),"User has existed",Toast.LENGTH_SHORT).show();
                }
                else if(password.equals(password2)){
                    values.put("name",username);
                    values.put("password",password);
                    long id =db.insert("information",null,values);
                    Log.d("myDeBug","insert");
                    db.close();

                    Toast.makeText(getApplicationContext(),"Sign up successful",Toast.LENGTH_SHORT).show();

                    Intent intent = null;
                    intent = new Intent(signupActivity.this,loginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Password double checked error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}