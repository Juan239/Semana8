package com.example.semana8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class activitySensor extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor rotationVectorSensor;
    private TextView xTextView, yTextView, zTextView, orientacion;
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        xTextView = findViewById(R.id.txtEjeX);
        yTextView = findViewById(R.id.txtEjeY);
        zTextView = findViewById(R.id.txtEjeZ);
        orientacion = findViewById(R.id.txtOrientacion);
        imagen = findViewById(R.id.imagenRotacion);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float rotationVectorX = event.values[0];
            float rotationVectorY = event.values[1];
            float rotationVectorZ = event.values[2];



            // Obtener la matriz de rotación a partir de los valores del sensor.
            float[] rotationMatrix = new float[9];
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

            // Obtener los ángulos de Euler (azimuth, pitch, roll) a partir de la matriz de rotación.
            float[] orientationValues = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientationValues);

            // Los ángulos de Euler están en radianes, conviértelos a grados.
            float azimuthInDegrees = (float) Math.toDegrees(orientationValues[0]);
            float pitchInDegrees = (float) Math.toDegrees(orientationValues[1]);
            float rollInDegrees = (float) Math.toDegrees(orientationValues[2]);

            xTextView.setText("X: " + azimuthInDegrees);
            yTextView.setText("Y: " + pitchInDegrees);
            zTextView.setText("Z: " + rollInDegrees);


            if(rollInDegrees>0 && rollInDegrees < 45){
                orientacion.setText("Posición normal");
                imagen.setRotation(0);
            } else if (rollInDegrees<0 && rollInDegrees > -45) {
                orientacion.setText("Posición normal");
                imagen.setRotation(0);
            } else if (rollInDegrees>=45 && rollInDegrees <= 90) {
                orientacion.setText("Inclinado a la derecha");
                imagen.setRotation(-90);
            } else if (rollInDegrees<-45 && rollInDegrees > -90) {
                orientacion.setText("Inclinado a la izquierda");
                imagen.setRotation(90);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario implementar esto en este caso.
    }
}