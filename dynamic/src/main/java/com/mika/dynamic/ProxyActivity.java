package com.mika.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @Author: mika
 * @Time: 2018-12-17 17:32
 * @Description:
 * <p>
 *     需要在主项目中Manifest.xml中注册代理Activity。
 * </p>
 */
public class ProxyActivity extends BaseHostActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDexPath = getIntent().getStringExtra(DynamicConstant.EXTRA_DEX_PATH);
        mClass = getIntent().getStringExtra(DynamicConstant.EXTRA_DEX_CLASS);

        loadClassLoader();
        loadResources();
        launchTargetActivity();
    }
}
