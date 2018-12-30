package com.example.pascal_pc.tasklist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllTaskFragment extends TaskListFragment {

    public static AllTaskFragment newInstance() {

        Bundle args = new Bundle();

        AllTaskFragment fragment = new AllTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public AllTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public List<Task> getTasks() {
        TaskList taskList = TaskList.getInstance();
        return taskList.getAllTasks();
    }


}
