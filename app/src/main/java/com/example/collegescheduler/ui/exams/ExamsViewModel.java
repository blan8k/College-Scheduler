package com.example.collegescheduler.ui.exams;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExamsViewModel extends AndroidViewModel {
    private final MutableLiveData<List<String>> examsLiveData = new MutableLiveData<>();
    private final SharedPreferences sharedPreferences;
    private static final String EXAMS_KEY = "exams";

    public ExamsViewModel(Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences("ExamsPref", Context.MODE_PRIVATE);
        loadExams();
    }

    private void loadExams() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(EXAMS_KEY, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        List<String> exams = gson.fromJson(json, type);
        if (exams == null) {
            exams = new ArrayList<>();
        }
        examsLiveData.setValue(exams);
    }

    public void addExam(String exam) {
        List<String> currentExams = examsLiveData.getValue();
        if (currentExams == null) currentExams = new ArrayList<>();
        currentExams.add(exam);
        examsLiveData.setValue(currentExams);
        saveExams();
    }

    private void saveExams() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(examsLiveData.getValue());
        editor.putString(EXAMS_KEY, json);
        editor.apply();
    }

    public LiveData<List<String>> getExamsLiveData() {
        return examsLiveData;
    }
}
