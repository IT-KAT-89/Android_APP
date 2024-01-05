package de.androidnewcomer.sensor_daten_lesen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView tVbeschX, tVbeschY, tVbeschZ;
    private TextView tVgyroX, tVgyroY, tVgyroZ;
    private boolean startBesch, startGyro;
    File sdCard = Environment.getExternalStorageDirectory();
    File dir = new File (sdCard.getAbsolutePath() + "/app_Daten/");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setze ClickListener
        findViewById(R.id.besch_start).setOnClickListener(this);
        findViewById(R.id.besch_stop).setOnClickListener(this);
        findViewById(R.id.gyro_start).setOnClickListener(this);
        findViewById(R.id.gyro_stop).setOnClickListener(this);
        findViewById(R.id.speicherCsv).setOnClickListener(this);

        //Textview Zuweisung
        tVbeschX = findViewById(R.id.beschX);//Beschleunigung
        tVbeschY = findViewById(R.id.beschY);
        tVbeschZ = findViewById(R.id.beschZ);
        tVgyroX = findViewById(R.id.gyroX);//Gyro
        tVgyroY = findViewById(R.id.gyroY);
        tVgyroZ = findViewById(R.id.gyroZ);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Sensortypen Auswahl
        Sensor sensBesch = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor sensGameRotation = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        Sensor sensGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor sensMotionDetect = sensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT);
        Sensor sensStationDetect = sensorManager.getDefaultSensor(Sensor.TYPE_STATIONARY_DETECT);
        



        //Sensor Objekte mit zugehoerigen Methoden erzeugen
        SensorEventListener sensorEventListenerBesch = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent event)
            {
                //abgreifen der Sensorwerte
                aktualisiereAnzeigeBesch(event.values[0], event.values[1], event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy)
            {

            }
        };

        SensorEventListener sensorEventListenerGyro = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent event)
            {
                //abgreifen der Sensorwerte
                aktualisiereAnzeigeGyro(event.values[0], event.values[1], event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy)
            {

            }
        };
        sensorManager.registerListener(sensorEventListenerBesch, sensBesch, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerGyro, sensGyro, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.besch_start)
        {
            startBesch = true;
        }

        if(v.getId()==R.id.besch_stop)
        {
            startBesch = false;
            tVbeschX.setText("X-Wert");
            tVbeschY.setText("Y-Wert");
            tVbeschZ.setText("Z-Wert");
        }

        if(v.getId()==R.id.gyro_start)
        {
            startGyro = true;
        }

        if(v.getId()==R.id.gyro_stop)
        {
            startGyro = false;
            tVgyroX.setText("X-Wert");
            tVgyroY.setText("Y-Wert");
            tVgyroZ.setText("Z-Wert");
        }

        if(v.getId()==R.id.speicherCsv)
        {

            //Log.i("NumberGenerated", "1 YOYOYO");
            schreibe_In_Csv("Hallo it is a Test");
            //Log.i("NumberGenerated", "2 YOYOYO");
        }
    }

    public void aktualisiereAnzeigeBesch(float v1, float v2, float v3)
    {
        if(startBesch) {
            tVbeschX.setText("" + v1);
            tVbeschY.setText("" + v2);
            tVbeschZ.setText("" + v3);
        }
    }

    public void aktualisiereAnzeigeGyro(float v1, float v2, float v3) {
        if(startGyro) {
            tVgyroX.setText("" + v1);
            tVgyroY.setText("" + v2);
            tVgyroZ.setText("" + v3);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void schreibe_In_Csv(String txt)
    {
        try {
            //String content = "Separe here integers by semi-colon";
            String content = txt;
            //Log.i("NumberGenerated", "5 YOYOYO"+ dir.toString());


            File file = new File( dir, "sensor_Daten.csv");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                //Log.i("NumberGenerated", "3 YOYOYO");
                file.createNewFile();
                //Log.i("NumberGenerated", "4 YOYOYO");
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());

            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
