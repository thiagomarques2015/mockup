package br.net.mockup.model;

import android.media.ImageReader;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thiago on 05/11/2015.
 */
public class ImageParcelable implements Parcelable {

    private byte[] data;
    private ImageReader reader;

    protected ImageParcelable(Parcel in) {
    }

    public static final Creator<ImageParcelable> CREATOR = new Creator<ImageParcelable>() {
        @Override
        public ImageParcelable createFromParcel(Parcel in) {
            return new ImageParcelable(in);
        }

        @Override
        public ImageParcelable[] newArray(int size) {
            return new ImageParcelable[size];
        }
    };

    public ImageParcelable(byte[] data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(reader);
    }

    public ImageParcelable(ImageReader reader) {
        this.reader = reader;
    }

    public ImageReader getReader() {
        return reader;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setImage(ImageReader image) {
        this.reader = image;
    }
}
