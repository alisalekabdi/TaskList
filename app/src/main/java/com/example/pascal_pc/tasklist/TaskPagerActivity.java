package com.example.pascal_pc.tasklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class TaskPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTaskTabLayout;
    private String[] mTitle=new String[]{"All","DONE","UNDONE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);

        mViewPager=findViewById(R.id.task_view_pager);
        mTaskTabLayout=findViewById(R.id.tablayout_task_pager);

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i){
                    case 0:
                    return AllTaskFragment.newInstance(0);

                    case 1:
                        return DoneTaskFragmen.newInstance(1);

                    case 2:
                        return UnDoneTaskFragmen.newInstance(2);
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
}
