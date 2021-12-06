package com.example.smartgesture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //private Spinner spinner;
    private static final String[] paths = {"Select Gesture", "Turn on lights", "Turn off lights",
            "Turn on fan", "Turn off fan", "Increase fan speed",
            "Decrease fan speed", "Set Thermostat to specified temperature",
            "zero","one","two","three","four","five","six","seven","eight","nine"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Function to check and request read storage permission
        final int READ_STORAGE_PERMISSION_CODE = 101;
        final int WRITE_STORAGE_PERMISSION_CODE = 102;
        final int CAMERA_PERMISSION_CODE = 103;
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_PERMISSION_CODE);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_STORAGE_PERMISSION_CODE);
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        openScreen2(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    public void openScreen2(int position) {
        if(position!=0) {
            Intent screen2Intent = new Intent(this, expertgesture.class);
            //screen2Intent.putExtra("spinnerValue",spinner.getSelectedItem().toString());
            screen2Intent.putExtra("spinnerValue",position);
            startActivity(screen2Intent);
        }
    }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            //Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

        } /*else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }*/
    }

}