package com.example.first;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.first.Filter;
import com.example.first.MainActivity;
import com.example.first.R;
import com.example.first.ResidentialAdapter;
import com.example.first.pojo.Residential;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FilterDialog extends DialogFragment implements RangeSeekBar.OnRangeSeekBarChangeListener{
    private MainActivity context;

    private  Number minPrice;
    private Number maxPrice;
    private Number minYear;
    private Number maxYear;
    private Number distMetro;
    private Number distMkad;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=(MainActivity) context;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(), R.style.CustomDialog);   //создание через билдер базового класса с добавлением стиля
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.dialog_layout,null);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.h2);
        RangeSeekBar br=v.findViewById(R.id.seekbar);
        RangeSeekBar metroBar=v.findViewById(R.id.seekbar_metro);
        RangeSeekBar ccBar=v.findViewById(R.id.cc_bar);
        br.setRangeValues(0F,10F,0.1);
        minPrice=br.getAbsoluteMinValue();
        maxPrice=br.getAbsoluteMaxValue();
        distMetro=metroBar.getAbsoluteMaxValue();
        minYear=ccBar.getAbsoluteMinValue();
        maxYear=ccBar.getAbsoluteMaxValue();


      br.setOnRangeSeekBarChangeListener(this);
      metroBar.setOnRangeSeekBarChangeListener(this);
      ccBar.setOnRangeSeekBarChangeListener(this);


        builder.setView(v)
               //потом поставить вектор воблу


            .setNeutralButton("применить", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        String spinnerText=context.getPreferences(context.MODE_PRIVATE).getString("spinner_text","empty");    //получение сохраненный данных из SharedPreferences
                        int buttonId=context.getPreferences(context.MODE_PRIVATE).getInt("button",R.id.all_button);
                  //      Log.d("PARAMETERS"," "+context.getPreferences(context.MODE_PRIVATE).getString("spinner_text","fuck"));
//

                 //  List<Residential> temp= Filter.getFiltering(context.getList(),minPrice,maxPrice);                   //применение фильтра по кнопке (старый) выгрузка из листа
                    List<Residential> temp;
                if(spinnerText.equals("empty"))
                    temp=context.getFilter().getAllObjects();
                else
                   temp=context.getFilter().getSelectedObject(buttonId,spinnerText,minPrice,maxPrice,distMetro,minYear,maxYear,null);  //фильтрация из бд


                  ListView listView=context.findViewById(R.id.lv);

                   ResidentialAdapter residentialAdapter=(ResidentialAdapter) listView.getAdapter();                        //   перерисовка списка и добавление нового адаптера
                   residentialAdapter.clear();
                   if(temp!=null)
                   residentialAdapter.addAll(temp);


                }
            });







        return builder.create();



    }

    @Override
    public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
            int id= bar.getId();
            switch (id){
                case R.id.seekbar:
                    minPrice=minValue;
                    maxPrice=maxValue;
                    break;
                case R.id.seekbar_metro:

                    distMetro=maxValue;
                    break;
                case R.id.cc_bar:
                    minYear=minValue;
                    maxYear=maxValue;
                    Log.d("PARAMETERS",""+minValue+" "+maxValue);
                    break;
            }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
