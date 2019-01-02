package com.mika.plugin1;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.mika.dynamic.activity.BasePluginActivity;
import com.mika.plugin1.view.IjkVideoView;

/**
 * @Author: mika
 * @Time: 2018/12/28 2:44 PM
 * @Description:
 */
public class TestPlayerActivity extends BasePluginActivity {

    private FrameLayout frameLayout;
    private IjkVideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        videoView = (IjkVideoView) findViewById(R.id.videoView);

    }
}
