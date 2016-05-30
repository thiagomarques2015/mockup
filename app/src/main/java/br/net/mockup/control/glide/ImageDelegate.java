package br.net.mockup.control.glide;

import android.graphics.Bitmap;

/**
 * Created by Thiago on 27/03/2015.
 */
public class ImageDelegate {
    public interface BitmapListener {
        void createdImageBitmap(Bitmap imageBitmap);
    }

    public interface BytesListener {
        void createdImageBytes(byte[] data);
    }
}
