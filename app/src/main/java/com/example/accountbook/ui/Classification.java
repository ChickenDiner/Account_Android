/*** 分类选择/管理页
 * by gw 1711324
 */
package com.example.accountbook.ui;
import com.example.accountbook.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Classification extends AppCompatActivity {
    Button but_return;
    RecyclerView recyclerView;
    RecycleviewAdapter adapter;
    List<LabelItemC> expend_list;
    List<LabelItemC> income_list;
    Boolean expend_or_spend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifcation);
        Intent intent=getIntent();
        expend_or_spend=intent.getBooleanExtra("e_or_i",true);
        but_return=(Button)findViewById(R.id.btn_classfication_return);
        recyclerView=(RecyclerView)findViewById(R.id.id_recylerView);
        expend_list=new ArrayList<LabelItemC>();
        income_list=new ArrayList<LabelItemC>();
        LabelItemC terr1=new LabelItemC("餐饮",true,R.drawable.canyin);
        LabelItemC terr2=new LabelItemC("交通",true,R.drawable.jiaotong);
        LabelItemC terr3=new LabelItemC("娱乐",true,R.drawable.huaban);
        LabelItemC terr4=new LabelItemC("服饰",true,R.drawable.fushi);
        LabelItemC terr5=new LabelItemC("其他",true,R.drawable.qita);
        expend_list.add(terr1);
        expend_list.add(terr2);
        expend_list.add(terr3);
        expend_list.add(terr4);
        expend_list.add(terr5);
        LabelItemC i_terr1=new LabelItemC("工作",true,R.drawable.work);
        LabelItemC i_terr2=new LabelItemC("兼职",true,R.drawable.jianzhi);
        LabelItemC i_terr3=new LabelItemC("理财",true,R.drawable.licai);
        LabelItemC i_terr4=new LabelItemC("礼金",true,R.drawable.lijin);
        LabelItemC i_terr5=new LabelItemC("其他",true,R.drawable.qita);
        income_list.add(i_terr1);
        income_list.add(i_terr2);
        income_list.add(i_terr3);
        income_list.add(i_terr4);
        income_list.add(i_terr5);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(expend_or_spend)
            adapter=new RecycleviewAdapter(this,expend_list);
        else
            adapter=new RecycleviewAdapter(this,income_list);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new RecycleviewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(expend_or_spend)
                {
                    Intent intent=new Intent(Classification.this,Calculate.class);
                    intent.putExtra("expend",expend_list.get(position).label_name);
                    intent.putExtra("type",1);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(Classification.this,Calculate.class);
                    intent.putExtra("expend",income_list.get(position).label_name);
                    intent.putExtra("type",2);
                    startActivity(intent);
                }

            }
        });

        but_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Classification.this,Calculate.class);
                startActivity(intent);
            }
        });

    }
}
