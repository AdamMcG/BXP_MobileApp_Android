package com.allnone.app.Controllers;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allnone.app.Models.Hamster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 18/08/15.
 * diary class corrosponds to the diary activity.
 * It holds a list of appoinments which populate a listview
 * in the view.
 */
public class GridViewAdapter extends BaseAdapter
{
    ArrayList<Hamster> items;
    Context context;
    LayoutInflater inflater;
    public GridViewAdapter(Context context, List<Hamster> items)
    {
        this.context = context;
        this.items = (ArrayList<Hamster>) items;
        inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, null);
        }
        TextView tview1 = (TextView) convertView.findViewById(android.R.id.text1);
        TextView tview2 = (TextView) convertView.findViewById(android.R.id.text2);
        settingRowColours(position, tview1, tview2);
        settingAttributesToTextViews(position, tview1, tview2);

        return convertView;
    }

    private void settingAttributesToTextViews(int position, TextView tview1, TextView tview2) {
        tview1.setText(items.get(position).getName());
        tview1.setTextColor(Color.parseColor("#FFFFFF"));
        String a = String.valueOf(items.get(position).getDetail());
        tview2.setText(a);
        tview1.setTextSize(12);
        tview2.setTextColor(Color.parseColor("#FFFFF0"));
        tview2.setTextSize(30);
        tview1.setGravity(Gravity.CENTER);
        tview2.setGravity(Gravity.CENTER);
    }

    private void settingRowColours(int position, TextView tview1, TextView tview2) {
        if (items.get(position).getName().equals("green") || items.get(position).getName().equals("jade")
                || items.get(position).getName().equals("emerald") || items.get(position).getName().equals("apple")
                || items.get(position).getName().equals("help")) {
           tview1.setBackgroundColor(Color.parseColor("#328332"));
            tview2.setBackgroundColor(Color.parseColor("#328332"));
        } else if (items.get(position).getName().equals("white") || items.get(position).getName().equals("olive") ||
                items.get(position).getName().equals("lime") || items.get(position).getName().equals("suggestion")
                || items.get(position).getName().equals("newUser"))
        {
            tview1.setBackgroundColor(Color.parseColor("#000080"));
            tview2.setBackgroundColor(Color.parseColor("#000080"));
        } else
        {
            tview1.setBackgroundColor(Color.parseColor("#FF3232"));
            tview2.setBackgroundColor(Color.parseColor("#FF3232"));
          }
    }
}
