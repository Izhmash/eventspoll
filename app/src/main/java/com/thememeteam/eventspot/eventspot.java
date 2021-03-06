package com.thememeteam.eventspot;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.List;

import static android.R.attr.data;
import static android.R.id.list;

public class eventspot extends AppCompatActivity {

    String FILENAME = "events_file";

    static int hour = 0;
    static int min = 0;
    static int annee = 0;
    static int mois = 0;
    static int jour = 0;
    static String type = "";

    EditText eventName;

    static TextView time;
    static TextView Date;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_eventspot);

        super.onCreate(savedInstanceState);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        eventName = (EditText) findViewById(R.id.editText);
        time = (TextView)findViewById(R.id.time);
        Date = (TextView)findViewById(R.id.date);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("eventspot Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**
     * Fragment class for TimePicker.  Controls how the picker is
     * created and also where the data goes.
     */
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
            time.setText((Integer.toString(hour)) + ":" + (Integer.toString(min)));
        }

    }

    /**
     * Fragment class for DatePicker.  Controls how the picker is
     * created and also where the data goes.
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            annee = year;
            mois = month + 1;
            jour = day;
            Date.setText((Integer.toString(annee)) + "/" + (Integer.toString(mois)) + "/" + (Integer.toString(jour)));
        }
    }

    /**
     * Determines the data sent from the event selection radio buttons
     * @param view
     */
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.party:
                if (checked)
                    type = "Party\n";
                break;
            case R.id.concert:
                if (checked)
                    type = "Concert\n";
                break;

            case R.id.seminar:
                if (checked)
                    type = "Seminar\n";
                break;

            case R.id.sport:
                if (checked)
                    type = "Sports\n";
                break;
        }
    }

    /**
     * Called when the event create button is pressed.
     * Writes all data to file and finds the lat & lng coords
     * for the input address
     * @param v
     * @throws IOException
     */
    public void geoLocate(View v) throws IOException {
        hideSoftKeyboard(v);

        EditText et = (EditText) findViewById(R.id.editText2);
        String location = et.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> addresses;
        double latitude = 0;
        double longitude = 0;

        addresses = gc.getFromLocationName(location, 1);
        if(addresses.size() > 0) {

            latitude= addresses.get(0).getLatitude();
            longitude= addresses.get(0).getLongitude();
            // To test lat and lng creation
            //Toast.makeText(this,Double.toString(latitude) , Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,Double.toString(longitude) , Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Event Created!" , Toast.LENGTH_SHORT).show();
        }

        int i;

        // Write new event to file

        FileOutputStream fos = openFileOutput(FILENAME, MODE_APPEND); // is this creating a new file?
        //deleteFile(FILENAME); //DEBUG
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        fos.write((eventName.getText().toString() + '\n').getBytes());

        fos.write(type.getBytes());

        fos.write ((Integer.toString(hour)).getBytes());
        fos.write (':');
        fos.write ((Integer.toString(min) + '\n').getBytes());

        fos.write ((Integer.toString(annee)).getBytes());
        fos.write ('/');
        fos.write ((Integer.toString(mois)).getBytes());
        fos.write ('/');
        fos.write ((Integer.toString(jour) + '\n').getBytes());

        fos.write(Double.toString(latitude).getBytes());
        fos.write('\n');
        fos.write(Double.toString(longitude).getBytes());
        fos.write('\n');
        fos.write('#');
        fos.write((Integer.toString(getNumEvents()) + '\n').getBytes() );

        // start testing as per IanB
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();

        // For testing if events were added to the file correctly
        /*try {
            FileInputStream fis = openFileInput(FILENAME);
            int j = fis.read();
            byteArrayOutputStream2.write(j);
            Toast.makeText(this,
                    byteArrayOutputStream2.toString(),
                    Toast.LENGTH_LONG).show();
            fis.close();
            Toast.makeText(this,
                    getEventStr(1),
                    Toast.LENGTH_LONG).show();
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
        //}
        onBackPressed();
    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Looks for pound-symbols <#> in the data and increment the
     * number of events.  Returns the number of events
     */
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
}