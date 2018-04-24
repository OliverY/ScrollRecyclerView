package com.yxj.scrollrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Author:  Yxj
 * Time:    18/4/24 上午10:57
 * -----------------------------------------
 * Description:
 */

public class ScrollRecyclerView extends RecyclerView {

    int scrollPosition = 0;
    boolean move = false;
    LinearLayoutManager layoutManager;

    public ScrollRecyclerView(Context context) {
        super(context);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        addScrollView();
    }

    public void addScrollView(){
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(move && layoutManager!=null){
                    move = false;
                    int firstItem = layoutManager.findFirstVisibleItemPosition();
                    int top = getChildAt(scrollPosition - firstItem).getTop();
                    smoothScrollBy(0,top);
                }
            }
        });
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        this.layoutManager = layoutManager;
    }

    public void moveToPosition(int position) {
        this.scrollPosition = position;
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        if(position < firstItem){
            smoothScrollToPosition(position);
        }else if(position < lastItem){
            int top = getChildAt(position - firstItem).getTop();
            smoothScrollBy(0,top);
        }else{
            smoothScrollToPosition(position);
            move = true;
        }
    }
}
