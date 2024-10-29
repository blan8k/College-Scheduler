package com.example.collegescheduler.ui.exams;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.collegescheduler.databinding.FragmentAddExamBinding;

import java.util.Calendar;

public class AddExamFragment extends Fragment {

    private FragmentAddExamBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddExamBinding.inflate(inflater, container, false);
        setupDateAndTimePickers();
        setupAddAndCancelButtons();
        return binding.getRoot();
    }

    private void setupDateAndTimePickers() {
        binding.editTextDate.setOnClickListener(view -> showDatePicker());
        binding.editTextTime.setOnClickListener(view -> showTimePicker());
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(requireContext(), this::onDateSet, year, month, day).show();
    }

    private void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String formattedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        binding.editTextDate.setText(formattedDate);
    }

    private void showTimePicker() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        new TimePickerDialog(requireContext(), this::onTimeSet, hour, minute, false).show();
    }

    private void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String formattedTime = String.format("%02d:%02d", hourOfDay, minute);
        binding.editTextTime.setText(formattedTime);
    }

    private void setupAddAndCancelButtons() {
        binding.buttonAdd.setOnClickListener(this::addExam);
        binding.buttonCancel.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
    }

    private void addExam(View v) {
        // Extract input data
        String subject = binding.editTextSubject.getText().toString();
        String name = binding.editTextName.getText().toString();
        String time = binding.editTextTime.getText().toString();
        String date = binding.editTextDate.getText().toString();
        String location = binding.editTextLocation.getText().toString();

        if (validateInput(subject, name, time, date, location)) {
            ExamsViewModel examsViewModel = new ViewModelProvider(requireActivity()).get(ExamsViewModel.class);
            examsViewModel.addExam(name + ", " + subject + " - " + time + ", " + date + ", " + location);
            Navigation.findNavController(v).popBackStack();
        } else {
            // Show error message or validation feedback
        }
    }

    private boolean validateInput(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return false; // Invalid input found
            }
        }
        return true; // All inputs are valid
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }
}
