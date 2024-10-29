package com.example.collegescheduler.ui.ToDo;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.collegescheduler.R;
import com.example.collegescheduler.ui.Assignments.Edit;

import java.util.ArrayList;

public class EditTask extends DialogFragment {
    Button cancel;
    Button confirm;
    EditText input;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edittask, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cancel = getView().findViewById(R.id.CancelButtonTask);
        confirm = getView().findViewById(R.id.ConfirmButton);
        input = getView().findViewById(R.id.editTextText);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(EditTask.this).commit();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = input.getText().toString();
                if(str.equals("")){
                    Toast.makeText(getContext(), "Please fill in the blank.", Toast.LENGTH_SHORT).show();
                }else{
                    ToDo.tasks.set(ToDo.index,str);
                    ArrayList<Integer> checked = new ArrayList<>();
                    for (int i = 0; i < ToDo.tasks.size(); i++) {
                        if (ToDo.todoList.isItemChecked(i)) {
                            checked.add(i);
                        }
                    }
                    ToDo.todoList.setAdapter(ToDo.adapter);
                    for (int i = 0; i < checked.size(); i++) {
                        ToDo.todoList.setItemChecked(checked.get(i), true);
                    }

                    ToDo.save();
                    getFragmentManager().beginTransaction().remove(EditTask.this).commit();
                }
            }
        });
    }
}
