package com.example.pascal_pc.tasklist;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;
import com.example.pascal_pc.tasklist.utils.PictureUtils;

import java.io.File;
import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends DialogFragment {

    private static final String DIALOG_EDIT_FRAGMENT_TAG = "dialog_edit_task";
    private static final int REQ_EDIT_FRAGMENT = 0;


    private static final String EXTRA_TASK_ID = "com.example.pascal_pc.tasklist.taskId";
    private TextView mTitleTV;
    private TextView mDescriptionTxtView;
    private TextView mDateTxtView;
    private TextView mStatusTv;
    private Button mEditBtn;
    private Button mShareBtn;
    private ImageView mTaskImgView;
    private Task mTask;
    private File mPhotoFile;
    private Long taskId;


    public static TaskDetailFragment newInstance(Long taskID) {

        Bundle args = new Bundle();

        args.putLong(EXTRA_TASK_ID, taskID);
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
        mPhotoFile = TaskList.getInstance().getPhotoFile(getActivity(),mTask);
    }

    public void getInfo() {
        taskId = getArguments().getLong(EXTRA_TASK_ID);
        mTask = TaskList.getInstance().getTask(taskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_detail, container, false);

        setDetail(view);
        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTaskDetailFragment fragment = EditTaskDetailFragment.newInstance(taskId);
                fragment.setTargetFragment(getTargetFragment(), REQ_EDIT_FRAGMENT);
                fragment.show(getFragmentManager(), DIALOG_EDIT_FRAGMENT_TAG);
                dismiss();

            }
        });
        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reportIntent = new Intent(Intent.ACTION_SEND);
                reportIntent.setType("text/plain");
                reportIntent.putExtra(Intent.EXTRA_TEXT, getTaskReport());
                reportIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.task_report_subject));
                startActivity(Intent.createChooser(reportIntent, getString(R.string.send_report)));
            }
        });
        return view;
    }
    private void setDetail(View view) {
        mTitleTV=view.findViewById(R.id.title_tv);
        mDescriptionTxtView = view.findViewById(R.id.description_tv);
        mDateTxtView = view.findViewById(R.id.date_tv);
        mEditBtn = view.findViewById(R.id.Edit_btn);
        mStatusTv=view.findViewById(R.id.status_tv);
        mShareBtn=view.findViewById(R.id.share_btn);
        mTaskImgView=view.findViewById(R.id.task_img);

        updatePhotoView();
        mTitleTV.setText(mTask.getMTitle());
        mDescriptionTxtView.setText(mTask.getMDescription());
        String done=(mTask.getMDone()==true?"done":"not done");
        mStatusTv.setText("Task is "+done );
        mDateTxtView.setText("Date:"+new SimpleDateFormat("EEE-d MMM-yyyy ").format(mTask.getMDate())
                +" Time : " +new SimpleDateFormat("kk:mm").format(mTask.getMDate()));
    }

    @SuppressLint("ResourceType")
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            //set image view from drawable
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(),
                    getActivity());

            mTaskImgView.setImageBitmap(bitmap);
        }
    }
    private String getTaskReport() {
        String dateString = new SimpleDateFormat("yyyy/MM/dd"+"kk:mm").format(mTask.getMDate());

        String solvedString = mTask.getMDone() ?
                "It's done" :
                "It's not done";
        String description = mTask.getMDescription()== null ?
                "No description" :
                "And description is:" +mTask.getMDescription();

        String report = getString(R.string.task_report, mTask.getMTitle(),
                dateString,description, solvedString);
        return report;
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
//        setDetail(getView());
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}
