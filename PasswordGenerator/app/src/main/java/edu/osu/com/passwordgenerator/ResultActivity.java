package edu.osu.com.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import edu.osu.com.passwordgenerator.utility.PasswordDataObject;


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

        String finalPassword = passwordData.currentPasswordString();

        resultTextView = (TextView)findViewById(R.id.passwordResultTextView);
        resultTextView.setText(finalPassword);
        System.out.println("Final Password: " + finalPassword);
        resultTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("password", resultTextView.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Password Copied to Clipboard",Toast.LENGTH_SHORT).show();
            }
        });

        Button newPasswordButton = (Button)findViewById(R.id.newPasswordButton);
        newPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, PasswordConfigurationActivity.class);
                startActivity(intent);
            }
        });

        Button quitButton = (Button)findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Button changeReminderButton = (Button)findViewById(R.id.changeReminderButton);
        changeReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", true);
                intent.putExtra("rrule", "FREQ=MONTHLY");
                intent.putExtra("title", "Change Password for Application: ");
                startActivity(intent);
            }
        });
    }
}
