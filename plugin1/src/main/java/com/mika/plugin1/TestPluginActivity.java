package com.mika.plugin1;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mika.dynamic.activity.BasePluginActivity;
import com.mika.dynamic.activity.DyIntent;


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
        findViewById(R.id.btnTestJni).setOnClickListener(this);
        findViewById(R.id.btnJumpActivity).setOnClickListener(this);
        findViewById(R.id.btnJumpOutActivity).setOnClickListener(this);
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
            case R.id.btnTestJni:
                Toast.makeText(that, new JniUtils().getString(), Toast.LENGTH_LONG).show();
                break;
            case R.id.btnJumpActivity:
                DyIntent dyIntent = new DyIntent("com.mika.plugin1.SecondActivity");
                startActivity(dyIntent);
                break;
            case R.id.btnJumpOutActivity:
                DyIntent dyIntent2 = new DyIntent("com.mika.plugin2.MainActivity");
                startActivity(dyIntent2);
                break;
        }
    }

}
