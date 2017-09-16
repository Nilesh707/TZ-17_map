package com.technozion.tz_17;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class
Events extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String category1;
    public Cursor cr;
    public static Events mInstance;
    Database sdb=new Database(getInstance());
    SQLiteDatabase db=sdb.create_db();
    public static synchronized Events getInstance(){
        return mInstance;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar;
        mInstance=this;
        toolbar= (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        setTitle(this.getIntent().getExtras().getString("Title").toString());

       // mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if(this.getIntent().getExtras().getString("Title").toString().equals("All Events"))
            mAdapter = new Adapter_Events(Events.this,getalldata());
        else
            mAdapter = new Adapter_Events(Events.this,getdata());
        mRecyclerView.setAdapter(mAdapter);
    }

    public static void setImage(int k, String event_id, ImageView event_icon){
        Database sdb=new Database(getInstance());
        SQLiteDatabase db=sdb.create_db();
        String imgbit = "select image_str from event_image where event_id = " + event_id;
        final Cursor imgc =  db.rawQuery(imgbit, null);
        if(imgc.getCount()>0) {
            imgc.moveToFirst();
            String bitmap = imgc.getString(0);
            System.out.println(imgc.getCount() + bitmap);

            byte[] decodedString = Base64.decode(bitmap, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            event_icon.setImageBitmap(decodedByte);
        }
    }

    private List<Event_list_item> getdata() {
        List<Event_list_item> data=new ArrayList<>();

        category1=getIntent().getExtras().getString("category1", null);
        cr = db.rawQuery(category1, null);
        for (int i = 0; cr.moveToNext(); i++) {
            Event_list_item current = new Event_list_item();

            current.title = cr.getString(cr.getColumnIndex("name"));
            System.out.println(current.title);
            current.event_type=cr.getString(cr.getColumnIndex("category1"));
            current.event_id=cr.getString(cr.getColumnIndex("id"));
            data.add(current);
        }

        return data;
    }

    private List<Event_list_item> getalldata() {
        List<Event_list_item> data=new ArrayList<>();
        System.out.println("ANSHUL ALL DATA FUNC");
        String query="select category1,name,id from main_event where event_type = \"event\" " ;
        cr = db.rawQuery(query, null);
        for (int i = 0; cr.moveToNext(); i++) {
            Event_list_item current = new Event_list_item();
            current.title = cr.getString(cr.getColumnIndex("name"));
            current.event_type=cr.getString(cr.getColumnIndex("category1"));
            current.event_id=cr.getString(cr.getColumnIndex("id"));
            data.add(current);
        }

        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

       /* if (id == R.id.notify_img){
            Intent I=new Intent(this,Notify.class);
            startActivity(I);
        }
        return super.onOptionsItemSelected(item);*/
        return  false;
    }
}
