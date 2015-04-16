package edu.osu.com.passwordgenerator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import edu.osu.com.passwordgenerator.utility.PasswordDataObject;
import edu.osu.com.passwordgenerator.utility.WordModule;


public class ResultActivity extends ActionBarActivity {

    private PasswordDataObject passwordData;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        passwordData = (PasswordDataObject)getIntent().getSerializableExtra("PasswordData");
        System.out.println("Password Length: " + passwordData.getPasswordLength());
        System.out.println("UpperCase Min Count: " + passwordData.getUppercaseCount());
        System.out.println("Number Min Count: " + passwordData.getNumberCount());
        System.out.println("Special Character Min Count: " + passwordData.getSpecialCharacterCount());
        System.out.println("PasswordData: " + passwordData.toString());

        String finalPassword = "";
        for (WordModule currentWordModule : passwordData.getWordModuleList()){
            finalPassword += currentWordModule.getWord();
            for (Character currentChar : currentWordModule.getExtraCharacterSet()){
                finalPassword += currentChar;
            }
        }

        resultTextView = (TextView)findViewById(R.id.passwordResultTextView);
        resultTextView.setText(finalPassword);
    }
}
