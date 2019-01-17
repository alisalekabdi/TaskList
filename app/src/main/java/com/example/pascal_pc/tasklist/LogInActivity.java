package com.example.pascal_pc.tasklist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pascal_pc.tasklist.models.User;
import com.example.pascal_pc.tasklist.models.UserList;

public class LogInActivity extends AppCompatActivity {

    public static final String NULL_USER_ID = "null";
    private EditText mUserName;
    private EditText mPassword;
    private Button mlogin;
    private Button mSignup;
    private Button mGuset;
    private User mUser = new User();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        mUserName = findViewById(R.id.username_edite_text);
        mPassword = findViewById(R.id.password_edite_text);
        mlogin = findViewById(R.id.login_btn);
        mSignup = findViewById(R.id.signup_btn);
        mGuset = findViewById(R.id.geust_btn);

        mUserName.addTextChangedListener(new TextWatcher() {
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
        mPassword.addTextChangedListener(new TextWatcher() {
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

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser.getUserName()!=null&&mUser.getPassword()!=null) {
                    String userId = UserList.getInstance(LogInActivity.this).getUserId(mUser);
                    if (userId != null) {
                        Intent intent = TaskPagerActivity.newIntent(LogInActivity.this,userId);
                        startActivity(intent);
                        LogInActivity.this.finish();
                    } else {
                        Toast.makeText(LogInActivity.this, "Invalid password or User Name !!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LogInActivity.this, "Fill blank", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start register fragment and record data
                Intent intent = ResgisterActivity.newIntent(LogInActivity.this,0);
                startActivity(intent);
            }
        });
        mGuset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                start Task pager activity
                Intent intent=TaskPagerActivity.newIntent(LogInActivity.this,NULL_USER_ID);
                startActivity(intent);
                LogInActivity.this.finish();

            }
        });
    }
}
