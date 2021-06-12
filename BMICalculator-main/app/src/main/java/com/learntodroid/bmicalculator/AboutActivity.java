package com.learntodroid.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private Button returnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setupHyperlink();
        //final SharedPreferences sharedPref = this.getSharedPreferences("Previous BMI", Context.MODE_PRIVATE);
        //sharedPref.getString("Previous BMI", "Error");

        returnButton = (Button) findViewById(R.id.returnbutton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutReturn();
                //double bmi = 0;
                //SharedPreferences.Editor editor = sharedPref.edit();
                //editor.putInt("Previous BMI", bmi);
                //String b = Double.toString(bmi);
               // editor.putString("Previous BMI", b);
                //editor.commit();
            }
        });
    }

    private void setupHyperlink() {
        TextView linkTextView = findViewById(R.id.about_main_link);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void openAboutReturn(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

