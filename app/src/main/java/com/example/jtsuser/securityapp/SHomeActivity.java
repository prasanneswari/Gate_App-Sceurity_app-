package com.example.jtsuser.securityapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.jtsuser.securityapp.Owner.Activity.SHomeOwnerActivity;
import com.example.jtsuser.securityapp.Security.Activity.SHomeSecurityActivity;

public class SHomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button butSecurity,butOwner;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shome);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        butSecurity=(Button)findViewById(R.id.but_security);
        butOwner=(Button)findViewById(R.id.but_owner);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Access Control </font>"));
        getSupportActionBar().setIcon(R.drawable.jts);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        butOwner.setOnClickListener(this);
        butSecurity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_security:
                Intent intent=new Intent(SHomeActivity.this,SHomeSecurityActivity.class);
                startActivity(intent);
                break;
            case R.id.but_owner:
                Intent intent1=new Intent(SHomeActivity.this,SHomeOwnerActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
