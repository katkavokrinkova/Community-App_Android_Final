package net.impacthub.members.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.model.callback.OnListItemClickListener;

import java.util.LinkedList;
import java.util.List;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseListAdapter<VH extends RecyclerView.ViewHolder, DTO> extends RecyclerView.Adapter<VH> {

    private final UserAccount mUserAccount = userAccountProvider();
    private final List<DTO> mItems = new LinkedList<>();
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
        return url + "?oauth_token=" + mUserAccount.getAuthToken();
    }

    public void setItems(List<DTO> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected DTO getItem(int index) {
        return mItems.get(index);
    }
}
