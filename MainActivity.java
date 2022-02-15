package com.example.torch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    ToggleButton btntorch;
    private static final int CAMERA_REQUEST = 123;
    boolean CameraFlash = false;
    LinearLayout picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        CameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        picture=findViewById(R.id.pic);
        btntorch = findViewById(R.id.btn);
        btntorch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (CameraFlash)
                {
                    if (btntorch.getText().toString().contains("OFF"))
                    {
                        btntorch.setText("ON");

                        torchoff();
                        picture.setBackgroundResource(R.drawable.lightoff);
                        btntorch.setBackgroundResource(R.drawable.off);
                    }
                    else
                    {
                        btntorch.setText("OFF");
                        torchon();
                        picture.setBackgroundResource(R.drawable.lighton);
                        btntorch.setBackgroundResource(R.drawable.on1);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No Torch Available on Your Device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void torchon() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String CameraID = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(CameraID, true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void torchoff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String CameraID = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(CameraID, false);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

}