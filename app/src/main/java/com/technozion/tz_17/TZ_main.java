package com.technozion.tz_17;

/**
 * Created by Anshul Goyal on 03-10-2016.
 */
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TZ_main extends ActionBarActivity {
    private Toolbar toolbar;
    private int col=2;
    private int rows;
    private int count=0;
    public Cursor cr;
    Database sdb=new Database(this);
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tz_main);
        toolbar= (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        db=sdb.create_db();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        populate_grid();
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    private void populate_grid() {
        TableLayout grid_table= (TableLayout) findViewById(R.id.grid_table);
        cr=db.rawQuery("select distinct category1 from main_event",null);
        rows= (int) Math.ceil(cr.getCount()/ 2.0);
        for (int i=0;i<rows;i++){
            TableRow grid_row=new TableRow(this);
            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1.0f
                    );
            grid_row.setLayoutParams(tableRowParams);
            grid_table.addView(grid_row);

            for (int j=0;j<col && cr.moveToNext();j++){
                final String category1;
                category1=cr.getString(cr.getColumnIndex("category1"));
                ImageView event_image=new ImageView(this);
                event_image.setImageResource(R.drawable.e1);
                event_image.setPadding(1,1,1,1);
                event_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        direct(v,category1);
                    }
                });
                grid_row.setLayoutParams(tableRowParams);
                grid_row.addView(event_image);
            }
        }
    }
    public void direct(View v,String category1){
        Intent I=new Intent(TZ_main.this,Events.class);
        // String query="select event_name,remarks from events where category=' "+category1+" ' ";
        String query="select name from main_event where category1='"+category1+"' ";
        I.putExtra("category1", query);
        startActivity(I);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spree_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
//        if (id == R.id.notify_img){
//            Intent I=new Intent(this,Notify.class);
//            startActivity(I);
//        }
//
//        return super.onOptionsItemSelected(item);
        return false;
    }
}

