package com.example.pascal_pc.tasklist;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascal_pc.tasklist.models.TaskList;
import com.example.pascal_pc.tasklist.models.User;
import com.example.pascal_pc.tasklist.models.UserList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private static final String EXTRA_REQ_CODE = "reqCode";
    private EditText mUserNameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private TextView mRegisterBtn;

    private String mConfirmPassword;
    private int mReqCode;
    private User mUser = new User();

    public static RegisterFragment newInstance(int reqCode) {

        Bundle args = new Bundle();

        args.putInt(EXTRA_REQ_CODE, reqCode);
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReqCode = getArguments().getInt(EXTRA_REQ_CODE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_register, container, false);

        mUserNameEditText = view.findViewById(R.id.user_name_reg_editText);
        mPasswordEditText = view.findViewById(R.id.password_reg_editText);
        mRegisterBtn = view.findViewById(R.id.register_btn);
        mConfirmPasswordEditText = view.findViewById(R.id.confrim_passwprd_editText);

        mUserNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mConfirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mConfirmPassword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add user and send UUID for task table
                if (UserList.getInstance(getActivity()).checkUser(mUser.getUserName())) {
                    Toast.makeText(getActivity(), "This user already have been exist !!", Toast.LENGTH_SHORT).show();
                } else if (!mUser.getUserName().equals("") && !mUser.getPassword().equals("") && mUser.getPassword().equals(mConfirmPassword)) {
                    if (mReqCode == 0) {
                        UserList.getInstance(getActivity()).addUser(mUser);
                        Intent intent = TaskPagerActivity.newIntent(getActivity(), mUser.getUserId().toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        UserList.getInstance(getActivity()).addUser(mUser);
                        TaskList.getInstance(getActivity()).updateGuestTask(mUser.getUserId().toString());
                        getActivity().finish();
                    }
                } else if (!mUser.getPassword().equals(mConfirmPassword)) {
                    Toast.makeText(getActivity(), "password is incorrect", Toast.LENGTH_SHORT).show();
                } else {
                    final Snackbar snackbar = Snackbar.make(view, "You should fill blank", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbar.show();
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                }
            }
        });
        return view;
    }

}
