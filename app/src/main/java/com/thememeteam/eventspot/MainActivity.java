package com.thememeteam.eventspot;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.thememeteam.eventspot.R.layout.content_main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*String FILENAME = "events_file";
    String string = "Party at Joe's house\n" +
            "Party\n" +
            "21:00\n" +
            "2017/1/28\n" +
            "42.3601\n" +
            "-71.0589\n" +
            "#1\n" +
            "It's a concert!\n" +
            "Concert\n" +
            "18:00\n" +
            "2017/1/28\n" +
            "44.4905\n" +
            "-70.1112\n" +
            "#2\n" +
            "ECE Seminar\n" +
            "Seminar\n" +
            "17:00\n" +
            "2017/1/29\n" +
            "45.4905\n" +
            "-73.1112\n" +
            "#3\n" +
            "Hockey Game\n" +
            "Sports\n" +
            "18:00\n" +
            "2017/2/3\n" +
            "47.4905\n" +
            "-75.1112\n" +
            "#4\n" +
            "Off!\n" +
            "Concert\n" +
            "18:00\n" +
            "2017/1/30\n" +
            "44.4762466\n" +
            "-73.2103198\n" +
            "#5";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize view to my events
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new EventsFragment())
                .commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Testing file io*/
        /*int i;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, this.MODE_PRIVATE);
            fos.write(string.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fis = openFileInput(FILENAME);
            i = fis.read();
            byteArrayOutputStream.write(i);
            Toast.makeText(this,
                    byteArrayOutputStream.toString(),
                    Toast.LENGTH_SHORT).show();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();


        if (id == R.id.nav_my_events) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new EventsFragment())
                    .commit();
        }
        else if (id == R.id.nav_map) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new MapFragment())
                    .commit();
        }
        else if (id == R.id.nav_create_event) {
            Intent intent = new Intent(this,eventspot.class);
            startActivity(intent);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new CreateEventFragment())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openMaps(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void openMenu(View view){
        Intent intent = new Intent(this, eventspot.class);
        startActivity(intent);
    }
}

