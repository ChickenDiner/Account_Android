package com.example.accountbook.backend;

import java.util.Date;

public class AccountItem {
    public static final String TYPE_CATERING = "餐饮";
    public static final String TYPE_TRANSPORTATION = "交通";
    public static final String TYPE_ENTERTAINMENT = "娱乐";
    public static final String TYPE_CLOTHS = "服饰";
    public static final String TYPE_FULL_TIME_JOB = "工作";
    public static final String TYPE_PART_TIME_JOB = "兼职";
    public static final String TYPE_FINANCIAL = "理财";
    public static final String TYPE_CASHGIFT = "礼金";
    public static final String TYPE_OTHER = "其它";

    // id
    public int      id;
    // 日期
    public Date     date;
    // 金额
    public double   money;
    // 收入或支出
    public boolean  isIncome;
    // 类型
    public String   type;
    // 备注
    public String   info;

    // 这个构造函数理论上应该由后端调用
    public AccountItem() {
        id          = -1;
        date        = new Date();
        money       = 0;
        isIncome    = true;
        type        = "";
        info        = "";
    }
    // 这个构造函数理论上应该由后端调用
    public AccountItem(int id, Date date, Double money, boolean isIncome, String type, String info) {
        this.id         = id;
        this.date       = date;
        this.money      = money;
        this.isIncome   = isIncome;
        this.type       = type;
        this.info       = info;
    }
    // 可能会有BUG
    @Deprecated
    public AccountItem(Date date, Double money, boolean isIncome, String type, String info) {
        this.id         = -1;
        this.date       = date;
        this.money      = money;
        this.isIncome   = isIncome;
        this.type       = type;
        this.info       = info;
    }

    public int getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public double getMoney() {
        return money;
    }
    public String getType() {
        return type;
    }
    public String getInfo() {
        return info;
    }
    // 这个账目是收入吗？
    public boolean isIncome() {
        return isIncome;
    }
    // 这个账目是支出吗?
    public boolean isExpense() {
        return !isIncome;
    }
}
