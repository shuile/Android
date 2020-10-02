package com.cyt.testbinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cyt.testbinder.testBundle.activity.FirstActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bundle_first:
                start(FirstActivity.class);
                break;
        }
    }

    private void start(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
