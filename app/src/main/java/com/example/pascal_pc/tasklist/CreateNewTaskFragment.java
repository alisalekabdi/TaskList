package com.example.pascal_pc.tasklist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewTaskFragment extends Fragment {

    private Button mCreateBtn;
    private EditText mTitleEditeTxt;
    private EditText mDescriptionEditeTxt;

    private Task mTask;

    public static CreateNewTaskFragment newInstance() {

        Bundle args = new Bundle();

        CreateNewTaskFragment fragment = new CreateNewTaskFragment();
        fragment.setArguments(args);
        return fragment;
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
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTask.getTitle()!=null){
                    TaskList.getInstance().addAllTask(mTask);
                    TaskList.getInstance().addUnDoneTask(mTask);
                    getActivity().finish();
                }else {
                    Toast.makeText(getActivity(), "You should fill title", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


}
