package com.example.collegescheduler.ui.Assignments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.collegescheduler.R;

public class Edit extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editor, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button cancel = getView().findViewById(R.id.CancelButtonTask);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(Edit.this).commit();
            }
        });
        Button delete = getView().findViewById(R.id.deleteButton1);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(Edit.this).commit();
                Assignments assignments = new Assignments();
                assignments.delete();
            }
        });
        Button edit = getView().findViewById(R.id.ConfirmButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Edit.this.getActivity(), EditClass.class));
                getFragmentManager().beginTransaction().remove(Edit.this).commit();
            }
        });
    }
}
