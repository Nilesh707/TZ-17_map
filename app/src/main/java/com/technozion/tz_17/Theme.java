package com.technozion.tz_17;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Theme extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        Toolbar toolbar;

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Theme");
        TextView text = (TextView) findViewById(R.id.theme_text);
        text.setText("Presenting \"Chronos\" as the theme for the eleventh edition of Technozion, the blend of technology and mythology. A 3-day peek into technology accompanied by a mythological eye.\n" +
                "\n" +
                "There once was a wise old man: Husband to the Mother Earth and a proud parent of time. He terrified people, for he devoured everyone and everything. With a scythe and an hour glass, the former being less fatal than the latter, he treaded along the only direction he knew. He was omnipresent, butchering lives and destroying souls. The dimensions to him were frivolous, so he created the fourth one. A toy for many, the hour glass to him was a tool, to control and manipulate his only child. He thus became the most powerful and feared entity there ever was.\n" +
                "\n" +
                "The old man had another face to his existence, the one no one knew. For everything he devoured, there was something better and evolved. For every life he took, there were many that flourished. His wrinkled skin personified the end; an end to everything including him.\n" +
                "\n" +
                "As a witnesse of every disruptive technological revolution, like how mean e-mail was to telegrams, the old man will be here, at NIT Warangal, introducing you to his child. He’ll guide you and take you from past to present and from present to future, taking pride in every disruption he ever caused and witnessed. Be nice to him, and he may even let you peek into the future." );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_theme, menu);
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
}
