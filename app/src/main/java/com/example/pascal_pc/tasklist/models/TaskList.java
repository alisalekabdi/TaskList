package com.example.pascal_pc.tasklist.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pascal_pc.tasklist.datdbase.TaskBaseHelper;
import com.example.pascal_pc.tasklist.datdbase.TaskDbSchema;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskList {

    private static TaskList instance;
    private SQLiteDatabase mDataBase;
    private Context mContext;


    private TaskList(Context context){
        mContext=context.getApplicationContext();
        mDataBase=new TaskBaseHelper(context).getWritableDatabase();
  }

    public void addTask(Task task){
        ContentValues values=getContentValues(task);
        mDataBase.insert(TaskDbSchema.TaskTable.NAME,null,values);
    }
    public void removeTask(Task task){
        String WhereClause=TaskDbSchema.TaskTable.Col.UUID +" =?";
        String[] WhereArgs=new String[]{task.getId().toString()};
        mDataBase.delete(TaskDbSchema.TaskTable.NAME,WhereClause,WhereArgs);
    }

    public static TaskList getInstance(Context context) {
        if (instance == null)
            instance = new TaskList(context);

        return instance;
    }
    public List<Task> getTasks() {
        List<Task> tasks=new ArrayList<>();
        Cursor cursor=mDataBase.query(
                TaskDbSchema.TaskTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

         try {
             if(cursor.getCount()==0){
                 return tasks;
             }
             cursor.moveToFirst();

             while (!cursor.isAfterLast()){
                 UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.UUID)));
                 String title = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.TITLE));
                 String description = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DESCRIPTION));
                 Date date = new Date(cursor.getLong(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DATE)));
                 boolean isDone = cursor.getInt(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DONE))!=0;

                 Task task=new Task();
                 task.setId(uuid);
                 task.setTitle(title);
                 task.setDescription(description);
                 task.setDate(date);
                 task.setDone(isDone);

                 tasks.add(task);
                 cursor.moveToNext();
             }
         } finally {
             cursor.close();
         }
        return tasks;
    }

    public Task getTask(UUID id) {
        String WhereClause=TaskDbSchema.TaskTable.Col.UUID +" =?";
        String[] WhereArgs=new String[]{id.toString()};

        Cursor cursor=mDataBase.query(
                TaskDbSchema.TaskTable.NAME,
                null,
                WhereClause,
                WhereArgs,
                null,
                null,
                null,
                null);

        try {
            if (cursor.getCount()==0){ return null;}

            cursor.moveToFirst();
            UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.UUID)));
            String title = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.TITLE));
            String description = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DESCRIPTION));
            Date date = new Date(cursor.getLong(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DATE)));
            boolean isDone = cursor.getInt(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DONE))!=0;

            Task task=new Task();
            task.setId(uuid);
            task.setTitle(title);
            task.setDescription(description);
            task.setDate(date);
            task.setDone(isDone);

            return task;
        }finally {
            cursor.close();
        }
    }
    public void update(Task task){
        ContentValues values=getContentValues(task);
        String WhereClause=TaskDbSchema.TaskTable.Col.UUID +" =?";
        String[] WhereArgs=new String[]{task.getId().toString()};
        mDataBase.update(TaskDbSchema.TaskTable.NAME,values,WhereClause,WhereArgs);
    }
    public ContentValues getContentValues(Task task){
        ContentValues values =new ContentValues();
        values.put(TaskDbSchema.TaskTable.Col.USERID,"add user id");
        values.put(TaskDbSchema.TaskTable.Col.UUID,task.getId().toString());
        values.put(TaskDbSchema.TaskTable.Col.TITLE,task.getTitle());
        values.put(TaskDbSchema.TaskTable.Col.DESCRIPTION,task.getDescription());
        values.put(TaskDbSchema.TaskTable.Col.DATE,task.getDate().getTime());
        values.put(TaskDbSchema.TaskTable.Col.DONE,task.isDone()? 1 : 0);
        return values;
    }
    public void deleteAllTasks(){

        mDataBase.delete(TaskDbSchema.TaskTable.NAME,null,null);
    }

}
