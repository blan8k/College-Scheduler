package com.example.collegescheduler.ui.ToDo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAssignmentsBinding;
import com.example.collegescheduler.databinding.FragmentToDoBinding;
import com.example.collegescheduler.ui.Assignments.Edit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ToDo extends Fragment {

    private FragmentToDoBinding binding;
    public static ListView todoList;
    public static ArrayAdapter<String> adapter;
    public static ArrayList<String> tasks;
    Button add;
    Button edit;
    Button delete;
    EditText input;
    EditText edited;
    static SharedPreferences sp;
    public static final String LIST_KEY = "LIST_KEY";
    public static final String DONE_KEY = "DONE_KEY";
    Button sort;
    public static ArrayList<Integer> done;
    public static int index;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ToDoModel galleryViewModel =
                new ViewModelProvider(this).get(ToDoModel.class);

        binding = FragmentToDoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add = getView().findViewById(R.id.addButton);
        todoList = getView().findViewById(R.id.toDoList);
        tasks = read(getContext());
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        done = readDone((getContext()));
        if (done == null) {
            done = new ArrayList<>();
        }
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_multiple_choice, tasks);
        todoList.setAdapter(adapter);
        for(int i = 0; i < done.size(); i++){
            todoList.setItemChecked(done.get(i), true);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = getView().findViewById(R.id.userInput);
                String task = input.getText().toString();
                if (task.equals("")) {
                    Toast.makeText(getContext(), "The text field is currently empty.", Toast.LENGTH_SHORT).show();
                } else {
                    tasks.add(task);
                    done = new ArrayList<>();
                    for (int i = 0; i < tasks.size(); i++) {
                        if (todoList.isItemChecked(i)) {
                            done.add(i);
                        }
                    }
                    todoList.setAdapter(adapter);
                    for (int i = 0; i < done.size(); i++) {
                        todoList.setItemChecked(done.get(i), true);
                    }
                    input.setText("");
                    save();
                }
            }
        });
        delete = getView().findViewById(R.id.clearAll);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> deleted = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                    if (todoList.isItemChecked(i)) {
                        deleted.add(i);
                    }
                }
                if (deleted.size() == 0) {
                    Toast.makeText(getContext(), "Please select tasks to delete.", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < deleted.size(); i++) {
                        tasks.remove(deleted.get(i) - i);
                    }
                    todoList.setAdapter(adapter);
                    save();
                }
            }
        });
        /*edit = getView().findViewById(R.id.Edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = 0;
                int index = 0;
                for (int i = 0; i < tasks.size(); i++) {
                    if (todoList.isItemChecked(i)) {
                        checked++;
                        index = i;
                    }
                }
                if (checked > 1) {
                    Toast.makeText(getContext(), "Please only check one task at a time to edit.", Toast.LENGTH_SHORT).show();
                } else if (checked == 0) {
                    Toast.makeText(getContext(), "Please select one task to edit.", Toast.LENGTH_SHORT).show();
                } else {
                    edited = getView().findViewById(R.id.editedText);
                    String editedTask = edited.getText().toString();
                    if (editedTask.equals("")) {
                        Toast.makeText(getContext(), "Please add a task.", Toast.LENGTH_SHORT).show();
                    } else {
                        tasks.set(index, editedTask);
                        todoList.setAdapter(adapter);
                        edited.setText("");
                        save();
                    }
                }
            }
        });*/
        sort = getView().findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done = new ArrayList<>();
                ArrayList<Integer> notDone = new ArrayList<>();
                ArrayList<String> placeHolder = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                    if (todoList.isItemChecked(i)) {
                        done.add(i);
                    } else {
                        notDone.add(i);
                    }
                    placeHolder.add(tasks.get(i));
                }
                for (int i = 0; i < done.size(); i++) {
                    tasks.set(i, placeHolder.get(done.get(i)));
                }
                for (int i = 0; i < notDone.size(); i++) {
                    tasks.set(done.size() + i, placeHolder.get(notDone.get(i)));
                }
                todoList.setAdapter(adapter);
                for (int i = 0; i < done.size(); i++) {
                    todoList.setItemChecked(i, true);
                }
                done = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                    if (todoList.isItemChecked(i)) {
                        done.add(i);
                    }
                }
                save();
            }
        });
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Edit1 edit1 = new Edit1();
                edit1.show(getActivity().getSupportFragmentManager(), "editor1");
                index = position;
                todoList.setItemChecked(position, false);
                done = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                    if (todoList.isItemChecked(i)) {
                        done.add(i);
                    }
                }
            }
        });
    }

    public static void save() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(tasks);
        String jsonString1 = gson.toJson(done);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.putString(DONE_KEY, jsonString1);
        editor.apply();
    }

    public ArrayList<String> read(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sp.getString(LIST_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> list = gson.fromJson(jsonString, type);
        return list;
    }

    public ArrayList<Integer> readDone(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sp.getString(DONE_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> list = gson.fromJson(jsonString, type);
        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}