package com.example.pascal_pc.tasklist.models;


import com.example.pascal_pc.tasklist.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class UserList {
    private static UserList instance;
    private UserDao mUserDao = (App.getApp()).getDaoSession().getUserDao();


    private UserList() {

    }

    public static UserList getInstance() {
        if (instance == null)
            instance = new UserList();

        return instance;
    }

    public void addUser(User user) {
        mUserDao.insert(user);
    }

    public void updateUser(User user) {
        mUserDao.update(user);
    }

    public User getUserLogin(String userName, String password) {
        List<User> users = mUserDao.queryBuilder()
                .where(UserDao.Properties.MUserName.eq(userName), UserDao.Properties.MPassword.eq(password))
                .list();
        User user = (users.size() > 0 ? users.get(0) : null);
        return user;
    }

    public User getUser(String userName) {
        QueryBuilder<User> qb = mUserDao.queryBuilder();
        qb.where(UserDao.Properties.MUserName.eq(userName));
//                .list();
//        User user = (users.size() > 0 ? users.get(0) : null);
        User user = qb.unique();
        return user;
    }
}
