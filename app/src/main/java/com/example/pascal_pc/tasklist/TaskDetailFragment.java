package com.example.pascal_pc.tasklist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pascal_pc.tasklist.models.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {

    private TextView mDescriptionTxtView;
    private TextView mTimeTxtView;
    private TextView mDateTxtView;
    private Task mTask;

    public static TaskDetailFragment newInstance() {

        Bundle args = new Bundle();

        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public TaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_task_detail, container, false);

        mDescriptionTxtView=view.findViewById(R.id.description_text_view);
        mDateTxtView=view.findViewById(R.id.date_text_view);
        mTimeTxtView=view.findViewById(R.id.time_text_view);

        return view;
    }

}
