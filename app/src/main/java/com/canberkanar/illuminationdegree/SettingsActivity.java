package com.canberkanar.illuminationdegree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonAdd;
    Button buttonRemove;
    Button buttonUpdate;
    Button buttonBack;

    VeriErisim dbYardimcisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        buttonAdd = findViewById(R.id.buttonSettingsAdd);
        buttonRemove = findViewById(R.id.buttonSettingsRemove);
        buttonUpdate = findViewById(R.id.buttonSettingsUpdate);
        buttonBack = findViewById(R.id.buttonSettingsBack);

        buttonAdd.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        dbYardimcisi = new VeriErisim(this);
        dbYardimcisi.veritabaninaBaglan();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonSettingsAdd: {/* When the 'Open' Button is Clicked */
                showAddDialog();
                break;
            }

            case R.id.buttonSettingsBack: {/* When the 'Open' Button is Clicked */
                Intent intent = new Intent(this, MainActivity.class);/* the Activity to be Rendered is Declared */
                startActivity(intent);/* Activity is Run */
                break;
            }

            case R.id.buttonSettingsUpdate: {
                showUpdateDialog();
                break;
            }

            case R.id.buttonSettingsRemove:{
                showRemoveDialog();
                break;
            }
        }
    }

    private void showAddDialog() {
        final LinearLayout addRoomDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_add, null);

        new AlertDialog.Builder(this)
                .setTitle("")
                .setView(addRoomDialog)
                .setPositiveButton(getApplicationContext().getString(R.string.dialog_finish), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText roomName = addRoomDialog.findViewById(R.id.textRoomName);
                        EditText minTreshold = addRoomDialog.findViewById(R.id.textRoomMinLightTreshold);
                        EditText maxTreshold = addRoomDialog.findViewById(R.id.textRoomMaxLightTreshold);

                        if ((roomName.getText().toString().isEmpty()) || (minTreshold.getText().toString().isEmpty()) || (maxTreshold.getText().toString().isEmpty())) {
                            Toast.makeText(SettingsActivity.this, getApplicationContext().getString(R.string.empty_edittext).toString(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        Room newRoom = new Room(roomName.getText().toString(), Integer.parseInt(minTreshold.getText().toString()), Integer.parseInt(maxTreshold.getText().toString()));

                        if (-1 != dbYardimcisi.AddRoom(newRoom)) {
                            Toast.makeText(SettingsActivity.this, getApplicationContext().getString(R.string.database_addRoom_success).toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SettingsActivity.this, getApplicationContext().getString(R.string.database_addRoom_failure).toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(getApplicationContext().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }

    private void showUpdateDialog() {
        final LinearLayout updateRoomDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_update, null);
        final Spinner roomOriginalName = updateRoomDialog.findViewById(R.id.spinnerRoomToBeUpdated);
        fillSpinner(roomOriginalName, updateRoomDialog);

        final EditText roomName = updateRoomDialog.findViewById(R.id.textRoomName);
        final EditText minTreshold = updateRoomDialog.findViewById(R.id.textRoomMinLightTreshold);
        final EditText maxTreshold = updateRoomDialog.findViewById(R.id.textRoomMaxLightTreshold);

        roomOriginalName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Room selectedRoom = dbYardimcisi.GetRoomByName(adapterView.getSelectedItem().toString());
                roomName.setText(selectedRoom.name);
                minTreshold.setText(String.valueOf(selectedRoom.minIntensity));
                maxTreshold.setText(String.valueOf(selectedRoom.maxIntensity));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        new AlertDialog.Builder(this)
                .setTitle("")
                .setView(updateRoomDialog)
                .setPositiveButton(getApplicationContext().getString(R.string.dialog_finish), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        if ((roomName.getText().toString().isEmpty()) || (minTreshold.getText().toString().isEmpty()) || (maxTreshold.getText().toString().isEmpty())) {
                            Toast.makeText(SettingsActivity.this, getApplicationContext().getString(R.string.empty_edittext).toString(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        Room newRoom = new Room(roomName.getText().toString(), Integer.parseInt(minTreshold.getText().toString()), Integer.parseInt(maxTreshold.getText().toString()));

                        if (-1 != dbYardimcisi.UpdateRoom(roomOriginalName.getSelectedItem().toString(), newRoom)) {
                            Toast.makeText(SettingsActivity.this, getApplicationContext().getString(R.string.database_updateRoom_success).toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SettingsActivity.this, getApplicationContext().getString(R.string.database_updateRoom_failure).toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(getApplicationContext().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })

                .create()
                .show();
    }

    String selectedRoomName;

    private void showRemoveDialog() {
        final LinearLayout removeRoomDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_remove, null);
        final Spinner roomOriginalName = removeRoomDialog.findViewById(R.id.spinnerRoomToBeUpdated);
        fillSpinner(roomOriginalName, removeRoomDialog);


        roomOriginalName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Room selectedRoom = dbYardimcisi.GetRoomByName(adapterView.getSelectedItem().toString());
                selectedRoomName = selectedRoom.name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        new AlertDialog.Builder(this)
                .setTitle("")
                .setView(removeRoomDialog)
                .setPositiveButton(getApplicationContext().getString(R.string.dialog_finish), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        if (selectedRoomName.isEmpty() ) {
                            Toast.makeText(SettingsActivity.this, getApplicationContext().getString(R.string.empty_edittext).toString(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (-1 != dbYardimcisi.DeleteRoom(roomOriginalName.getSelectedItem().toString())) {
                            Toast.makeText(SettingsActivity.this, getApplicationContext().getString(R.string.database_removeRoom_success).toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SettingsActivity.this, getApplicationContext().getString(R.string.database_removeRoom_failure).toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(getApplicationContext().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })

                .create()
                .show();
    }

    protected void fillSpinner(Spinner roomTypeSpinner, LinearLayout updateRoomDialog) {
        roomTypeSpinner = updateRoomDialog.findViewById(R.id.spinnerRoomToBeUpdated);

        Cursor cursor = dbYardimcisi.getRoomsIdsNames();

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
    }
}
