package com.mutouyizhi.recyclerviewtest;

import android.gesture.GestureOverlayView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by DELL on 2017-09-08.
 */

public abstract class MyOnItemTouchListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetectorCompat;
    private RecyclerView mRecyclerView;
    private class MyOnGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(child);
                onClick(viewHolder);
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            View child = mRecyclerView.findChildViewUnder(e1.getX(), e1.getY());
            if (child != null) {
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(child);
                onSwipe(viewHolder);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(child);
                onLongClick(viewHolder);
            }
        }
    }

    public MyOnItemTouchListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(), new MyOnGesture());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }


    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void onClick(RecyclerView.ViewHolder viewHolder);

    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);

    public abstract void onSwipe(RecyclerView.ViewHolder viewHolder);
}
