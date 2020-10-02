package com.cyt.testbinder.testBundle.activity;

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

import com.cyt.testbinder.Constants;
import com.cyt.testbinder.R;
import com.cyt.testbinder.testBundle.UserS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish();
                }
            }
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_save_data:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File dir = new File(Constants.DIR);
                        if (!dir.exists()) {
                            boolean res = dir.mkdirs();
                            Log.d(TAG, "run: " + dir.getAbsolutePath() + " is created " + res);
                        }

                        // Serializable
                        UserS userS = new UserS(1, "Serializable", false);
                        File userSFile = new File(Constants.FILE_USERS);
                        ObjectOutputStream objectOutputStream1 = null;
                        try {
                            objectOutputStream1 = new ObjectOutputStream(new FileOutputStream(userSFile));
                            objectOutputStream1.writeObject(userS);
                            Log.d(TAG, "run: persist userS: " + userS);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (objectOutputStream1 != null) {
                                try {
                                    objectOutputStream1.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        // Parcelable
//                        UserP userP = new UserP(2, "Parcelable", true);
//                        File userPFile = new File(Constants.FILE_USERP);
//                        ObjectOutputStream objectOutputStream2 = null;
//                        try {
//                            objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(userPFile));
//                            objectOutputStream2.writeObject(userP);
//                            Log.d(TAG, "run: persist userP: " + userP);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } finally {
//                            if (objectOutputStream2 != null) {
//                                try {
//                                    objectOutputStream2.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
                    }
                }).start();
                break;
            case R.id.btn_goto_second_activity:
                startActivity(new Intent(this, SecondActivity.class));
                break;
        }
    }
}
