package com.example.pascal_pc.tasklist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class TaskDetailActivity extends AppCompatActivity {

    private static final String EXTRA_TASK_ID ="com.example.pascal_pc.tasklist.task_id" ;

    public static Intent newIntent(Context context, UUID taskId){

        Intent intent=new Intent(context,TaskDetailActivity.class);
        intent.putExtra(EXTRA_TASK_ID,taskId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        UUID taskId= (UUID) getIntent().getSerializableExtra(EXTRA_TASK_ID);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container,TaskDetailFragment.newInstance(taskId))
                    .commit();
        }
    }
}
