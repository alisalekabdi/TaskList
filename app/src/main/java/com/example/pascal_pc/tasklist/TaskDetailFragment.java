package com.example.pascal_pc.tasklist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;

import java.text.SimpleDateFormat;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends DialogFragment {

    private static final String DIALOG_EDIT_FRAGMENT_TAG = "dialog_edit_task";
    private static final int REQ_EDIT_FRAGMENT = 0;


    private static final String EXTRA_TASK_ID = "com.example.pascal_pc.tasklist.taskId";
    private static final String EXTRA_USER_ID = "userId";
    private TextView mDescriptionTxtView;
    private TextView mTimeTxtView;
    private TextView mDateTxtView;
    private Button mEditBtn;
    private Button mDltBtn;
    private CheckBox mDoneCheckBox;
    private Task mTask;
    private UUID taskId;
    private String mUserId;


    public static TaskDetailFragment newInstance(UUID taskID, String userId) {

        Bundle args = new Bundle();

        args.putSerializable(EXTRA_TASK_ID, taskID);
        args.putString(EXTRA_USER_ID, userId);
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

        getInfo();
    }

    public void getInfo() {
        taskId = (UUID) getArguments().getSerializable(EXTRA_TASK_ID);
        mUserId = getArguments().getString(EXTRA_USER_ID);
        mTask = TaskList.getInstance(getActivity()).getTask(taskId, mUserId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        setDetail(view);
        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTaskDetailFragment fragment = EditTaskDetailFragment.newInstance(taskId, mUserId);
                fragment.setTargetFragment(TaskDetailFragment.this, REQ_EDIT_FRAGMENT);
                fragment.show(getFragmentManager(), DIALOG_EDIT_FRAGMENT_TAG);


            }
        });

        mDescriptionTxtView.setEnabled(false);
        mDateTxtView.setEnabled(false);
        mTimeTxtView.setEnabled(false);
        mDoneCheckBox.setEnabled(false);
        mDltBtn.setVisibility(View.GONE);

        return view;
    }

    private void setDetail(View view) {
        mDescriptionTxtView = view.findViewById(R.id.description_text_view);
        mDateTxtView = view.findViewById(R.id.date_text_view);
        mTimeTxtView = view.findViewById(R.id.time_text_view);
        mDoneCheckBox = view.findViewById(R.id.done_checkBox);
        mEditBtn = view.findViewById(R.id.edite_btn);
        mDltBtn=view.findViewById(R.id.delete_btn);

        mDescriptionTxtView.setText(mTask.getDescription());
        mDateTxtView.setText(new SimpleDateFormat("EEE-d MMM-yyyy ").format(mTask.getDate()));
        mTimeTxtView.setText(new SimpleDateFormat("kk:mm").format(mTask.getDate()));
        mDoneCheckBox.setChecked(mTask.isDone());
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
        setDetail(getView());
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

}
