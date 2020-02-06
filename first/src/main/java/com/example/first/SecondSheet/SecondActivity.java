package com.example.first.SecondSheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.first.AbstractActivity;
import com.example.first.MainActivity;
import com.example.first.R;
import com.example.first.pojo.Residential;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.sqlcipher.database.SQLiteDatabase;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SecondActivity extends AbstractActivity implements OnMapReadyCallback {
    private Residential residential;
    private ViewPager viewPager;
    private MapView mapView;
    private DBHelperSecond dbHelper;
    double lat=0;
    double lng=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle bundle = getIntent().getExtras();

        dbHelper=new DBHelperSecond(this);
        residential = (Residential) bundle.get(Residential.class.getSimpleName());
        lat=residential.getMap_lat();
        lng=residential.getMap_lng();
        //   Drawable draw=getDrawableFromAssets(residential.getMainImagePath());

//        ScrollView scrollView=findViewById(R.id.scrollview_by_second);
//        for (int i=0;i<scrollView.getChildCount();i++){
//            switch(scrollView.getChildAt(i).getId()){
//                case R.id.second_name_object_h2: TextView textView=(TextView) scrollView.getChildAt(i);
//                                                textView.setText(residential.getName());
//            }
//        }
        String temp;
        int t;
        TextView textTitle=findViewById(R.id.second_name_object_h2);
        textTitle.setText(residential.getName());
        TextView textPrice=findViewById(R.id.second_price);
        if((t=residential.getMinPrice())!=0&&(t=residential.getMaxPrice())!=0)
        textPrice.setText(residential.getMinPrice()+" - "+residential.getMaxPrice());
        TextView textMinYear=findViewById(R.id.second_min_year);
        if((t=residential.getMinYear())!=0)
            textMinYear.setText("1-я очередь - "+t+" год");
        TextView textMaxYear=findViewById(R.id.second_max_year);
        if((t=residential.getMaxYear())!=0)
            textMaxYear.setText("Срок сдачи всего объекта - "+t+" год");
        TextView textGk=findViewById(R.id.second_developer);
        if((temp=residential.getgK())!=null)
            textGk.setText("Застройщик "+temp);
        TextView textMetro=findViewById(R.id.second_metro);
        if((temp=residential.getMetro())!=null)
          textMetro.setText(temp);
        TextView textMetroTime=findViewById(R.id.second_metro_time);
        if((t=residential.getMetroTime())!=0)
            textMetroTime.setText(t+" минут");
        TextView textMkadTime=findViewById(R.id.second_mkad_time);
        if((t-residential.getMkadTime())!=0)
            textMkadTime.setText(t+" минут");
        TextView textMinCC=findViewById(R.id.second_cc);
        if(residential.getMinCC()!=0&&residential.getMaxCC()!=0)
            textMinCC.setText(residential.getMinCC()+" - "+residential.getMaxCC()+" кв/м");
        TextView textDescription=findViewById(R.id.description_object);
        if((temp=residential.getDescription())!=null)
            textDescription.setText(temp);

        TextView textCC=findViewById(R.id.second_phone_developer);
        textCC.setText(getPhoneByDB(residential.getgK()));


        List<Drawable> images = getDrawableFromAssets(residential.getImagesPath());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, images);
        viewPager = findViewById(R.id.myViewPager);


        viewPager.setAdapter(viewPagerAdapter);

        TextView textView = findViewById(R.id.second_name_object_h2);
        textView.setText(residential.getName());

        mapView = findViewById(R.id.map);
        mapView.getMapAsync(this);
        mapView.requestDisallowInterceptTouchEvent(true);
        mapView.onCreate(savedInstanceState);




    }
/*    private String[] getImagesId(){
        AssetManager manager=getAssets();
        String imgsPath="id"+residential.getId()+"/";
        Set<String> paths=new HashSet<>();

        try{
            String[] images=manager.list(imgsPath);
            if(images!=null){
                for(String s:images){
                    File file=new File(imgsPath+s);
                    if(!file.isDirectory())
                        paths.add(file.toString());
                }

            }
            return paths.toArray(images);
        }
        catch (IOException e){
            e.getStackTrace();

        }
     return null;
    }*/   //для тестов метод
//private Drawable getDrawableFromAssets(String path) {                                //вытащить картринку из assets
//
//    InputStream inputStream = null;
//    try{
//        if(path!=null)
//            inputStream = getAssets().open(path);
//        else
//            inputStream=getAssets().open("default/prizm.jpg");
//        Drawable d = Drawable.createFromStream(inputStream, null);
//        return d;
//    }
//    catch (IOException e){
//        e.printStackTrace();
//
//    }
//
//    finally {
//        try{
//            if(inputStream!=null)
//                inputStream.close();
//        }
//        catch (IOException ex){
//            ex.printStackTrace();
//        }
//    }
//    return null;
//}


    public List<Drawable> getDrawableFromAssets(String[] paths) {
        List<Drawable> list = new ArrayList<>();
        for (String s : residential.getImagesPath())
            list.add(super.getDrawableFromAssets(s));
        return list;
    }

    //
//    @Override
//    public int getOrientation() {
//        return super.getOrientation();
//    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        CardView view = null;
        view = findViewById(R.id.card);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();  //получение параметров
        params.height = (int) getResources().getDimension(R.dimen.image_card);
        view.setLayoutParams(params);
        CardView mapCardView = findViewById(R.id.card_map);
        params = (LinearLayout.LayoutParams) mapCardView.getLayoutParams();
        params.height = (int) getResources().getDimension(R.dimen.maps_card);
        mapCardView.setLayoutParams(params);
//        Display display=getWindowManager().getDefaultDisplay();                     //размер дисплея
//        Point size=new Point();
//        display.getSize(size);
//
//        float dp=getResources().getDisplayMetrics().density;
//        float height=200f;
//        int dpHeight=(int)(dp*height);                //размер в dp
//
//        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE) {
//        params.height=size.y;
//       // params.setMargins(0,0,0,0);
//        view.setLayoutParams(params);
//       // view.setRadius(0f);
//
//        }
//        else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
//            params.height=dpHeight;
//            view.setLayoutParams(params);
//
//            view.setRadius(20f);
//    }
//
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lng))
               .zoom(12)
               .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
       googleMap.animateCamera(cameraUpdate);

      if(null != googleMap){
           googleMap.addMarker(new MarkerOptions()
                   .position(new LatLng(lat, lng))
                    .title("Mark")
                    .draggable(false)
          );
       }
  }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    String getPhoneByDB(String developer){
        SQLiteDatabase db=dbHelper.getReadableDatabase(dbHelper.PASS);
        Cursor cursor=db.query(dbHelper.DEV_TABLE,null,"title="+"'"+developer+"'",null,null,null,null);
        if(cursor.moveToFirst()){
            int indexPhone=cursor.getColumnIndex(dbHelper.PHONE);

            return cursor.getString(indexPhone);
        }
        else
            cursor.close();
        return "";
    }


}