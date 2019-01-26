package com.example.pascal_pc.tasklist.models;


import android.content.Context;
import android.os.Environment;

import com.example.pascal_pc.tasklist.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.util.List;


public class TaskList {

    private static TaskList instance;
    private TaskDao mTaskDao = (App.getApp()).getDaoSession().getTaskDao();


    private TaskList() {

    }

    public void addTask(Task task) {
        mTaskDao.insert(task);
    }

    public void removeTask(Task task) {
        mTaskDao.delete(task);
    }

    public static TaskList getInstance() {
        if (instance == null)
            instance = new TaskList();

        return instance;
    }

    public List<Task> getTasks(String userName) {
        User user = UserList.getInstance().getUser(userName);
        List<Task> tasks = mTaskDao.queryBuilder()
                .where(TaskDao.Properties.MUserId.eq(user.getMUserId()))
                .list();
        return tasks;
    }

    public Task getTask(Long id) {
        return mTaskDao.load(id);
    }


    public void update(Task task) {
        mTaskDao.update(task);
    }


    public void deleteAllTasks(String userName) {
        User user = UserList.getInstance().getUser(userName);
        QueryBuilder<Task> queryBuilder = mTaskDao.queryBuilder();
        queryBuilder.where(TaskDao.Properties.MUserId.eq(user.getMUserId()));
        List<Task> tasks = queryBuilder.list();
        for (Task task : tasks) {
            mTaskDao.delete(task);
        }
    }
    public File getPhotoFile(Context context,Task task) {
        Context mContext = context.getApplicationContext();
        File filesDir = mContext.getFilesDir();
        if(task.getMPhotoAddress() != null){
            return new File(task.getMPhotoAddress());
        }else{
            return new File(filesDir, task.getPhotoName());
        }
    }
}
