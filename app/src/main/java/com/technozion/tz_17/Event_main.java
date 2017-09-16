package com.technozion.tz_17;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;


public class Event_main extends ActionBarActivity {
    private static ApiEndpointInterface apiService;
    ImageView phone_btn, register_btn, share_btn;
    Database sdb = new Database(this);
    SQLiteDatabase db;


    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    private ProgressDialog mProgressDialog;
    SharedPreferences sharedPreferences;
    private static Event_main mInstance;
    private Dialog loading_dialog = null;
    boolean isClicked = true;
    public static synchronized Event_main getInstance(){
        return mInstance;
    }

    private void showProgressDialog()
    {
        if(mProgressDialog==null)
        {
            mProgressDialog=new ProgressDialog(this);
            mProgressDialog.setMessage("Registering you for the event");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog()
    {
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.hide();
    }

    class MinMaxAsync extends AsyncTask<String, String, String> {

        String mEventId;
        Context mContext;

        MinMaxAsync(String eventId, Context context) {
            this.mContext = context;
            this.mEventId = eventId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("retrofit", "preexecute");

        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("retrofit", "network request" + mEventId);
            Call<String> call = Event_main.apiService.fetchMinMaxEvent(mEventId);
            String response = "";

            try {
                response = call.execute().body();
                Log.d("retrofit", "response do : " + response);
            } catch (Exception e) {
                Log.d("retrofit", "response doex : " + response);
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("retrofit", "response : " + s);
        }
    }


    class RegisterAsync extends AsyncTask<String, String, String> {

        String mEventId;
        ArrayList<String> mUserIds;
        Context mContext;

        RegisterAsync(ArrayList<String> userIds, String eventId, Context context) {
            this.mContext = context;
            this.mEventId = eventId;
            this.mUserIds = new ArrayList<>();
            this.mUserIds = userIds;
        }

        @Override
        protected String doInBackground(String... params) {
            Call<String> call = Event_main.apiService.registerTeam(new EventRegisterRetrofitClass(this.mEventId, this.mUserIds));
            String response = "";
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.d("retrofit", "response ex: " + response);
                e.printStackTrace();
            }
            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("invalid")) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), "You cannot register for this event from the app.", Toast.LENGTH_LONG).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://register.springspree.in"));
                startActivity(browserIntent);

            } else {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Log.d("retrofit", "response : " + s);
            }
        }
    }


    TextView event_manager, max_team,min_team,cost,team_size;
    ImageView event_icon;
    
    String event_desc="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        Toolbar toolbar;
        mInstance = this;
        //showProgress(true);

        db = sdb.create_db();
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
//        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        SharedPreferences prefs = getSharedPreferences("spree_login", MODE_PRIVATE);
        final String userid = prefs.getString("userid", "-1");

        final Intent intent = getIntent();
        register_btn = (ImageView) findViewById(R.id.register_button);
        //change it when phone icon is implemented
        phone_btn = (ImageView) findViewById(R.id.event_cost_icon);
        share_btn = (ImageView) findViewById(R.id.share_button);

//not required
//        event_manager = (TextView) findViewById(R.id.event_manager);
//        max_team = (TextView) findViewById(R.id.event_max_teamsize);
//        min_team = (TextView) findViewById(R.id.event_min_teamsize);


          team_size = (TextView) findViewById(R.id.event_teamsize);
          cost = (TextView) findViewById(R.id.event_cost);

          final String title = intent.getExtras().getString("title");
//        setTitle(title);


        String icon = intent.getExtras().getString("icon_id");
        final String event_id = intent.getExtras().getString("event_id");
        boolean ifworkshop = false;
//        event_title = (TextView) findViewById(R.id.event_title);
        //description = (TextView) findViewById(R.id.event_text);
        event_icon = (ImageView) findViewById(R.id.event_icon_main);
//
        int icon_id = event_icon.getResources().getIdentifier(icon, "drawable", getPackageName());
//
//        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Carleton.ttf");
//        event_title.setTypeface(face);
////
//        event_title.setText(title);
        event_icon.setImageResource(icon_id);

        if(icon_id==0){
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


        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = Landing_page_data_test.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new Landing_page_adapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });






        System.out.println("Event id here :" + event_id);
        String query = "Select c.title,c.content from main_event_contents ec,main_content c where ec.event_id =" + event_id + " and ec.content_id = c.id";
        final Cursor desc = db.rawQuery(query,null);

        //description.setText(desc.getString(1));
        String tv_str="";                                               //Text View String
        String html_string = "";
        String ls = System.getProperty("line.separator");
        desc.moveToFirst();
        do {
            if(desc==null||desc.getCount()==0) break;
            html_string =desc.getString(1);
            html_string =html_string.replaceAll("[\r\n]+","");
            html_string = html_string.replaceAll("(\\\\r\\\\n|\\\\n)","");
            String body = Jsoup.parse(desc.getString(1)).text();
            body = body.replaceAll("(\\\\r\\\\n|\\\\n)",ls);
            body = body.replaceAll("[\r\n]+","");
            tv_str = tv_str + ls + desc.getString(0) + ls + body + ls;

        }while(desc.moveToNext());


        System.out.println(event_desc);
//        system.out.println

        event_desc = tv_str;
        Landing_page_data_test.setDescription(html_string) ;
        query = "Select em.name,em.phone_no from main_event_contact_members ecm,main_contactmember em where ecm.event_id =" + event_id + " and ecm.contactmember_id = em.id";

        tv_str = "";

        final Cursor em = db.rawQuery(query,null);
        em.moveToFirst();
        if(em!=null&&em.getCount()>0)
            tv_str = em.getString(1);
        final String em_contact = tv_str;

        tv_str = "";
        do {
            if(em!=null&&em.getCount()>0)
            tv_str = "<p>" +tv_str + "</p>" + "<p>" +"<b>" + em.getString(0) + "</b>" +" :-  " + em.getString(1) + "</p>"    ;
        }while(em.moveToNext());

// not needed
//        event_manager.setText(tv_str);

        Landing_page_data_test.setContacts(tv_str);

        query = "Select name from main_event where id = " + event_id ;
        final Cursor eventName = db.rawQuery(query,null);
        eventName.moveToFirst();
        String event_name = eventName.getString(0);
        TextView event_title = (TextView) findViewById(R.id.eventTitle);
        event_title.setText(event_name);

        query = "Select maxTeamSize,minTeamSize from main_event where id = " + event_id ;
        final Cursor ts = db.rawQuery(query,null);
        ts.moveToFirst();
        tv_str = "Max Team Size : " + ts.getString(0);
        final String maxteam = ts.getString(0);
  //      max_team.setText(tv_str);
        String max =tv_str;
        max= max.replaceAll("[^0-9]" , "");
        tv_str = "Min Team Size : " + ts.getString(1);
        final String minteam = ts.getString(1);
  //      min_team.setText(tv_str);
        String min = tv_str;
        min = min.replaceAll("[^0-9]" , "");
        String final_size = min + '-' + max;
        if(min.equals(max) ){
            team_size.setText(min);
        }
       else{
            team_size.setText(final_size);
        }

        query = "Select cost from main_event where id = " + event_id ;
        final Cursor cs = db.rawQuery(query,null);
        cs.moveToFirst();
        String cost1 = "";
        if(cs!=null&&cs.getCount()>0)
        {
            if(cs.getString(0)==null){
                cost.setText("Free")  ;
            }
            else if(!(cs.getString(0).equals("")||cs.getString(0).equals("0")))
            {

                String rs = getResources().getString(R.string.Rs);
                cost.setText(rs + " " +cs.getString(0));
                cost1 = cs.getString(0);
                query = "Select * from main_event where event_type = \"workshop\" and id = " + event_id ;
                final Cursor w = db.rawQuery(query,null);
                if(w!=null&&w.getCount()>0)
                {
                    //This event is a Workshop
                    ifworkshop=true;
                }
            }
        }

        boolean islecture = false;

        query = "select event_type from main_event where id = " + event_id ;
        final Cursor lec = db.rawQuery(query,null);
        if(lec!=null&&lec.getCount()>0){
            lec.moveToFirst();
            if(lec.getString(0).equals("lectures")||lec.getString(0).equals("initiative"))
            {
                islecture = true;
            }
        }

        query = "select day from main_event where id = " + event_id;
        final Cursor dq = db.rawQuery(query,null);
        dq.moveToFirst();
        TextView dt = (TextView) findViewById(R.id.event_day);
        if(dq!=null && dq.getCount()>0 && dq.getString(0)!=null){
            dt.setText("Day " + dq.getString(0));
        }else{
            dt.setText("TBD");
            System.out.println("day is null");
        }

        query = "select time from main_event where id = " + event_id;
        final Cursor tq = db.rawQuery(query,null);
        tq.moveToFirst();
//        TextView tt = (TextView) findViewById(R.id.event_time);
        if(tq!=null && tq.getCount()>0 && tq.getString(0)!=null){
//            tt.setText("Time : " + tq.getString(0));
        }else{
//            tt.setText("Time : Will be announced soon");
            System.out.println("time is null");
        }
        if(Constants.TOKEN!=null)
        NetworkRequests.Checkdatetime(event_id);
        else
        NetworkRequests.CheckNoAuthdatetime(event_id);

        final boolean ifl = islecture;
        final String ifw = String.valueOf(ifworkshop);
        final String cost_bheja = cost1;

        final String Lstate = Constants.LoginState(getApplicationContext());
        final String regstate = Constants.getRegStatus(getApplicationContext());
        final String c2 = cost1;
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ifl==true)
                {
                    Toast.makeText(getApplicationContext(), "Registration option not available for this event. ", Toast.LENGTH_SHORT).show();
                }
                else if (Lstate == null || !Lstate.equals("1")) {
                    Intent I = new Intent(getApplicationContext(), Login.class);
                    Toast.makeText(getApplicationContext(), "Please login to Continue", Toast.LENGTH_SHORT).show();
                    I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(I);
                } else {
                    if (isNetworkAvailable()) {
                        if(!Constants.LoginState(getApplicationContext()).equals("1")){
                            Toast.makeText(getApplicationContext(), "Please Signin to Continue", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }else if(regstate == null || !Constants.getRegStatus(getApplicationContext()).equals("true")) {
                            Toast.makeText(getApplicationContext(), "Please complete Registration to register for the event", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Profile.class);
                            startActivity(i);
                        } else{
                            Intent i = new Intent(getApplicationContext(), EventRegister.class);
                            i.putExtra("event_id",event_id);
                            i.putExtra("minteam",minteam);
                            i.putExtra("maxteam",maxteam);
                            i.putExtra("ifworkshop",ifw);
                            i.putExtra("cost",cost_bheja);
                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hello, I will be attending "+ title +" event at Technozion'16, NIT Warangal. Go to http://www.technozion.org/ for more details. Hope to see you there.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

//        phone_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(em_contact=="") { Toast.makeText(getApplicationContext(), "No Event Manager Contact Available", Toast.LENGTH_LONG).show(); }
//                else {
//                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + em_contact));
//                    startActivity(intent);
//                }
//            }
//        });

        ImageView locbut = (ImageView) findViewById(R.id.location_button);
        locbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()) {
                    if(Constants.TOKEN!=null)
                    NetworkRequests.getLocation(event_id);
                    else
                    NetworkRequests.getNoAuthLocation(event_id);
                    showProgress(true);
                }else{
                    Toast.makeText(getApplicationContext(), "Check network Connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void onLocationSuccess(JSONObject response) throws JSONException {
        Intent i = new Intent(getApplicationContext(), EventMap.class);
        i.putExtra("lat",response.getString("lat"));
        i.putExtra("longt", response.getString("longt"));
        i.putExtra("name", response.getString("name"));
        startActivity(i);
        showProgress(false);
    }

    public void onDateTimeSuccess(JSONObject response, String event_id) {
        TextView e1 = (TextView) findViewById(R.id.event_day);
//        TextView e2 = (TextView) findViewById(R.id.event_time);
        try{
            String day = response.getString("day");
            String query = "update main_event set day = \"" + day + "\" where id = " +event_id;
            db.execSQL(query);
            e1.setText( day);
        } catch (JSONException e) {
            e.printStackTrace();
            e1.setText("TBD");
        }

        try {
            String time = response.getString("time");
            String query = "update main_event set time = \"" + time + "\"  where id = " +event_id;
            db.execSQL(query);
//            e2.setText("Time : " + time);
        } catch (JSONException e) {
            e.printStackTrace();
//            e2.setText("Time : Will be announced soon");
        }

    }

    public void onDateTimeError(int i, String s) {
        System.out.println(i);
    }

    public void onSuccessAddition(JSONObject response){
        Toast.makeText(this, "Successfully registered for the event", Toast.LENGTH_SHORT).show();
        showProgress(false);
    }
    public void onRequestFailure(int i, String s) {
        showProgress(false);
        Toast.makeText(getApplicationContext(), "Something went wrong. Please try Again", Toast.LENGTH_SHORT).show();
    }
    public void AlreadyAdded(JSONObject response) {
        Toast.makeText(this, "You have already regsitered for this event", Toast.LENGTH_SHORT).show();
        showProgress(false);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showProgress(final boolean show) {
        if (show) {
            if (loading_dialog != null)
                loading_dialog.dismiss();
            loading_dialog = new Dialog(getInstance());
            loading_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loading_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loading_dialog.setCancelable(false);
            loading_dialog.setContentView(R.layout.loading_dialog_layout);
            loading_dialog.show();
        } else {
            if (loading_dialog != null){
                loading_dialog.dismiss();
                loading_dialog = null;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_main, menu);
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
        }*/

//        if (id == R.id.notify_img) {
//            Intent I = new Intent(this, Notify.class);
//            startActivity(I);
//        }
//        return super.onOptionsItemSelected(item);
        return  false;
    }

    public void btn_desc (View view){
        Intent i = new Intent(this, popup_description.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("description", event_desc);
        startActivity(i);

    }

}
