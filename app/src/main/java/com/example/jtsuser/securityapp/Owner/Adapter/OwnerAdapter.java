package com.example.jtsuser.securityapp.Owner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jtsuser.securityapp.R;

public class OwnerAdapter extends BaseAdapter {
    String[] names;
    String[] Contacts;
    String[] Others;
    Context mContext;
    public OwnerAdapter(Context context, String[] names, String[] contact, String[] other) {
        this.mContext=context;
        this.names=names;
        this.Contacts=contact;
        this.Others=other;
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
        View view=inflater.inflate(R.layout.owner_adapter,parent,false);
        TextView textView=(TextView)view.findViewById(R.id.text_request);
        TextView textView1=(TextView)view.findViewById(R.id.text_contact);
        TextView textView2=(TextView)view.findViewById(R.id.text_others);

        ImageView imageAccept=(ImageView)view.findViewById(R.id.image_accept);
        ImageView imageCancel=(ImageView)view.findViewById(R.id.image_cancel);

        imageAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Contact is accepted",Toast.LENGTH_LONG).show();
            }
        });
        imageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Contact is Cancel",Toast.LENGTH_LONG).show();
            }
        });

        textView.setText(names[position]);
        textView1.setText(Contacts[position]);
        textView2.setText(Others[position]);
        return view;

    }
}
