package com.example.accountbook.backend;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountInquiry {
    private AccountDataBase db;
    public AccountInquiry(Context context) {
        db = new AccountDataBase(context);
        Log.d(" - Inquiry", "Inquiry Layer initialized");
    }
    // 增加账单
    public void insert(Date date, double money, boolean isIncome, String type, String info) {
        db.insertItem(date, money, isIncome, type, info);
        Log.w(" - Inquiry", "insert");
    }
    // 修改账单, 根据id改
    public void modify(int id, Date date, double money, boolean isIncome, String type, String info) {
        db.modifyItem(id, date, money, isIncome, type, info);
        Log.w(" - Inquiry", "modify");
    }
    // 删除账单，根据id删
    public void delete(int id) {
        db.deleteItem(id);
        Log.w(" - Inquiry", "delete");
    }
    // 返回所有
    public ArrayList<AccountItem> inquiryAll() {
        Log.w(" - Inquiry", "inquiry all");
        return db.inquiryItems(null, null);
    }
    public ArrayList<AccountItem> inquiryAll(String section, String[] sectionArgs) {
        return db.inquiryItems(section, sectionArgs);
    }
    public ArrayList<AccountItem> inquiryBetweenDate (Date begin, Date end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String beginDateStr = format.format(begin);
        String endDateStr   = format.format(end);
        return db.inquiryItems(db.KEY_DATE + ">=? AND " + db.KEY_DATE + "<=?",
                new String[]{
                        beginDateStr,
                        endDateStr
                });
    }

    public double inquiryIncomeSumOnDate(Date date) {
        return db.inquirySumOnDate(date, true);
    }

    public double inquiryExpenseSumOnDate(Date date) {
        return db.inquirySumOnDate(date, false);
    }

    public double inquiryIncomeSumBetweenDate(Date begin, Date end) {
        return db.inquirySumBetweenDate(begin, end, true);
    }

    public double inquiryExpenseSumBetweenDate(Date begin, Date end) {
        return db.inquirySumBetweenDate(begin, end, false);
    }

    public List<AccountItem> inquiryIncomeSumOnTypeBetweenDate(Date dateBegin, Date dateEnd) {
        return db.inquirySumAllType(dateBegin, dateEnd, true);
    }

    public List<AccountItem> inquiryExpenseSumOnTypeBetweenDate(Date dateBegin, Date dateEnd) {
        return db.inquirySumAllType(dateBegin, dateEnd, false);
    }

}
