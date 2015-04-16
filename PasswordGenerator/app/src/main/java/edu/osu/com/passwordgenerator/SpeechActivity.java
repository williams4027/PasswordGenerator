package edu.osu.com.passwordgenerator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import edu.osu.com.passwordgenerator.utility.PasswordDataObject;


public class SpeechActivity extends ActionBarActivity {

    protected static final int SPEECH_SEED = 1;

    private ImageButton btnAnswerSpeak;
    private TextView txtResult;

    // List of supported special characters
    private char[] specialCharacterList = new char[]{
       '@', '%', '+', '\\', '/', '\'', '!', '#', '$', '^', '?', ':', ',',
            '(', ')', '{', '}', '[', ']', '~', '-', '_'
    };

    private PasswordDataObject passwordData;
    private Button nextStageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        passwordData = (PasswordDataObject)getIntent().getSerializableExtra("PasswordData");
        System.out.println("Password Length: " + passwordData.getPasswordLength());
        System.out.println("UpperCase Min Count: " + passwordData.getUppercaseCount());
        System.out.println("Number Min Count: " + passwordData.getNumberCount());
        System.out.println("Special Character Min Count: " + passwordData.getSpecialCharacterCount());
        System.out.println("PasswordData: " + passwordData.toString());

        TextView currentPasswordView = (TextView)findViewById(R.id.currentPasswordSpeechView);
        currentPasswordView.setText("Current Password: " + passwordData.currentPasswordString());

        nextStageButton = (Button) findViewById(R.id.speechNextStageButton);
        nextStageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(SpeechActivity.this, ResultActivity.class);

                passwordData.clearCharacterSet();
                assignSpecialCharacters(passwordData, txtResult.getText().hashCode());

                configIntent.putExtra("PasswordData", passwordData);
                startActivity(configIntent);
            }
        });

        txtResult = (TextView) findViewById(R.id.resultText);
        btnAnswerSpeak = (ImageButton) findViewById(R.id.micButton);

        btnAnswerSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                try {
                    startActivityForResult(intent, SPEECH_SEED);
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    private void assignSpecialCharacters(PasswordDataObject passwordData, int seedValue) {

        Random randomAccess = new Random(System.currentTimeMillis() * seedValue) ;
        for ( int numberIndex = 0; numberIndex < passwordData.getSpecialCharacterCount(); numberIndex++ ){
            // Randomly Choose a Module to get the number appended
            int randomModuleIndex = Math.abs(randomAccess.nextInt()) % passwordData.getWordModuleList().size();

            // Randomly choose a special character to append
            char randomSpecialChar = specialCharacterList[Math.abs(randomAccess.nextInt()) % specialCharacterList.length];

            // Assign to the module
            passwordData.getWordModuleList().get(randomModuleIndex).getExtraCharacterList().add(randomSpecialChar);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SPEECH_SEED: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txtResult.setText(text.get(0));
                }
                break;
            }
        }
    }

    protected void onResume() {
        super.onResume();
    }
}