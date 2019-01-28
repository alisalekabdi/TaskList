package com.example.pascal_pc.tasklist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pascal_pc.tasklist.models.TaskList;
import com.example.pascal_pc.tasklist.models.User;
import com.example.pascal_pc.tasklist.models.UserList;

public class LogInActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassword;
    private Button mlogin;
    private Button mSignup;
    private Button mGuset;
    private User mUser = new User();
    private String GUEST = "guest";

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


        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUserName.getText().toString();
                String password = mPassword.getText().toString();

                if (userName == null || password == null) {
                    Toast.makeText(LogInActivity.this, "Fill blank", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        mUser = UserList.getInstance().getUserLogin(userName, password);
                        Intent intent = TaskPagerActivity.newIntent(LogInActivity.this, mUser.getMUserName());
                        startActivity(intent);
                        LogInActivity.this.finish();
                    } catch (Exception i) {
                        Toast.makeText(LogInActivity.this, "Invalid password or User Name !!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start register fragment and record data
                Intent intent = ResgisterActivity.newIntent(LogInActivity.this, 0);
                startActivity(intent);
                LogInActivity.this.finish();
            }
        });
        mGuset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                start Task pager activity
                try {

                    if (UserList.getInstance().getUser(GUEST)!= null) {
                        TaskList.getInstance().deleteAllTasks(GUEST);
                        mUser=UserList.getInstance().getUser(GUEST);
                        Intent intent = TaskPagerActivity.newIntent(LogInActivity.this, mUser.getMUserName());
                        startActivity(intent);
                    } else {
                        mUser.setMUserName(GUEST);
                        mUser.setMPassword(GUEST);
                        UserList.getInstance().addUser(mUser);
                        Intent intent = TaskPagerActivity.newIntent(LogInActivity.this, mUser.getMUserName());
                        startActivity(intent);
                    }
                } finally {
                    LogInActivity.this.finish();
                }
            }
        });
    }
}
