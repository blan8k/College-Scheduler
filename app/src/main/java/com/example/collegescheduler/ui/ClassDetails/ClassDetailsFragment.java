package com.example.collegescheduler.ui.ClassDetails;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAssignmentsBinding;
import com.example.collegescheduler.databinding.FragmentExamsBinding;
import com.example.collegescheduler.databinding.FragmentToDoBinding;
import com.example.collegescheduler.ui.Assignments.Edit;
import com.example.collegescheduler.ui.ToDo.ToDoModel;
import com.example.collegescheduler.ui.exams.ExamsViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;


public class ClassDetailsFragment extends AppCompatActivity {

  ListView listView1;
  EditText inputText1;
  Button btnAdd, btnUpdate;

  ArrayList<String> classes = new ArrayList<String>();
  ArrayAdapter myAdapter1;

  Integer indecxVal;
  String item;
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.classdetails);

    listView1 = (ListView) findViewById(R.id.listview1);
    btnAdd = (Button) findViewById(R.id.button1);
    btnUpdate = (Button) findViewById(R.id.button2);
    inputText1 = (EditText) findViewById(R.id.editText);

    classes.add("EXAMPLE: Linear Algebra - 2pm - Salvador Barone");
    classes.add("EXAMPLE: Introduction to OOP - 3:30pm - Richard Landry");

    myAdapter1 = new ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, classes);
    listView1.setAdapter(myAdapter1);

    btnAdd.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
          String stringval = inputText1.getText().toString();
          classes.add(stringval);
          myAdapter1.notifyDataSetChanged();

          inputText1.setText("");
        }
    });

    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString() + "has been selected";
        indecxVal = i;
        Toast.makeText(ClassDetailsFragment.this, item, Toast.LENGTH_SHORT).show();
      }
    });

    btnUpdate.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        String stringval = inputText1.getText().toString();
        classes.set(indecxVal, stringval);
        myAdapter1.notifyDataSetChanged();
      }
    });


    listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long  l) {
        item = adapterView.getItemAtPosition(i).toString() + "has been selected";
        Toast.makeText(ClassDetailsFragment.this, item, Toast.LENGTH_SHORT).show();

        classes.remove(i);
        myAdapter1.notifyDataSetChanged();

        return true;
      }
    });
  }
}
