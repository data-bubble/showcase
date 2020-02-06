package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;

import net.sqlcipher.database.SQLiteDatabase;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.first.SecondSheet.SecondActivity;
import com.example.first.pojo.Residential;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




public class MainActivity extends AbstractActivity implements AdapterView.OnItemClickListener {
    DBHelper dbHelper;
    private Filter filter;
    private int orientation;
  //  SharedPreferences preferences;




    //Filter filter;
    private List<Residential> list=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase.loadLibs(this);

        dbHelper = new DBHelper(this);
        filter = new Filter(this);


        //      createListFromDB();                         // cipher
        list = filter.getAllObjects();                  //   фильтер с обычным классом Sqlitedatabase

        ListView listView = findViewById(R.id.lv);
        listView.setAdapter(new ResidentialAdapter(this, R.layout.residential_layout, list));
        listView.setOnItemClickListener(this);




    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

     //   Log.d("PARAMETERS",((Residential)adapterView.getItemAtPosition(i)).getName());
        Intent intent=new Intent(this,SecondActivity.class);
        Residential r=(Residential)adapterView.getItemAtPosition(i);

        intent.putExtra(Residential.class.getSimpleName(),r);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i=item.getItemId();
        FilterDialog dialog=new FilterDialog();
        if(i==R.id.filter){
       //     Log.d("PARAMETERS",getPreferences(MODE_PRIVATE).getString("spinner_text","savepreferences value"));

         dialog.show(getSupportFragmentManager(),"custom");}
        if(i==R.id.location){
            LocationDialog locationDialog=new LocationDialog();

            locationDialog.show(getSupportFragmentManager(),"custom2");
        }



        return super.onOptionsItemSelected(item);
    }


    public List<Residential> getList() {
        return list;
    }

//    public List<Residential> createListFromDB(){
//       SQLiteDatabase db=dbHelper.getWritableDatabase(DBHelper.PASS);
//        Cursor cursor=db.query(dbHelper.RS_TABLE,null,null,null,null,null,dbHelper.MIN_PRICE);
//
//        if(cursor.moveToNext()){
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
//
//            list.clear();
//                do{
//                    Log.d("mlog","ID="+cursor.getInt(idIndex)+", Title="+cursor.getString(titleIndex)+", minP="+cursor.getInt(minPriceIndex)+
//                            ", maxP="+cursor.getInt(maxPriceIndex)+", imgSrc="+cursor.getString(imgPathIndex));
//                    AssetManager assetManager=getAssets();
//
//                Residential temp=new Residential(cursor.getInt(idIndex),
//                            cursor.getString(titleIndex),
//                            cursor.getInt(minPriceIndex),
//                            cursor.getInt(maxPriceIndex),
//                            cursor.getString(imgPathIndex),
//                            cursor.getString(metroIndex),
//                            cursor.getInt(mkadTimeIndex),
//                            cursor.getString(gkIndex),
//                           cursor.getInt(metroTimeIndex),
//                           cursor.getString(districtIndex),
//                           cursor.getString(descriptionIndex),this);
//
//                list.add(temp);
//                }while(cursor.moveToNext());
//        }
//        else
//            Log.d("mLog","0 rows");
//        cursor.close();
//        return null;
//    }


//    public Drawable getDrawableFromAssets(String path) {                                //вытащить картринку из assets метод в абстрактном классе
//
//        InputStream inputStream = null;
//        try{
//            if(path!=null)
//            inputStream = getAssets().open(path);
//            else
//                inputStream=getAssets().open("default/prizm.jpg");
//            Drawable d = Drawable.createFromStream(inputStream, null);
//            return d;
//        }
//        catch (IOException e){
//            e.printStackTrace();
//
//        }
//
//        finally {
//            try{
//                if(inputStream!=null)
//                    inputStream.close();
//            }
//            catch (IOException ex){
//                ex.printStackTrace();
//            }
//        }
//        return null;
//    }


    public DBHelper getDbHelper() {
        return dbHelper;
    }

    Filter getFilter() {
        return filter;
    }

//    public void setBackgroundSize(final int orientation) {
//
//
//        RelativeLayout layout =findViewById(R.id.residential_layout);
//        ListView listView=findViewById(R.id.lv);
//
//        if(orientation== Configuration.ORIENTATION_LANDSCAPE) {
//            layout.setMinimumHeight(600);
//
//        }
//            else if(orientation==Configuration.ORIENTATION_PORTRAIT) {
//            layout.setMinimumHeight(400);
//        }
//    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientation = newConfig.orientation;

//        Display display = getWindowManager().getDefaultDisplay();                     //размер дисплея
//        Point size = new Point();
//        display.getSize(size);
//        RelativeLayout listViewItem = findViewById(R.id.residential_layout);
//        ListView.LayoutParams params =  (ListView.LayoutParams) listViewItem.getLayoutParams();
//
////
//        float dp = getResources().getDisplayMetrics().density;
//        float height=getResources().getDimension(R.dimen.image_view_list);
//        int dpHeight=(int)(dp*height);                //размер в dp
// Log.d("PARAMETERS",height+"");
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            params.height = (int)height;
//            listViewItem.setLayoutParams(params);
//
//            listViewItem.setLayoutParams(params);}
//            else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT) {
//            params.height=(int)height;
//            listViewItem.setLayoutParams(params);
//
//            }

    }



    public int getOrientation() {
        return orientation;
    }

}
