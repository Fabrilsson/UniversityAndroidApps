package com.example.myapplication.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.data.TasksHelper;
import com.example.myapplication.data.adapter.TaskAdapter;
import com.example.myapplication.data.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


public class EditTask extends Fragment {

    private Task task;
    private FloatingActionButton floatingActionButton;
    private TaskAdapter taskAdapter;
    protected TasksHelper tasksHelper;

    public EditTask(Task task, FloatingActionButton floatingActionButton, TaskAdapter taskAdapter, TasksHelper tasksHelper) {
        this.task = task;
        this.floatingActionButton = floatingActionButton;
        this.taskAdapter = taskAdapter;
        this.tasksHelper = tasksHelper;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText taskName = (EditText)view.findViewById(R.id.taskName);
        EditText taskAnnotation = (EditText)view.findViewById(R.id.taskAnotation);

        taskName.setText(task.getName());
        taskAnnotation.setText(task.getAnnotation());

        Button saveTask = view.findViewById(R.id.saveTask);

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setName(taskName.getText().toString());
                task.setAnnotation(taskAnnotation.getText().toString());
                task.setCreationDate(new Date());

                taskAdapter.updateTask(task);
                tasksHelper.updateTask(task);

                FragmentManager fm = getFragmentManager();
                fm.popBackStack("placeHolder", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        floatingActionButton.show();
    }
}