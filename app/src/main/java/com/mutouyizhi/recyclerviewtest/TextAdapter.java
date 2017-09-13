package com.mutouyizhi.recyclerviewtest;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL on 2017-09-08.
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextHolder> {
    private List<String> mStrings;

    public TextAdapter(List<String> strings) {
        mStrings = strings;
    }

    @Override
    public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new TextHolder(view);
    }

    @Override
    public void onBindViewHolder(TextHolder holder, int position) {
        holder.bind(mStrings.get(position));
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public List getDataList() {
        return mStrings;
    }

    public class TextHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private int mItemBackgroundColor;

        public TextHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_view);
            mItemBackgroundColor = itemView.getDrawingCacheBackgroundColor();
        }

        public void bind(String text) {
            mTextView.setText(text);
        }

        public int getItemBackgroundColor() {
            return mItemBackgroundColor;
        }
    }
}
