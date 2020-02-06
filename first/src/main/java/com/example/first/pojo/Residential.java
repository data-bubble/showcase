package com.example.first.pojo;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.first.MainActivity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Residential implements Serializable {

    private int id;
   private  String name;
   private int minPrice;
  private int maxPrice;
    private  String[] imagesPath;
  private  String imgPath;
  private String metro;
  private int mkadTime;
  private String gK;
  private int metroTime;
  private String district;
 private String description;
// AssetManager manager;
  private String mainImagepath;
  private int minYear;
  private int maxYear;
  private double map_lat;
  private double map_lng;
  private double minCC;
  private double maxCC;
 transient   private Context context;



    public Residential(){}

//    public Residential(int id, String name, int minPrice, int maxPrice,String imgPath,Context context){
//        this.id=id;
//        this.name=name;
//        this.minPrice=minPrice;
//        this.maxPrice=maxPrice;
//        if(imgPath!=null)
//        this.imgPath=imgPath;
//      //  this.manager=context.getAssets();
//        this.context=context;
//        getImages();
//    }
//
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMinYear() {
        return minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    private void getImages() {
       //вспомогательный метод
//        AssetManager manager=context.getAssets();
//        if(imgPath!=null){
//       imagesPath = manager.list(imgPath);
//     mainImagepath=imgPath+imagesPath[0];                               //путь аассет для первой картинки из дерриктории
//       Log.d("PATHHHHH",mainImagepath);}
            AssetManager manager = context.getAssets();

            String imsPath ="id" + id ;
            Set<String> paths = new HashSet<>();

            String[] images=null;
            try {
                images = manager.list(imsPath);

                if (images != null) {
                    for (String s : images) {
                        File file = new File(imsPath +"/"+ s);
                        if (!file.isDirectory()) {
                            paths.add(file.toString());
                            mainImagepath = (mainImagepath == null) ? file.toString() : mainImagepath;

                        }
                    }

                }
                imagesPath = paths.toArray(images);


            } catch (IOException e) { }                                //добавить обработку исключения в случае исключения!!!!!!!!!

    }

    public String getMainImagePath() {
        return mainImagepath;
    }

    public String getMetro() {
        return metro;
    }

    public int getMkadTime() {
        return mkadTime;
    }

    public String getgK() {
        return gK;
    }

    public int getMetroTime() {
        return metroTime;
    }

    public String getDescription() {
        return description;
    }

    public double getMap_lat() {
        return map_lat;
    }

    public double getMap_lng() {
        return map_lng;
    }

    public double getMinCC() {
        return minCC;
    }

    public double getMaxCC() {
        return maxCC;
    }

    public Residential(int id, String name, int minPrice, int maxPrice,
                       @Nullable String imgPath, @Nullable String metro, int mkadTime, String gK,
                       int metroTime, String district, @Nullable String description, int minYear,
                       int maxYear, double map_lat, double map_lng, double minCC, double maxCC, Context context) {

        this.id=id;
        this.name=name;
        this.minPrice=minPrice;
        this.maxPrice=maxPrice;
        this.imgPath=imgPath;
   //     manager=context.getAssets();
        this.context=context;
       getImages();
        this.metro=metro;
        this.mkadTime=mkadTime;
        this.gK=gK;
        this.metroTime=metroTime;
        this.district=district;
        this.description=description;
        this.minYear=minYear;
        this.maxYear=maxYear;
        this.map_lat=map_lat;
        this.map_lng=map_lng;
        this.minCC=minCC;
        this.maxCC=maxCC;

    }

    public String[] getImagesPath() {
        return imagesPath;
    }
}
