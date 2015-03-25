package edu.osu.com.passwordgenerator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;


public class PasswordConfigurationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_configuration);

        final LinearLayout numberLayout = (LinearLayout) findViewById(R.id.numberOptions);
        numberLayout.setVisibility(View.INVISIBLE);

        final CheckBox numberCheckbox = (CheckBox) findViewById(R.id.numberCheckbox);
        numberCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberCheckbox.isChecked()){
                    numberLayout.setVisibility(View.VISIBLE);
                } else {
                    numberLayout.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
