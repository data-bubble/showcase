package com.example.first;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.first.MainActivity;
import com.example.first.R;
import com.example.first.ResidentialAdapter;
import com.example.first.pojo.Residential;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LocationDialog extends DialogFragment {
    private SharedPreferences preferences;

    private RadioGroup radioGroup;
    private Spinner spinner;
    private MainActivity activity;
    ArrayList<String> testList=new ArrayList<>();


//    static final String CHECKED_ID="checked_id";
//    static final String  =2131296340;

//    private String checkedSpinnerText;
//    private int checkedButtonId;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity=(MainActivity) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d("ONCREATE","oncreate");

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),R.style.CustomDialog);
        LayoutInflater inflater;
        try{
        inflater=getActivity().getLayoutInflater();}
        catch(NullPointerException e){
            inflater=activity.getLayoutInflater();
            }
        View view=inflater.inflate(R.layout.dialog_location_layout,null);

       spinner=view.findViewById(R.id.spinner);                                                       //установка спиннера
       testList.add("Выберите тип поиска");
        final ArrayAdapter<String> locationAdapter=new ArrayAdapter<String>(activity,R.layout.spinner_row,R.id.spinner_items,testList);
         spinner.setAdapter(locationAdapter);
         radioGroup=view.findViewById(R.id.radiogroup);//  перерисовка спиннера по радиогруппе
        radioGroup.check(R.id.all_button);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ArrayAdapter<String> adapter=(ArrayAdapter<String>) spinner.getAdapter();
                adapter.clear();

//                checkedButtonId=checkedId;

                switch (checkedId) {
                    case R.id.metro_button:
                         adapter.addAll( activity.getFilter().getDataOfColumn(DBHelper.METRO));
                       //      ((ArrayAdapter<String>) spinner.getAdapter()).notifyDataSetChanged();
                        break;
                    case R.id.district_button:
                        adapter.addAll(activity.getFilter().getDataOfColumn(DBHelper.DISTRICT));
                    //    ((ArrayAdapter<String>) spinner.getAdapter()).notifyDataSetChanged();

                        break;
                    case R.id.all_button:
                        adapter.addAll("все");

                }
            }
        });

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {                          //доработать
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    checkedSpinnerText=parent.getItemAtPosition(position).toString();
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                    checkedSpinnerText=parent.getSelectedItem().toString();
//            }
//        });

        builder.setNeutralButton("применить",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView listView=activity.findViewById(R.id.lv);               //дописать передачу списка в дилог фильтрации

                String selectedText=spinner.getSelectedItem().toString();
                int checkId=radioGroup.getCheckedRadioButtonId();
                ResidentialAdapter adapter=(ResidentialAdapter) listView.getAdapter();
                    adapter.clear();
                    activity.getFilter().getSelectedObjects(checkId,selectedText);
                    adapter.addAll(activity.getFilter().getSelectedObjects(checkId,selectedText));


//                Log.d("SELECTED","selected string by spinner   "+spinner.getSelectedItem().toString());
////                    ResidentialAdapter adapter=(ResidentialAdapter) listView.getAdapter();
//                Log.d("BUTTON", "---- "+radioGroup.getCheckedRadioButtonId());


            }
        });
        builder.setView(view);

        return builder.create();
    }
//    private List<String> getTitleOfMetroByMainList(){                                           //на первое время without database
//            List<String> result=new LinkedList<>();
//            String m;
//
//            for(Residential r:activity.getList()){
//                Log.d("MEtro","------------"+r.getMetro());
//                if( (m=r.getMetro())!=null)
//                result.add(m);
//            }
//              return result;
//    }


    private void savePreferences(){                                                     //сохранение SharedPreferences
            preferences=activity.getPreferences(Context.MODE_PRIVATE);

            SharedPreferences.Editor edit=preferences.edit();
            edit.putInt("button",radioGroup.getCheckedRadioButtonId());
            edit.putInt("spinnerPosition",spinner.getSelectedItemPosition());
            if(spinner.getSelectedItem()!=null)
            edit.putString("spinner_text",spinner.getSelectedItem().toString());

            edit.commit();



    }
    private void loadPreferences(){                                                     //загрузка
           preferences=activity.getPreferences(Context.MODE_PRIVATE);
            radioGroup.check(preferences.getInt("button",1));
            spinner.setSelection(preferences.getInt("spinnerPosition",0));

    }



    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
        Log.d("RESUME","onresume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savePreferences();
    //    Log.d("DESTROY","destroy");
    }
}
