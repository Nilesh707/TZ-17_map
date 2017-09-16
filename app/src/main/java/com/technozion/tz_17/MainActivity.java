package com.technozion.tz_17;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.technozion.tz_17.VideoPlayer.StandaloneVideoHolder;
import com.technozion.tz_17.VideoPlayer.VideoPlayer;
import com.technozion.tz_17.eventCategories.EventCategory;
import com.technozion.tz_17.eventCategories.EventCategoryAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private int col=2;
    private int rows;
    private int count=0;
    public Cursor cr;
    Database sdb=new Database(this);
    SQLiteDatabase db;
    SwipeRefreshLayout mswipe;
    private static MainActivity mInstance;
    CardView videoPageButton;
    CardView registerButton;
    CardView profileButton;

    public static synchronized MainActivity getInstance(){
        return mInstance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tz_main);
        toolbar= (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Technozion NITW");

        registerButton = (CardView) findViewById(R.id.registerButton);
        profileButton = (CardView) findViewById(R.id.profileButton);
        if(loggedIn()){
            registerButton.setVisibility(View.GONE);
            profileButton.setVisibility(View.VISIBLE);
            setProfileButton();
        }
        else
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, Register.class));
                }
            });





        videoPageButton = (CardView) findViewById(R.id.videoPageButton);
        videoPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoPage.class));
            }
        });

        db=sdb.create_db();
        mInstance = this;

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        setYoutubePlayer();
        populateCategoryRecyclerView();


        String q = "select * from main_event  ";
        cr = db.rawQuery(q,null);
        rows = cr.getCount();
        for(int x = 0;x<rows && cr.moveToNext();x++){
            String s = cr.getString(cr.getColumnIndex("name"));
            String c = cr.getString(cr.getColumnIndex("id"));
            String w = cr.getString(cr.getColumnIndex("category1"));
            String l = cr.getString(cr.getColumnIndex("event_type"));

            s=s.replaceAll("\\s+","");
            System.out.println(s+c+l+w);
        }

        mswipe = (SwipeRefreshLayout) findViewById(R.id.swipeeventref);
        mswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("Refresh");
                String event_ids = "";
                cr = db.rawQuery("select distinct id from main_event",null);
                rows = cr.getCount();
                for(int x = 0;x<rows && cr.moveToNext();x++){
                    String s = cr.getString(cr.getColumnIndex("id"));
                    System.out.println(s);
                    event_ids+=s + ",";
                }
                System.out.println(event_ids);
                event_ids = event_ids.substring(0,event_ids.length()-1);

                final String eid = event_ids;
                if(Constants.TOKEN!=null)
                NetworkRequests.getNewEvents(eid);
                else NetworkRequests.getNoAuthNewEvents(eid);
//                findViewById(R.id.tzmainlin).setVisibility(View.FOCUS_BACKWARD);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_9, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public boolean checkevent(String i)
    {
        try {
            String query = "Select * from main_event where id = " + i;
            final Cursor c = db.rawQuery(query, null);
            if (c == null || c.getCount() < 1) return true;

            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean checkcontent(String i)
    {
        try {
            String query = "Select * from main_content where id = " + i;
            final Cursor c = db.rawQuery(query, null);
            if (c == null || c.getCount() < 1) return true;

            return false;
        }

        catch(Exception e)
        {
            return false;
        }
    }

    public void onEventSuccess(JSONObject response) {
//        System.out.println(response + "success");
//        mswipe.setRefreshing(false);
//        JSONArray r = response.getJSONArray("details");
////        String s = response.getString("id");
//        JSONObject k = (JSONObject) r.get(0);
//        System.out.println(k.getString("id"));
        System.out.println(response);
        try {
            JSONArray all_events = response.getJSONArray("details");
            db = sdb.create_db();
            System.out.println("inside" + all_events.length());

            String id, content_id, contactmember_id, maxTeamSize, minTeamSize, category_new;
            String event_type, name, department, category1, category2, contact_name, phno, title, content,cost;

            for (int i = 0; i < all_events.length(); i++) {
                System.out.println("hello");
                JSONObject event = (JSONObject) all_events.get(i);
                System.out.println(event);
                id = event.getString("id");
                id = id.trim();
                id = id.replaceAll("\\s+","");

                //CHECKING IF THIS IS A NEW EVENT and ADDING

                if (checkevent(id)) {
                    //New event added in the database
                    event_type = event.getString("event_type").toLowerCase();
                    event_type = event_type.trim();
                    event_type = event_type.replaceAll("\\s+","");

                    name = event.getString("name");
                    department = event.getString("department");
                    category1 = event.getString("category1");
                    category1 = category1.trim();
                    category1 = category1.replaceAll("\\s+","");
                    category2 = event.getString("category2").toLowerCase();
                    maxTeamSize = event.getString("maxTeamSize");
                    minTeamSize = event.getString("minTeamSize");
                    cost=event.getString("cost");
                    //content_id = event.getString("content_id");


                    if (!event_type.equalsIgnoreCase("event"))
                        category_new = "0";

                    else if (category1.equalsIgnoreCase("Spotlight"))
                        category_new = "0";

                    else if (category1.equalsIgnoreCase("Online"))
                        category_new = "1";

                    else if (category1.equalsIgnoreCase("Robotics"))
                        category_new = "2";

                    else if (category1.equalsIgnoreCase("General"))
                        category_new = "3";

                    else if (category1.equalsIgnoreCase("Papers"))
                        category_new = "4";

                    else
                        category_new = "5";


                    String query = "insert into main_event values ( " + id + " , \"" + event_type + "\" , \"" + name + "\" , \" sample \"" + " , \"" + department + "\" , \"" + category1 + "\" , \"" + category2 + "\" , \" sample \" , 0 , 0 , 0 , 1 , 1 , 1, " + cost + " , " + maxTeamSize + " , " + minTeamSize + " , 0 , 0 , " + category_new + " , NULL , NULL );";
                    db.execSQL(query);
                    System.out.println("value" + i + query);
                }

                //CHECKING IF THERE IS A NEW CONTACT MEMBER
                int j = 0;
                String s = event.getString("contact_members");
                if (!s.equals("")) {
                    System.out.println("abc");
                    for (String diff_contact : s.split(",")) {
                        j = 0;
                        String[] cvalues = new String[4];
                        cvalues[0] = "2";
                        for (String val : diff_contact.split(":")) {
                            cvalues[j] = val;
                            j++;
                        }
                        System.out.println(cvalues[0]);
                        String query1 = "select * from main_contactmember where id = " + cvalues[0];
                        System.out.println("hello" + query1);
                        final Cursor desc = db.rawQuery(query1, null);
                        int count = desc.getCount();
                        System.out.println(count);
                        if (desc == null || desc.getCount() < 1) {
                            System.out.println("insertadata");
                            String q = "insert into main_contactmember values ( " + cvalues[0] + ", \" " + cvalues[1] + " \", \" " + cvalues[2] + " \" , \" " + cvalues[3] + " \" )";
                            System.out.println("successful");
                            db.execSQL(q);

                            //linking the new contact member with the corresponding event
                            q = "select max(id) from main_event_contact_members";
                            final Cursor cm = db.rawQuery(q, null);
                            cm.moveToFirst();
                            String max = "";
                            if (cm != null && cm.getCount() > 0)
                                max = cm.getString(0);

                            int x = Integer.parseInt(max) + 1;
                            String ecm_id = String.valueOf(x);

                            q = "insert into main_event_contact_members values ( " + ecm_id + " , " + id + " , " + cvalues[0] + " ) ; ";
                            db.execSQL(q);

                        }
                    }

                }
                try {
                    String image = event.getString("logo");
                    String ie = "insert into event_image values ( " + id + " , \" " + image + " \" )";
                    System.out.println(ie);
                    db.execSQL(ie);
                }catch (Exception e){
                    e.printStackTrace();
                }

                //CHECKING IF THERE IS A NEW CONTENT
                content_id = event.getString("content_id");
                if (checkcontent(content_id)) {
                    //new content added
                    title = event.getString("content_title");
                    content = event.getString("content_content");

                    String cq = "insert into main_content values ( " + content_id + " , \" " + title + " \" , \" " + content + " \" , \"  sample \" ) ; ";
                    db.execSQL(cq);

                    //linking the new content with the corresponding event
                    cq = "select max(id) from main_event_contents";
                    final Cursor cm = db.rawQuery(cq, null);
                    cm.moveToFirst();
                    String max = "";
                    if (cm != null && cm.getCount() > 0)
                        max = cm.getString(0);

                    int x = Integer.parseInt(max) + 1;
                    String ec_id = String.valueOf(x);

                    cq = "insert into main_event_contents values ( " + ec_id + " , " + id + " , " + content_id + " ) ; ";
                    db.execSQL(cq);
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        mswipe.setRefreshing(false);
        Toast.makeText(getApplicationContext(), "Updated ", Toast.LENGTH_SHORT).show();

    }

    public void onNoEventSuccess(JSONObject response) {

        System.out.println(response+"nosuccess");
        mswipe.setRefreshing(false);
        Toast.makeText(getApplicationContext(), "Already updated", Toast.LENGTH_SHORT).show();

    }

    public void onRequestFailure(int i, String s) {

        System.out.println("failed");
        mswipe.setRefreshing(false);
        Log.d("MainActivity", "Failed");

    }

    public void setYoutubePlayer(){
        View view = findViewById(R.id.youtubeElement);
        String videoId = "vX3hG2OsXS8"; //TZ 17 youtube video ID
        StandaloneVideoHolder standaloneVideoHolder = new StandaloneVideoHolder(view, this, videoId);
        standaloneVideoHolder.init();
        standaloneVideoHolder.getVideoPlayer().setTitle("Technozion Teaser");
        standaloneVideoHolder.getVideoPlayer().setTitleVisiblity(VideoPlayer.TITLE_GONE);
    }

    void populateCategoryRecyclerView(){
        ArrayList<EventCategory> eventCategories = new ArrayList<>();
        int img[]={
                this.getResources().getIdentifier("e1", "drawable", this.getPackageName()),
                this.getResources().getIdentifier("e6", "drawable", this.getPackageName()),
                this.getResources().getIdentifier("e3", "drawable", this.getPackageName()),
                this.getResources().getIdentifier("e4", "drawable", this.getPackageName()),
                this.getResources().getIdentifier("e5", "drawable", this.getPackageName()),
        };

        eventCategories.add(new EventCategory(1, "Online", img[0]));
        eventCategories.add(new EventCategory(2, "Robotics", img[3]));
        eventCategories.add(new EventCategory(3, "General", img[2]));
        eventCategories.add(new EventCategory(4, "Papers", img[3]));
        eventCategories.add(new EventCategory(5, "Department", img[4]));
        eventCategories.add(new EventCategory(6, "All Events", img[0]));

        EventCategoryAdapter eventCategoryAdapter = new EventCategoryAdapter(this, eventCategories);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventCategoryAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        eventCategoryAdapter.notifyDataSetChanged();

        //scroll to top of the scrollView, resolving conflict due to recyclerView
        ((ScrollView) findViewById(R.id.scrollView)).smoothScrollTo(0,0);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


/** old category table layout code **/
//    private void populate_grid() {
//        TableLayout grid_table= (TableLayout) findViewById(R.id.grid_table);
//        // cr=db.rawQuery("select distinct category_new from events where category_new > 0 order by category_new ASC",null);
//        cr=db.rawQuery("select distinct category_new from main_event where category_new > 0 order by category_new ASC",null);
//        rows= (int) Math.ceil(cr.getCount()/ 2.0);
//        int c=0;
//
//        for (int i=0;i<rows;i++){
//            TableRow grid_row=new TableRow(this);
//            TableLayout.LayoutParams tableRowParams=
//                    new TableLayout.LayoutParams(
//                            TableRow.LayoutParams.MATCH_PARENT,
//                            TableRow.LayoutParams.MATCH_PARENT,
//                            1.0f
//                    );
//            grid_row.setLayoutParams(tableRowParams);
//            grid_table.addView(grid_row);
//
//
//
//            for (int j=0;j<col && cr.moveToNext();j++){
//                final int category1;
//
//                category1=Integer.parseInt(cr.getString(cr.getColumnIndex("category_new")));
//                int img[]={
//                        this.getResources().getIdentifier("e1", "drawable", this.getPackageName()),
//                        this.getResources().getIdentifier("e6", "drawable", this.getPackageName()),
//                        this.getResources().getIdentifier("e3", "drawable", this.getPackageName()),
//                        this.getResources().getIdentifier("e4", "drawable", this.getPackageName()),
//                        this.getResources().getIdentifier("e5", "drawable", this.getPackageName()),
//                };
//                ImageView  event_image=new ImageView(this);
//                event_image.setImageResource(img[c]);c++;
//                event_image.setPadding(1,1,1,1);
//                event_image.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        direct(v,category1);
//                    }
//                });
//                grid_row.setLayoutParams(tableRowParams);
//                grid_row.addView(event_image);
//            }
//
//            int all_events_id = this.getResources().getIdentifier("all_events","drawable",this.getPackageName());
//            ImageView event_image = new ImageView(this);
//            event_image.setImageResource(all_events_id);
//            event_image.setPadding(1,1,1,1);
//            event_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    all_events_direct(v);
//                }
//            });
//            grid_row.setLayoutParams(tableRowParams);
//            grid_row.addView(event_image);
//
//        }
//    }
//    public void direct(View v,int category1){
//        Intent I=new Intent(MainActivity.this,Events.class);
//        // String query="select event_name,remarks from events where category=' "+category1+" ' ";
//        String query="select category1,name,id from main_event where category_new="+category1+" order by category_new ASC";
//        I.putExtra("category1", query);
//        String title="";
//        switch(category1)
//        {
//            case 2: title="Robotics";
//                break;
//            case 5: title="Department";
//                break;
//            case 4: title="Papers";
//                break;
//            case 3: title="General";
//                break;
//            case 1: title="Online";
//                break;
//        }
//        I.putExtra("Title",title);
//        startActivity(I);
//    }

    boolean loggedIn(){
        SharedPreferences sp = getSharedPreferences(Constants.APP_PREFERENCE, Context.MODE_PRIVATE);
        String loggedIn = sp.getString(Constants.isSignedIN, null);
        if(loggedIn != null && loggedIn.equals("1"))
            return true;
        else return false;
    }

    void setProfileButton(){
        SharedPreferences sp = getSharedPreferences(Constants.APP_PREFERENCE, Context.MODE_PRIVATE);
        String loggedIn = sp.getString(Constants.isSignedIN, null);
        if(loggedIn != null && loggedIn.equals("1")){
            String name = sp.getString(Constants.username, null);
            String email = sp.getString(Constants.email, null);
            TextView nameView = (TextView) findViewById(R.id.profileName);
            TextView emailView = (TextView) findViewById(R.id.profileEmail);
            if(name != null )
                nameView.setText("Logged in as " + name);
            if(email != null)
                emailView.setText(email);
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, Profile.class));
                }
            });
        }
    }



}
