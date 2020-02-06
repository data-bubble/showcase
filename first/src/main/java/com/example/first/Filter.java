package com.example.first;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SqliteWrapper;

import android.util.Log;

import com.example.first.pojo.Residential;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

 class Filter{

     private MainActivity context;
     private DBHelper dbHelper;




     Filter(Context context){
         this.context=(MainActivity)context;
         dbHelper=((MainActivity) context).getDbHelper();
         SQLiteDatabase db=dbHelper.getReadableDatabase(DBHelper.PASS);
     }
//     static List<Residential> getFiltering(List<Residential> list,Number min ,Number max){       //фильтрация из листа
//        System.out.println(list.size());
//        List<Residential> residentials=new ArrayList<>();
//
//
//            for(int i=0;i<list.size();i++){
//                Double minD=list.get(i).getMinPrice()/1000000.0;
//                Double maxD=list.get(i).getMaxPrice()/1000000.0;
//                if(min.doubleValue()<=maxD&&max.doubleValue()>=minD)
//
//                    residentials.add(list.get(i));
//            }
//        return residentials;
//    }

     List<Residential> getSelectedObject(int checkedId, String spinnerSelectedItem, Number minPrice, Number maxPrice, Number minDistOfMetro, Number minYear, Number maxYear, Number distMkad){
         SQLiteDatabase db=dbHelper.getReadableDatabase(DBHelper.PASS);
        Double minDPrice=minPrice.doubleValue()*1000000.0;
        Double maxDPrice=maxPrice.doubleValue()*1000000.0;
        Cursor cursor=null;
        String requestYear=DBHelper.YEAR_MIN+"<="+maxYear.intValue()+" and "+DBHelper.YEAR_MAX+">="+minYear.intValue();
        String requestFilter=DBHelper.MIN_PRICE+"<="+maxDPrice+" and "+DBHelper.MAX_PRICE+">="+minDPrice+" and "+DBHelper.METRO_TIME+"<="+minDistOfMetro.intValue()+" and "+requestYear;
         switch (checkedId){
             case R.id.district_button:

                 cursor=db.query(dbHelper.RS_TABLE,null,DBHelper.DISTRICT+"=\'"+spinnerSelectedItem+"\' and "+requestFilter,null,null,null,DBHelper.MIN_PRICE);
                 break;
             case R.id.metro_button:

                 cursor=db.query(dbHelper.RS_TABLE,null,DBHelper.METRO+"= \'"+spinnerSelectedItem+"\' and "+ requestFilter,null,null,null,DBHelper.MIN_PRICE);
                 break;
             case R.id.all_button:
                 cursor=db.query(dbHelper.RS_TABLE,null,requestFilter,null,null,null,dbHelper.MIN_PRICE);
         }


         return getAllObject(cursor);

     }

    List<String> getDataOfColumn(String column) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(DBHelper.PASS);
        List<String> result=new LinkedList<>();
       // String[] arr = new String[]{dbHelper.METRO};
        Cursor cursor = db.query(dbHelper.RS_TABLE, null, column+ " NOT NULL", null, column, null, null);

        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(column);
            do {
              //  System.out.println("-------------------------"+column);
                result.add(cursor.getString(index));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }
    List<Residential> getSelectedObjects(int checkedId,String spinnerSelectedItem){
         SQLiteDatabase db=dbHelper.getWritableDatabase(DBHelper.PASS);
         Cursor cursor=null;
//         String selection=null;
//         String[] args=new String[]{spinnerSelectedItem};

         switch (checkedId){
        case R.id.district_button:

                  cursor=db.query(dbHelper.RS_TABLE,null,DBHelper.DISTRICT+"=\'"+spinnerSelectedItem+"\'",null,null,null,DBHelper.MIN_PRICE);
                  break;
             case R.id.metro_button:

                     cursor=db.query(dbHelper.RS_TABLE,null,DBHelper.METRO+"= \'"+spinnerSelectedItem+"\'",null,null,null,DBHelper.MIN_PRICE);
                     break;
             case R.id.all_button:
                     cursor=db.query(dbHelper.RS_TABLE,null,null,null,null,null,dbHelper.MIN_PRICE);
                        }


         return getAllObject(cursor);
    }
    List<Residential> getSelectedObject(Cursor cursor){
        return null;
    }
    List<Residential> getAllObjects(){
        SQLiteDatabase db=dbHelper.getWritableDatabase(DBHelper.PASS);
        Cursor cursor=db.query(dbHelper.RS_TABLE,null,null,null,null,null,dbHelper.MIN_PRICE);

//        if(cursor.moveToFirst()){
//            int idIndex=cursor.getColumnIndex(dbHelper.KEY_ID);
//            int titleIndex=cursor.getColumnIndex(dbHelper.RESIDENTIAL_TITLE);
//            int minPriceIndex=cursor.getColumnIndex(dbHelper.MIN_PRICE);
//            int maxPriceIndex=cursor.getColumnIndex(dbHelper.MAX_PRICE);
//            int imgPathIndex=cursor.getColumnIndex(dbHelper.IMG_PATH);
//            int metroIndex=cursor.getColumnIndex(dbHelper.METRO);
//            int mkadTimeIndex=cursor.getColumnIndex(dbHelper.MKAD);
//            int gkIndex=cursor.getColumnIndex(dbHelper.GK);
//            int metroTimeIndex=cursor.getColumnIndex(dbHelper.METRO_TIME);
//            int districtIndex=cursor.getColumnIndex(dbHelper.DISTRICT);
//            int descriptionIndex=cursor.getColumnIndex(dbHelper.DESCRIPTION);
//            int indexMinYear=cursor.getColumnIndex(dbHelper.YEAR_MIN);
//            int indexMaxYear=cursor.getColumnIndex(dbHelper.YEAR_MAX);
//
//
//            List<Residential> list;
//            list=context.getList();
//                    list.clear();
//            do{
//                Log.d("mlog","ID="+cursor.getInt(idIndex)+", Title="+cursor.getString(titleIndex)+", minP="+cursor.getInt(minPriceIndex)+
//                        ", maxP="+cursor.getInt(maxPriceIndex)+", imgSrc="+cursor.getString(imgPathIndex));
//              //  AssetManager assetManager=getAssets();
//
//                Residential temp=new Residential(cursor.getInt(idIndex),
//                        cursor.getString(titleIndex),
//                        cursor.getInt(minPriceIndex),
//                        cursor.getInt(maxPriceIndex),
//                        cursor.getString(imgPathIndex),
//                        cursor.getString(metroIndex),
//                        cursor.getInt(mkadTimeIndex),
//                        cursor.getString(gkIndex),
//                        cursor.getInt(metroTimeIndex),
//                        cursor.getString(districtIndex),
//                        cursor.getString(descriptionIndex),
//                        cursor.getInt(indexMinYear),
//                        cursor.getInt(indexMaxYear),context);
//
//                list.add(temp);
//            }while(cursor.moveToNext());
//        }
//        else
//            Log.d("mLog","0 rows");
//        cursor.close();
        return getAllObject(cursor);
    }
    List<Residential> getAllObject(Cursor cursor){

        List<Residential> list=new ArrayList<>();
        if(cursor.moveToFirst()){
            int idIndex=cursor.getColumnIndex(dbHelper.KEY_ID);
            int titleIndex=cursor.getColumnIndex(dbHelper.RESIDENTIAL_TITLE);
            int minPriceIndex=cursor.getColumnIndex(dbHelper.MIN_PRICE);
            int maxPriceIndex=cursor.getColumnIndex(dbHelper.MAX_PRICE);
            int imgPathIndex=cursor.getColumnIndex(dbHelper.IMG_PATH);
            int metroIndex=cursor.getColumnIndex(dbHelper.METRO);
            int mkadTimeIndex=cursor.getColumnIndex(dbHelper.MKAD);
            int gkIndex=cursor.getColumnIndex(dbHelper.GK);
            int metroTimeIndex=cursor.getColumnIndex(dbHelper.METRO_TIME);
            int districtIndex=cursor.getColumnIndex(dbHelper.DISTRICT);
            int descriptionIndex=cursor.getColumnIndex(dbHelper.DESCRIPTION);
            int indexMinYear=cursor.getColumnIndex(dbHelper.YEAR_MIN);
            int indexMaxYear=cursor.getColumnIndex(dbHelper.YEAR_MAX);
            int indexMapLat=cursor.getColumnIndex(dbHelper.MAP_LAT);
            int indexMapLng=cursor.getColumnIndex(dbHelper.MAP_LNG);
            int indexMinCC=cursor.getColumnIndex(dbHelper.MIN_CC);
            int indexMaxCC=cursor.getColumnIndex(dbHelper.MAX_CC);




           // list.clear();
            do{
                Log.d("mlog","ID="+cursor.getInt(idIndex)+", Title="+cursor.getString(titleIndex)+", minP="+cursor.getInt(minPriceIndex)+
                        ", maxP="+cursor.getInt(maxPriceIndex)+", imgSrc="+cursor.getString(imgPathIndex));
                //  AssetManager assetManager=getAssets();

                Residential temp=new Residential(cursor.getInt(idIndex),
                        cursor.getString(titleIndex),
                        cursor.getInt(minPriceIndex),
                        cursor.getInt(maxPriceIndex),
                        cursor.getString(imgPathIndex),
                        cursor.getString(metroIndex),
                        cursor.getInt(mkadTimeIndex),
                        cursor.getString(gkIndex),
                        cursor.getInt(metroTimeIndex),
                        cursor.getString(districtIndex),
                        cursor.getString(descriptionIndex),
                        cursor.getInt(indexMinYear),
                        cursor.getInt(indexMaxYear),
                        cursor.getDouble(indexMapLat),
                        cursor.getDouble(indexMapLng),
                        cursor.getDouble(indexMinCC),
                        cursor.getDouble(indexMaxCC),
                        context);

                list.add(temp);
            }while(cursor.moveToNext());
        }
        else
            Log.d("mLog","0 rows");
        cursor.close();
        return list;
    }
    String getPhoneByDevelopers(String developer){
         SQLiteDatabase db=dbHelper.getReadableDatabase(dbHelper.PASS);
         Cursor cursor=db.query(dbHelper.DEV_TABLE,null,"title="+developer,null,null,null,null);
         if(cursor.moveToFirst()){
             int indexDeveloper=cursor.getColumnIndex(dbHelper.DEVELOPER_TITLE);

             return cursor.getString(indexDeveloper);
         }
         else
             cursor.close();
         return "fuck";
    }



}
