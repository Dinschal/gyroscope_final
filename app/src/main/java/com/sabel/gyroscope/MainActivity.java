package com.sabel.gyroscope;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView tv_textX, tv_textY, tv_textZ;
    SensorManager sensorManager;
    Sensor sensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gibt alle verfügbaren Systemservices zurück, daher Diensttyp mit angeben
        //weitere SystemServices : Audiodienste, Standortdienste, Alarmdienste
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        tv_textX = findViewById(R.id.tv_textx);
        tv_textY = findViewById(R.id.tv_texty);
        tv_textY = findViewById(R.id.tv_textz);
    }

    SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //Gibt Veränderungen im Event zurück
            // sensorEvent.values => Gibt die Messewerte des deklaierten Sensors wieder
            // hier in Rad/s (Umdrehung/s) also die Winkelgeschwindigkeit mit den einzelnen Achsen
            // 0 = x-Achse, 1 = y-Achse, 2 = z-Achse
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            tv_textX.setText("X-Achse: " + (int) x + " rad/s"); //gecastet auf int, Veränderung gibt nur eine einstellige Zahl zurück
            tv_textY.setText("Y-Achse: " + String.format("%.3d", y) + " rad/s"); //Fließkommazahl begrenzt auf 3-Stellen nach dem Komma
            tv_textZ.setText("Z-Achse: " + z + " rad/s"); //Fließkommazahl wird zurückgegeben
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            //Methode zur Überwachung von Veränderungen am Sensor selbst
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, sensor, sensorManager.SENSOR_DELAY_NORMAL);
        //SENSOR_DELAY_ = Aktualisierungsrate der Anwendung
        //Normal = 200.000 Microsekunden
        //Game = 20.000 Microsekunden (für Spiele noch angenehm)
        //UI = 60.000 Microsekunden
        // Fastest = 0 Microsekunden
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gyroListener);
    }
}
