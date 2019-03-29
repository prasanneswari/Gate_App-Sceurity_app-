package com.example.jtsuser.securityapp.Security.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jtsuser.securityapp.R;

import org.w3c.dom.Text;

public class CustomAdapter extends BaseAdapter {
    String[] names;
    String[] numbers;
    String[] add;
    String[] date;
    Context mContext;


    public CustomAdapter(Context context, String[] names, String[] number, String[] add, String[] date) {
        this.mContext=context;
        this.names=names;
        this.numbers=number;
        this.add=add;
        this.date=date;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.adpter_list,parent,false);
        TextView textView=(TextView)view.findViewById(R.id.text_request);
        TextView textView1=(TextView)view.findViewById(R.id.txt_contact);
        TextView textView2=(TextView)view.findViewById(R.id.txt_ownerdetails);
        TextView textView3=(TextView)view.findViewById(R.id.txt_time);
        textView.setText(names[position]);
        textView1.setText(numbers[position]);
        textView2.setText(add[position]);
        textView3.setText(date[position]);
        return view;
    }
}
