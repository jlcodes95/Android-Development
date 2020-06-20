package com.example.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_MAIN";
    private final int DARK_LUX = 51;
    private CustomDrawingSurface drawingSurface;

    //light sensor
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightSensorListener;

    //geomag sensor
    private Sensor magnetSensor;
    private Sensor accelerometorSensor;
    private SensorEventListener geoMagSensorListener;
    private float[] accelerometerData = new float[3];
    private float[] magnetometerData = new float[3];
    private float currentAzimuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Log.d(TAG, "size: (" + size.x + ", " + size.y + ")");

        LinearLayout layout = findViewById(R.id.layoutMain);
        drawingSurface = new CustomDrawingSurface(this, size.x, size.y);

        layout.addView(drawingSurface);

        //configure light sensor
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor != null) {
            // configure the listener
            this.lightSensorListener = this.configureLightListener();
            sensorManager.registerListener(this.lightSensorListener, lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        //configure magnetic sensor
        this.accelerometorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        this.magnetSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (this.accelerometorSensor != null && this.magnetSensor != null) {
            Log.d(TAG, "Able to access geomanetic sensor");
            this.geoMagSensorListener = configureGeoMagListener();
            this.sensorManager.registerListener(this.geoMagSensorListener, accelerometorSensor, SensorManager.SENSOR_DELAY_NORMAL);
            this.sensorManager.registerListener(this.geoMagSensorListener, magnetSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onLockPressed(View view) {
        Log.d(TAG, "onLockPressed");
        drawingSurface.onLockScreen();
    }

    public void onThrowPressed(View view) {
        Log.d(TAG, "onThrowPressed");
        float degrees = (float)Math.toDegrees(currentAzimuth);
        if (degrees < 0.0) {
            degrees += 360.0f;
        }
        drawingSurface.onThrow(degrees);
    }

    public void onSweepPressed(View view) {
        Log.d(TAG, "onSweepPressed");
        drawingSurface.onSweep();
    }

    // This function gets run when user switches from the game to some other app on the phone
    @Override
    protected void onPause() {
        super.onPause();

        // Pause the game
        drawingSurface.pauseGame();
    }

    // This function gets run when user comes back to the game
    @Override
    protected void onResume() {
        super.onResume();

        // Start the game
        drawingSurface.startGame();
    }

    public SensorEventListener configureLightListener() {

        return new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float lightLevel = sensorEvent.values[0];
                Log.d(TAG, "The light level in lux is: " + lightLevel);
                if (lightLevel <= DARK_LUX) {
                    drawingSurface.erase();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
    }

    public SensorEventListener configureGeoMagListener() {
        return new SensorEventListener() {
            public void onSensorChanged(SensorEvent event) {
                // 1. check which sensor the data is coming from
                int sensorType = event.sensor.getType();

                if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                    accelerometerData = event.values.clone();  // float array of 3 items
                }else if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
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
                    currentAzimuth = orientationValues[0];
                    // output it to the screen!
                    Log.d(TAG, "azimuth: " + currentAzimuth);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

}
