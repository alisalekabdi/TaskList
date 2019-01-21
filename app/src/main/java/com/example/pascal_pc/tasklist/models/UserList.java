package com.example.pascal_pc.tasklist.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pascal_pc.tasklist.datdbase.TaskBaseHelper;
import com.example.pascal_pc.tasklist.datdbase.TaskDbSchema;

public class UserList {
    private static UserList instance;
    private SQLiteDatabase mDataBase;
    private Context mContext;

    private UserList(Context context) {
        mContext = context.getApplicationContext();
        mDataBase = new TaskBaseHelper(context).getWritableDatabase();
    }

    public static UserList getInstance(Context context) {
        if (instance == null)
            instance = new UserList(context);

        return instance;
    }

    public void addUser(User user) {
        ContentValues values = getContentValues(user);
        mDataBase.insert(TaskDbSchema.UserTable.NAME, null, values);
    }

    public String getUserId(User user) {

        String WhereClause = TaskDbSchema.UserTable.Col.USER + " =? ";
        String[] WhereArgs = new String[]{user.getUserName()};
        String userId = null;

        Cursor cursor = mDataBase.query(
                TaskDbSchema.UserTable.NAME,
                null,
                WhereClause,
                WhereArgs,
                null,
                null,
                null,
                null);

        try {
            if (cursor.getCount() == 0) {
                return userId;
            }
            cursor.moveToFirst();

            if (cursor.getString(cursor.getColumnIndexOrThrow(TaskDbSchema.UserTable.Col.PASSWORD)).equals(user.getPassword())) {
                userId = cursor.getString(cursor.getColumnIndex(TaskDbSchema.UserTable.Col.USER_ID));
            }
        } finally {
            cursor.close();
        }
        return userId;
    }

    public ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(TaskDbSchema.UserTable.Col.USER, user.getUserName());
        values.put(TaskDbSchema.UserTable.Col.PASSWORD, user.getPassword());
        values.put(TaskDbSchema.UserTable.Col.USER_ID, user.getUserId().toString());
        return values;
    }
    public boolean checkUser(String userName){
        String WhereClause = TaskDbSchema.UserTable.Col.USER + " =? ";
        String[] WhereArgs = new String[]{userName};

        Cursor cursor = mDataBase.query(
                TaskDbSchema.UserTable.NAME,
                null,
                WhereClause,
                WhereArgs,
                null,
                null,
                null,
                null);

        try {
            if (cursor.getCount() == 0) {
                return false;
            }
            cursor.moveToFirst();

            if (cursor.getString(cursor.getColumnIndexOrThrow(TaskDbSchema.UserTable.Col.USER)).equals(userName)) {
                return true;
            }
        } finally {
            cursor.close();
        }
        return false;
    }
}
