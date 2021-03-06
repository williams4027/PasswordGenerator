package edu.osu.com.passwordgenerator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import edu.osu.com.passwordgenerator.utility.PasswordDataObject;


public class ShakeActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private  MediaPlayer mp;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private long shakeCount = 1;

    private static final int SHAKE_THRESHOLD = 600;
    private TextView shakeCountText;

    private Button nextStageButton;
    private View thisView = null;

    private PasswordDataObject passwordData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        passwordData = (PasswordDataObject)getIntent().getSerializableExtra("PasswordData");
        System.out.println("Password Length: " + passwordData.getPasswordLength());
        System.out.println("UpperCase Min Count: " + passwordData.getUppercaseCount());
        System.out.println("Number Min Count: " + passwordData.getNumberCount());
        System.out.println("Special Character Min Count: " + passwordData.getSpecialCharacterCount());
        System.out.println("PasswordData: " + passwordData.toString());

        TextView currentPasswordView = (TextView)findViewById(R.id.currentPasswordShakeView);
        currentPasswordView.setText("Current Password: " + passwordData.currentPasswordString());

        nextStageButton = (Button) findViewById(R.id.shakeNextStageButton);
        nextStageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(ShakeActivity.this, SpeechActivity.class);

                passwordData.clearNumberSet();
                assignNumbers(passwordData, shakeCount);

                configIntent.putExtra("PasswordData", passwordData);
                startActivity(configIntent);
            }
        });

        shakeCountText = (TextView) findViewById(R.id.shakeCountText);
        shakeCountText.setText(Long.toString(shakeCount));

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.cant_touch_this);
        mp.start();

        thisView = findViewById(R.id.shakeView);
    }

    private void assignNumbers(PasswordDataObject passwordData, long seedValue) {

        Random randomAccess = new Random(System.currentTimeMillis() * shakeCount) ;
        for ( int numberIndex = 0; numberIndex < passwordData.getNumberCount(); numberIndex++ ){
            // Randomly Choose a Module to get the number appended
            int randomModuleIndex = Math.abs(randomAccess.nextInt()) % passwordData.getWordModuleList().size();

            // Randomly choose a number to append
            char randomNumberChar = Character.forDigit(Math.abs(randomAccess.nextInt() % 10), 10);
            System.out.println("Number Generated: " + randomNumberChar);
            // Assign to the module
            passwordData.getWordModuleList().get(randomModuleIndex).getExtraCharacterList().add(randomNumberChar);
        }
    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
        mp.stop();
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mp.start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    shakeCount++;
                    shakeCountText.setText(Long.toString(shakeCount));

                    // Party time!
                    if (shakeCount % 5 == 0) {
                        thisView.setBackgroundColor(Color.rgb(Math.round(x * last_x) % 256, Math.round(y * last_y) % 256, Math.round(z * last_z) % 256));
                    }
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
