package com.example.pascal_pc.tasklist;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {

    private static final String DIALOG_DATE_TAG = "DialogDate";
    private static final String DIALOG_TIME_TAG = "DialogTime";
    private static final int REQ_DATE_PICKER = 0;
    private static final int REQ_TIME_PICKER = 1;

    private static final String EXRTA_TASK_ID = "com.example.pascal_pc.tasklist.taskId";
    private static final String EXTRA_USER_ID = "userId";
    private TextView mDescriptionTxtView;
    private TextView mTimeTxtView;
    private TextView mDateTxtView;
    private Button mDltBtn;
    private Button mEditeBtn;
    private CheckBox mDoneCheckBox;
    private Task mTask;
    private String mUserId;
    private boolean mPermit=false;

    public static TaskDetailFragment newInstance(UUID taskID,String userId) {

        Bundle args = new Bundle();

        args.putSerializable(EXRTA_TASK_ID,taskID);
        args.putString(EXTRA_USER_ID,userId);
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
        mUserId=getArguments().getString(EXTRA_USER_ID);
        mTask=TaskList.getInstance(getActivity()).getTask(taskId,mUserId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_task_detail, container, false);

        mDescriptionTxtView=view.findViewById(R.id.description_text_view);
        mDateTxtView=view.findViewById(R.id.date_text_view);
        mTimeTxtView=view.findViewById(R.id.time_text_view);
        mDoneCheckBox=view.findViewById(R.id.done_checkBox);
        mDltBtn=view.findViewById(R.id.delete_btn);
        mEditeBtn=view.findViewById(R.id.edite_btn);

        mDescriptionTxtView.setText(mTask.getDescription());
       mDateTxtView.setText(new SimpleDateFormat("EEE-d MMM-yyyy ").format(mTask.getDate()));
        mTimeTxtView.setText(new SimpleDateFormat("kk:mm").format(mTask.getDate()));
        mDoneCheckBox.setChecked(mTask.isDone());
        mEditeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPermit=true;
            }
        });
//        if(mPermit=false){
//            mDescriptionTxtView.setEnabled(false);
//            mDateTxtView.setEnabled(false);
//            mTimeTxtView.setEnabled(false);
//            mDltBtn.setEnabled(false);
//            mDoneCheckBox.setEnabled(false);
//        }else{
//            mDescriptionTxtView.setEnabled(true);
//            mDateTxtView.setEnabled(true);
//            mTimeTxtView.setEnabled(true);
//            mDltBtn.setEnabled(true);
//            mDoneCheckBox.setEnabled(true);
//        }

        mDescriptionTxtView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getDate());
                datePickerFragment.setTargetFragment(TaskDetailFragment.this,
                        REQ_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), DIALOG_DATE_TAG);
            }
        });
        mTimeTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getDate());
                timePickerFragment.setTargetFragment(TaskDetailFragment.this, REQ_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), DIALOG_TIME_TAG);
            }
        });
        mDltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(getActivity());
                alertBuilder.setTitle("Delete this item");
                alertBuilder.setIcon(R.drawable.ic_delete);
                alertBuilder.setMessage("Are you sure to delete task!!!");
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaskList.getInstance(getActivity()).removeTask(mTask,mUserId);
                        getActivity().finish();
                    }
                });
                alertBuilder.setNegativeButton("No",null);
                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
        });
        mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTask.setDone(isChecked);
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQ_TIME_PICKER) {
            Date date= (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mTask.setDate(date);
            mTimeTxtView.setText(new SimpleDateFormat("kk:mm").format(date));
        }

        if (requestCode == REQ_DATE_PICKER) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTask.setDate(date);
            mDateTxtView.setText(new SimpleDateFormat("EEE-d MMM-yyyy ").format(date));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        TaskList.getInstance(getActivity()).update(mTask,mUserId);
    }
}
