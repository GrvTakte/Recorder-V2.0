package com.gaurav.recorder.model;

/**
 * Created by gaurav on 03/08/17.
 */

public class RecordData {

    private int image;
    private String name;

    public RecordData(int image, String name){
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RecordData{" +
                "image=" + image +
                ", name='" + name + '\'' +
                '}';
    }
}
