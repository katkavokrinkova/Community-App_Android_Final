package net.impacthub.members.ui.base;

import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseListAdapter<VH extends RecyclerView.ViewHolder, I> extends RecyclerView.Adapter<VH> {

    private final List<I> mItems = new LinkedList<>();

    public void setItems(List<I> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected I getItem(int index) {
        return mItems.get(index);
    }
}
