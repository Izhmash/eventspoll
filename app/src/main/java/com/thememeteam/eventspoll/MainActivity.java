package com.thememeteam.eventspoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    final int params = 5;

    String labels="caption";
    String text="";
    String[] s;
    private Vector<String> wordss;
    int j=0;
    private StringTokenizer tokenizer;
    //String[] eventList = new String[params]; //array holds event data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordss = new Vector<String>();

        String lines[] = getEventStr(2).split("\r\n");
        TextView testText = (TextView) findViewById(R.id.text_test);
        testText.setText(lines[0]);

    }

    /*int getNumEvents() {
        InputStream inputStream = getResources().openRawResource(R.raw.events);
        //System.out.println(inputStream);

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
    }*/

    /**
     * Returns the event info for a given id
     */
    private String getEventStr(int id) {

        InputStream inputStream = getResources().openRawResource(R.raw.events);
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
    public void openMaps(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}