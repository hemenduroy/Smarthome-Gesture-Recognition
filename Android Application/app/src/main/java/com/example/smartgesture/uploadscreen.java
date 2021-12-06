package com.example.smartgesture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class uploadscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadscreen);

        Button confirmUploadbutton = findViewById(R.id.button2);
        TextView fileListBox = findViewById(R.id.textView4);
        ArrayList<String> fileList = (ArrayList<String>) getIntent().getExtras().getSerializable("fileListArray");

        String allFilesList = "";
        for(int i=0;i<fileList.size() && i<3;i++) {
            allFilesList = allFilesList + fileList.get(i) + "\n";
        }
        fileListBox.setText(allFilesList);
        confirmUploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadMultipleFiles(fileList);
                finish();
            }
        });

    }
    private void uploadMultipleFiles(ArrayList<String> fileList) {
        String videoPath = Environment.getExternalStorageDirectory().getPath();
        System.out.println(Environment.getExternalStorageDirectory().getPath());
        System.out.println(fileList.get(0).replace("/external_files/",""));
        File video1 = new File(videoPath, fileList.get(0).replace("/external_files/",""));
        File video2 = new File(videoPath, fileList.get(1).replace("/external_files/",""));
        File video3 = new File(videoPath, fileList.get(2).replace("/external_files/",""));

        MultipartBody.Part videoUpload1 = MultipartBody.Part.createFormData(video1.getName(),
                video1.getName(), RequestBody.create(MediaType.parse("*/*"), video1));
        MultipartBody.Part videoUpload2 = MultipartBody.Part.createFormData(video2.getName(),
                video2.getName(), RequestBody.create(MediaType.parse("*/*"), video2));
        MultipartBody.Part videoUpload3 = MultipartBody.Part.createFormData(video3.getName(),
                video3.getName(), RequestBody.create(MediaType.parse("*/*"), video3));
        ConfigApi getResponse = ConfigFile.getRetrofit().create(ConfigApi.class);
        Call<FlaskResponse> apicall = getResponse.uploadMulFile(videoUpload1, videoUpload2, videoUpload3);
        apicall.enqueue(new Callback<FlaskResponse>() {
            @Override
            public void onResponse(Call<FlaskResponse> call, Response<FlaskResponse> response) {
                FlaskResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    System.out.println("Response"+ serverResponse.toString());
                }
                //startActivity(mainActivity);


            }

            @Override
            public void onFailure(Call<FlaskResponse> call, Throwable t) {
                //startActivity(mainActivity);
            }
        });
    }
}