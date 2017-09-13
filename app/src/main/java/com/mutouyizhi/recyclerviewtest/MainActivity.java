package com.mutouyizhi.recyclerviewtest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TextAdapter mTextAdapter;
    private List<String> mStrings = new ArrayList<>();

    private ItemTouchHelper.Callback mCallback = new ItemTouchHelper.Callback() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(Color.DKGRAY);
            }
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(((TextAdapter.TextHolder) viewHolder).getItemBackgroundColor());
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                int drags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int swaps = 0;
                return makeMovementFlags(drags, swaps);
            } else {
                int drags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swaps = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(drags, swaps);
            }
        }

        @Override
        public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
            if (target.getAdapterPosition() == 0) {
                return false;
            }
            return true;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            if (fromPosition < toPosition) {
                for (int i  = fromPosition; i < toPosition; i++) {
                    Collections.swap(mTextAdapter.getDataList(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mTextAdapter.getDataList(), i, i - 1);
                }
            }
            mTextAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mTextAdapter.getDataList().remove(position);
            mTextAdapter.notifyItemRemoved(position);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(new MyOnItemTouchListener(mRecyclerView) {
            @Override
            public void onClick(RecyclerView.ViewHolder viewHolder) {

            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() != 0) {
                    itemTouchHelper.startDrag(viewHolder);
                }
            }

            @Override
            public void onSwipe(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() != 0) {
                    itemTouchHelper.startSwipe(viewHolder);
                }
            }
        });
        mTextAdapter = new TextAdapter(mStrings);
        mRecyclerView.setAdapter(mTextAdapter);
    }

    private void init() {
        for (int i = 0; i < 2; i++) {
            mStrings.add("英雄联盟");
            mStrings.add("DNF");
            mStrings.add("CF");
            mStrings.add("地下城过与勇士");
            mStrings.add("炉石传说");
            mStrings.add("暗黑破坏神");
            mStrings.add("守望先锋");
            mStrings.add("正义联盟");
        }
    }
}
