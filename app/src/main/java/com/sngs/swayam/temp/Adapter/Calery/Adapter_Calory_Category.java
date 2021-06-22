package com.sngs.swayam.temp.Adapter.Calery;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sngs.swayam.temp.Model.Model_Calories;
import com.sngs.swayam.temp.R;

import java.util.ArrayList;

public class Adapter_Calory_Category extends BaseAdapter {
    LayoutInflater flater;
    ArrayList<Model_Calories> Arr_Calery;
    Context context;

    public Adapter_Calory_Category(Activity context, ArrayList<Model_Calories> list) {
        this.Arr_Calery = list;
        this.context = context;
        flater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return Arr_Calery.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = flater.inflate(R.layout.item_calery, null);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtcalery);
        txtTitle.setText(Arr_Calery.get(position).getT_Categories());
        ImageView imageView = (ImageView) view.findViewById(R.id.imgcalry);
        imageView.setVisibility(View.GONE);
        Log.e("CateGORYNAEM", Arr_Calery.get(position).getT_ItemName() + " POSTIION" + position + " IMAGE " + Arr_Calery.get(position).getT_Imagename());

        return view;
    }
}
