package com.sparenparts.sparenparts;

/**
 * Created by Loknath Shankar on 2/28/2016.
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<ItemList>{

    Context context;
    int layoutResourceId;
    ItemList data[] = null;

    public ItemAdapter(Context context, int layoutResourceId, ItemList []data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ItemHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.list_image);
            holder.categoryText = (TextView)row.findViewById(R.id.categoryText);
            holder.metaText = (TextView)row.findViewById(R.id.metaText);

            row.setTag(holder);
        }
        else
        {
            holder = (ItemHolder)row.getTag();
        }

        ItemList itemList = data[position];
        float _textSize=Index.textSize+Index.textSize*.35f;
        holder.categoryText.setText(itemList.categoryText);
        holder.categoryText.setTextSize(_textSize);
        holder.metaText.setText(itemList.metaText);
        holder.metaText.setTextSize(_textSize);
        holder.imgIcon.setImageResource(itemList.imgIcon);
        return row;
    }

    static class ItemHolder
    {
        ImageView imgIcon;
        TextView categoryText;
        TextView metaText;
    }
}