package com.example.accountbook.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Item>> mData;

    public HomeViewModel() {
        mData = new MutableLiveData<>();
        //mText.setValue("开始记账");
    }

    public LiveData<List<Item>> getData() {
        return mData;
    }
}