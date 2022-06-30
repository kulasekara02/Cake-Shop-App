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

public class AddProduct extends AppCompatActivity {


    private EditText TextProductId, TextProductName, TextPrice, TextQty;
    private TextView TextErrorMsg;
    private Button ButtonAddProduct;
    private Spinner SpinnerCategory;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
      //  Toolbar toolbar = findViewById(R.id.toolbar);
    //    setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        createDatabase();
        TextProductId = (EditText) findViewById(R.id.txtHProductId);
        TextProductName = (EditText) findViewById(R.id.txtHProductName);
        TextPrice = (EditText) findViewById(R.id.txtHProdcutPrice);
        TextQty = (EditText) findViewById(R.id.txtHQty);
        TextErrorMsg = (TextView) findViewById(R.id.txtHpError);


        SpinnerCategory = (Spinner) findViewById(R.id.spHCategory);
        List<String> list = new ArrayList<>();
        list.add("Pound Cake");
        list.add("Red Velvet Cake");
        list.add("Carrot Cake");
        list.add("Sponge Cake");
        list.add("Genoise Cake");
        list.add("Chiffon Cake");


        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCategory.setAdapter(dataAdapter);

        ButtonAddProduct = (Button) findViewById(R.id.btnHAddprodcut);
        ButtonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertIntoDb();
            }
        });

    }


    protected void createDatabase() {
        try {
            db = openOrCreateDatabase("85DB", Context.MODE_PRIVATE, null);

            db.execSQL("CREATE TABLE IF NOT EXISTS Products (ProdctId VARCHAR PRIMARY KEY  NOT NULL,ProdcutName VARCHAR,Category VARCHAR,Price int,Qty int);");

            Toast.makeText(getApplicationContext(), "database created Successfully", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            TextErrorMsg.setText("Error creating DB" + ex);

        }
    }

    protected void insertIntoDb() {
        try {

            boolean valid = true;

            if (TextProductId.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Product id can not be empty", Toast.LENGTH_LONG).show();
                valid = false;
            }
            if (TextProductName.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Product name can not be empty", Toast.LENGTH_LONG).show();
                valid = false;
            }
            if (TextPrice.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Product price can not be empty", Toast.LENGTH_LONG).show();
                valid = false;
            }
            if (TextQty.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Product quantity  can not  be empty", Toast.LENGTH_LONG).show();
                valid = false;
            }

            if (valid) {
                String ProductId = TextProductId.getText().toString().trim();
                String ProductName = TextProductName.getText().toString().trim();
                String Price = TextPrice.getText().toString().trim();
                String Qty = TextQty.getText().toString().trim();
                String Category = SpinnerCategory.getSelectedItem().toString().trim();

                String query = "INSERT INTO Products values('" + ProductId + "','" + ProductName + "','" + Category + "','" + Price + "','" + Qty + "');";
                db.execSQL(query);
                Toast.makeText(getApplicationContext(),
                        "Dtata Entered Successfully", Toast.LENGTH_LONG).show();

            }

        } catch (Exception ex) {
            TextErrorMsg.setText("Error inserting data" + ex);
        }
    }
}