package com.example.pascal_pc.tasklist.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskList {

    private static TaskList instance;
    private List<Task> mAllTasks;
    private List<Task> mDoneTasks;
    private List<Task> mUnDoneTasks;


    private TaskList(){
        mAllTasks=new ArrayList<>();
        mDoneTasks=new ArrayList<>();
        mUnDoneTasks=new ArrayList<>();
  }

    public void addAllTask(Task task){
        mAllTasks.add(task);
    }
    public void addDoneTask(Task task){
        mDoneTasks.add(task);
    }
    public void addUnDoneTask(Task task){
        mUnDoneTasks.add(task);
    }
    public void removeDoneTask(Task task){
        mDoneTasks.remove(task);
    }
    public void removeUnDoneTask(Task task){
        mUnDoneTasks.remove(task);
    }
    public void removeTask(Task task){
        mAllTasks.remove(task);
        mUnDoneTasks.remove(task);
        mDoneTasks.remove(task);


    }

    public static TaskList getInstance() {
        if (instance == null)
            instance = new TaskList();

        return instance;
    }
    public List<Task> getAllTasks() {
        return mAllTasks;
    }
    public List<Task> getDoneTasks() {
        return mDoneTasks;
    }
    public List<Task> getUnDoneTasks() {
        return mUnDoneTasks;
    }

    public Task getTask(UUID id) {
        for(int i=0;i<mAllTasks.size();i++){
            if(mAllTasks.get(i).getId().equals(id)){
                return mAllTasks.get(i);
            }
        }
        return null;
    }

}
