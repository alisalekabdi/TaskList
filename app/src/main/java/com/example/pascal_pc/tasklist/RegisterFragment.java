package com.example.pascal_pc.tasklist;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
    private User mUser=new User();
    private int mReqCode;
    private String mError="";

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

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = mUserNameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String confirmPassword = mConfirmPasswordEditText.getText().toString();


                if (userName.equals("") || password.equals("") || confirmPassword.equals("")) {
                    mError = "You should fill blank !!!";
                    showSnackBar();
                    return;
                } else if (!password.equals(confirmPassword)) {
                    mError = "Password is incorrect !!";
                    showSnackBar();
                    return;
                } else if (UserList.getInstance().getUser(userName) != null) {
                    mError = userName + " Already has exist :|";
                    showSnackBar();
                    return;
                } else {
                    mUser.setMUserName(userName);
                    mUser.setMPassword(password);
                    UserList.getInstance().addUser(mUser);
                    if (mReqCode == 0) {
                        Intent intent = TaskPagerActivity.newIntent(getActivity(), mUser.getMUserName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        User user=new User();
                        user.setMUserName("guest");
                        user.setMPassword("guest");
                        UserList.getInstance().updateUser(mUser,user);
                        getActivity().finish();
                    }
                }


            }
        });
        return view;
    }

    private void showSnackBar() {
        final Snackbar snackbar = Snackbar.make(getView(), mError, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }
}

