package com.gaurav.recorder.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaurav.recorder.R;
import com.gaurav.recorder.adapter.AudioCustomArrayAdapter;
import com.gaurav.recorder.model.RecordData;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by gaurav on 31/07/17.
 */

public class AudioRecordingFragment extends Fragment implements ListView.OnItemClickListener {

    ListView listView;
    ArrayList<RecordData> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.audio_fragment,container, false);

        listView = (ListView) rootView.findViewById(R.id.audio_listView);
        arrayList = new ArrayList<>();

        String path  = Environment.getExternalStorageDirectory().getAbsolutePath()+"/eyEar/Audio";

        File directory = new File(path);
        File[] files = directory.listFiles();

        Log.d("Files number:- ",""+files.length);
        for(int i=0; i<files.length; i++){
            arrayList.add(new RecordData(R.drawable.audio,""+files[i].getName()));
        }

        if (arrayList.size() == 0){
            listView.setVisibility(View.GONE);
            TextView textView = (TextView) rootView.findViewById(R.id.null_display_audio);
            textView.setVisibility(View.VISIBLE);
        }

        AudioCustomArrayAdapter adapter = new AudioCustomArrayAdapter(getActivity().getApplicationContext(),R.layout.audio_record_list,arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            String path = "file://"+Environment.getExternalStorageDirectory().getAbsolutePath() + "/eyEar/Audio/" + arrayList.get(position).getName();
            Uri audioUri = Uri.parse(path);
            Intent audioIntent = new Intent();
            audioIntent.setAction(Intent.ACTION_VIEW);
            audioIntent.setDataAndType(audioUri, "audio/*");
            startActivity(Intent.createChooser(audioIntent,null));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
