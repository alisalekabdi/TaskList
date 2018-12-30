package com.example.pascal_pc.tasklist.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class TaskList {

    private static TaskList instance;
    private LinkedHashMap<UUID, Task> mAllTasks;
    private LinkedHashMap<UUID, Task> mDoneTasks;
    private LinkedHashMap<UUID, Task> mUnDoneTasks;


    private TaskList(){
        mAllTasks=new LinkedHashMap<>();
        mDoneTasks=new LinkedHashMap<>();
        mUnDoneTasks=new LinkedHashMap<>();
    }

    public void addTask(Task task){
        mAllTasks.put(task.getId(),task);
        if(task.isDone()){
            mDoneTasks.put(task.getId(),task);
        }else{
            mUnDoneTasks.put(task.getId(),task);
        }
    }
    public void removeTask(Task task){
        mAllTasks.remove(task.getId());
        if(task.isDone()){
            mDoneTasks.remove(task.getId());
        }else{
            mUnDoneTasks.remove(task.getId());
        }
    }

    public static TaskList getInstance() {
        if (instance == null)
            instance = new TaskList();

        return instance;
    }
    public List<Task> getAllTasks() {
        return new ArrayList<>(mAllTasks.values());
    }
    public List<Task> getDoneTasks() {
        return new ArrayList<>(mDoneTasks.values());
    }
    public List<Task> getUnDoneTasks() {
        return new ArrayList<>(mUnDoneTasks.values());
    }

    public Task getTask(UUID id) {
        return mAllTasks.get(id);
    }
}
