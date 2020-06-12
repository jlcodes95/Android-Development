package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_MAIN";

    TextView tvLightLevel;
    TextView tvAzimuth;
    TextView tvPitch;
    TextView tvRoll;
    RelativeLayout defaultLayout;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accelerometorSensor;
    private Sensor magnetSensor;
    private SensorEventListener lightSensorListener;
    private SensorEventListener geoMagSensorListener;
    float[] accelerometerData = new float[3];
    float[] magnetometerData = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate views
        this.tvLightLevel = (TextView) findViewById(R.id.tvLightLevel);
        this.tvAzimuth = (TextView) findViewById(R.id.tvAzimuth);
        this.tvRoll = (TextView) findViewById(R.id.tvRoll);
        this.tvPitch = (TextView) findViewById(R.id.tvPitch);

        this.defaultLayout = (RelativeLayout) findViewById(R.id.lay_default);

        //configure sensor manager
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //1. check what sensors are available
        List<Sensor> sensorsOnDevice = this.sensorManager.getSensorList(Sensor.TYPE_ALL);

        //2. loop through the list and output what sensors are available
        for (Sensor sensor: sensorsOnDevice) {
            Log.d(TAG, "Sensor: " + sensor.getName());
        }

        //3. configure the manager and sensor
//        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //4. if sensor is available then
        if (this.lightSensor == null) {
            Log.d(TAG, "light sensor not available or inaccessable");
            return;
        }else {
            Log.d(TAG, "Able to access the light sensor");
            //configure lightSensor variable
            this.lightSensorListener = configureLightListener();
            //do something
        }


        this.accelerometorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        this.magnetSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (this.accelerometorSensor != null && this.magnetSensor != null) {
            Log.d(TAG, "Able to access geomanetic sensor");
            this.geoMagSensorListener = configureGeoMagListener();
            this.sensorManager.registerListener(this.geoMagSensorListener, accelerometorSensor, SensorManager.SENSOR_DELAY_NORMAL);
            this.sensorManager.registerListener(this.geoMagSensorListener, magnetSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Log.d(TAG, "Unable to access geomanetic sensor");
            return;
        }

    }

    public SensorEventListener configureLightListener() {
        return new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //inside listener, wait for sensor to send data & do something
                float lightLevel = event.values[0];
                Log.d(TAG, "Light level is: " + lightLevel);
                tvLightLevel.setText("Light level is: " + lightLevel);
                if (lightLevel < 401) {
                    defaultLayout.setBackgroundColor(Color.parseColor("#FF00FF"));
                    tvLightLevel.setTextColor(Color.parseColor("#FFFFFF"));
                }else {
                    defaultLayout.setBackgroundColor(Color.parseColor("#FFFF00"));
                    tvLightLevel.setTextColor(Color.parseColor("#000000"));
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // i.e. often in location services (wifi -> gps)
            }
        };
    }

    public SensorEventListener configureGeoMagListener() {
        return new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                // 1. check which sensor the data is coming from
                int sensorType = event.sensor.getType();

                if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                    // data is coming from accelerometer
                    // - clone() creates a copy of teh data
                    // - we could do: accelerometerData = sensorEvent.values
                    // But the sensor tends to update data faster than you can save and process it
                    // Thus, its better to make a copy than to just use whatever was pulled from the sensor.
                    accelerometerData = event.values.clone();  // float array of 3 items
                }
                else if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
                    magnetometerData = event.values.clone();
                }


                // figure out the orientation of the phone
                float[] rotationMatrix = new float[9];
                boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magnetometerData);


                // if the device actually rotated, then rotationOK = true
                // otherwise, rotationOK = false;
                float orientationValues[] = new float[3];
                if (rotationOK == true) {
                    // returns the "angle" or "tilt" of the device
                    SensorManager.getOrientation(rotationMatrix, orientationValues);


                    // azmithu
                    float azimuth = orientationValues[0];
                    // pitch
                    float pitch = orientationValues[1];
                    // roll
                    float roll = orientationValues[2];


                    // output it to the screen!
                    tvAzimuth.setText("Azimuth: " +  azimuth);
                    tvPitch.setText("Pitch: " + pitch);
                    tvRoll.setText("Roll: " + roll);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void enableLightSensorPressed(View view) {
        Log.d(TAG, "Enable sensor button pressed");
        if (this.lightSensor != null) {
            this.sensorManager.registerListener(this.lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void disableLightSensorPressed(View view) {
        Log.d(TAG, "Disable sensor button pressed");
        if (this.lightSensor != null) {
            this.sensorManager.unregisterListener(this.lightSensorListener);
        }
    }
}
