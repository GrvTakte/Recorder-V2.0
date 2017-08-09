package com.gaurav.recorder;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.gaurav.recorder.sub_activities.AboutUs;
import com.gaurav.recorder.sub_activities.StopAudio;
import com.gaurav.recorder.sub_activities.StopVideo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SYSTEM_ALERT_WINDOW;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, NavigationView.OnNavigationItemSelectedListener{

    public static SurfaceView mSurfaceView;
    public static SurfaceHolder mSurfaceHolder;
    public static final int RequestPermissionCode = 1;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private Toolbar toolbar;
    boolean state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean cameraServiceStatus = isServiceStart(CameraService.class,this);
        Log.d("Camera service status",""+cameraServiceStatus);

        boolean audioServiceStatus = isServiceStart(RecorderService.class,this);
        Log.d("Audio service status",""+audioServiceStatus);

        if (cameraServiceStatus){
            state = false;
            //setContentView(R.layout.stop_recording_video);
            Intent intent = new Intent(getApplicationContext(),StopVideo.class);
            startActivity(intent);
        }else if(audioServiceStatus){
            state = false;
            Intent intent = new Intent(getApplicationContext(),StopAudio.class);
            startActivity(intent);
        }else{
            state = true;
            setContentView(R.layout.activity_main);
        }

        if (!cameraServiceStatus && !audioServiceStatus) {
            mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
            mSurfaceHolder = mSurfaceView.getHolder();
            mSurfaceHolder.addCallback(this);
            mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkDrawOverlayPermission();
            requestPermission();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (state) {
            getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

                    if ((drawer != null) && (navigationView != null)) {
                        toolbar = (Toolbar) findViewById(R.id.toolbar);
                        setSupportActionBar(toolbar);
                        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
                        drawer.setDrawerListener(toggle);
                        toggle.syncState();
                        navigationView.setNavigationItemSelectedListener(MainActivity.this);

                        AdView adView = (AdView) findViewById(R.id.adView);
                        AdRequest request = new AdRequest.Builder().build();
                        adView.loadAd(request);
                    }
                }
            });
        }

        String root_directory_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/eyEar";
        String audio_directory_path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/eyEar/Audio";
        String video_directory_path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/eyEar/Video";

        File root_directory = new File(root_directory_path);
        File audio_directory = new File(audio_directory_path);
        File video_directory = new File(video_directory_path);

        if (!root_directory.exists()) {
            root_directory.mkdirs();
            audio_directory.mkdirs();
            video_directory.mkdirs();
        }
    }

    public void playAudio(View v){
        Toast.makeText(getApplicationContext(), "Audio Recording started", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, RecorderService.class);
        startService(intent);
        Intent audio_intent = new Intent(getApplicationContext(), StopAudio.class);
        startActivity(audio_intent);
    }

    public void playVideo(View view){
        Toast.makeText(getApplicationContext(), "Video Recording started", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, CameraService.class);
        startService(intent);
        Intent video_intent = new Intent(getApplicationContext(), StopVideo.class);
        startActivity(video_intent);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean CameraPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                }
                break;
        }
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),android.Manifest.permission.RECORD_AUDIO);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(),android.Manifest.permission.CAMERA);
        //int result3 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.SYSTEM_ALERT_WINDOW);
        return (result == PackageManager.PERMISSION_GRANTED) && (result1 == PackageManager.PERMISSION_GRANTED) && (result2 == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, CAMERA, SYSTEM_ALERT_WINDOW}, RequestPermissionCode);
    }

    public final static int REQUEST_CODE = 65500;

    @TargetApi(Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (!Settings.canDrawOverlays(this)) {
            /** if not construct intent to request permission */
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            /** request permission via start activity for result */
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        /** check if received result code
         is equal our requested code for draw permission  */
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                // continue here - permission was granted
            }
        }
    }

    private boolean isServiceStart(Class<?> serviceClass, Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if (serviceClass.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            moveTaskToBack(true);
         }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.nav_home){
        }else if (id == R.id.nav_save_record){
            Intent intent_one = new Intent(getApplicationContext(), com.gaurav.recorder.sub_activities.SavedRecord.class);
            startActivity(intent_one);
        }else if (id == R.id.nav_about_us){
            Intent intent_three = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(intent_three);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}