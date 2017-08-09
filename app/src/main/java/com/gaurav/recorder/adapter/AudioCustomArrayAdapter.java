package com.gaurav.recorder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaurav.recorder.R;
import com.gaurav.recorder.model.RecordData;

import java.util.ArrayList;

/**
 * Created by gaurav on 03/08/17.
 */

public class AudioCustomArrayAdapter extends ArrayAdapter<RecordData> {

    ArrayList<RecordData> arrayList;
    public AudioCustomArrayAdapter(Context context, int resourceId, ArrayList<RecordData> arrayList){
        super(context,resourceId,arrayList);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.audio_record_list,null);

        ImageView imageView = (ImageView) v.findViewById(R.id.audio_imageView);
        TextView textView = (TextView) v.findViewById(R.id.audio_textView);

        imageView.setImageResource(arrayList.get(position).getImage());
        textView.setText(arrayList.get(position).getName());
        return v;
    }
}
