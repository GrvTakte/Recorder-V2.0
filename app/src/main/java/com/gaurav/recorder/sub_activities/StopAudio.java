package com.gaurav.recorder.sub_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gaurav.recorder.MainActivity;
import com.gaurav.recorder.R;
import com.gaurav.recorder.RecorderService;

/**
 * Created by gaurav on 07/08/17.
 */

public class StopAudio extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_recording_audio);

        Button stopAudio = (Button) findViewById(R.id.audio_stop);

        stopAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Audio Recording stopped",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StopAudio.this, RecorderService.class);
                stopService(intent);
                Intent intent1 = new Intent(StopAudio.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
