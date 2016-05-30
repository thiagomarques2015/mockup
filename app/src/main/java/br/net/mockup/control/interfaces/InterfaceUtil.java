package br.net.mockup.control.interfaces;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by Thiago on 29/10/2015.
 */
class InterfaceUtil {

    public static final int GALERIA = 1;
    public static final int CAPTURAR_IMAGEM = 2;

    public static Uri capturarCamera(String dir) {
        String diretorio = Environment.getExternalStorageDirectory().toString() + File.separator + dir + File.separator;

        new File(diretorio).mkdirs();

        String nomeImagem = diretorio + System.currentTimeMillis() + ".jpg";

        File file = new File(nomeImagem);

        return Uri.fromFile(file);
    }

    public static Intent criarIntentCapturarCamera(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }
}
