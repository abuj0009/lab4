package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    Button button;
    Switch aSwitch;
    EditText editText;
    Button loginButton;
    Button chatButton;
    SharedPreferences prefs;
    ImageButton imgBtn;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("EmailFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        editText = findViewById(R.id.editText2);
        editText.setText(prefs.getString("emailId","pppp"));

        loginButton = (Button)findViewById(R.id.button2);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onPause();

            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MainActivity", "In function: onCreate");

        password = findViewById(R.id.editText3);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("emailId", editText.getText().toString());
        edit.commit();
        Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
        goToProfile.putExtra("EMAIL",editText.getText().toString());
        startActivity(goToProfile);

    }

}