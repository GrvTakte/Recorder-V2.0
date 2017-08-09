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

public class VideoCustomArrayAdapter extends ArrayAdapter<RecordData> {

    ArrayList<RecordData> arrayList;

    public VideoCustomArrayAdapter(Context context, int resourceId, ArrayList<RecordData> arrayList){
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
        v = inflater.inflate(R.layout.video_record_list,null);

        TextView textView = (TextView) v.findViewById(R.id.video_textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.video_imageView);

        imageView.setImageResource(arrayList.get(position).getImage());
        textView.setText(arrayList.get(position).getName());
        return v;
    }
}
