package com.example.pascal_pc.tasklist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pascal_pc.tasklist.models.TaskList;

public class ResgisterActivity extends AppCompatActivity {

    private static final String REQ_CODE = "reqCode";
    private int mReqCode;


    public static Intent newIntent(Context context, int reqCode) {
        Intent intent = new Intent(context, ResgisterActivity.class);
        intent.putExtra(REQ_CODE, reqCode);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);

        mReqCode = getIntent().getIntExtra(REQ_CODE, 0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragment_container_register) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container_register, RegisterFragment.newInstance(mReqCode))
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mReqCode == 1) {
            //null is id for guest user
            TaskList.getInstance(this).deleteAllTasks("null");
            this.finish();
        }
    }
}
