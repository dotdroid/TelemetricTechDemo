package ru.dotdroid.telemetrictechdemo.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import ru.dotdroid.telemetrictechdemo.R;

public class CameraScreenActivity extends AppCompatActivity {

    public static final String TAG = "CameraScreenActivity";
    public static final String EXTRA_DEVEUI = "ru.dotdroid.telemetrictechdemo.deviceEui";
    private static final int PERMISSION_REQUEST_CODE = 100;

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_screen);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);

        setupPermissions();
        codeScanner(scannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void codeScanner(CodeScannerView view) {
        mCodeScanner = new CodeScanner(this, view);
        mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
        mCodeScanner.setFormats(CodeScanner.ALL_FORMATS);
        mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        mCodeScanner.setScanMode(ScanMode.SINGLE);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setFlashEnabled(false);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        if(String.valueOf(result) != null) {
                            intent.putExtra(EXTRA_DEVEUI, String.valueOf(result));
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            setResult(RESULT_CANCELED);
                        }
                    }
                });
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        mCodeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull final Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Camera initialization error");
                    }
                });
            }
        });
    }

    private void setupPermissions() {
        int permission = ContextCompat.checkSelfPermission(CameraScreenActivity.this, Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED) requestPermission();
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(CameraScreenActivity.this, new String[] {Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Need camera permission", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
