package com.example.myapplication.ui.main;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.adapter.TaskAdapter;
import com.example.myapplication.data.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private RecyclerView tasksRecycleView = null;
    private TaskAdapter taskAdapter;
    private List<Task> tasks;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        tasks = new ArrayList<>();

        tasksRecycleView = view.findViewById(R.id.tasksRecyclerView);
        tasksRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskAdapter = new TaskAdapter((MainActivity)getActivity());
        tasksRecycleView.setAdapter(taskAdapter);

        int index = pageViewModel.getIndex();

        FloatingActionButton fab = view.findViewById(R.id.fab);

        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment aaa = new AddNewTask();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.teste123, aaa);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }

        Task task = new Task();
        SpannableString string = null;

        if(index == 2) {
            string = new SpannableString("This is a task");
            task.setStatus(0);
        }
        else{
            string = new SpannableString("This is a done task");
            string.setSpan(new StrikethroughSpan(), 0, string.length(), 0);
            task.setStatus(1);
        }

        task.setDescription(string);
        task.setId(1);

        tasks.add(task);
        tasks.add(task);
        tasks.add(task);
        tasks.add(task);
        tasks.add(task);

        taskAdapter.setTasks(tasks);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        int index = pageViewModel.getIndex();

        View root = null;

        if(index == 2)
            root = inflater.inflate(R.layout.fragment_main, container, false);
        else
            root = inflater.inflate(R.layout.fragment_done, container, false);

        //final TextView textView = root.findViewById(R.id.section_label);
        //pageViewModel.getText().observe(this, new Observer<String>() {
            //@Override
            //public void onChanged(@Nullable String s) {
                //textView.setText(s);
            //}
        //});
        return root;
    }
}