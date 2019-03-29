package com.example.jtsuser.securityapp.Owner.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jtsuser.securityapp.Owner.Adapter.OwnerAdapter;
import com.example.jtsuser.securityapp.R;
import com.example.jtsuser.securityapp.Security.Adapter.CustomAdapter;

public class OCurrentFragment extends Fragment {
    ListView listViewOwner;
    String[] names={"Name","Name","Name","Name","Name"};
    String[] contact={"Contact","Contact","Contact","Contact","Contact"};
    String[] other={"Time","Others","Others","Others","Others"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_owner_current,container,false);
        listViewOwner=(ListView)view.findViewById(R.id.list_owner);

        OwnerAdapter owerAdapter=new OwnerAdapter(getContext(),names,contact,other);
        listViewOwner.setAdapter(owerAdapter);


        return view;
    }
}
