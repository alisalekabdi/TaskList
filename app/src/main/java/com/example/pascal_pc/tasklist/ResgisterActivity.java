package com.example.pascal_pc.tasklist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResgisterActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        Intent intent =new Intent(context,ResgisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);

        FragmentManager fragmentManager=getSupportFragmentManager();
        if(fragmentManager.findFragmentById(R.id.fragment_container_register)==null){
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container_register,RegisterFragment.newInstance())
                    .commit();
        }
    }
}
