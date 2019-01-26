package com.example.pascal_pc.tasklist.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity
public class Task {
    @Id(autoincrement = true)
    private Long mId;
    private Long mUserId;

    @ToOne(joinProperty = "mUserId")
    private User user;

    private String mTitle;

    private Date mDate;
    private String mDescription;
    private boolean mDone;
    private String mPhotoAddress;

    public Task(Long mUserId) {
        mDate=new Date();
        mDone=false;
        this.mUserId = mUserId;
    }
    public String getPhotoName() {
        return "IMG_" + mId.toString() + ".jpg";
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1469429066)
    private transient TaskDao myDao;

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    @Generated(hash = 2083718750)
    public Task(Long mId, Long mUserId, String mTitle, Date mDate, String mDescription,
            boolean mDone, String mPhotoAddress) {
        this.mId = mId;
        this.mUserId = mUserId;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mDescription = mDescription;
        this.mDone = mDone;
        this.mPhotoAddress = mPhotoAddress;
    }
    @Generated(hash = 733837707)
    public Task() {
    }
    public Long getMId() {
        return this.mId;
    }
    public void setMId(Long mId) {
        this.mId = mId;
    }
    public String getMTitle() {
        return this.mTitle;
    }
    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public Date getMDate() {
        return this.mDate;
    }
    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }
    public String getMDescription() {
        return this.mDescription;
    }
    public void setMDescription(String mDescription) {
        this.mDescription = mDescription;
    }
    public boolean getMDone() {
        return this.mDone;
    }
    public void setMDone(boolean mDone) {
        this.mDone = mDone;
    }
    public Long getMUserId() {
        return this.mUserId;
    }
    public void setMUserId(Long mUserId) {
        this.mUserId = mUserId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 858028002)
    public User getUser() {
        Long __key = this.mUserId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2004017736)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            mUserId = user == null ? null : user.getMUserId();
            user__resolvedKey = mUserId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    public String getMPhotoAddress() {
        return this.mPhotoAddress;
    }
    public void setMPhotoAddress(String mPhotoAddress) {
        this.mPhotoAddress = mPhotoAddress;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1442741304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaskDao() : null;
    }


}
