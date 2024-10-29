package com.example.collegescheduler.ui.ToDo;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.collegescheduler.R;

import java.util.ArrayList;


public class Edit1 extends DialogFragment {
    public Button cancel;
    public Button check;
    public Button edit;
    public Button delete;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editor1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cancel = getView().findViewById(R.id.CancelButton);
        check = getView().findViewById(R.id.CancelButtonTask);
        edit = getView().findViewById(R.id.ConfirmButton);
        delete = getView().findViewById(R.id.deleteButton1);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo.done = new ArrayList<>();
                for (int i = 0; i < ToDo.tasks.size(); i++) {
                    if (ToDo.todoList.isItemChecked(i)) {
                        ToDo.done.add(i);
                    }
                }
                ToDo.save();
                getFragmentManager().beginTransaction().remove(Edit1.this).commit();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo.todoList.setItemChecked(ToDo.index,true);
                ToDo.done = new ArrayList<>();
                for (int i = 0; i < ToDo.done.size(); i++) {
                    if (ToDo.todoList.isItemChecked(i)) {
                        ToDo.done.add(i);
                    }
                }
                ToDo.save();
                getFragmentManager().beginTransaction().remove(Edit1.this).commit();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo.tasks.remove(ToDo.index);
                int zero = 0;
                ToDo.done = new ArrayList<>();
                for (int i = 0; i < ToDo.tasks.size()+1; i++) {
                    if(ToDo.index == i){
                        zero = 1;
                    }
                    if (ToDo.todoList.isItemChecked(i)) {
                        ToDo.done.add(i-zero);
                    }
                }
                ToDo.todoList.setAdapter(ToDo.adapter);
                for (int i = 0; i < ToDo.done.size(); i++) {
                    ToDo.todoList.setItemChecked(ToDo.done.get(i), true);
                }
                ToDo.save();
                getFragmentManager().beginTransaction().remove(Edit1.this).commit();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(Edit1.this).commit();
                EditTask editTask = new EditTask();
                editTask.show(getActivity().getSupportFragmentManager(), "edittask");
                getActivity().getSupportFragmentManager().executePendingTransactions();
               // DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
               // int width = metrics.widthPixels;
                //int height = metrics.heightPixels;
                editTask.getDialog().getWindow().setLayout(1100,950);
            }
        });
    }
}
