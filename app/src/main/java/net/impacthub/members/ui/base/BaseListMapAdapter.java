/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.members.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.ui.common.RecyclerViewHolder;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public abstract class BaseListMapAdapter<VH extends RecyclerView.ViewHolder, DTO> extends BaseListAdapter<VH, DTO> {

    private final UserAccount mUserAccount = userAccountProvider();
    private final LayoutInflater mLayoutInflater;
    private OnListItemClickListener<DTO> mItemClickListener;

    protected BaseListMapAdapter(LayoutInflater inflater) {
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

    protected abstract class ViewHolder extends RecyclerViewHolder<DTO> implements View.OnClickListener {

        protected ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
