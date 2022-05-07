package com.example.accountbook.ui.home;

public class Item {



    String type;
    String expend_or_income;
    String money;
    public Item(String type,boolean e_or_i,String money)
    {

        if(e_or_i)
            this.expend_or_income="支出";
        else
            this.expend_or_income="收入";
        this.money=money;
        this.type=type;
    }



    public String getExpend_or_income() {
        return expend_or_income;
    }

    public String getMoney() {
        return money;
    }

    public String getType() {
        return type;
    }
}
