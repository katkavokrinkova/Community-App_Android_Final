package net.impacthub.app.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.pojo.Searchable;
import net.impacthub.app.ui.common.UserAccountDelegate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseListAdapter<VH extends RecyclerView.ViewHolder, DTO extends Searchable> extends RecyclerView.Adapter<VH> {

    private final List<DTO> mAllItems = new LinkedList<>();
    private final List<DTO> mFilteredItems = new LinkedList<>();
    private final LayoutInflater mLayoutInflater;
    protected OnListItemClickListener<DTO> mItemClickListener;

    private String mLastFilterQuery = "";
    private boolean mHasFilters;
    private Map<String, List<String>> mFilters;

    protected BaseListAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    protected LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public void setItemClickListener(@NonNull OnListItemClickListener<DTO> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    protected UserAccount getUserAccount() {
        return UserAccountDelegate.getAccountManager();
    }

    protected String buildUrl(String url) {
        return UserAccountDelegate.buildUrl(url);
    }

    public void setItems(List<DTO> items) {
        mAllItems.clear();
        mFilteredItems.clear();
        mAllItems.addAll(items);
        mFilteredItems.addAll(items);
        notifyDataSetChanged();
    }

    public void appendItems(List<DTO> items) {
        int size = mAllItems.size();
        mAllItems.addAll(items);
        mFilteredItems.addAll(items);
        notifyItemRangeInserted(size, mAllItems.size());
    }

    public void appendItems(int position, List<DTO> items) {
        int size = mAllItems.size();
        mAllItems.addAll(position, items);
        mFilteredItems.addAll(position, items);
        notifyItemRangeInserted(size, mAllItems.size());
    }

    public void appendItem(DTO item) {
        mAllItems.add(item);
        mFilteredItems.add(item);
        notifyItemInserted(mAllItems.size());
    }

    public void clearItems() {
        mAllItems.clear();
        mFilteredItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFilteredItems.size();
    }

    public DTO getItem(int index) {
        return mFilteredItems.get(index);
    }

    public void filterSearch(String filterQuery) {
        mLastFilterQuery = filterQuery;
        mFilteredItems.clear();
        if(mHasFilters) {
            applyFilters(mFilters);
        } else if(filterQuery.isEmpty()){
            mFilteredItems.addAll(mAllItems);
        } else{
            filterQuery = filterQuery.toLowerCase();
            for (DTO dto : mAllItems) {
                if(dto.isSearchable(filterQuery)) {
                    mFilteredItems.add(dto);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void applyFilters(Map<String, List<String>> filters) {
        mFilters = filters;
        mHasFilters = true;
        mFilteredItems.clear();
        for (DTO dto : mAllItems) {
            if(dto.isFilterable(filters) && dto.isSearchable(mLastFilterQuery)) {
                mFilteredItems.add(dto);
            }
        }
        notifyDataSetChanged();
    }

    public void resetFilters() {
        mHasFilters = false;
        filterSearch(mLastFilterQuery);
    }
}
