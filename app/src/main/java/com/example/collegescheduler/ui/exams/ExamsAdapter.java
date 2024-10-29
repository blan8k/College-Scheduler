package com.example.collegescheduler.ui.exams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;

import java.util.List;

public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ExamViewHolder> {

    private List<String> examsList;
    private View.OnClickListener onItemClickListener; // Add an OnClickListener member

    // Adjusted constructor to accept an OnClickListener
    public ExamsAdapter(List<String> examsList, View.OnClickListener onItemClickListener) {
        this.examsList = examsList;
        this.onItemClickListener = onItemClickListener; // Initialize the click listener
    }

    // Method to update the list of exams and refresh the RecyclerView
    public void updateExams(List<String> newExams) {
        this.examsList.clear();
        this.examsList.addAll(newExams);
        notifyDataSetChanged(); // Notify the adapter to refresh the views
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout for each item in the list
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam, parent, false);
        itemView.setOnClickListener(onItemClickListener); // Set the click listener to itemView
        return new ExamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        // Set the text for the TextView from the exams list
        String examDetail = examsList.get(position);
        holder.examTextView.setText(examDetail);
        holder.itemView.setTag(position); // Tag the itemView with its position
    }

    @Override
    public int getItemCount() {
        // Return the size of the exams list
        return examsList != null ? examsList.size() : 0;
    }

    static class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView examTextView; // TextView to display exam details

        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextView from the item layout
            examTextView = itemView.findViewById(R.id.textViewExamDetail);
        }
    }
}
