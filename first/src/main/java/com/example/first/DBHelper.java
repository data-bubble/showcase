package com.example.first;

import android.content.Context;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import androidx.annotation.Nullable;

 class DBHelper extends SQLiteOpenHelper {
     static final int DATABASE_VERSION=1;
     static final String DATABASE_NAME="ResidentialDB";
     static final String PASS="!#Base";

     static final String RS_TABLE="objects";

     static  final String DEV_TABLE="developers";
     static  final String DEVELOPER_TITLE="title";
     static final String KEY_ID="_id";
     static final String RESIDENTIAL_TITLE   ="title";
     static final String MIN_PRICE="min_price";
     static final String MAX_PRICE="max_price";
     static final String IMG_PATH="img_path";

     static final String METRO="metro";
     static final String MKAD="mkad_time";
     static final String GK="gk";
     static final String METRO_TIME="metro_time";
     static final String DISTRICT="district";
     static final String DESCRIPTION="description";
     static final String YEAR_MIN="year_min";
     static final String YEAR_MAX="year_max";
     static final String MAP_LAT="map_lat";
     static final String MAP_LNG="map_lng";
     static final String MIN_CC="min_cc";
     static final String MAX_CC="max_cc";


     DBHelper(@Nullable Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+RS_TABLE+"("+KEY_ID+" integer primary key,"+RESIDENTIAL_TITLE+" text not null unique,"+MIN_PRICE+" integer,"+MAX_PRICE+" integer,"+IMG_PATH+" text, "+
        METRO+" text,"+MKAD+" integer,"+GK+" text,"+METRO_TIME+" integer,"+DISTRICT+" text,"+DESCRIPTION+" text,"+YEAR_MIN+" integer,"+YEAR_MAX+" integer,"+MAP_LAT+" real,"+MAP_LNG+" real,"
                +MIN_CC+" real,"+MAX_CC+" real"+")");
        Log.d("DATATATAT","create database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+RS_TABLE );
        onCreate(db);
    }
}
