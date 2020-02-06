package com.example.first;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.first.pojo.Residential;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResidentialAdapter extends ArrayAdapter<Residential>  {
    private LayoutInflater inf;
    private int resource;
  //  private List<Residential> list=new ArrayList<>();
    private MainActivity context;

    public ResidentialAdapter(@NonNull Context context, int resource, @NonNull List<Residential> objects) {
        super(context, resource, new ArrayList<Residential>(objects));
        this.inf=LayoutInflater.from(context);
        this.resource=resource;

      //  this.list.addAll(objects);
        this.context=(MainActivity)context;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        final Residential residential=super.getItem(position);
        Drawable drawable=context.getDrawableFromAssets(residential.getMainImagePath());


        if(convertView==null) {
            convertView = inf.inflate(this.resource, parent, false);

            //добавить первую картинку из дерриктории на бекграунд


            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder) convertView.getTag();}

        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams) convertView.findViewById(R.id.card_list_view).getLayoutParams();
            params.height=(int)context.getResources().getDimension(R.dimen.image_view_list);
        viewHolder.cardView.setLayoutParams(params);       //установка праметров для карточки лист-вью
        viewHolder.imageView.setImageDrawable(drawable);
        viewHolder.textName.setText(residential.getName());
        viewHolder.textPrice.setText(""+residential.getMinPrice());

        return convertView;
    }


//    private int getBackgroundHeight(){
//
//
//
////        Display display = context.getWindowManager().getDefaultDisplay();                     //размер дисплея
////        Point size = new Point();
////        display.getSize(size);
//  //      ListView.LayoutParams params =  (ListView.LayoutParams) view.getLayoutParams();
//
////
//        float dp = context.getResources().getDisplayMetrics().density;
//        float height=context.getResources().getDimension(R.dimen.image_view_list);
//      //  int dpHeight=(int)(dp*height);                //размер в dp
//        Log.d("PARAMETERS",height+"");
//
//
//        if(context.getOrientation()== Configuration.ORIENTATION_PORTRAIT) {
//            return (int) height;
//        }
//        else if(context.getOrientation()==Configuration.ORIENTATION_LANDSCAPE){
//            return (int)height;
//           }
//        return 0;
//    }



    private class ViewHolder {

      //  final ImageView iView;
        final CardView cardView;
        final TextView textName;
        final TextView textPrice;
        final ImageView imageView;

        public ViewHolder(View v) {
          //  this.iView = v.findViewById(R.id.iView);
            this.cardView=v.findViewById(R.id.card_list_view);
            this.textName = v.findViewById(R.id.textName);
            this.textPrice = v.findViewById(R.id.textPrice);
            this.imageView=v.findViewById(R.id.list_view_background);
        }
    }


}
