package com.example.wdc.widgets;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dechao on 2018/2/9.
 *
 * @Title: recyclerView 加载更多的简单封装
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public abstract class LoadMoreListener extends RecyclerView.OnScrollListener{

    private int lastVisibleItem = 0;
    private int currentPage = 0;

    /**
     * newState
     * 0 停止滑动 SCROLL_STATE_IDLE
     * 1 手按住滑动 SCROLL_STATE_DRAGGING
     * 2 手松开了但是还在滑动 SCROLL_STATE_SETTLING
     */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == RecyclerView.SCROLL_STATE_IDLE
                && lastVisibleItem+1 == recyclerView.getAdapter().getItemCount()){
            View child = recyclerView.getChildAt(recyclerView.getChildCount()-1);
            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(child);
            currentPage++;
            loadMoreData(currentPage,holder);
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        if (manager instanceof LinearLayoutManager){
            lastVisibleItem = ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();
        }

    }

    public abstract void loadMoreData(int page,RecyclerView.ViewHolder holder);
}
