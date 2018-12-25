package com.mika.host;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.mika.dynamic.activity.DyIntent;


/**
 * @Author: mika
 * @Time: 2018-12-17 18:37
 * @Description:
 */
public class ThatActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_that);

        findViewById(R.id.btnLoadPlugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPlugin();
            }
        });
    }


    private void loadPlugin() {
        DyIntent dyIntent = new DyIntent("com.mika.plugin1.TestPluginActivity");
        startActivity(dyIntent);
    }
}
