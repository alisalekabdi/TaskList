package com.example.pascal_pc.tasklist;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.pascal_pc.tasklist.models.Task;
import com.example.pascal_pc.tasklist.models.TaskList;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {

    private static final String EXTRA_POSITION = "com.example.pascal_pc.tasklist.position";
    private static final String EXTRA_USER_NAME = "user_name";

    private RecyclerView mRecyclerView;
    private ImageView mMsgImgView;
    private TaskAdapter mTaskAdapter;
    private FloatingActionButton mAddFab;
    private SearchView mSearchView;
    private int mCurrentPosition;
    private String mUserName;

    public static TaskListFragment newInstance(int position, String userName) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_POSITION, position);
        args.putString(EXTRA_USER_NAME, userName);

        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mCurrentPosition = getArguments().getInt(EXTRA_POSITION);
        mUserName = getArguments().getString(EXTRA_USER_NAME);

    }

    @SuppressLint({"RestrictedApi", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);


        mRecyclerView = view.findViewById(R.id.task_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddFab = view.findViewById(R.id.add_fab);
        mSearchView = view.findViewById(R.id.task_search_view);
        mMsgImgView = view.findViewById(R.id.img_empty_list_btn);
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                DialogFragment fragment = CreateNewTaskFragment.newInstance(mUserName);
                fragment.setTargetFragment(TaskListFragment.this, 0);
                fragment.show(fragmentManager, "tag");
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mTaskAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mTaskAdapter.filter(newText);
                return true;
            }
        });

        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.task_list_fragment, menu);
        MenuItem itemDlt = menu.findItem(R.id.delete_all_tasks);

        if (mCurrentPosition == 0) {
            itemDlt.setVisible(true);
        } else {
            itemDlt.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_tasks:
                TaskList.getInstance().deleteAllTasks(mUserName);
                updateUI();
                return true;
            case R.id.app_bar_search:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        List<Task> tasks = TaskList.getInstance().getTasks(mUserName);

        if (tasks.size() == 0) {
            mMsgImgView.setVisibility(View.VISIBLE);
        } else {
            mMsgImgView.setVisibility(View.GONE);
        }
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(tasks);
            mTaskAdapter.notifyDataSetChanged();
        }
    }

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

                    FragmentManager fragmentManager = getFragmentManager();
                    TaskDetailFragment fragment = TaskDetailFragment.newInstance(mTask.getMId());
                    fragment.setTargetFragment(TaskListFragment.this, 0);
                    fragment.show(fragmentManager, "tag");
                    mTaskAdapter.notifyDataSetChanged();
                }
            });
        }

        public void bind(Task task) {
            mTask = task;
            String mTaskTitle = mTask.getMTitle();
            mTitleTextView.setText(mTaskTitle);
            mFirstLetterOfTitle.setText(mTaskTitle.substring(0, 1).toUpperCase());
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks = new ArrayList<>();
        private List<Task> mTaskCopy;

        public void setTasks(List<Task> tasks) {
            mTaskCopy = getTasks(tasks);
            filter("");
        }

        public TaskAdapter(List<Task> tasks) {
            mTaskCopy = getTasks(tasks);
            filter("");
        }

        public void filter(String text) {
            mTasks.clear();
            if (text.isEmpty()) {
                mTasks.addAll(mTaskCopy);
            } else {
                text = text.toLowerCase();
                for (Task item : mTaskCopy) {
                    if (item.getMTitle().toLowerCase().contains(text.toLowerCase())) {
                        mTasks.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }

        private List<Task> getTasks(List<Task> tasks) {
            List<Task> taskList = new ArrayList<>();
            if (mCurrentPosition == 0) {
                taskList = tasks;
            } else if (mCurrentPosition == 1) {
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).getMDone()) {
                        taskList.add(tasks.get(i));

                    }
                }
            } else {
                for (int i = 0; i < tasks.size(); i++) {
                    if (!tasks.get(i).getMDone()) {
                        taskList.add(tasks.get(i));
                    }
                }
            }
            return taskList;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_task_title, viewGroup, false);
            TaskHolder taskHolder = new TaskHolder(view);
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