package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.data.TasksHelper;
import com.example.myapplication.data.adapter.TaskAdapter;
import com.example.myapplication.data.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class AddNewTask extends Fragment {

    private FloatingActionButton floatingActionButton;
    private TaskAdapter taskAdapter;
    private TasksHelper tasksHelper;

    public AddNewTask(FloatingActionButton fab, TaskAdapter taskAdapter, TasksHelper tasksHelper){
        this.floatingActionButton = fab;
        this.taskAdapter = taskAdapter;
        this.tasksHelper = tasksHelper;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button saveTask = view.findViewById(R.id.saveTask);

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText taskName = (EditText)view.findViewById(R.id.taskName);
                EditText annotation = (EditText)view.findViewById(R.id.taskAnotation);

                Task task = new Task(0, taskName.getText().toString(), annotation.getText().toString(), new Date());

                taskAdapter.insertTask(task);

                tasksHelper.insertTask(task);

                FragmentManager fm = getFragmentManager();
                fm.popBackStack("placeHolder", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        Button button = view.findViewById(R.id.taskRemember);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        floatingActionButton.show();
    }
}
