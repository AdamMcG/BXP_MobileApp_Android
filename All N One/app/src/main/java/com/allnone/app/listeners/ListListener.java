package com.allnone.app.listeners;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.allnone.app.DetailsActivity;
import com.allnone.app.data.RssItem;

import java.util.List;


public class ListListener implements OnItemClickListener {


    List<RssItem> listItems;
    Activity activity;

    public ListListener(List<RssItem> aListItems, Activity anActivity) {
        listItems = aListItems;
        activity  = anActivity;
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Intent i = new Intent(activity, DetailsActivity.class);
        i.setData(Uri.parse(listItems.get(pos).getContent()));

        i.putExtra("title", listItems.get(pos).getTitle());
        i.putExtra("summary", listItems.get(pos).getContent());

        activity.startActivity(i);

    }
}
