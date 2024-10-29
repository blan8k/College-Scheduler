package com.example.collegescheduler.ui.Assignments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegescheduler.MainActivity;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAssignmentsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Assignments extends Fragment implements java.lang.Comparable {

    private FragmentAssignmentsBinding binding;
    public static ListView listView;
    public static ArrayList<String[]> storage;
    public static ArrayList<HashMap<String, String>> list;
    public static HashMap<String, String> item;
    FloatingActionButton addButton;
    public static SimpleAdapter simpleAdapter;
    public static String[] data;
    public static boolean added = false;
    public static int index;
    public static AdapterView<?> parent1;
    public static View view1;
    private static final String LIST_KEY = "list_key";
    private static final String STRING_KEY = "string_key";
    public static SharedPreferences sp;
    public MainActivity activity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AssignmentsModel homeViewModel =
                new ViewModelProvider(this).get(AssignmentsModel.class);
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        binding = FragmentAssignmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        list = readHashmap(getContext());
        if (list == null) {
            list = new ArrayList<>();
        }
        storage = readStringArr(getContext());
        if (storage == null) {
            storage = new ArrayList<>();
        }
        simpleAdapter = new SimpleAdapter(getContext(), list, R.layout.multilines,
                new String[]{"line1", "line2", "line3"}, new int[]{R.id.className, R.id.title, R.id.dueDate});
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.assignmentList);
        listView.setAdapter(simpleAdapter);
        addButton = getView().findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this.getActivity(), addAssignment.class));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Edit edit = new Edit();
                edit.show(getActivity().getSupportFragmentManager(), "editor");
                //Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                index = position;
                parent1 = parent;
                view1 = view;
            }
        });
        Button sortClass = getView().findViewById(R.id.sortClass);
        sortClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> classes = new ArrayList<>();
                ArrayList<HashMap<String, String>> placeHolder = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    classes.add(list.get(i).get("line1"));
                    placeHolder.add(list.get(i));
                }
                Collections.sort(classes, String.CASE_INSENSITIVE_ORDER);
                for (int i = 0; i < classes.size(); i++) {
                    for (int j = 0; j < placeHolder.size(); j++) {
                        String inClass = placeHolder.get(j).get("line1");
                        if (classes.get(i).equals(inClass)) {
                            list.set(i, placeHolder.get(j));
                            placeHolder.remove(j);
                            break;
                        }
                    }
                }
                listView.setAdapter(simpleAdapter);
                save();
            }
        });
        Button sortDue = getView().findViewById(R.id.sortDueDate);
        sortDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> dueDates = new ArrayList<>();
                ArrayList<HashMap<String, String>> placeHolder = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    dueDates.add(list.get(i).get("line3"));
                    placeHolder.add(list.get(i));
                }
                Collections.sort(dueDates, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        int month1 = Integer.parseInt(o1.substring(0, o1.indexOf("/")));
                        int day1 = Integer.parseInt(o1.substring(o1.indexOf("/") + 1, o1.indexOf("/", o1.indexOf("/") + 1)));
                        int year1 = Integer.parseInt(o1.substring(o1.indexOf("/", o1.indexOf("/") + 1) + 1));
                        int month2 = Integer.parseInt(o2.substring(0, o2.indexOf("/")));
                        int day2 = Integer.parseInt(o2.substring(o2.indexOf("/") + 1, o2.indexOf("/", o2.indexOf("/") + 1)));
                        int year2 = Integer.parseInt(o2.substring(o2.indexOf("/", o2.indexOf("/") + 1) + 1));
                        if (year1 != year2) {
                            return (year1 - year2) * 10000;
                        }
                        if (month1 != month2) {
                            return (month1 - month2) * 100;
                        }
                        return day1 - day2;
                    }
                });
                for (int i = 0; i < dueDates.size(); i++) {
                    for (int j = 0; j < placeHolder.size(); j++) {
                        String inClass = placeHolder.get(j).get("line3");
                        if (dueDates.get(i).equals(inClass)) {
                            list.set(i, placeHolder.get(j));
                            placeHolder.remove(j);
                            break;
                        }
                    }
                }
                listView.setAdapter(simpleAdapter);
                save();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (added) {
            listView = (ListView) getView().findViewById(R.id.assignmentList);
            storage.add(data);
            item = new HashMap<String, String>();
            item.put("line1", storage.get(storage.size() - 1)[0]);
            item.put("line2", storage.get(storage.size() - 1)[1]);
            item.put("line3", storage.get(storage.size() - 1)[2]);
            list.add(item);
            listView.setAdapter(simpleAdapter);
            added = false;
            save();
        }


    }

    public void delete() {
        //Toast.makeText(getActivity(),"HERE",Toast.LENGTH_SHORT).show();
        list.remove(index);
        storage.remove(index);
        listView.setAdapter(simpleAdapter);
        save();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public int compareTo(Object object) {
        return 0;
    }

    public void save() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        //sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LIST_KEY, jsonString);
        Gson gson1 = new Gson();
        String jsonString1 = gson1.toJson(storage);
        editor.putString(STRING_KEY, jsonString1);
        editor.apply();
    }

    public ArrayList<HashMap<String, String>> readHashmap(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sp.getString(LIST_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        ArrayList<HashMap<String, String>> list = gson.fromJson(jsonString, type);
        return list;
    }

    public ArrayList<String[]> readStringArr(Context context) {
        //sp = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sp.getString(STRING_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String[]>>() {

        }.getType();
        ArrayList<String[]> storage1 = gson.fromJson(jsonString, type);
        return storage1;
    }

}