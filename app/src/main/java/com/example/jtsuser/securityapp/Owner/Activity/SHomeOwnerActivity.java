package com.example.jtsuser.securityapp.Owner.Activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jtsuser.securityapp.Owner.Fragment.OCurrentFragment;
import com.example.jtsuser.securityapp.Owner.Fragment.OGenerateFragment;
import com.example.jtsuser.securityapp.Owner.Fragment.OHistoryFragment;
import com.example.jtsuser.securityapp.R;

public class SHomeOwnerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shome_owner);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Access Control </font>"));
        loadFragment(new OGenerateFragment());
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_signout:
                Toast.makeText(getApplicationContext(),"LogOut is Clicked",Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){



            case R.id.navigation_request:
                fragment = new OGenerateFragment();
                break;

            case R.id.navigation_current:
                fragment = new OCurrentFragment();
                break;

            case R.id.navigation_history:
                fragment = new OHistoryFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
