package me.code.yiguitest;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Yangyanyan on 2018/1/10.
 */

public class MyAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public MyAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        for (int i = 0; i < data.size(); i++) {
            Log.i(TAG, "货道号: "+data.get(i));
        }
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_channle,"货道号: " + s);
    }
}
