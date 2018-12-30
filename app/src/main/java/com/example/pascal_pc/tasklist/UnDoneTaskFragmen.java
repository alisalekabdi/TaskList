package com.example.pascal_pc.tasklist;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UnDoneTaskFragmen extends TaskListFragment {

    public static UnDoneTaskFragmen newInstance() {
        
        Bundle args = new Bundle();
        
        UnDoneTaskFragmen fragment = new UnDoneTaskFragmen();
        fragment.setArguments(args);
        return fragment;
    }

    public UnDoneTaskFragmen() {
        // Required empty public constructor
    }


    @Override
    public List<Task> getTasks() {
            TaskList taskList = TaskList.getInstance();
            return taskList.getUnDoneTasks();
    }
}
