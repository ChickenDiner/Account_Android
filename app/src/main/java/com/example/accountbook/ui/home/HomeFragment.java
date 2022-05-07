package com.example.accountbook.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.R;
import com.example.accountbook.backend.AccountInquiry;
import com.example.accountbook.backend.AccountItem;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    Context context;
    TextView tv_month;
    Button btn_next;
    Button btn_last;
    RecyclerView recyclerView;
    RecordAdapter recordAdapter;
    List<Item> list;
    ImageView imageView_blank;
    TextView textView_blank;
    TextView textView_expend;
    TextView textView_income;
    private AccountInquiry inquiry;

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH)+1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        tv_month=root.findViewById(R.id.text_month);
        btn_next=root.findViewById(R.id.btn_next);
        btn_last=root.findViewById(R.id.btn_last);
        recyclerView=root.findViewById(R.id.record_recylerView);
        imageView_blank=root.findViewById(R.id.img_write);
        textView_blank=root.findViewById(R.id.tv_sentence);
        textView_expend=root.findViewById(R.id.tv_expendnum);
        textView_income=root.findViewById(R.id.tv_incomenum);
        list=new ArrayList<Item>();
        tv_month.setText(String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
        inquiry = new AccountInquiry(this.getActivity());


        final java.text.SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String begin_str=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
        Date begin=new Date();
        try {
            begin=formatter.parse(begin_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<AccountItem> a_list=inquiry.inquiryBetweenDate(begin,begin);
        for(int i=0;i<a_list.size();i++)
        {
            Item terr=new Item(a_list.get(i).getType(),a_list.get(i).isExpense(),String.valueOf(a_list.get(i).getMoney()));
            list.add(terr);
        }
        if(list.size()!=0)
        {
            imageView_blank.setVisibility(View.GONE);
            textView_blank.setVisibility(View.GONE);
            double sum_expend=inquiry.inquiryExpenseSumOnDate(begin);
            double sum_import=inquiry.inquiryIncomeSumOnDate(begin);
            textView_expend.setText(String.valueOf(sum_expend));
            textView_income.setText(String.valueOf(sum_import));
        }
        context=getActivity();
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recordAdapter=new RecordAdapter(getContext(),list);
        recyclerView.setAdapter(recordAdapter);

        btn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(day>1)
                {
                    day=day-1;
                }
                else
                {
                    if(month>1)
                    {
                        month=month-1;
                        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)
                            day=31;
                        else if(month==4||month==6||month==9||month==11)
                            day=30;
                        else if(year%4==0)
                            day=29;
                        else
                            day=28;
                    }
                    else
                    {
                        year=year-1;
                        month=12;
                        day=31;
                    }


                }
                tv_month.setText(String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));

                String date=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
                list.clear();
                Date begin=new Date();
                try {
                    begin=formatter.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                List<AccountItem> a_list=inquiry.inquiryBetweenDate(begin,begin);


                for(int i=0;i<a_list.size();i++)
                {
                    Item terr=new Item(a_list.get(i).getType(),a_list.get(i).isExpense(),String.valueOf(a_list.get(i).getMoney()));
                    list.add(terr);
                }
                if(list.size()!=0)
                {
                    imageView_blank.setVisibility(View.GONE);
                    textView_blank.setVisibility(View.GONE);

                }
                else
                {
                    imageView_blank.setVisibility(View.VISIBLE);
                    textView_blank.setVisibility(View.VISIBLE);

                }
                double sum_expend=inquiry.inquiryExpenseSumOnDate(begin);
                double sum_import=inquiry.inquiryIncomeSumOnDate(begin);
                textView_expend.setText(String.valueOf(sum_expend));
                textView_income.setText(String.valueOf(sum_import));
                context=getActivity();
                LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recordAdapter=new RecordAdapter(getContext(),list);
                recyclerView.setAdapter(recordAdapter);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)
                {
                    if (day<31)
                        day=day+1;
                    else if(month==12)
                    {
                        year=year+1;
                        month=1;
                        day=1;
                    }
                    else
                    {
                        month=month+1;
                        day=1;
                    }
                }
                else if(month==4||month==6||month==9||month==11)
                {
                    if(day<30)
                        day=day+1;
                    else
                    {
                        month=month+1;
                        day=1;
                    }
                }
                else
                {
                    if(year%4==0 && day==29)
                    {
                        month=month+1;
                        day=1;
                    }
                    else if(year%4!=0 && day==28)
                    {
                        month=month+1;
                        day=1;
                    }
                    else
                        day=day+1;
                }

                tv_month.setText(String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
                String date=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
                list.clear();
                Date begin=new Date();
                try {
                    begin=formatter.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                List<AccountItem> a_list=inquiry.inquiryBetweenDate(begin,begin);
                for(int i=0;i<a_list.size();i++)
                {
                    Item terr=new Item(a_list.get(i).getType(),a_list.get(i).isExpense(),String.valueOf(a_list.get(i).getMoney()));
                    list.add(terr);
                }
                if(list.size()!=0)
                {
                    imageView_blank.setVisibility(View.GONE);
                    textView_blank.setVisibility(View.GONE);

                }
                else
                {
                    imageView_blank.setVisibility(View.VISIBLE);
                    textView_blank.setVisibility(View.VISIBLE);
                }
                double sum_expend=inquiry.inquiryExpenseSumOnDate(begin);
                double sum_import=inquiry.inquiryIncomeSumOnDate(begin);
                textView_expend.setText(String.valueOf(sum_expend));
                textView_income.setText(String.valueOf(sum_import));
                context=getActivity();
                LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recordAdapter=new RecordAdapter(getContext(),list);
                recyclerView.setAdapter(recordAdapter);
            }
        });


        return root;
    }

}