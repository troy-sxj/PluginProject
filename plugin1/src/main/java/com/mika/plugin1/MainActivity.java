package com.mika.plugin1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * @Author: mika
 * @Time: 2018/12/25 5:14 PM
 * @Description:
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnTestJni).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTestJni:
                Toast.makeText(this, new JniUtils().getString(), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
