/*** 计算页面
 *  by gw 1711324
 */
package com.example.accountbook.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accountbook.MainActivity;
import com.example.accountbook.R;
import com.example.accountbook.backend.AccountInquiry;

import java.util.Date;

public class Calculate extends AppCompatActivity {
    private AccountInquiry inquiry;

    Button btn_return;
    Button btn_expend;
    Button btn_income;
    Button btn_more;
    Button btn_none;
    Button btn_save;
    EditText edit_money_color;
    TextView money_sign_color;
    boolean expend_or_income;//expend为true，income为false
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * added by hrl
         */
        inquiry = new AccountInquiry(this);

        setContentView(R.layout.activity_calculate);
        expend_or_income=true;
        btn_return=(Button)findViewById(R.id.btn_return);
        btn_expend=(Button)findViewById(R.id.btn_account_expend);
        btn_income=(Button)findViewById(R.id.btn_account_income);
        btn_more=(Button)findViewById(R.id.btn_more);
        btn_none=(Button)findViewById(R.id.btn_none);
        btn_save=(Button)findViewById(R.id.btn_save);
        edit_money_color=(EditText) findViewById(R.id.et_money);
        money_sign_color=(TextView)findViewById(R.id.tv_y);

        Intent intent=getIntent();
        if(intent.getIntExtra("type",0)==1)
        {
            String expend_label=intent.getStringExtra("expend");
            money_sign_color.setTextColor(Color.parseColor("#333333"));
            edit_money_color.setHintTextColor(Color.parseColor("#333333"));
            edit_money_color.setTextColor(Color.parseColor("#333333"));
            expend_or_income=true;
            btn_expend.setTextColor(Color.parseColor("#008577"));
            btn_income.setTextColor(Color.parseColor("#000000"));
            btn_none.setText(expend_label);
        }
        if(intent.getIntExtra("type",0)==2)
        {
            String expend_label=intent.getStringExtra("expend");
            money_sign_color.setTextColor(Color.parseColor("#FF0000"));
            edit_money_color.setHintTextColor(Color.parseColor("#FF0000"));
            edit_money_color.setTextColor(Color.parseColor("#FF0000"));
            expend_or_income=false;
            btn_expend.setTextColor(Color.parseColor("#000000"));
            btn_income.setTextColor(Color.parseColor("#008577"));
            btn_none.setText(expend_label);
        }
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Calculate.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_expend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money_sign_color.setTextColor(Color.parseColor("#333333"));
                edit_money_color.setHintTextColor(Color.parseColor("#333333"));
                edit_money_color.setTextColor(Color.parseColor("#333333"));
                expend_or_income=true;
                btn_expend.setTextColor(Color.parseColor("#008577"));
                btn_income.setTextColor(Color.parseColor("#000000"));
                btn_none.setText("餐饮");
            }
        });
        btn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money_sign_color.setTextColor(Color.parseColor("#FF0000"));
                edit_money_color.setHintTextColor(Color.parseColor("#FF0000"));
                edit_money_color.setTextColor(Color.parseColor("#FF0000"));
                expend_or_income=false;
                btn_expend.setTextColor(Color.parseColor("#000000"));
                btn_income.setTextColor(Color.parseColor("#008577"));
                btn_none.setText("工作");
            }
        });
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Calculate.this, Classification.class);
                intent.putExtra("e_or_i",expend_or_income);
                startActivityForResult(intent,1);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * added by hrl
                 */
                String type = btn_none.getText().toString();
                boolean isIncome = !expend_or_income;
                Date date = new Date();
                String moneyStr = edit_money_color.getText().toString();
                if (moneyStr.length()==0) {
                    moneyStr = "0.0";
                    Toast.makeText(Calculate.this, "请输入合法数值", Toast.LENGTH_SHORT).show();
                }
                else {
                    Double money = Double.valueOf(edit_money_color.getText().toString());
                    Log.d(" - Insert", isIncome ? "收入" : "支出" + ", " + type + " " + moneyStr);
                    inquiry.insert(date, money, isIncome, type, "");

                    // 返回
                    Intent intent = new Intent(Calculate.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
