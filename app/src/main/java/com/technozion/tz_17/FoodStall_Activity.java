package com.technozion.tz_17;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Rishu Kumar on 26/08/2017.
 */

public class FoodStall_Activity extends AppCompatActivity {
    private ListView lvProduct;
    private FoodStall_ListAdapter adapter;
    private List<FoodStall> mStallList;
    private DataBaseHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_stall);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Food Stalls");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        lvProduct = (ListView)findViewById(R.id.listview_stalls);
        mDBHelper = new DataBaseHelper(this);
        mStallList = mDBHelper.getListStall();
        adapter = new FoodStall_ListAdapter(this, mStallList);
        lvProduct.setAdapter(adapter);
    }

}
