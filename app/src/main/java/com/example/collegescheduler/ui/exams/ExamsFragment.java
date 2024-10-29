package com.example.collegescheduler.ui.exams;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentExamsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ExamsFragment extends Fragment {

    private FragmentExamsBinding binding;
    private ExamsViewModel examsViewModel;
    private ExamsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        examsViewModel = new ViewModelProvider(requireActivity()).get(ExamsViewModel.class);
        binding = FragmentExamsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the RecyclerView with a LinearLayoutManager
        binding.examsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the ExamsAdapter with an OnClickListener for item clicks
        adapter = new ExamsAdapter(new ArrayList<>(), this::onExamItemClick);
        binding.examsRecyclerView.setAdapter(adapter);

        examsViewModel.getExamsLiveData().observe(getViewLifecycleOwner(), exams -> {
            if (exams != null) {
                adapter.updateExams(exams);
            }
        });

        binding.btnAddExam.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_examsFragment_to_addExamFragment);
        });
    }

    // Define what happens when an exam item is clicked
    private void onExamItemClick(View view) {
        int position = (int) view.getTag(); // Retrieve the clicked item's position
        // Here, you can handle the click event, e.g., showing a dialog or navigating to another fragment
        showCurrentTimeDialog(); // For example purposes, let's just show the current time
    }

    private void showCurrentTimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Current Time");

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        builder.setMessage("Current time: " + currentTime);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
