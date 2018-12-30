package com.example.pascal_pc.tasklist;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTaskFragmen extends TaskListFragment {

    public static DoneTaskFragmen newInstance() {

        Bundle args = new Bundle();

        DoneTaskFragmen fragment = new DoneTaskFragmen();
        fragment.setArguments(args);
        return fragment;
    }


    public DoneTaskFragmen() {
        // Required empty public constructor
    }


    @Override
    public List<Task> getTasks() {
        TaskList taskList = TaskList.getInstance();
        return taskList.getDoneTasks();
    }


}
