package edu.osu.com.passwordgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.osu.com.passwordgenerator.utility.PasswordDataObject;
import edu.osu.com.passwordgenerator.utility.WordModule;


public class PasswordConfigurationActivity extends ActionBarActivity {

    private static final int MIN_WORD_LENGTH = 3;
    private SeekBar passwordLengthSeekbar;
    private NumberPicker minimumUppercasePicker;
    private NumberPicker minimumNumberPicker;
    private NumberPicker minimumSpecialCharPicker;
    private TextView seekBarValue;
    private Button nextStageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_configuration);

        passwordLengthSeekbar = (SeekBar) findViewById(R.id.passwordLengthSeekbar);
        seekBarValue = (TextView) findViewById(R.id.seekbarValue);

        passwordLengthSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        minimumUppercasePicker = (NumberPicker) findViewById(R.id.minUpperPicker);
        minimumUppercasePicker.setMinValue(0);
        minimumUppercasePicker.setMaxValue(32);

        minimumNumberPicker = (NumberPicker) findViewById(R.id.minNumberPicker);
        minimumNumberPicker.setMinValue(0);
        minimumNumberPicker.setMaxValue(32);

        minimumSpecialCharPicker = (NumberPicker) findViewById(R.id.minSspecialCharPicker);
        minimumSpecialCharPicker.setMinValue(0);
        minimumSpecialCharPicker.setMaxValue(32);

        nextStageButton = (Button) findViewById(R.id.passwordConfigNextStageButton);
        nextStageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateFields()) {
                    PasswordDataObject passwordData = new PasswordDataObject();
                    passwordData.setPasswordLength(passwordLengthSeekbar.getProgress());
                    passwordData.setUppercaseCount(minimumUppercasePicker.getValue());
                    passwordData.setNumberCount(minimumNumberPicker.getValue());
                    passwordData.setSpecialCharacterCount(minimumSpecialCharPicker.getValue());

                    assignInitialWords(passwordData);

                    Intent configIntent = new Intent(PasswordConfigurationActivity.this, ShakeActivity.class);
                    configIntent.putExtra("PasswordData", passwordData);
                    startActivity(configIntent);
                }
            }
        });
    }

    private boolean validateFields() {
        int passwordLength = passwordLengthSeekbar.getProgress();
        int uppercaseMin = minimumUppercasePicker.getValue();
        int numberMin = minimumNumberPicker.getValue();
        int specialCharMin = minimumSpecialCharPicker.getValue();

        boolean fieldsValid = true;
        if ((uppercaseMin + numberMin + specialCharMin) > passwordLength) {
            Toast.makeText(getApplicationContext(), "The number of uppercase, minimum numbers, and special characters cannot be more than the password length.", Toast.LENGTH_LONG).show();
            fieldsValid = false;
        }
        if ((passwordLength - numberMin - specialCharMin) < MIN_WORD_LENGTH) {
            Toast.makeText(getApplicationContext(), "The password length must be able to create words of at least " + MIN_WORD_LENGTH + " letters after removing the minimum numbers and special character count.", Toast.LENGTH_LONG).show();
            fieldsValid = false;
        }
        return fieldsValid;
    }

    private void assignInitialWords(PasswordDataObject passwordData) {
        BufferedReader dictionaryBufferedReader = new BufferedReader(new InputStreamReader(this.getApplicationContext().getResources().openRawResource(R.raw.words)));

        Map<Integer, List<String>> dictionaryWordMap = new HashMap<>();
        String currentLine = null;
        try {
            while ((currentLine = dictionaryBufferedReader.readLine()) != null) {
                if (currentLine != null && currentLine.length() > MIN_WORD_LENGTH) {
                    int wordLength = currentLine.length();
                    List<String> tempWordLengthList = null;
                    if (dictionaryWordMap.get(wordLength) == null) {
                        tempWordLengthList = new ArrayList<>();
                    } else {
                        tempWordLengthList = dictionaryWordMap.get(wordLength);
                    }
                    tempWordLengthList.add(currentLine);

                    // Place the map back in
                    dictionaryWordMap.put(wordLength, tempWordLengthList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Here");
        System.out.println(dictionaryWordMap.toString());

        int passwordLength = passwordLengthSeekbar.getProgress();
        int numberMin = minimumNumberPicker.getValue();
        int specialCharMin = minimumSpecialCharPicker.getValue();

        int remainingCharacters = passwordLength - numberMin - specialCharMin;
        Random randomAccess = new Random(System.currentTimeMillis());
        while (remainingCharacters > 0) {
            int randomWordLength = ( Math.abs(randomAccess.nextInt()) % remainingCharacters );
            if (randomWordLength < MIN_WORD_LENGTH){
                randomWordLength = remainingCharacters;
            }
            List<String> randomSizedWordList = dictionaryWordMap.get(randomWordLength);

            // There exists words that match this size
            if (randomSizedWordList != null) {
                remainingCharacters = remainingCharacters - randomWordLength;
                int randomWordIndex = Math.abs(randomAccess.nextInt()) % randomSizedWordList.size();
                String randomWord = randomSizedWordList.get(randomWordIndex);
                WordModule currentWordModule = new WordModule(randomWord);
                passwordData.addWordModule(currentWordModule);
            }
        }

        System.out.println(passwordData.getWordModuleList().toString());
    }
}
