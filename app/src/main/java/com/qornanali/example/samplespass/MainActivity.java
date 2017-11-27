package com.qornanali.example.samplespass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

public class MainActivity extends AppCompatActivity {

    Button btnIdentify, btnRegister;

    Spass myPass;
    SpassFingerprint myFingerprintPass;

    boolean isSupported = false;
    boolean isFeatureEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIdentify = (Button) findViewById(R.id.btn_identify);
        btnRegister = (Button) findViewById(R.id.btn_register);

        myPass = new Spass();
        try {
            myPass.initialize(MainActivity.this);
            isSupported = true;
        } catch (SsdkUnsupportedException e) {

        } catch (UnsupportedOperationException e) {

        }

        if (isSupported) {
            isFeatureEnabled = myPass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);
            if (isFeatureEnabled) {
                myFingerprintPass = new SpassFingerprint(MainActivity.this);
            }else{
                Toast.makeText(MainActivity.this, "fitur tidak diaktifkan", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "device tidak support samsung galaxy sdk", Toast.LENGTH_SHORT).show();
        }

        btnIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFeatureEnabled){
                    myFingerprintPass.startIdentifyWithDialog(MainActivity.this, new SpassFingerprint.IdentifyListener() {
                        @Override
                        public void onFinished(int i) {
                            if(i == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS){
                                Toast.makeText(MainActivity.this, "Sukses mengidentifikasi", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Gagal mengindentifikasi", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onReady() {

                        }

                        @Override
                        public void onStarted() {

                        }

                        @Override
                        public void onCompleted() {

                        }
                    }, false);
                }else{
                    Toast.makeText(MainActivity.this, "fitur tidak diaktifkan", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(isFeatureEnabled){
                   myFingerprintPass.registerFinger(MainActivity.this, new SpassFingerprint.RegisterListener() {
                       @Override
                       public void onFinished() {
                           Toast.makeText(MainActivity.this, "Selesai daftar", Toast.LENGTH_SHORT).show();
                       }
                   });
               }else{
                   Toast.makeText(MainActivity.this, "fitur tidak diaktifkan", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

}
