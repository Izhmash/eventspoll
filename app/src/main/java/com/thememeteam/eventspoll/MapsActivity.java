package com.thememeteam.eventspoll;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    //https://www.simplifiedcoding.net/android-mysql-tutorial-to-perform-basic-crud-operation/

    String FILENAME = "hello_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()  //crash
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        try {
            getNumEvents();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Boston, USA.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        /*BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 4;
        Bitmap newBitmap = BitmapFactory.decodeFile(R.drawable.books_48, opts);*/

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmpBook = Bitmap.createBitmap(170, 170, conf);
        Bitmap bmpHat = Bitmap.createBitmap(170, 170, conf);
        Bitmap bmpGuit = Bitmap.createBitmap(170, 170, conf);
        Bitmap bmpHock = Bitmap.createBitmap(170, 170, conf);

        Canvas canvasBook = new Canvas(bmpBook);
        Canvas canvasHat = new Canvas(bmpHat);
        Canvas canvasGuit = new Canvas(bmpGuit);
        Canvas canvasHock = new Canvas(bmpHock);

// paint defines the text color, stroke width and size
        Paint color = new Paint();
        color.setTextSize(35);
        color.setColor(Color.BLACK);

// modify canvas
        canvasBook.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.books_48), 0,0, color);
        canvasHat.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.party_hat_48), 0,0, color);
        canvasGuit.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.rock_music_filled_50), 0,0, color);
        canvasHock.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.hockey_48), 0,0, color);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


        // Add a marker in Boston and move the camera
        //LatLng boston = new LatLng(42.3601, -71.0589);
        /*Marker mBoston = googleMap.addMarker(new MarkerOptions()
                .position(boston)
                .title("Marker in Boston")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .snippet("This marker is blue!"));
        mBoston.setTag(0);*/
        googleMap.setOnMarkerClickListener(this);

        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(boston));

        String lines[];
        String title;
        String type;
        String time;
        String date;
        double lat;
        double lng;
        //Marker events[];

        // Read events from text file
        // i < (numEvents + 1)
        try {
            Toast.makeText(this, Integer.toString(getNumEvents()) + " events", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 1; i < getNumEvents() + 1; i++) {
                lines = getEventStr(i).split("\n");
                Toast.makeText(this, lines[0], Toast.LENGTH_LONG).show();
                if (i > 1) {
                    title = lines[1];
                    type = lines[2];
                    time = lines[3];
                    date = lines[4];
                    lat = Double.parseDouble(lines[5]);
                    lng = Double.parseDouble(lines[6]);
                }
                else {
                    title = lines[0];
                    type = lines[1];
                    time = lines[2];
                    date = lines[3];
                    lat = Double.parseDouble(lines[4]);
                    lng = Double.parseDouble(lines[5]);
                }
                LatLng tempLatLng = new LatLng(lat, lng);

                if (type.equals("Seminar")) {
                    Marker tempMark = googleMap.addMarker(new MarkerOptions()
                            .position(tempLatLng)
                            .title(type)
                            //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .icon(BitmapDescriptorFactory.fromBitmap(bmpBook))
                            .snippet(title + ": " + time + " on " + date)
                            .anchor(0.5f, 1));
                    tempMark.setTag(lines); // Each marker carries it's descriptors
                }
                else if (type.equals("Concert")) {
                    Marker tempMark = googleMap.addMarker(new MarkerOptions()
                            .position(tempLatLng)
                            .title(type)
                            //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .icon(BitmapDescriptorFactory.fromBitmap(bmpGuit))
                            .snippet(title + ": " + time + " on " + date)
                            .anchor(0.5f, 1));
                    tempMark.setTag(lines); // Each marker carries it's descriptors
                }
                else if (type.equals("Party")) {
                    Marker tempMark = googleMap.addMarker(new MarkerOptions()
                            .position(tempLatLng)
                            .title(type)
                            //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .icon(BitmapDescriptorFactory.fromBitmap(bmpHat))
                            .snippet(title + ": " + time + " on " + date)
                            .anchor(0.5f, 1));
                    tempMark.setTag(lines); // Each marker carries it's descriptors
                }
                else if (type.equals("Sports")) {
                    Marker tempMark = googleMap.addMarker(new MarkerOptions()
                            .position(tempLatLng)
                            .title(type)
                            //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .icon(BitmapDescriptorFactory.fromBitmap(bmpHock))
                            .snippet(title + ": " + time + " on " + date)
                            .anchor(0.5f, 1));
                    tempMark.setTag(lines); // Each marker carries it's descriptors
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            int i = fis.read();
            byteArrayOutputStream.write(i);
            Toast.makeText(this,
                    byteArrayOutputStream.toString(),
                    Toast.LENGTH_SHORT).show();
            fis.close();
            Toast.makeText(this,
                    getEventStr(1),
                    Toast.LENGTH_SHORT).show();
            Toast.makeText(this,
                    getEventStr(2),
                    Toast.LENGTH_SHORT).show();
            Toast.makeText(this,
                    getEventStr(3),
                    Toast.LENGTH_SHORT).show();
            Toast.makeText(this,
                    getEventStr(4),
                    Toast.LENGTH_SHORT).show();
            Toast.makeText(this,
                    getEventStr(5),
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }

    /**
     * Called when the user clicks a marker.
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {



        Toast.makeText(this,
                marker.getTitle(),
                Toast.LENGTH_SHORT).show();


        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    int getNumEvents() throws FileNotFoundException {
        //InputStream inputStream = getResources().openRawResource(R.raw.events);
        FileInputStream inputStream = openFileInput(FILENAME);
        int i;
        int num = 0;
        try {
            i = inputStream.read();
            while (i != -1) {
                i = inputStream.read();
                if (i == '#') {
                    num++;
                }
            }
            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return num;
    }

    /**
     * Returns the event info for a given id
     */
    private String getEventStr(int id) throws FileNotFoundException {

        //InputStream inputStream = getResources().openRawResource(R.raw.events);
        FileInputStream inputStream = openFileInput(FILENAME);
        System.out.println(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
                if (i == '#') {
                    i = inputStream.read();
                    if (Character.getNumericValue(i) == id) {
                        break;
                    } else {
                        byteArrayOutputStream.reset();
                        i = inputStream.read();
                    }
                }
            }
            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return byteArrayOutputStream.toString();
    }
}
