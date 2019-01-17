package com.example.pascal_pc.tasklist.datdbase;

public class TaskDbSchema {
    public static final String NAME = "task.db";
    public static final int VERSION = 1;

    public static final class TaskTable{
        public static final String NAME = "task";

        public static final class Col {

            public static final String UUID = "taskId";
            public static final String TITLE ="title";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String DONE = "done";
            public static final String USERID="user_id";

        }
    }
    public static final class UserTable {
        public static final String NAME="user";

        public static final class Col {

            public static final String USER = "user";
            public static final String PASSWORD = "password";
            public static final String USER_ID = "user_id";
        }
    }
}

