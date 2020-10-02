package com.cyt.testbinder.testBundle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cyt.testbinder.Constants;
import com.cyt.testbinder.R;
import com.cyt.testbinder.testBundle.UserS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SecondActivity extends AppCompatActivity {

    private TextView mTvRecovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        init();
    }

    private void init() {
        mTvRecovery = findViewById(R.id.tv_recovery);
    }

    public void click(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Serializable
                UserS userS = null;
                File userSFile = new File(Constants.FILE_USERS);
                if (userSFile.exists()) {
                    ObjectInputStream objectInputStream1 = null;
                    try {
                        objectInputStream1 = new ObjectInputStream(new FileInputStream(Constants.FILE_USERS));
                        userS = (UserS) objectInputStream1.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        if (objectInputStream1 != null) {
                            try {
                                objectInputStream1.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                // Parcelable
//                UserP userP = null;
//                File userPFile = new File(Constants.FILE_USERP);
//                if (userPFile.exists()) {
//                    ObjectInputStream objectInputStream2 = null;
//                    try {
//                        objectInputStream2 = new ObjectInputStream(new FileInputStream(Constants.FILE_USERP));
//                        userP = (UserP) objectInputStream2.readObject();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (objectInputStream2 != null) {
//                            try {
//                                objectInputStream2.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }

                // show
                final StringBuilder sb = new StringBuilder();
                if (userS != null) {
                    sb.append(userS.toString()).append("\n");
                }
//                if (userP != null) {
//                    sb.append(userP.toString());
//                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvRecovery.setText(sb.toString());
                    }
                });
            }
        }).start();
    }
}
