package com.thememeteam.eventspot;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingsFragment extends Fragment {

    View myView;

    ToggleButton toggleButton;
    TextView textView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings, container, false);

        //added
        textView = (TextView) myView.findViewById(R.id.themeBtnText);
        toggleButton = (ToggleButton) myView.findViewById(R.id.toggleButton);


        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme(myView);
            }
        });

        return myView;
    }

    public void changeTheme(View view) {
        boolean checked = (toggleButton.isChecked());



        if (checked) {
            textView.setText("On");
            textView.setVisibility(View.VISIBLE);

        } else {
            textView.setText("Off");
            textView.setVisibility(View.VISIBLE);
        }

    }
}