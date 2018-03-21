package com.sabel.gyroscope;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity{

    TextView tv_textx, tv_texty, tv_textz;
    SensorManager sensorManager;
    Sensor sensor;

    public SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            tv_textx.setText("X-Achse: " + (int)x + " rad/s");
            tv_texty.setText("Y-Achse: " + (int)y + " rad/s");
            tv_textz.setText("Z-Achse: " + (int)z + " rad/s");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int acc) {

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        tv_textx = (TextView)findViewById(R.id.tv_textx);
        tv_texty = (TextView)findViewById(R.id.tv_texty);
        tv_textz = (TextView)findViewById(R.id.tv_textz);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gyroListener);
    }
}