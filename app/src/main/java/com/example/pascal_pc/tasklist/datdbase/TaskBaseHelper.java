package com.example.pascal_pc.tasklist.datdbase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskBaseHelper extends SQLiteOpenHelper {
    public TaskBaseHelper(Context context) {
        super(context,TaskDbSchema.NAME, null, TaskDbSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TaskDbSchema.TaskTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TaskDbSchema.TaskTable.Col.USERID +", " +
                TaskDbSchema.TaskTable.Col.UUID +", " +
                TaskDbSchema.TaskTable.Col.TITLE +", " +
                TaskDbSchema.TaskTable.Col.DESCRIPTION +", " +
                TaskDbSchema.TaskTable.Col.DATE +", " +
                TaskDbSchema.TaskTable.Col.DONE +
                ")"
        );
        db.execSQL("create table " + TaskDbSchema.UserTable.NAME + "(" +
                "_id integer primary key autoincrement, "+
                TaskDbSchema.UserTable.Col.USER +", " +
                TaskDbSchema.UserTable.Col.PASSWORD +", " +
                TaskDbSchema.UserTable.Col.USER_ID +
                ")"

        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
