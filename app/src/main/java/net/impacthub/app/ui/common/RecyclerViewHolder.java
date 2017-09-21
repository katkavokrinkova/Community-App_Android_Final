package net.impacthub.app.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class RecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void bindViewsWith(T itemData);
}
