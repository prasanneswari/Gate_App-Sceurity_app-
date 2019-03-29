package com.example.jtsuser.securityapp.Security.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.jtsuser.securityapp.R;
import com.example.jtsuser.securityapp.Security.Adapter.CustomAdapter;

public class SHistoryFragment extends Fragment {
    ListView listView;
    String[] names={"AnjiReddy","BuchiBabu","Ramesh","Sunil","Mahesh"};
    String[] number={"9010168075","9010168076","8801914985","9573240927","801914985"};
    String[] add={"Hyderabad","SRnagar","Ameerpet","Panjagutta","Dilsukunagar"};
    String[] date={"12-01-2019","13-01-2019","02/Jan/2018","02/Jan/2019","05-09-2019"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history,container,false);

        listView=(ListView)view.findViewById(R.id.list_history);
        CustomAdapter adapter=new CustomAdapter(getContext(),names,number,add,date);
        listView.setAdapter(adapter);
        return view;
    }
}
