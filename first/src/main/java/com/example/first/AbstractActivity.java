package com.example.first;

import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class AbstractActivity extends AppCompatActivity {

    public Drawable getDrawableFromAssets(String path) {                                //вытащить картринку из assets

        InputStream inputStream = null;
        try{
            if(path!=null)
                inputStream = getAssets().open(path);
            else
                inputStream=getAssets().open("default/prizm.jpg");
            Drawable d = Drawable.createFromStream(inputStream, null);
            return d;
        }
        catch (IOException e){
            e.printStackTrace();

        }

        finally {
            try{
                if(inputStream!=null)
                    inputStream.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }


}
