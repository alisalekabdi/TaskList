package com.example.pascal_pc.tasklist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.pascal_pc.tasklist.models.TaskList;

public class TaskPagerActivity extends AppCompatActivity {

    private static String EXTRA_USER_NAME ="user_id.com.example.pascal_pc.tasklist";

    public static Intent newIntent(Context context, String userName){
        Intent intent=new Intent(context,TaskPagerActivity.class);
        intent.putExtra(EXTRA_USER_NAME,userName);
        return intent;
    }

    public static final int MODE_ALL = 0;
    public static final int MODE_DONE = 1;
    public static final int MODE_UNDONE = 2;

    private  String mUserName;

    private ViewPager mViewPager;
    private TabLayout mTaskTabLayout;
    private String[] mTitle=new String[]{"All","DONE","UNDONE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);

        mUserName=getIntent().getStringExtra(EXTRA_USER_NAME);

        mViewPager=findViewById(R.id.task_view_pager);
        mTaskTabLayout=findViewById(R.id.tablayout_task_pager);


        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i){
                    case MODE_ALL:
                    return TaskListFragment.newInstance(0,mUserName);

                    case MODE_DONE:
                        return TaskListFragment.newInstance(1,mUserName);

                    case MODE_UNDONE:
                        return TaskListFragment.newInstance(2,mUserName);
                }
                return null;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position];
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        mTaskTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onBackPressed() {
        final int REQ_FOR_REGISTER = 1;
        final String GUEST_USER_NAME="guest";
        if (mUserName.equals(GUEST_USER_NAME)) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TaskPagerActivity.this);
            alertBuilder.setTitle("Register");
            alertBuilder.setIcon(R.drawable.ic_delete);
            alertBuilder.setMessage("Do you want to register?");
            alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = ResgisterActivity.newIntent(TaskPagerActivity.this, REQ_FOR_REGISTER);
                    startActivity(intent);
                    TaskPagerActivity.this.finish();
                }
            });
            alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TaskList.getInstance().deleteAllTasks(GUEST_USER_NAME);
                    TaskPagerActivity.this.finish();
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }else{
            this.finish();
        }

    }
}
