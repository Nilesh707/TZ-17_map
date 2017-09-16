package com.technozion.tz_17;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Notify extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String type;
    public Cursor cr;
    Database sdb=new Database(this);
    SQLiteDatabase db=sdb.create_db();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        Toolbar toolbar;
        toolbar= (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Notify_adapter(Notify.this,getdata());
        mRecyclerView.setAdapter(mAdapter);


    }

    private List<Notify_list_item> getdata() {
        List<Notify_list_item> data=new ArrayList<>();
        int[] icons={R.drawable.event,R.drawable.event,R.drawable.event};
        //type=getIntent().getExtras().getString("type", null);
        cr=db.rawQuery("select name,description from events where type='general'", null);
        for (int i=0;cr.moveToNext() && i<icons.length;i++){
            Notify_list_item current= new Notify_list_item();
            current.icon=icons[i];
            current.title=cr.getString(cr.getColumnIndex("name"));
            current.text=cr.getString(cr.getColumnIndex("description"));
            current.time="";
            data.add(current);
        }
        Toast.makeText(this,"returning",Toast.LENGTH_SHORT).show();
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }
*/
       /* if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
*/
        return super.onOptionsItemSelected(item);
    }
}
