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

    private static String EXTRA_USER_ID="user_id.com.example.pascal_pc.tasklist";

    public static Intent newIntent(Context context,String userId){
        Intent intent=new Intent(context,TaskPagerActivity.class);
        intent.putExtra(EXTRA_USER_ID,userId);
        return intent;
    }

    public static final int MODE_ALL = 0;
    public static final int MODE_DONE = 1;
    public static final int MODE_UNDONE = 2;

    private static  String mUserID;

    private ViewPager mViewPager;
    private TabLayout mTaskTabLayout;
    private String[] mTitle=new String[]{"All","DONE","UNDONE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);

        mUserID=getIntent().getStringExtra(EXTRA_USER_ID);

        mViewPager=findViewById(R.id.task_view_pager);
        mTaskTabLayout=findViewById(R.id.tablayout_task_pager);


        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i){
                    case MODE_ALL:
                    return TaskListFragment.newInstance(0,mUserID);

                    case MODE_DONE:
                        return TaskListFragment.newInstance(1,mUserID);

                    case MODE_UNDONE:
                        return TaskListFragment.newInstance(2,mUserID);
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
        if (mUserID.equals("null")) {
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
                    TaskList.getInstance(TaskPagerActivity.this).deleteAllTasks(mUserID);
                    TaskPagerActivity.this.finish();
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }

    }
}
