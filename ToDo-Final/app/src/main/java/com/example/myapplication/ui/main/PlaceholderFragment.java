package com.example.myapplication.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.TasksHelper;
import com.example.myapplication.data.adapter.TaskAdapter;
import com.example.myapplication.data.model.Task;
import com.example.myapplication.util.RecyclerViewOnClickListenerHack;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaceholderFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private static PlaceholderFragment fragmentTodo;
    private static PlaceholderFragment fragmentDone;
    private PageViewModel pageViewModel;
    private RecyclerView tasksRecycleView = null;
    private static TaskAdapter toDoTaskAdapter;
    private static TaskAdapter doneTaskAdapter;
    private List<Task> toDoTasks;
    private List<Task> doneTasks;
    private TasksHelper tasksHelper;

    private FloatingActionButton fab;

    public static PlaceholderFragment newInstance(int index) {

        if (index == 2) {
            fragmentTodo = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_SECTION_NUMBER, index);
            fragmentTodo.setArguments(bundle);
            return fragmentTodo;
        }
        else {
            fragmentDone = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_SECTION_NUMBER, index);
            fragmentDone.setArguments(bundle);
            return fragmentDone;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tasksHelper = new TasksHelper(getActivity());

        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        pageViewModel.setIndex(index);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toDoTasks = new ArrayList<>();
        doneTasks = new ArrayList<>();

        tasksRecycleView = view.findViewById(R.id.tasksRecyclerView);
        tasksRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));


        int index = pageViewModel.getIndex();

        fab = view.findViewById(R.id.fab);

        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddNewTask();
                }
            });
        }

        if(index == 2) {

            List<Task> toDoTasks = tasksHelper.getAllToDoTasks();
            this.toDoTasks.addAll(toDoTasks);
            toDoTaskAdapter = new TaskAdapter((MainActivity)getActivity(), tasksHelper, fab);
            tasksRecycleView.setAdapter(toDoTaskAdapter);

            toDoTaskAdapter.setRecyclerViewOnClickListenerHack(this);
            toDoTaskAdapter.setTasks(this.toDoTasks);
        }
        else {

            List<Task> doneTasks = tasksHelper.getAllDoneTasks();
            this.doneTasks.addAll(doneTasks);
            doneTaskAdapter = new TaskAdapter((MainActivity)getActivity(), tasksHelper, fab);
            tasksRecycleView.setAdapter(doneTaskAdapter);

            doneTaskAdapter.setRecyclerViewOnClickListenerHack(this);
            doneTaskAdapter.setTasks(this.doneTasks);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        int index = pageViewModel.getIndex();

        View root = null;

        if(index == 2)
            root = inflater.inflate(R.layout.fragment_todo, container, false);
        else
            root = inflater.inflate(R.layout.fragment_done, container, false);

        return root;
    }

    @Override
    public void onClickListener(View view, int position) {

        int index = pageViewModel.getIndex();

        if (index == 2) {
            Task task = toDoTasks.get(position);
            toDoTaskAdapter.deleteTask(task);

            task.setStatus(1);
            task.setFinishDate(new Date());

            task.setStrikedName(task.getName().toString());

            doneTaskAdapter.insertTask(task);

            tasksHelper.updateTaskStatus(task);
        }
        else {
            Task task = doneTasks.get(position);
            doneTaskAdapter.deleteTask(task);

            task.setStatus(0);
            task.setFinishDate(null);

            task.setName(task.getName().toString());

            toDoTaskAdapter.insertTask(task);

            tasksHelper.updateTaskStatus(task);
        }
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        int index = pageViewModel.getIndex();

        if (index == 2) {
            Task task = toDoTasks.get(position);
            showActionsDialog(task, position);
        }
    }

    private void showAddNewTask(){
        fab.hide();
        Fragment addNewTask = new AddNewTask(fab, toDoTaskAdapter, tasksHelper);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.teste123, addNewTask);
        transaction.addToBackStack("placeHolder");
        transaction.commit();
    }

    private void showActionsDialog(final Task task, final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose a action");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int acao) {
                if (acao == 0) {
                    showEditFragment(task);
                } else {
                    deleteTask(task, position);
                }
            }
        });
        builder.show();
    }

    private void showEditFragment(Task task) {
        fab.hide();
        Fragment addNewTask = new EditTask(task, fab, toDoTaskAdapter, tasksHelper);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.teste123, addNewTask);
        transaction.addToBackStack("placeHolder");
        transaction.commit();
    }

    private void deleteTask(Task task, int position) {
        toDoTaskAdapter.deleteTask(task);
        tasksHelper.deleteTask(task);
    }
}