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
import com.gaurav.recorder.adapter.VideoCustomArrayAdapter;
import com.gaurav.recorder.model.RecordData;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by gaurav on 31/07/17.
 */

public class VideoRecordingFragment extends Fragment implements ListView.OnItemClickListener {

    ListView listView;
    ArrayList<RecordData> arrayList;
    File directory;
    File[] files;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_fragment,container, false);
        listView = (ListView) rootView.findViewById(R.id.video_listView);
        arrayList = new ArrayList<>();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/eyEar/Video";
        directory = new File(path);
        files = directory.listFiles();

        for (int i = 0; i<files.length;i++){
            arrayList.add(new RecordData(R.drawable.video,""+files[i].getName()));
        }

        if (arrayList.size() == 0){
            listView.setVisibility(View.GONE);
            TextView textView = (TextView) rootView.findViewById(R.id.null_display);
            textView.setVisibility(View.VISIBLE);
        }

        VideoCustomArrayAdapter adapter = new VideoCustomArrayAdapter(getActivity().getApplicationContext(),R.layout.video_record_list,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String file_path = "file://"+Environment.getExternalStorageDirectory().getAbsolutePath()+"/eyEar/Video/";
        Log.d("FILE PATH",file_path);
        Uri uri = Uri.parse(file_path+arrayList.get(position).getName());
        Log.d("FILE PATH",uri.toString());
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri,"video/*");
        startActivity(intent);
    }
}