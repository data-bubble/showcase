package com.example.first.SecondSheet;

import android.content.Context;
import android.content.res.Configuration;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class DBHelperSecond extends SQLiteOpenHelper {

    static final int DATABASE_VERSION=1;
    static final String DATABASE_NAME="ResidentialDB";
    static final String KEY_ID="_id";
    static final String PASS="!#Base";
    static final String PHONE="phone";

    static  final String DEV_TABLE="developers";
    static  final String DEVELOPER_TITLE="title";

    DBHelperSecond(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ DEV_TABLE+"(integer"+KEY_ID+" not null primary key autoincrement,"+"text "+DEVELOPER_TITLE+" not null unique,"+"text "+PHONE+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+DEV_TABLE);
       onCreate(db);
    }
}
