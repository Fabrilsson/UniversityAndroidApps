package com.example.lightsensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor light;
    private Sensor proximity;
    private ConstraintLayout layout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (ConstraintLayout) findViewById(R.id.background);
        imageView = (ImageView) findViewById(R.id.imageView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_LIGHT) {

            float lightLumens = event.values[0];

            layout.setBackgroundResource(R.color.black);

            if (lightLumens > 10000)
                layout.setBackgroundResource(R.color.white);
        }

        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

            float proximityCentimeters = event.values[0];

            Drawable.ConstantState drawable = layout.getBackground().getConstantState();

            Drawable.ConstantState aaa = getResources().getDrawable(R.color.black).getConstantState();

            if (drawable == aaa)
                imageView.setImageResource(R.drawable.ic_baseline_mic_24_white);
            else
                imageView.setImageResource(R.drawable.ic_baseline_mic_24_black);

            if (proximityCentimeters > 6)
                if (drawable == aaa)
                    imageView.setImageResource(R.drawable.ic_baseline_mic_off_24_white);
                else
                    imageView.setImageResource(R.drawable.ic_baseline_mic_off_24_black);
        }

        // Do something with this sensor data.
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}