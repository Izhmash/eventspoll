package com.thememeteam.eventspoll;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
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
    String[] eventList = new String[params]; //array holds event data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordss = new Vector<String>();


        TextView testText = (TextView) findViewById(R.id.text_test);
        testText.setText(readTxt(eventList));

    }


    private String readTxt(String[] events){

        InputStream inputStream = getResources().openRawResource(R.raw.events);
        System.out.println(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
                if (i == '#') {
                    break;
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