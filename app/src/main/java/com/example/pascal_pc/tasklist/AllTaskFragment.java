package com.example.pascal_pc.tasklist;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllTaskFragment extends TaskListFragment {

    private static final String EXTRA_POSITON = "com.example.pascal_pc.tasklist.position";

    public static AllTaskFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.getInt(EXTRA_POSITON,position);

        AllTaskFragment fragment = new AllTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public AllTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public int getCurrentPosition() {
        return getArguments().getInt(EXTRA_POSITON);
    }

    @Override
    public List<Task> getTasks() {
//        TaskList taskList = TaskList.getInstance();
        return TaskList.getInstance().getAllTasks();
    }


}
