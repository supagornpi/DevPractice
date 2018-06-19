package com.supagorn.devpractice.customs.adapter.java;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.supagorn.devpractice.R;
import com.supagorn.devpractice.customs.listener.ScrollLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter<T> extends RecyclerView.Adapter<CustomAdapter<T>.ViewHolder> {

    private static final int TYPE_LOAD_MORE = 0;
    private static final int TYPE_ITEM = 1;

    private List<T> list = new ArrayList<>();
    private boolean isLoadMore = false;
    private int pageSize = 10; //default is 10
    private OnBindViewListener onBindViewListener;
    private ScrollLoadMoreListener onScrollLoadMoreListener = null;

    public CustomAdapter(OnBindViewListener onBindViewListener) {
        this.onBindViewListener = onBindViewListener;
    }

    public CustomAdapter<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * To enable load more listener
     *
     * @param recyclerView
     * @return
     */
    public CustomAdapter<T> setOnLoadMoreListener(RecyclerView recyclerView, final T emptyItem,
                                                  final ScrollLoadMoreListener.OnLoadMoreListener onLoadMoreListener) {
        //Enable load more
        ScrollLoadMoreListener scrollLoadMoreListener =
                new ScrollLoadMoreListener(new ScrollLoadMoreListener.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        showLoadMore(emptyItem);
                        onLoadMoreListener.onLoadMore();
                    }
                });
        this.onScrollLoadMoreListener = scrollLoadMoreListener;
        recyclerView.addOnScrollListener(scrollLoadMoreListener);
        return this;
    }

    @Override
    public CustomAdapter<T>.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_LOAD_MORE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_load_more,
                    parent, false);
        } else if (viewType == TYPE_ITEM) {
            view = onBindViewListener.onCreateView();
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter<T>.ViewHolder holder, int position) {
        T item = list.get(position);
        if (holder.getItemViewType() == TYPE_ITEM) {
            onBindViewListener.onBindViewHolder(item, holder.itemView, holder.getItemViewType(), position);
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == this.list.size() - 1 && this.isLoadMore) ? TYPE_LOAD_MORE : TYPE_ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnBindViewListener {
        <T> void onBindViewHolder(T item, View itemView, int viewType, int position);

        View onCreateView();
    }

    public void setListItem(List<T> list) {
        if (this.onScrollLoadMoreListener != null) {
            this.onScrollLoadMoreListener.setHasLoadingMore(list.size() == this.pageSize);
        }
        this.list = list;
        notifyDataSetChanged();
    }

    public void addListItem(List<T> list) {
        if (this.onScrollLoadMoreListener != null) {
            this.onScrollLoadMoreListener.setHasLoadingMore(list.size() == this.pageSize);
        }
        if (isLoadMore) {
            hideLoadMore();
        }
        int notifyStart = this.list.size();
        this.list.addAll(list);
        notifyItemRangeChanged(notifyStart, (notifyStart + list.size()));
    }

    /**
     * hide automatically when add item list if isLoadMore is true
     */
    public void hideLoadMore() {
        //remove last item
        this.isLoadMore = false;
        this.list.remove(this.list.size() - 1);
        notifyItemRemoved(this.list.size() - 1);
    }

    /**
     * show automatically when add item list if isLoadMore is false
     *
     * @param emptyObject
     */
    private void showLoadMore(T emptyObject) {
        //show load more item
        this.isLoadMore = true;
        this.list.add(emptyObject);
        notifyItemInserted(this.list.size() - 1);
    }
}
