package edu.osu.com.passwordgenerator;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import edu.osu.com.passwordgenerator.utility.PasswordDataObject;


public class LightActivity extends ActionBarActivity {

    private SensorManager lightSensorManager;
    private Sensor lightSensor;

    private TextView textLIGHT_reading, textLIGHT_max;
    private ProgressBar lightMeter;

    private Button nextStageButton;

    private PasswordDataObject passwordData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        passwordData = (PasswordDataObject) getIntent().getSerializableExtra("PasswordData");
        System.out.println("Password Length: " + passwordData.getPasswordLength());
        System.out.println("UpperCase Min Count: " + passwordData.getUppercaseCount());
        System.out.println("Number Min Count: " + passwordData.getNumberCount());
        System.out.println("Special Character Min Count: " + passwordData.getSpecialCharacterCount());

        nextStageButton = (Button) findViewById(R.id.lightNextStageButton);
        nextStageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(LightActivity.this, ResultActivity.class);
                configIntent.putExtra("PasswordData", passwordData);
                startActivity(configIntent);
            }
        });

        lightSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = lightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        lightMeter = (ProgressBar) findViewById(R.id.lightMeter);
        textLIGHT_max = (TextView) findViewById(R.id.max);
        textLIGHT_reading = (TextView) findViewById(R.id.reading);

        if(lightSensor == null) {
            Toast.makeText(LightActivity.this,
                    "Opps! Your device doesn't support Light Sensor",
                    Toast.LENGTH_LONG).show();
        } else {
            float max = lightSensor.getMaximumRange();
            lightMeter.setMax((int) max);
            textLIGHT_max.setText("Max Reading: " + String.valueOf(max));

            lightSensorManager.registerListener(lightSensorEventListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    SensorEventListener lightSensorEventListener = new SensorEventListener() {

        @Override
        public final void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Do something here if sensor accuracy changes.
        }

        @Override
        public final void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                float currentReading = event.values[0];
                lightMeter.setProgress((int) currentReading);
                textLIGHT_reading.setText("Current Reading: " + String.valueOf(currentReading));
            }
        }

    };

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        lightSensorManager.registerListener(lightSensorEventListener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        lightSensorManager.unregisterListener(lightSensorEventListener);
    }



}
