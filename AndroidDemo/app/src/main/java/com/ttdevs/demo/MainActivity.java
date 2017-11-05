package com.ttdevs.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ttdevs.lib.log.TtdevsLogger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TtdevsLogger.i("Hello,I'm ttdevs!");
    }
}
