package com.mika.plugin1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author: mika
 * @Time: 2018-12-19 11:03
 * @Description:
 */
public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(), R.layout.fragment_test, null);
        initView(inflate);
        return inflate;
    }

    protected void initView(View rootView){
//        rootView.findViewById()
    }
}
