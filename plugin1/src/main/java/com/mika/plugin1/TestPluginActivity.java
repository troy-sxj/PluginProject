package com.mika.plugin1;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentHostCallback;
import android.view.View;
import android.widget.Toast;

import com.mika.dynamic.BasePluginActivity;


/**
 * @Author: mika
 * @Time: 2018-12-17 19:10
 * @Description:
 */
public class TestPluginActivity extends BasePluginActivity implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_plugin);
        findViewById(R.id.btnCreateObj).setOnClickListener(this);
        findViewById(R.id.btnCreateFragment).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateObj:
                Bean bean = new Bean();
                Toast.makeText(that, bean.getName(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCreateFragment:
                Toast.makeText(that, "create Fragment", Toast.LENGTH_SHORT).show();
                TestFragment testFragment = new TestFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, testFragment).commitAllowingStateLoss();
                break;
        }
    }

    class PluginHostCallbacks extends FragmentHostCallback<FragmentActivity> {

        public PluginHostCallbacks(@NonNull Context context, @NonNull Handler handler, int windowAnimations) {
            super(that, handler, windowAnimations);
        }

        @Nullable
        @Override
        public FragmentActivity onGetHost() {
            return that;
        }

    }
}
