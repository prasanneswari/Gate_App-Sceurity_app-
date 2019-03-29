package com.example.jtsuser.securityapp.Owner.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.jtsuser.securityapp.R;

public class OGenerateFragment extends Fragment {

    Spinner spinnerUsertype,spinnerValidty;

    String[] userType={"Select User Type","Visitor","Maid","Technision","Delivery","Cab"};
    String[] validity={"Select Validty Time","30mins","2hour's","6hour's","8hour's","12hour's","24hour's","1week","1Year"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_owner_genrate,container,false);

        spinnerUsertype=(Spinner)view.findViewById(R.id.sp_user_type);
        spinnerValidty=(Spinner)view.findViewById(R.id.sp_validety);

        ArrayAdapter adapter= new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,userType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsertype.setAdapter(adapter);
        spinnerUsertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerUsertype.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter adapter1= new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,validity);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerValidty.setAdapter(adapter1);
        spinnerValidty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValidty.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        return view;
    }
}
