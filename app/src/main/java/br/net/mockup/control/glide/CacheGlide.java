package br.net.mockup.control.glide;

import android.provider.MediaStore;

import com.bumptech.glide.signature.MediaStoreSignature;

/**
 * Created by Thiago on 31/07/2015.
 */
class CacheGlide extends MediaStoreSignature  {
    /**
     * Constructor for {@link MediaStoreSignature}.
     *
     * @param mimeType     The mime type of the media store media. Ok to default to empty string "". See
     *                     {@link MediaStore.Images.ImageColumns#MIME_TYPE} or
     *                     {@link MediaStore.Video.VideoColumns#MIME_TYPE}.
     * @param dateModified The date modified time of the media store media. Ok to default to 0. See
     *                     {@link MediaStore.Images.ImageColumns#DATE_MODIFIED} or
     *                     {@link MediaStore.Video.VideoColumns#DATE_MODIFIED}.
     * @param orientation  The orientation of the media store media. Ok to default to 0. See
     *                     {@link MediaStore.Images.ImageColumns#ORIENTATION}.
     */
    public CacheGlide(String mimeType, long dateModified, int orientation) {
        super(mimeType, dateModified, orientation);
    }
}
