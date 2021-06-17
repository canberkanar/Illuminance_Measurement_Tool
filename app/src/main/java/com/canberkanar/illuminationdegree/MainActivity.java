package com.canberkanar.illuminationdegree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    Spinner roomTypeSpinner;
    VeriErisim myVeriErisim;
    private Cursor cursor;
    SimpleCursorAdapter rooms;

    int selectedRowID;

    Button startButton;
    Button settingsButton;
    ImageButton infoButton;

    String selectedRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myVeriErisim = new VeriErisim(this);
        myVeriErisim.veritabaninaBaglan();

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(this);

        infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(this);

        fillSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();

        fillSpinner();
    }

    protected void fillSpinner(){
        roomTypeSpinner = findViewById(R.id.spinnerRoomType);

        cursor = myVeriErisim.getRoomsIdsNames();

        ArrayList<String> myList = new ArrayList<>();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                myList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        myList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        roomTypeSpinner.setAdapter(spinnerArrayAdapter);

        roomTypeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        selectedRoom = adapterView.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedRowID = 1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.startButton:{
                Intent intent = new Intent(this, StartActivity.class);/* the Activity to be Rendered is Declared */

                intent.putExtra("roomName", selectedRoom);

                startActivity(intent);/* Activity is Run */
                break;
            }

            case R.id.settingsButton: {
                Intent intent = new Intent(this, SettingsActivity.class);/* the Activity to be Rendered is Declared */
                startActivity(intent);/* Activity is Run */
                break;
            }

            case R.id.infoButton: {
                showInfoDialog();
                break;
            }
        }
    }

    private void showInfoDialog() {
        final LinearLayout addRoomDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_info, null);

        new AlertDialog.Builder(this)
                .setTitle("")
                .setView(addRoomDialog)
                .setPositiveButton(getApplicationContext().getString(R.string.dialog_finish), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                    }
                })
                .create()
                .show();
    }
}
