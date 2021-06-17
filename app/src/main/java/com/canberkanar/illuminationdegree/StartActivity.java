package com.canberkanar.illuminationdegree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.Image;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;

    private VeriErisim dbYardimcisi;

    TextView result;
    TextView illuminationQuality;

    float lux;
    Room myRoom;

    int previousQuality = -1;
    ToneGenerator toneG;

    ImageButton infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        result = findViewById(R.id.plainTextSensorResult);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //name = (String.valueOf(lightSensor.getName()).equals(""))? String.valueOf(lightSensor.getName()) : "";
        System.out.println("Sensor Name " + lightSensor.getName());

        if(lightSensor != null){
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        dbYardimcisi = new VeriErisim(this);
        dbYardimcisi.veritabaninaBaglan();

        Intent mIntent = getIntent();
        String selectedRoomName = mIntent.getStringExtra("roomName");

        myRoom = dbYardimcisi.GetRoomByName(selectedRoomName);

        TextView txtRoomName = findViewById(R.id.start_room_name);
        TextView txtRoomMinIntensity = findViewById(R.id.start_room_min_intensity);
        TextView txtRoomMaxIntensity = findViewById(R.id.start_room_max_intensity);
        illuminationQuality = findViewById(R.id.plainTextIlluminationQuality);

        txtRoomName.setText(myRoom.name);
        txtRoomMinIntensity.setText(String.valueOf(myRoom.minIntensity));
        txtRoomMaxIntensity.setText(String.valueOf(myRoom.maxIntensity));

        toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);

        infoButton = (ImageButton)findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LinearLayout addRoomDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_instructions, null);

                new AlertDialog.Builder(StartActivity.this)
                        .setTitle("")
                        .setView(addRoomDialog)
                        .setPositiveButton(getApplicationContext().getString(R.string.dialog_finish), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {



                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent mIntent = getIntent();
        String selectedRoomName = mIntent.getStringExtra("roomName");
        myRoom = dbYardimcisi.GetRoomByName(selectedRoomName);

        TextView txtRoomName = findViewById(R.id.start_room_name);
        TextView txtRoomMinIntensity = findViewById(R.id.start_room_min_intensity);
        TextView txtRoomMaxIntensity = findViewById(R.id.start_room_max_intensity);
        illuminationQuality = findViewById(R.id.plainTextIlluminationQuality);

        txtRoomName.setText(myRoom.name);
        txtRoomMinIntensity.setText(String.valueOf(myRoom.minIntensity));
        txtRoomMaxIntensity.setText(String.valueOf(myRoom.maxIntensity));

        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){

            result.setText(String.valueOf(event.values[0]));

            if( myRoom.minIntensity <= event.values[0] &&   event.values[0] <= myRoom.maxIntensity)
            {
                illuminationQuality.setText(getApplicationContext().getString(R.string.İllumination_quality_good));
                illuminationQuality.setTextColor(Color.GREEN);

                if(1 != previousQuality){
                    previousQuality = 1;
                    toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 400);
                }

            }
            else if(myRoom.minIntensity > event.values[0]){
                illuminationQuality.setText(getApplicationContext().getString(R.string.İllumination_quality_low));
                illuminationQuality.setTextColor(Color.LTGRAY);

                if(2 != previousQuality){
                    previousQuality = 2;
                    toneG.startTone(ToneGenerator.TONE_CDMA_ANSWER, 200);
                }
            }
            else{
                illuminationQuality.setText(getApplicationContext().getString(R.string.İllumination_quality_high));
                illuminationQuality.setTextColor(Color.RED);

                if(3 != previousQuality){
                    previousQuality = 3;
                    toneG.startTone(ToneGenerator.TONE_CDMA_ANSWER, 200);
                }
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
