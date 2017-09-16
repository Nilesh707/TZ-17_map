package com.technozion.tz_17;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kiran Konduru on 10/7/2016.
 */

public class EventRegister extends AppCompatActivity {


    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
//    ArrayList<EditText> listItems=new ArrayList<EditText>();
//
//    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
//    ArrayAdapter<EditText> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter = 1;
    private Dialog loading_dialog = null;
    String maxteam, minteam, ifworkshop, event_id, cost_mila;
    private static EventRegister mInstance;

    public static synchronized EventRegister getInstance() {
        return mInstance;
    }

    String et_name;
    String bt_name, rm_name;
    private String tzids = "";
    int[] arr = new int[10];
    String[] idsa = new String[10];

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_event_register);

        Toolbar toolbar;
        mInstance = this;
        toolbar = (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        Intent i = getIntent();
        maxteam = i.getExtras().getString("maxteam");
        minteam = i.getExtras().getString("minteam");
        ifworkshop = i.getExtras().getString("ifworkshop");
        event_id = i.getExtras().getString("event_id");
        Button but = (Button) findViewById(R.id.regevebut);
        cost_mila = i.getExtras().getString("cost");
        tzids = Constants.gettz_id(this) + ",";
        for (int q = 0; q < 10; q++) arr[q] = 0;
        arr[1] = 1;
        if (ifworkshop.equals("false")) {
            but.setText("Register");
        } else {
            but.setText("Pay");
        }

        findViewById(R.id.regevebut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean canRegister = true;

                if (clickCounter < Integer.parseInt(minteam)) {
                    Toast.makeText(getApplicationContext(), "Min Team Length is : " + minteam, Toast.LENGTH_SHORT).show();
                    canRegister = false;
                } else {
                    for (int x = 1; x < clickCounter; x++) {
                        String et = "et" + String.valueOf(x);
                        final int resID = getResources().getIdentifier(et, "id", getPackageName());
                        EditText e = (EditText) findViewById(resID);
                        if (!validateTZID(e.getText().toString())) {
                            canRegister = false;
                            e.setTextColor(Color.RED);
                            Toast.makeText(getApplicationContext(), "Invalid TZ ID", Toast.LENGTH_SHORT).show();
                            e.setOnTouchListener(new View.OnTouchListener() {

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    final EditText e1 = (EditText) findViewById(resID);
                                    e1.setText("");
                                    e1.setTextColor(Color.BLACK);
                                    return false;
                                }
                            });
                        } else {
                            tzids += e.getText().toString() + ",";
                        }
                    }
                    System.out.println(tzids);
                    NetworkRequests.validateAppTzid(tzids);
                    showProgress(true);
                }

            }
        });


        findViewById(R.id.but1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) findViewById(R.id.et1);
                String id1 = e1.getText().toString();
                NetworkRequests.validateindiAppTzid(id1, 1);
            }
        });
        findViewById(R.id.but2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) findViewById(R.id.et2);
                String id1 = e1.getText().toString();
                NetworkRequests.validateindiAppTzid(id1, 2);
            }
        });
        findViewById(R.id.but3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) findViewById(R.id.et3);
                String id1 = e1.getText().toString();
                NetworkRequests.validateindiAppTzid(id1, 3);
            }
        });
        findViewById(R.id.but4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) findViewById(R.id.et4);
                String id1 = e1.getText().toString();
                NetworkRequests.validateindiAppTzid(id1, 4);
            }
        });
        findViewById(R.id.but5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) findViewById(R.id.et5);
                String id1 = e1.getText().toString();
                NetworkRequests.validateindiAppTzid(id1, 5);
            }
        });
        findViewById(R.id.but6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) findViewById(R.id.et6);
                String id1 = e1.getText().toString();
                NetworkRequests.validateindiAppTzid(id1, 6);
            }
        });
        findViewById(R.id.but7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) findViewById(R.id.et7);
                String id1 = e1.getText().toString();
                NetworkRequests.validateindiAppTzid(id1, 7);
            }
        });

        findViewById(R.id.but8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) findViewById(R.id.et8);
                String id1 = e1.getText().toString();
                NetworkRequests.validateindiAppTzid(id1, 8);
            }
        });
        findViewById(R.id.but9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) findViewById(R.id.et9);
                String id1 = e1.getText().toString();
                NetworkRequests.validateindiAppTzid(id1, 9);
            }
        });

        findViewById(R.id.rbut1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t1 = (TextView) findViewById(R.id.id1);
                t1.setVisibility(View.GONE);
                TextView t2 = (TextView) findViewById(R.id.name1);
                t2.setVisibility(View.GONE);
                EditText e1 = (EditText) findViewById(R.id.et1);
                e1.setVisibility(View.VISIBLE);
                Button but = (Button) findViewById(R.id.but1);
                but.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.rbut2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t1 = (TextView) findViewById(R.id.id2);
                t1.setVisibility(View.GONE);
                TextView t2 = (TextView) findViewById(R.id.name2);
                t2.setVisibility(View.GONE);
                EditText e1 = (EditText) findViewById(R.id.et2);
                e1.setVisibility(View.VISIBLE);
                Button but = (Button) findViewById(R.id.but2);
                but.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.rbut3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t1 = (TextView) findViewById(R.id.id3);
                t1.setVisibility(View.GONE);
                TextView t2 = (TextView) findViewById(R.id.name3);
                t2.setVisibility(View.GONE);
                EditText e1 = (EditText) findViewById(R.id.et3);
                e1.setVisibility(View.VISIBLE);
                Button but = (Button) findViewById(R.id.but3);
                but.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.rbut4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t1 = (TextView) findViewById(R.id.id4);
                t1.setVisibility(View.GONE);
                TextView t2 = (TextView) findViewById(R.id.name4);
                t2.setVisibility(View.GONE);
                EditText e1 = (EditText) findViewById(R.id.et4);
                e1.setVisibility(View.VISIBLE);
                Button but = (Button) findViewById(R.id.but4);
                but.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.rbut5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t1 = (TextView) findViewById(R.id.id5);
                t1.setVisibility(View.GONE);
                TextView t2 = (TextView) findViewById(R.id.name5);
                t2.setVisibility(View.GONE);
                EditText e1 = (EditText) findViewById(R.id.et5);
                e1.setVisibility(View.VISIBLE);
                Button but = (Button) findViewById(R.id.but5);
                but.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.rbut6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t1 = (TextView) findViewById(R.id.id6);
                t1.setVisibility(View.GONE);
                TextView t2 = (TextView) findViewById(R.id.name6);
                t2.setVisibility(View.GONE);
                EditText e1 = (EditText) findViewById(R.id.et6);
                e1.setVisibility(View.VISIBLE);
                Button but = (Button) findViewById(R.id.but6);
                but.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.rbut7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t1 = (TextView) findViewById(R.id.id7);
                t1.setVisibility(View.GONE);
                TextView t2 = (TextView) findViewById(R.id.name7);
                t2.setVisibility(View.GONE);
                EditText e1 = (EditText) findViewById(R.id.et7);
                e1.setVisibility(View.VISIBLE);
                Button but = (Button) findViewById(R.id.but7);
                but.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.rbut8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t1 = (TextView) findViewById(R.id.id8);
                t1.setVisibility(View.GONE);
                TextView t2 = (TextView) findViewById(R.id.name8);
                t2.setVisibility(View.GONE);
                EditText e1 = (EditText) findViewById(R.id.et8);
                e1.setVisibility(View.VISIBLE);
                Button but = (Button) findViewById(R.id.but8);
                but.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.rbut9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t1 = (TextView) findViewById(R.id.id9);
                t1.setVisibility(View.GONE);
                TextView t2 = (TextView) findViewById(R.id.name9);
                t2.setVisibility(View.GONE);
                EditText e1 = (EditText) findViewById(R.id.et9);
                e1.setVisibility(View.VISIBLE);
                Button but = (Button) findViewById(R.id.but9);
                but.setVisibility(View.VISIBLE);
            }
        });

    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        if (clickCounter < Integer.parseInt(maxteam)) {
            if (arr[clickCounter] == 0) {
                Toast.makeText(getApplicationContext(), "Make Sure previous ids are valid", Toast.LENGTH_SHORT).show();
                return;
            }
            et_name = "et" + String.valueOf(clickCounter);
            bt_name = "but" + String.valueOf(clickCounter);
            rm_name = "rbut" + String.valueOf(clickCounter);
            clickCounter++;
            final int resID = getResources().getIdentifier(et_name, "id", getPackageName());
            final int butID = getResources().getIdentifier(bt_name, "id", getPackageName());
            final int remID = getResources().getIdentifier(rm_name, "id", getPackageName());
            EditText e = (EditText) findViewById(resID);
            Button b = (Button) findViewById(butID);
            Button r = (Button) findViewById(remID);
            e.setVisibility(View.VISIBLE);
            b.setVisibility(View.VISIBLE);
            r.setVisibility(View.VISIBLE);
//            e.setOnTouchListener(new View.OnTouchListener() {
//
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    final EditText e1 = (EditText) findViewById(resID);
//                    e1.setText("");
//                    return false;
//                }
//            });

        } else {
            if (Integer.parseInt(maxteam) == 1)
                Toast.makeText(getApplicationContext(), "Individual Event : No Team Required", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Max Team limit Reached", Toast.LENGTH_SHORT).show();
        }
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void remItems(View v) {
        if (clickCounter != 1) {
            clickCounter--;
            et_name = "et" + String.valueOf(clickCounter);
            int resID = getResources().getIdentifier(et_name, "id", getPackageName());
            EditText e = (EditText) findViewById(resID);
            e.setVisibility(View.INVISIBLE);
            e.setText("Enter the TZ ID");
            e.setTextColor(Color.BLACK);
        }
    }

    boolean validateTZID(String id) {
        //Validate the TZ ID entered by the user
        return true;
    }

    public boolean checkids(String s) {
        for (int i = 1; i < clickCounter; i++) {
            if (idsa[i].equals(s))
                return false;
        }
        return true;
    }

    public void onIndividualIdSuccess(int x, JSONObject response) throws JSONException {

        String s = response.getString("id");
        String et = "et" + String.valueOf(x);
        String but = "but" + String.valueOf(x);
        String t1 = "id" + String.valueOf(x);
        String t2 = "name" + String.valueOf(x);
        final int resID = getResources().getIdentifier(et, "id", getPackageName());
        final int butID = getResources().getIdentifier(but, "id", getPackageName());
        final int tID1 = getResources().getIdentifier(t1, "id", getPackageName());
        final int tID2 = getResources().getIdentifier(t2, "id", getPackageName());
        Button b = (Button) findViewById(butID);
        EditText e = (EditText) findViewById(resID);
        TextView tid = (TextView) findViewById(tID1);
        TextView tname = (TextView) findViewById(tID2);
        tname.setText(response.getString("name"));
        tid.setText(response.getString("id"));
        tid.setVisibility(View.VISIBLE);
        tname.setVisibility(View.VISIBLE);
        e.setVisibility(View.GONE);
        b.setVisibility(View.GONE);
//        e.setTextColor(Color.GREEN);
        idsa[x] = (String) tid.getText();
        arr[clickCounter] = 1;
        showProgress(false);
    }

    public void sameids(JSONObject response, int x) {
        String et = "et" + String.valueOf(x);
        final int resID = getResources().getIdentifier(et, "id", getPackageName());
        EditText e = (EditText) findViewById(resID);
        e.setTextColor(Color.RED);
        Toast.makeText(getApplicationContext(), "You cannot add yourself", Toast.LENGTH_SHORT).show();
        e.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final EditText e1 = (EditText) findViewById(resID);
                e1.setText("");
                e1.setTextColor(Color.BLACK);
                return false;
            }
        });
        showProgress(false);
    }

    public void onIndividualFailure(int x) {
        String et = "et" + String.valueOf(x);
        final int resID = getResources().getIdentifier(et, "id", getPackageName());
        EditText e = (EditText) findViewById(resID);
        e.setTextColor(Color.RED);
        Toast.makeText(getApplicationContext(), "Invalid TZ ID", Toast.LENGTH_SHORT).show();
        e.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final EditText e1 = (EditText) findViewById(resID);
                e1.setText("");
                e1.setTextColor(Color.BLACK);
                return false;
            }
        });
        showProgress(false);

    }

    public void onValidationSuccess() {

        System.out.println("Validated");

        if (ifworkshop.equals("false")) {
            //Register for event code
            Intent in = getIntent();
            String eventid = in.getStringExtra("event_id");
            System.out.println(tzids);
            NetworkRequests.addAppEvent(tzids, eventid);
            showProgress(true);

        } else {

            //Pay for workshop
            Intent in = getIntent();
            String eventid = in.getStringExtra("event_id");
            NetworkRequests.addAppWorkshop(eventid, tzids);
            showProgress(true);
        }
    }

    public void onValidationFailure(JSONObject response) throws JSONException {
        System.out.println(response);
        Toast.makeText(getApplicationContext(), response.getString("notvalid") + " is invalid id", Toast.LENGTH_SHORT).show();
        tzids = Constants.gettz_id(this) + ",";
        showProgress(false);
    }

    public void onRegRespSuccess(JSONObject response) {
        try {
            String trans_id = response.getString("transaction_id");
            String trans_amount = response.getString("transaction_amount");
            String hash = response.getString("hash");
            String salt = response.getString("salt");
            Intent i = new Intent(this, WebPayment.class);
            i.putExtra("Amount", trans_amount);
            i.putExtra("trans_id", trans_id);
            i.putExtra("hash", hash);
            i.putExtra("salt", salt);
            startActivity(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showProgress(false);
    }


    public void onWorkshopSuccess(JSONObject response) throws JSONException {
        System.out.println(response);
        Intent i = new Intent(getApplicationContext(), PaymentPayu.class);
        i.putExtra("Amount", response.getString("transaction_amount"));
        i.putExtra("trans_id", response.getString("transaction_id"));
        i.putExtra("event_id", event_id);
        i.putExtra("hash", response.getString("hash"));
        startActivity(i);
        showProgress(false);
    }


    public void onEventFailure(JSONObject response) throws JSONException {
        System.out.println(response);
        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
        showProgress(false);
        showProgress(false);
    }

    public void onEventSuccess(JSONObject response) {
        System.out.println(response);
        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        showProgress(false);
    }

    public void onSuccessAddition(JSONObject response) {
        showProgress(false);

    }

    public void onRequestFailure(int i, String s) {
        System.out.println(i);
        Toast.makeText(getApplicationContext(), "Some error occured. Please try again" , Toast.LENGTH_SHORT).show();
        showProgress(false);
    }

    public void payment(JSONObject response) {
        try {
            String trans_id = response.getString("transaction_id");
            String trans_amount = response.getString("transaction_amount");
            String hash = response.getString("hash");
            String salt = response.getString("salt");
            Intent i = new Intent(this, WebPayment.class);
            i.putExtra("Amount", trans_amount);
            i.putExtra("trans_id", trans_id);
            i.putExtra("hash", hash);
            i.putExtra("salt", salt);
            startActivity(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showProgress(false);
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
            if (loading_dialog != null) {
                loading_dialog.dismiss();
                loading_dialog = null;
            }
        }
    }
}
