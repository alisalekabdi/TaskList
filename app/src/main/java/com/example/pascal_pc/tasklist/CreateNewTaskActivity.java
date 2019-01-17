package com.example.pascal_pc.tasklist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class CreateNewTaskActivity extends AppCompatActivity {

    private static final String EXTRA_USER_ID ="com.example.pascal_pc.tasklist.user_id" ;

    public static Intent newIntent(Context context, String userId){
        Intent intent=new Intent(context,CreateNewTaskActivity.class);
        intent.putExtra(EXTRA_USER_ID,userId);
        return intent;
    }
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        mUserId=getIntent().getStringExtra(EXTRA_USER_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.new_task_frg_container) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.new_task_frg_container,CreateNewTaskFragment.newInstance(mUserId))
                    .commit();
        }
    }
}
