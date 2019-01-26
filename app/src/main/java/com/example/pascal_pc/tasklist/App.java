package com.example.pascal_pc.tasklist;

import android.app.Application;

import com.example.pascal_pc.tasklist.models.DaoMaster;
import com.example.pascal_pc.tasklist.models.DaoSession;
import com.example.pascal_pc.tasklist.utils.MyDevOpenHelper;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private static App app;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        MyDevOpenHelper myDevOpenHelper = new MyDevOpenHelper(this,"task.db");
        Database db = myDevOpenHelper.getWritableDb();

        daoSession =new DaoMaster(db).newSession();

        app=this;
    }

    public static App getApp() {
        return app;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
