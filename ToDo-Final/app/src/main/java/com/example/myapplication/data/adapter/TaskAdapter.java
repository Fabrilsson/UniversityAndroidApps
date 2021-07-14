package com.example.myapplication.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.TasksHelper;
import com.example.myapplication.data.model.Task;
import com.example.myapplication.util.RecyclerViewOnClickListenerHack;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolderOnClick> {

    private List<Task> tasks;
    private MainActivity activity;
    private TasksHelper tasksHelper;
    private FloatingActionButton floatingActionButton;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    public TaskAdapter(MainActivity activity, TasksHelper tasksHelper, FloatingActionButton floatingActionButton){
        this.activity = activity;
        this.tasks = new ArrayList<>();
        this.tasksHelper = tasksHelper;
        this.floatingActionButton = floatingActionButton;
    }

    public ViewHolderOnClick onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);

        return new ViewHolderOnClick(itemView);
    }

    public void onBindViewHolder(ViewHolderOnClick holder, int position) {
        Task item = tasks.get(position);
        holder.task.setText(item.getName());
        holder.task.setChecked(toBoolean(item.getStatus()));
    }

    public int getItemCount() {
        return tasks.size();
    }

    public boolean toBoolean(int value){
        return value != 0;
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public class ViewHolderOnClick extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CheckBox task;

        public ViewHolderOnClick(View view){
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
            task.setOnClickListener(this);
            task.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(view, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {

            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onLongPressClickListener(view, getPosition());
            }

            return false;
        }
    }

    public void insertTask(Task task) {
        if(tasks == null)
            tasks = new ArrayList<>();

        tasks.add(task);
        orderList();
        notifyDataSetChanged();
    }

    public void deleteTask(Task task) {
        tasks.removeIf(s -> s.getId() == task.getId());
        orderList();
        notifyDataSetChanged();
    }

    public void updateTask(Task task) {
        tasks.removeIf(t -> t.getId() == task.getId());
        tasks.add(task);
        orderList();
        notifyDataSetChanged();
    }

    private void orderList(){
        tasks.sort(Comparator.comparing(Task::getNameString));
    }
}