package com.example.smartgesture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

public class expertgesture extends AppCompatActivity {
    String gestureName = null;
    ArrayList<String> fileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expertgesture);

        //TextView gestureLabel = findViewById(R.id.textView2);
        VideoView gesturedemo = findViewById(R.id.videoView);
        ImageButton replayButton = findViewById(R.id.imageButton);
        Button practiceButton = findViewById(R.id.button);
        Button uploadScreenButton = findViewById(R.id.button3);


        int gestureID = (Integer) getIntent().getExtras().getSerializable("spinnerValue");
        System.out.println("gesture ID is : " + gestureID);

        String videoFileName = null;
        switch (gestureID) {
            case 1: {
                gestureName = "Turn on lights";
                videoFileName = "hlighton";
            }break;
            case 2: {
                gestureName = "Turn off lights";
                videoFileName = "hlightoff";
            }break;
            case 3: {
                gestureName = "Turn on fan";
                videoFileName = "hfanon";
            }break;
            case 4: {
                gestureName = "Turn off fan";
                videoFileName = "hfanoff";
            }break;
            case 5: {
                gestureName = "Increase fan speed";
                videoFileName = "hincreasefanspeed";
            }break;
            case 6: {
                gestureName = "Decrease fan speed";
                videoFileName = "hdecreasefanspeed";
            }break;
            case 7: {
                gestureName = "Set Thermostat to specified temperature";
                videoFileName = "hsetthermo";
            }break;
            case 8: {
                gestureName = "zero";
                videoFileName = "h0";
            }break;
            case 9: {
                gestureName = "one";
                videoFileName = "h1";
            }break;
            case 10: {
                gestureName = "two";
                videoFileName = "h2";
            }break;
            case 11: {
                gestureName = "three";
                videoFileName = "h3";
            }break;
            case 12: {
                gestureName = "four";
                videoFileName = "h4";
            }break;
            case 13: {
                gestureName = "five";
                videoFileName = "h5";
            }break;
            case 14: {
                gestureName = "six";
                videoFileName = "h6";
            }break;
            case 15: {
                gestureName = "seven";
                videoFileName = "h7";
            }break;
            case 16: {
                gestureName = "eight";
                videoFileName = "h8";
            }break;
            case 17: {
                gestureName = "nine";
                videoFileName = "h9";
            }break;
            /*default: {
                gestureName = "Turn on lights";
                videoFileName = "/H-LightOn.mp4";
            }
                break;*/
        }
        //Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/H-LightOn.mp4");
        //gestureLabel.setText(gestureName);
        /*//From sd card
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + videoFileName));
        gesturedemo.setVideoURI(uri);*/
        //From res/raw folder
        int rawId = getResources().getIdentifier(videoFileName,  "raw", getPackageName());
        String path = "android.resource://" + getPackageName() + "/" + rawId;
        gesturedemo.setVideoURI(Uri.parse(path));

        gesturedemo.start();

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gesturedemo.start();
            }
        });

        practiceButton.setOnClickListener(new View.OnClickListener() {
            int practice_number=0;
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,5);
                cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                //front cam
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                //cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
                //cameraIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                //cameraIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
                //front cam
                fileList.add(getOutputMediaFileUri(gestureName,practice_number).getPath());
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri(gestureName,practice_number));
                practice_number++;
                practice_number=practice_number%3;
                startActivityForResult(cameraIntent,1);
                
            }
        });

        uploadScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScreen3(fileList);
                finish();
            }
        });
    }

    public Uri getOutputMediaFileUri(String name, int pracno) {
        //return Uri.fromFile(getOutputMediaFile(type));
        return FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", getOutputMediaFile(name,pracno));

    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(String name, int pracno) {

        // External sdcard location
        File mediaStorageDir = new File (Environment.getExternalStorageDirectory().getPath());
        // Create a media file name
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + name.replace(" ","_") + "_PRACTICE_" + pracno + "_roy.mp4");
        System.out.println("vid path " + mediaFile.getPath());

        return mediaFile;
    }

    public void openScreen3(ArrayList<String> fileList) {
            Intent screen3Intent = new Intent(this, uploadscreen.class);
            //screen2Intent.putExtra("spinnerValue",spinner.getSelectedItem().toString());
            screen3Intent.putExtra("fileListArray",fileList);
            startActivity(screen3Intent);
        }
    }
