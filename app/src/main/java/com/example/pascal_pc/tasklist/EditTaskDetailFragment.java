package com.example.pascal_pc.tasklist;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;
import com.example.pascal_pc.tasklist.utils.PictureUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTaskDetailFragment extends DialogFragment {
    private static final String EXTRA_TASK_ID = "com.example.pascal_pc.tasklist.taskId";
    private static final String EXTRA_USER_ID = "userId";
    private static final int REQ_DATE_PICKER = 0;
    private static final int REQ_TIME_PICKER = 1;
    private static final int REQ_GALLERY = 2;
    private static final int REQ_CAMERA = 3;
    private static final String DIALOG_DATE_TAG = "dialog_date_tag";
    private static final String DIALOG_TIME_TAG = "dialog_time_tag";


    private TextView mDescriptionTxtView, mTitleTxtView, mTimeTxtView, mDateTxtView;
    private Button mDltBtn, mDateBtn, mTimeBtn, mSaveBtn;
    private ImageButton mCameraBtn;
    private CheckBox mDoneCheckBox;
    private ImageView mTaskPhoto;
    private Task mTask;
    private File mPhotoFile;
    private Uri mImgUri;
    private long taskId;
    private String mUserId;

    public static EditTaskDetailFragment newInstance(Long taskID) {

        Bundle args = new Bundle();

        args.putSerializable(EXTRA_TASK_ID, taskID);
        EditTaskDetailFragment fragment = new EditTaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EditTaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskId = getArguments().getLong(EXTRA_TASK_ID);
        mUserId = getArguments().getString(EXTRA_USER_ID);
        mTask = TaskList.getInstance().getTask(taskId);
        mPhotoFile = TaskList.getInstance().getPhotoFile(getActivity(),mTask);
    }

    private Uri getPhotoFileUri() {
        return FileProvider.getUriForFile(getActivity(),
                "com.example.pascal_pc.tasklist.fileprovider",
                mPhotoFile);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        mTitleTxtView = view.findViewById(R.id.Title_tv_edit);
        mTaskPhoto = view.findViewById(R.id.task_img_view);
        mCameraBtn = view.findViewById(R.id.camera_btn);
        mDescriptionTxtView = view.findViewById(R.id.description_text_view);
        mDateTxtView = view.findViewById(R.id.date_text_view);
        mTimeTxtView = view.findViewById(R.id.time_text_view);
        mDoneCheckBox = view.findViewById(R.id.done_checkBox);
        mDltBtn = view.findViewById(R.id.delete_btn);
        mSaveBtn = view.findViewById(R.id.save_btn);
        mTimeBtn = view.findViewById(R.id.time_btn);
        mDateBtn = view.findViewById(R.id.Date_btn);

        mTitleTxtView.setText(mTask.getMTitle());
        mDescriptionTxtView.setText(mTask.getMDescription());
        mDateTxtView.setText(new SimpleDateFormat("EEE-d MMM-yyyy ").format(mTask.getMDate()));
        mTimeTxtView.setText(new SimpleDateFormat("kk:mm").format(mTask.getMDate()));
        mDoneCheckBox.setChecked(mTask.getMDone());
        updatePhotoView();

        mTitleTxtView.addTextChangedListener(new TextWatcher() {
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
        mDescriptionTxtView.addTextChangedListener(new TextWatcher() {
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

        mCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose way");
                builder.setPositiveButton("Take photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mImgUri = getPhotoFileUri();
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImgUri);

                        PackageManager packageManager = getActivity().getPackageManager();
                        List<ResolveInfo> activities = packageManager.queryIntentActivities(
                                intent,
                                PackageManager.MATCH_DEFAULT_ONLY);

                        for (ResolveInfo activity : activities) {
                            getActivity().grantUriPermission(
                                    activity.activityInfo.packageName,
                                    mImgUri,
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }
                        startActivityForResult(intent, REQ_CAMERA);

                    }
                });
                builder.setNeutralButton("Choose from gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_GALLERY);

                    }
                });
                builder.show();
            }
        });

        mDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getMDate());
                datePickerFragment.setTargetFragment(EditTaskDetailFragment.this,
                        REQ_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), DIALOG_DATE_TAG);
            }
        });
        mTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getMDate());
                timePickerFragment.setTargetFragment(EditTaskDetailFragment.this, REQ_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), DIALOG_TIME_TAG);
            }
        });
        mDltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setTitle("Delete this item");
                alertBuilder.setIcon(R.drawable.ic_delete);
                alertBuilder.setMessage("Are you sure to delete task!!!");
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaskList.getInstance().removeTask(mTask);
                        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
                        for (Fragment fragment : fragments) {
                            if (fragment instanceof TaskListFragment) {
                                fragment.onResume();
                            }
                        }
                        dismiss();
                    }
                });
                alertBuilder.setNegativeButton("No", null);
                AlertDialog alert = alertBuilder.create();
                alert.show();

            }

        });
        mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTask.setMDone(isChecked);
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskList.getInstance().update(mTask);
            }
        });

        return view;
    }

    @SuppressLint("ResourceType")
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            //set image view from drawable
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(),
                    getActivity());

            mTaskPhoto.setImageBitmap(bitmap);
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
            mTimeTxtView.setText(new SimpleDateFormat("kk:mm").format(date));
        } else if (requestCode == REQ_DATE_PICKER) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTask.setMDate(date);
            mDateTxtView.setText(new SimpleDateFormat("EEE-d MMM-yyyy ").format(date));
        } else if (requestCode == REQ_CAMERA) {
            mImgUri= getPhotoFileUri();
            mTask.setMPhotoAddress(mPhotoFile.toString());
            getActivity().revokeUriPermission(mImgUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        } else if (requestCode == REQ_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String picturePath = cursor.getString(columnIndex); // returns null
            if (picturePath != null) {
                mTask.setMPhotoAddress(picturePath);
                updatePhotoView();
            }
            cursor.close();

        }
    }
}