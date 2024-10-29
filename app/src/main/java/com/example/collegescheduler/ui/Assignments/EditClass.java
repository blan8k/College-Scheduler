package com.example.collegescheduler.ui.Assignments;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.collegescheduler.R;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EditClass extends Activity {
    final static String DATE_FORMAT = "MM/dd/yyyy";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height));
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EditText c = findViewById(R.id.uClass);
        EditText a = findViewById(R.id.uAssignment);
        EditText d = findViewById(R.id.uDueDate);
        Object object = Assignments.listView.getItemAtPosition(Assignments.index);
        HashMap<String, String> pastInput = (HashMap<String, String>) object;
        c.setText(pastInput.get("line1"));
        a.setText(pastInput.get("line2"));
        d.setText(pastInput.get("line3"));
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.getText().toString().equals("") || a.getText().toString().equals("") ||
                        d.getText().toString().equals("")) {
                    Toast.makeText(EditClass.this, "Please complete all text fields.", Toast.LENGTH_SHORT).show();
                } else if (!isDateValid(d.getText().toString())) {
                    Toast.makeText(EditClass.this, "Please enter the date in correct format.", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> item = new HashMap<>();
                    item.put("line1", c.getText().toString());
                    item.put("line2", a.getText().toString());
                    item.put("line3", d.getText().toString());
                    Assignments.list.set(Assignments.index, item);
                    Assignments.listView.setAdapter(Assignments.simpleAdapter);
                    Toast.makeText(EditClass.this, "Saved.", Toast.LENGTH_SHORT).show();
                    Assignments assignments = new Assignments();
                    assignments.save();
                    finish();
                }
            }
        });
    }

    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
