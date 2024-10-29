package com.example.collegescheduler.ui.Assignments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AssignmentsModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AssignmentsModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}