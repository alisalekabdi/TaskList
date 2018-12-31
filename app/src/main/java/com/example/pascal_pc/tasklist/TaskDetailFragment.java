package com.example.pascal_pc.tasklist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {



    private static final String EXRTA_TASK_ID = "com.example.pascal_pc.tasklist.taskId";
    private TextView mDescriptionTxtView;
    private TextView mTimeTxtView;
    private TextView mDateTxtView;
    private Button mDltBtn;
    private Button mEditeBtn;
    private Button mDoneBtn;
    private Task mTask;

    public static TaskDetailFragment newInstance(UUID taskID) {

        Bundle args = new Bundle();

        args.putSerializable(EXRTA_TASK_ID,taskID);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID taskId= (UUID) getArguments().getSerializable(EXRTA_TASK_ID);
        mTask=TaskList.getInstance().getTask(taskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_task_detail, container, false);

        mDescriptionTxtView=view.findViewById(R.id.description_text_view);
        mDateTxtView=view.findViewById(R.id.date_text_view);
        mTimeTxtView=view.findViewById(R.id.time_text_view);
        mDoneBtn=view.findViewById(R.id.done_btn);
        mDltBtn=view.findViewById(R.id.delete_btn);
        mEditeBtn=view.findViewById(R.id.edite_btn);

        mDescriptionTxtView.setText(mTask.getDescription());
//        mDateTxtView.setText(new SimpleDateFormat("EEE-d MMM-yyyy ").format(mTask.getDate().toString()));
//        mTimeTxtView.setText(new SimpleDateFormat("kk:mm").format(mTask.getDate().toString()));

        return view;
    }

}
