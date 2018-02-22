package by.bstu.fit.lyolia.laba2_crypting_aes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static by.bstu.fit.lyolia.laba2_crypting_aes.Misc.cryptAES;

public class MainActivity extends AppCompatActivity {

    EditText stringEt;
    EditText encryptedTextEt;
    Button saveButton;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringEt = (EditText) findViewById(R.id.stringEt);
        encryptedTextEt = (EditText) findViewById(R.id.encryptedEt);
        saveButton = (Button) findViewById(R.id.saveButton);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }

    public void onSaveClick(View view) {
        if(!stringEt.getText().toString().isEmpty()) {
            String crypted = Misc.cryptAES(stringEt.getText().toString(), this.getApplicationContext());
            encryptedTextEt.setText(crypted);
        } else {
            Toast.makeText(this.getApplicationContext(), "You need to fill the string!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

}
