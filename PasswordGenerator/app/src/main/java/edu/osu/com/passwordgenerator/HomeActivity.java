package edu.osu.com.passwordgenerator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class HomeActivity extends ActionBarActivity {

    Context thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button submitButton = (Button)findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(thisContext, PasswordConfigurationActivity.class);
                startActivity(configIntent);
            }
        });
    }
}
