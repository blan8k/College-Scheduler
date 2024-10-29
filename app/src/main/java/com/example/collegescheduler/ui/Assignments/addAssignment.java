package com.example.collegescheduler.ui.Assignments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.collegescheduler.R;
import com.google.android.material.datepicker.CalendarConstraints;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addAssignment extends Activity {
    final static String DATE_FORMAT = "MM/dd/yyyy";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assignments);
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
        Button add = findViewById(R.id.save);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText classText = findViewById(R.id.uClass);
                EditText assignmentText = findViewById(R.id.uAssignment);
                EditText dueDateText = findViewById(R.id.uDueDate);
                String classT = classText.getText().toString();
                String assignmentT = assignmentText.getText().toString();
                String dueT = dueDateText.getText().toString();

                if (classT.equals("") || assignmentT.equals("") ||
                        dueT.equals("")) {
                    Toast.makeText(addAssignment.this, "Please complete all text fields.", Toast.LENGTH_SHORT).show();
                } else if (!isDateValid(dueT)) {
                    Toast.makeText(addAssignment.this, "Please enter the date in correct format.", Toast.LENGTH_SHORT).show();
                } else {
                    String[] assignment = {classT, assignmentT, dueT};
                    Assignments.data = assignment;
                    Assignments.added = true;
                    Toast.makeText(addAssignment.this, "Assignment Added", Toast.LENGTH_SHORT).show();
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
