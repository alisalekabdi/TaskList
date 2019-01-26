package com.example.pascal_pc.tasklist;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;
import com.example.pascal_pc.tasklist.models.UserList;
import com.example.pascal_pc.tasklist.utils.PictureUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewTaskFragment extends DialogFragment {

    private static final String DIALOG_DATE_TAG = "DialogDate";
    private static final String DIALOG_TIME_TAG = "DialogTime";
    private static final int REQ_DATE_PICKER = 0;
    private static final int REQ_TIME_PICKER = 1;
    private static final int REQ_PHOTOS = 2;
    private static final String EXTRA_USER_ID = "userId";

    private Button mCreateBtn;
    private Button mDateBtn;
    private Button mTimeBtn;
    private EditText mTitleEditTxt;
    private EditText mDescriptionEditTxt;
    private CheckBox mIsDoneCheckBox;
    private String mUserName;
    private Long mUserId;

    private ImageView mTaskImg;
    private ImageButton mCameraBtn;
//    boolean isSearched = false;

    private Task mTask;
    private File mPhotoFile;

    public static CreateNewTaskFragment newInstance(String userName) {

        Bundle args = new Bundle();
        args.putString(EXTRA_USER_ID, userName);

        CreateNewTaskFragment fragment = new CreateNewTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mPhotoFile=TaskList.getInstance().getPhotoFile(getActivity());
        mUserName = getArguments().getString(EXTRA_USER_ID);
        mUserId = UserList.getInstance().getUser(mUserName).getMUserId();
        mTask = new Task(mUserId);
    }

    public CreateNewTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_task, container, false);

        mTitleEditTxt = view.findViewById(R.id.title_editText);
        mDescriptionEditTxt = view.findViewById(R.id.descriptiion_editText);
        mCreateBtn = view.findViewById(R.id.create_btn);
        mDateBtn = view.findViewById(R.id.date_button_new_task);
        mTimeBtn = view.findViewById(R.id.time_button_new_task);
        mIsDoneCheckBox = view.findViewById(R.id.isDone_checkBox_newTask);
        mTaskImg = view.findViewById(R.id.task_img);
        mCameraBtn = view.findViewById(R.id.camera_btn);

        mTitleEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setMTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDescriptionEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setMDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mIsDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTask.setMDone(isChecked);
            }
        });
//        mCameraBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                Uri uri = getPhotoFileUri();
//                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//
//                PackageManager packageManager = getActivity().getPackageManager();
//                List<ResolveInfo> activities = packageManager.queryIntentActivities(
//                        captureIntent,
//                        PackageManager.MATCH_DEFAULT_ONLY);
//
//                for (ResolveInfo activity : activities) {
//                    getActivity().grantUriPermission(
//                            activity.activityInfo.packageName,
//                            uri,
//                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                }
//
//                startActivityForResult(captureIntent, REQ_PHOTOS);
//            }
//        });
        mDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getMDate());
                datePickerFragment.setTargetFragment(CreateNewTaskFragment.this,
                        REQ_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), DIALOG_DATE_TAG);
            }
        });
        mTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getMDate());
                timePickerFragment.setTargetFragment(CreateNewTaskFragment.this, REQ_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), DIALOG_TIME_TAG);
            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTitleEditTxt.getText().toString().equals("")) {
                    TaskList.getInstance().addTask(mTask);
//                    getTargetFragment().onResume();
                    List<Fragment> fragments = getFragmentManager().getFragments();
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof TaskListFragment) {
                            TaskListFragment taskListFragment = (TaskListFragment) fragment;
                            taskListFragment.onResume();
                        }
                    }
                    getDialog().dismiss();
                } else {
                    Toast.makeText(getActivity(), "You should fill title", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private Uri getPhotoFileUri() {
        return FileProvider.getUriForFile(getActivity(),
                "com.example.pascal_pc.tasklist.fileprovider",
                mPhotoFile);
    }
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mTaskImg.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(),
                    getActivity());

            mTaskImg.setImageBitmap(bitmap);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQ_TIME_PICKER) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mTask.setMDate(date);
            mTimeBtn.setText(new SimpleDateFormat("kk:mm").format(date));
        }else if (requestCode == REQ_DATE_PICKER) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTask.setMDate(date);
            mDateBtn.setText(new SimpleDateFormat("EEE-d MMM-yyyy ").format(date));
        }else if (requestCode == REQ_PHOTOS) {
//            Uri uri = getPhotoFileUri();
//            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            updatePhotoView();
        }
    }

}
