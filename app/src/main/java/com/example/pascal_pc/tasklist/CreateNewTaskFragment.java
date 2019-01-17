package com.example.pascal_pc.tasklist;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewTaskFragment extends Fragment {

    private static final String DIALOG_DATE_TAG = "DialogDate";
    private static final String DIALOG_TIME_TAG = "DialogTime";
    private static final int REQ_DATE_PICKER = 0;
    private static final int REQ_TIME_PICKER = 1;
    private static final String EXTRA_USER_ID ="userId" ;

    private Button mCreateBtn;
    private Button mDateBtn;
    private Button mTimeBtn;
    private EditText mTitleEditeTxt;
    private EditText mDescriptionEditeTxt;
    private CheckBox mIsDoneCheckBox;
    private String mUserId;
//    boolean isSearched = false;

    private Task mTask;

    public static CreateNewTaskFragment newInstance(String userId) {

        Bundle args = new Bundle();
        args.putString(EXTRA_USER_ID,userId);

        CreateNewTaskFragment fragment = new CreateNewTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserId=getArguments().getString(EXTRA_USER_ID);
    }

    public CreateNewTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_create_new_task, container, false);

        mTask=new Task();

        mTitleEditeTxt=view.findViewById(R.id.title_editText);
        mDescriptionEditeTxt=view.findViewById(R.id.descriptiion_editText);
        mCreateBtn=view.findViewById(R.id.create_btn);
        mDateBtn=view.findViewById(R.id.date_button_new_task);
        mTimeBtn=view.findViewById(R.id.time_button_new_task);
        mIsDoneCheckBox=view.findViewById(R.id.isDone_checkBox_newTask);

        mTitleEditeTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDescriptionEditeTxt.addTextChangedListener(new TextWatcher() {
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
        mIsDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTask.setDone(isChecked);
            }
        });
        mDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getDate());
                datePickerFragment.setTargetFragment(CreateNewTaskFragment.this,
                        REQ_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), DIALOG_DATE_TAG);
            }
        });
        mTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getDate());
                timePickerFragment.setTargetFragment(CreateNewTaskFragment.this, REQ_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), DIALOG_TIME_TAG);
            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTask.getTitle()!=null){
                    TaskList.getInstance(getActivity()).addTask(mTask,mUserId);
                    getActivity().finish();
                }else {
                    Toast.makeText(getActivity(), "You should fill title", Toast.LENGTH_SHORT).show();
                }
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
            mTimeBtn.setText(new SimpleDateFormat("kk:mm").format(date));
        }

        if (requestCode == REQ_DATE_PICKER) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTask.setDate(date);
            mDateBtn.setText(new SimpleDateFormat("EEE-d MMM-yyyy ").format(date));
        }
    }

}
