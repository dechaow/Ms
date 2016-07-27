package com.example.wdc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wdc on 2016/7/25.
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerHolder> {

    Context context;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerHolder(new View(context));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
