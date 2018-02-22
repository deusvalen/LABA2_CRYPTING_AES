package by.bstu.fit.lyolia.laba2_crypting_aes;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by User on 14.02.2018.
 */

public class Misc {

    public static final String TAG = "TEST";

    @Nullable
    public static String cryptAES (@NonNull String string, @NonNull Context context)
            throws NullPointerException {

        SecretKeySpec sks;
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, random);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
            return null;
        }

        byte[] encodedBytes;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = cipher.doFinal(string.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "AES encryption error");
            return null;
        }

        if(writeInFile(Base64.encodeToString(sks.getEncoded(), Base64.DEFAULT), "AES-key.txt", context)){
            Toast.makeText(context, "Key written in file", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Key not written in file!!!", Toast.LENGTH_SHORT).show();
        }
        return Base64.encodeToString(encodedBytes, Base64.DEFAULT);
    }


    public static boolean writeInFile (String text, String filename, Context context) {

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return false;
        }
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + File.separator + "AES");
        sdPath.mkdirs();
        File sdFile = new File(sdPath, filename);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            bw.write(text);
            bw.close();
            Log.d(TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
