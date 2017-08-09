package com.gaurav.recorder.sub_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gaurav.recorder.CameraService;
import com.gaurav.recorder.MainActivity;
import com.gaurav.recorder.R;

/**
 * Created by gaurav on 07/08/17.
 */

public class StopVideo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_recording_video);

        final Button stopVideo = (Button) findViewById(R.id.video_stop);

        stopVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StopVideo.this, "Video Recording stopped", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StopVideo.this, CameraService.class);
                stopService(intent);
                Intent intent1 = new Intent(StopVideo.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
