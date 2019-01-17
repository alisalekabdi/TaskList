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


    private TaskList(Context context) {
        mContext = context.getApplicationContext();
        mDataBase = new TaskBaseHelper(context).getWritableDatabase();
    }

    public void addTask(Task task, String userId) {
        ContentValues values = getContentValues(task, userId);
        mDataBase.insert(TaskDbSchema.TaskTable.NAME, null, values);
    }

    public void removeTask(Task task, String userId) {
        String WhereClause = TaskDbSchema.TaskTable.Col.UUID + " =? AND " + TaskDbSchema.TaskTable.Col.USERID + " =? ";
        String[] WhereArgs = new String[]{task.getId().toString(), userId};
        mDataBase.delete(TaskDbSchema.TaskTable.NAME, WhereClause, WhereArgs);
    }

    public static TaskList getInstance(Context context) {
        if (instance == null)
            instance = new TaskList(context);

        return instance;
    }

    public List<Task> getTasks(String userId) {
        List<Task> tasks = new ArrayList<>();
        String WhereClause = TaskDbSchema.TaskTable.Col.USERID + " =? ";
        String[] WhereArgs = new String[]{userId};
        Cursor cursor = mDataBase.query(
                TaskDbSchema.TaskTable.NAME,
                null,
                WhereClause,
                WhereArgs,
                null,
                null,
                null,
                null);

        try {
            if (cursor.getCount() == 0) {
                return tasks;
            }
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.UUID)));
                String title = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.TITLE));
                String description = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DESCRIPTION));
                Date date = new Date(cursor.getLong(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DATE)));
                boolean isDone = cursor.getInt(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DONE)) != 0;

                Task task = new Task();
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

    public Task getTask(UUID id, String userId) {
        String WhereClause = TaskDbSchema.TaskTable.Col.UUID + " =? AND " + TaskDbSchema.TaskTable.Col.USERID + " =? ";
        String[] WhereArgs = new String[]{id.toString(), userId};

        Cursor cursor = mDataBase.query(
                TaskDbSchema.TaskTable.NAME,
                null,
                WhereClause,
                WhereArgs,
                null,
                null,
                null,
                null);

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.UUID)));
            String title = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.TITLE));
            String description = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DESCRIPTION));
            Date date = new Date(cursor.getLong(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DATE)));
            boolean isDone = cursor.getInt(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DONE)) != 0;

            Task task = new Task();
            task.setId(uuid);
            task.setTitle(title);
            task.setDescription(description);
            task.setDate(date);
            task.setDone(isDone);

            return task;
        } finally {
            cursor.close();
        }
    }

    public void update(Task task, String userId) {
        ContentValues values = getContentValues(task, userId);
        String WhereClause = TaskDbSchema.TaskTable.Col.UUID + " =? AND " + TaskDbSchema.TaskTable.Col.USERID + " =? ";
        String[] WhereArgs = new String[]{task.getId().toString(), userId};
        mDataBase.update(TaskDbSchema.TaskTable.NAME, values, WhereClause, WhereArgs);
    }

    public ContentValues getContentValues(Task task, String userId) {
        ContentValues values = new ContentValues();
        values.put(TaskDbSchema.TaskTable.Col.USERID, userId);
        values.put(TaskDbSchema.TaskTable.Col.UUID, task.getId().toString());
        values.put(TaskDbSchema.TaskTable.Col.TITLE, task.getTitle());
        values.put(TaskDbSchema.TaskTable.Col.DESCRIPTION, task.getDescription());
        values.put(TaskDbSchema.TaskTable.Col.DATE, task.getDate().getTime());
        values.put(TaskDbSchema.TaskTable.Col.DONE, task.isDone() ? 1 : 0);
        return values;
    }

    public void deleteAllTasks(String userId) {
        String whereClause = TaskDbSchema.TaskTable.Col.USERID + " =? ";
        String[] whereArgs = new String[]{userId};
        mDataBase.delete(TaskDbSchema.TaskTable.NAME, whereClause, whereArgs);
    }

    public void updateGuestTask(String userId) {
        String WhereClause = TaskDbSchema.TaskTable.Col.UUID + " =? ";
        String[] WhereArgs = new String[]{"null"};
        Cursor cursor = mDataBase.query(
                TaskDbSchema.TaskTable.NAME,
                null,
                WhereClause,
                WhereArgs,
                null,
                null,
                null,
                null
        );
        try {
            if (cursor.getCount() == 0) {
                return;
            }

            cursor.moveToFirst();
            UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.UUID)));
            String title = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.TITLE));
            String description = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DESCRIPTION));
            Date date = new Date(cursor.getLong(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DATE)));
            boolean isDone = cursor.getInt(cursor.getColumnIndex(TaskDbSchema.TaskTable.Col.DONE)) != 0;

            Task task = new Task();
            task.setId(uuid);
            task.setTitle(title);
            task.setDescription(description);
            task.setDate(date);
            task.setDone(isDone);

            update(task,userId);
        } finally {
            cursor.close();
        }
    }

}
