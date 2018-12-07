package com.mika.plugin1;

import android.content.Context;

import com.mika.dynamic.business.IDynamic;

/**
 * @Author: mika
 * @Time: 2018-12-06 17:28
 * @Description:
 */
public class DynamicString implements IDynamic {
    @Override
    public String getStringForResId(Context context) {
        return context.getResources().getString(R.string.plugin1_test);
    }
}
