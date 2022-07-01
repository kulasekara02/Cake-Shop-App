package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    private EditText TextUserId, TextPassword, TextConfirmPassword;
    private TextView TextErrorMsg;
    private Spinner SpinnerUserType;
    private Button ButtonRegister;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       // Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        createDatabase();
        TextUserId = (EditText) findViewById(R.id.txtRUserId);
        TextPassword = (EditText) findViewById(R.id.txtRPassowrd);
        TextConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        TextErrorMsg = (TextView) findViewById(R.id.txtRError);
        ButtonRegister = (Button) findViewById(R.id.btnRegister);


        SpinnerUserType = (Spinner) findViewById(R.id.spUserType);
        List<String> list = new ArrayList<>();
        list.add("Admin");
        list.add("User");

        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerUserType.setAdapter(dataAdapter);

        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertIntoDb();
            }
        });
    }


    // protected void createDatabase() {
    //     try {
    //         db = openOrCreateDatabase("85DB", Context.MODE_PRIVATE, null);

    //         db.execSQL("CREATE TABLE IF NOT EXISTS ResgisterUser (UserId VARCHAR PRIMARY KEY  NOT NULL,Password VARCHAR,UserType VARCHAR);");

    //         Toast.makeText(getApplicationContext(), "database created Successfully",
    //                 Toast.LENGTH_LONG).show();
    //     } catch (Exception ex) {
    //         TextErrorMsg.setText("Error creating DB" + ex);

    //     }
    // }


    protected void insertIntoDb() {
        try {

            boolean valid = true;

            if (TextUserId.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "User Name can not be empty", Toast.LENGTH_LONG).show();
                valid = false;
            }
            if (TextPassword.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Password can not be empty", Toast.LENGTH_LONG).show();
                valid = false;
            }
            if (!TextPassword.getText().toString().isEmpty() && TextPassword.getText().toString().length() < 3) {
                Toast.makeText(getApplicationContext(), "Password must have minimum 8 characters", Toast.LENGTH_LONG).show();
                valid = false;
            }
            if (TextConfirmPassword.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Confirm Password can not be empty", Toast.LENGTH_LONG).show();
                valid = false;
            }

            if (!TextPassword.getText().toString().equals(TextConfirmPassword.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Passwords do not match please try again", Toast.LENGTH_LONG).show();
                valid = false;
            }

            if (valid) {
                String UserName = TextUserId.getText().toString().trim();
                String Password = TextPassword.getText().toString().trim();
                String UserType = SpinnerUserType.getSelectedItem().toString().trim();


                String query = "INSERT INTO ResgisterUser values('" + UserName + "','" + Password + "','" + UserType + "');";
                db.execSQL(query);
                Toast.makeText(getApplicationContext(),
                        "database created Successfully", Toast.LENGTH_LONG).show();


            }

        } catch (Exception ex) {
            TextErrorMsg.setText("Error inserting data" + ex);

        }

    }
}