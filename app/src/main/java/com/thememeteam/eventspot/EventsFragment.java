package com.thememeteam.eventspot;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.provider.Telephony.Mms.Part.FILENAME;


public class EventsFragment extends Fragment {

    String FILENAME = "events_file";

    View myView;

    String lines[];
    String title;
    String type;
    String time;
    String date;
    double lat;
    double lng;

    TextView allData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.my_events, container, false);

        allData = (TextView) myView.findViewById(R.id.textView);

        String allText = "";

        try {
            for (int i = 1; i < getNumEvents() + 1; i++) {
                lines = getEventStr(i).split("\n");
                if (i > 1) {
                    title = lines[1];
                    type = lines[2];
                    time = lines[3];
                    date = lines[4];
                    lat = Double.parseDouble(lines[5]);
                    lng = Double.parseDouble(lines[6]);

                } else {
                    title = lines[0];
                    type = lines[1];
                    time = lines[2];
                    date = lines[3];
                    lat = Double.parseDouble(lines[4]);
                    lng = Double.parseDouble(lines[5]);
                }

                try {
                    allText += getEventStr(i);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> listAddresses = geocoder.getFromLocation(lat, lng, 1);
                    if(null!=listAddresses&&listAddresses.size()>0){
                        String _Location = listAddresses.get(0).getAddressLine(0);
                        allText += _Location + '\n';
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        allData.setText(allText);

        return myView;
    }

    int getNumEvents() throws FileNotFoundException {
        //InputStream inputStream = getResources().openRawResource(R.raw.events);
        FileInputStream inputStream = getActivity().openFileInput(FILENAME);
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
        FileInputStream inputStream = getActivity().openFileInput(FILENAME);
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