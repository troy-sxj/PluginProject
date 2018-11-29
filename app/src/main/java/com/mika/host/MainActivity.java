package com.mika.host;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mika.host.dynamic.HookHelper;

public class MainActivity extends Activity implements View.OnClickListener {

    private final static String Tag = MainActivity.class.getSimpleName();
    private Button btn1, btn2, btn3;


    @Override
    protected void attachBaseContext(Context newBase) {
        HookHelper.attachBaseContext();
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                jumpTestActivity();
                break;
            case R.id.button2:
                jumpTestService();
                break;
            case R.id.button3:
                jumpTestReceiver();
                break;
        }
    }


    private void jumpTestActivity() {
        getApplicationContext().startActivity(new Intent(this, SecondActivity.class));
    }


    private void jumpTestService() {

    }

    private void jumpTestReceiver() {

    }

}

