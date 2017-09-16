package com.technozion.tz_17;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences sharedPreferences;
    private static SplashActivity mInstance;
    Database sdb = new Database(this);
    SQLiteDatabase db;

    public static synchronized SplashActivity getInstance(){
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mInstance = this;
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, MODE_PRIVATE);
        String s = sharedPreferences.getString(Constants.TOKEN_STR,null);
        Constants.TOKEN = s;
        db = sdb.create_db();

        if(Constants.TOKEN!=null) {
            NetworkRequests.EventData();
        }else{
            NetworkRequests.EventDataNoAuth();
        }
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
        return  false;
    }

    public void onSuccess(JSONObject response) throws JSONException {

//<<<<<<< HEAD
//        JSONObject r = response.getJSONObject("details");
//        JSONObject r1 = r.getJSONObject("1");
//        String s = r1.getString("contact_members");
//
//
//        int i = 0;
//        for (String diff_contact : s.split(",")) {
//            i = 0;
//            String[] cvalues = new String[4];
//            for (String val : diff_contact.split(":")) {
//                cvalues[i] = val;
//                i++;
//            }
//            System.out.println(cvalues[0]);
//            String query1 = "select id from main_contactmember where id = " + cvalues[0];
//            final Cursor desc = db.rawQuery(query1, null);
//            int count = desc.getCount();
//            System.out.println(count);
//            String q1 = "insert into main_contactmember values ( " + cvalues[0] + ", \" " + cvalues[1] + " \", \" " + cvalues[2] + " \" , \" " + cvalues[3] + " \" )";
//            System.out.println(q1);
//            if (count == 0) {
//                System.out.println("insertadata");
//                String q = "insert into main_contactmember values ( " + cvalues[0] + ", \" " + cvalues[1] + " \", \" " + cvalues[2] + " \" , \" " + cvalues[3] + " \" )";
//                db.execSQL(q);
//            }
//        }
////        System.out.println(response);
//=======
        try {

            JSONObject all_events = response.getJSONObject("details");
            db = sdb.create_db();
            System.out.println(response);

            String id, content_id, contactmember_id, maxTeamSize, minTeamSize, category_new;
            String event_type, name, department, category1, category2, contact_name, phno, title, content;

            for (int i = 0; i < all_events.length(); i++) {
                System.out.println("hello");
                JSONObject event = all_events.getJSONObject(String.valueOf(i));

                id = event.getString("id");

                //CHECKING IF THIS IS A NEW EVENT and ADDING

                if (checkevent(id)) {
                    //New event added in the database
                    event_type = event.getString("event_type").toLowerCase();
                    name = event.getString("name");
                    department = event.getString("department");
                    category1 = event.getString("category1").toLowerCase();
                    category2 = event.getString("category2").toLowerCase();
                    maxTeamSize = event.getString("maxTeamSize");
                    minTeamSize = event.getString("minTeamSize");
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

                    String query = "insert into main_event values ( " + id + " , \" " + event_type + " \" , \" " + name + " \" , \" sample \" " + " , \" " + department + " \" , \" " + category1 + " \" , \" " + category2 + " \" , \" sample \" , 0 , 0 , 0 , 1 , 1 , 1, 0 , " + maxTeamSize + " , " + minTeamSize + " , 0 , 0 , " + category_new + " );";
                    System.out.println(query);
                    db.execSQL(query);


                }

                //CHECKING IF THERE IS A NEW CONTACT MEMBER
                int j = 0;
                String s = event.getString("contact_members");
                if (s.equals("")) {
                    System.out.println("abc");
                    continue;
                }

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
            System.out.println(e.getCause());
        }


//        response.getString("")

        }



    public void onRequestFailure(int i, String s) {
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
}
