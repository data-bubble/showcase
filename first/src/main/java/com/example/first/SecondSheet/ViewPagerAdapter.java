package com.example.first.SecondSheet;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.first.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    SecondActivity context;
    LayoutInflater layoutInflater;
    List<Drawable> list;
    public ViewPagerAdapter(Context context, List<Drawable> list) {

            this.context=(SecondActivity) context;
            this.list=new ArrayList<>(list);
    }

    @Override
    public int getCount() {
        if(list!=null)
            return list.size();
        else
            return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
//        return super.instantiateItem(container, position);
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewPager viewPager=(ViewPager)container;
      //  if(context.getOrientation()== Configuration.ORIENTATION_LANDSCAPE)
            Log.d("BLOCH","-------------");

        final View itemView=layoutInflater.inflate(R.layout.view_pager_item,null);


        ImageView imageView=itemView.findViewById(R.id.pager_image);
        imageView.setImageDrawable(list.get(position));
        viewPager.addView(itemView);
/*
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                View v=layoutInflater.inflate(R.layout.view_pager_item,null);
                ImageView iv=v.findViewById(R.id.pager_image);
                iv.setImageDrawable(list.get(position));
                dialog.setContentView(v);
                dialog.show();
            }
        });
*/



        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      //  super.destroyItem(container, position, object);
        View view=(View)object;
        ViewPager viewPager=(ViewPager)container;
        viewPager.removeView(view);

    }
}
