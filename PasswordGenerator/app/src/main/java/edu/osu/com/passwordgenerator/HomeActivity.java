package edu.osu.com.passwordgenerator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HomeActivity extends ActionBarActivity {

    Context thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView label = (TextView) findViewById(R.id.homeLabel);
        Log.d("DEBUG", label.getText().toString());
        label.setText("Howdy");

        Button submitButton = (Button)findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(thisContext, ShakeActivity.class);
                startActivity(configIntent);
            }
        });
    }
}
