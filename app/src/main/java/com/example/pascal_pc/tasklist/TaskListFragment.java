package com.example.pascal_pc.tasklist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class TaskListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TaskListFragment.TaskAdapter mTaskAdapter;
    protected FloatingActionButton mAddFab;
//    public static TaskListFragment newInstance() {
//
//        Bundle args = new Bundle();
//
//        TaskListFragment fragment = new TaskListFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public TaskListFragment(){}

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mRecyclerView = view.findViewById(R.id.task_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddFab=view.findViewById(R.id.add_fab);
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        List<Task> tasks = getTasks();
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.notifyDataSetChanged();
        }
    }

    public abstract List<Task> getTasks() ;

    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mFirstLetterOfTitle;
        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            mFirstLetterOfTitle = itemView.findViewById(R.id.first_letter_title_txtview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // start activity detail
                }
            });
        }

        public void bind(Task task) {
            mTask = task;
            mTitleTextView.setText(mTask.getTitle());
            mFirstLetterOfTitle.setText(mTask.getTitle().charAt(0));
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.fragment_task_title,viewGroup,false);
            TaskHolder taskHolder=new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder taskHolder, int i) {
            Task task = mTasks.get(i);
            taskHolder.bind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}