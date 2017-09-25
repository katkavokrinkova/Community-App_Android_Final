package net.impacthub.app.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.pojo.Filterable;

import java.util.LinkedList;
import java.util.List;

import static net.impacthub.app.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseListAdapter<VH extends RecyclerView.ViewHolder, DTO extends Filterable> extends RecyclerView.Adapter<VH> {

    private final UserAccount mUserAccount = userAccountProvider();
    private final List<DTO> mAllItems = new LinkedList<>();
    private final List<DTO> mFilteredItems = new LinkedList<>();
    private final LayoutInflater mLayoutInflater;
    protected OnListItemClickListener<DTO> mItemClickListener;

    protected BaseListAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    protected LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public void setItemClickListener(@NonNull OnListItemClickListener<DTO> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    protected String buildUrl(String url) {
        if (mUserAccount != null) {
            return url + "?oauth_token=" + mUserAccount.getAuthToken();
        }
        return url;
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

    @Override
    public int getItemCount() {
        return mFilteredItems.size();
    }

    protected DTO getItem(int index) {
        return mFilteredItems.get(index);
    }

    public void filter(String filterQuery) {
        mFilteredItems.clear();
        if(filterQuery.isEmpty()){
            mFilteredItems.addAll(mAllItems);
        } else{
            filterQuery = filterQuery.toLowerCase();
            for (DTO dto : mAllItems) {
                if(dto.isFilterable(filterQuery)) {
                    mFilteredItems.add(dto);
                }
            }
        }
        notifyDataSetChanged();
    }
}
