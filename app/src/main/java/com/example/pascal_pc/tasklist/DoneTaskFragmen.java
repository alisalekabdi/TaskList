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

    private static final String EXTRA_POSITION ="com.example.pascal_pc.tasklist.position" ;

    public static DoneTaskFragmen newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_POSITION,position);

        DoneTaskFragmen fragment = new DoneTaskFragmen();
        fragment.setArguments(args);
        return fragment;
    }


    public DoneTaskFragmen() {
        // Required empty public constructor
    }

    @Override
    public int getCurrentPosition() {
        return getArguments().getInt(EXTRA_POSITION);
    }


    @Override
    public List<Task> getTasks() {
        return TaskList.getInstance().getDoneTasks();
    }


}
