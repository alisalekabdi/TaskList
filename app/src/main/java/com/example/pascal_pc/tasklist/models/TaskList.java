package com.example.pascal_pc.tasklist.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class TaskList {

    private static TaskList instance;
    private LinkedHashMap<UUID, Task> mTasks;

    private TaskList(){
        mTasks=new LinkedHashMap<>();
    }

    public void addCrime(Task task){
        mTasks.put(task.getId(),task);
    }
    public void removeCrime(Task task){
        mTasks.remove(task.getId());
    }

    public static TaskList getInstance() {
        if (instance == null)
            instance = new TaskList();

        return instance;
    }
    public List<Task> getCrimes() {
        return new ArrayList<>(mTasks.values());
    }

}
