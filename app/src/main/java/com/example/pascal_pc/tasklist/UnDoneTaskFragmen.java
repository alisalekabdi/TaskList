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

    private static final String EXTRA_POSITION ="com.example.pascal_pc.tasklist.position" ;

    public static UnDoneTaskFragmen newInstance(int position) {
        
        Bundle args = new Bundle();
        args.putInt(EXTRA_POSITION,position);
        
        UnDoneTaskFragmen fragment = new UnDoneTaskFragmen();
        fragment.setArguments(args);
        return fragment;
    }

    public UnDoneTaskFragmen() {
        // Required empty public constructor
    }

    @Override
    public int getCurrentPosition() {
        return getArguments().getInt(EXTRA_POSITION);
    }

    @Override
    public List<Task> getTasks() {
//            TaskList taskList = TaskList.getInstance();
            return TaskList.getInstance().getUnDoneTasks();
    }
}
