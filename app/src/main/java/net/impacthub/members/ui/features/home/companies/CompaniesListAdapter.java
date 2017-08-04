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

package net.impacthub.members.ui.features.home.companies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.companies.CompanyDTO;
import net.impacthub.members.ui.base.BaseListAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.RecyclerViewHolder;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

class CompaniesListAdapter extends BaseListAdapter<CompaniesListAdapter.ViewHolder, CompanyDTO> {

    private final UserAccount mUserAccount = userAccountProvider();
    private final LayoutInflater mLayoutInflater;
    private OnListItemClickListener<CompanyDTO> mItemClickListener;

    CompaniesListAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = mLayoutInflater.inflate(R.layout.item_companies_layout, parent, false);
        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    void setItemClickListener(@NonNull OnListItemClickListener<CompanyDTO> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerViewHolder<CompanyDTO> implements View.OnClickListener {

        final ImageView companyBannerLogo;
        final ImageView companyBannerImage;
        final TextView companyName;
        final TextView companySector;
        final TextView  memberCount;
        final TextView  locations;

        ViewHolder(View itemView) {
            super(itemView);
            companyBannerLogo = (ImageView) itemView.findViewById(R.id.image_company_logo);
            companyBannerImage = (ImageView) itemView.findViewById(R.id.image_company_banner);
            companyName = (TextView) itemView.findViewById(R.id.text_company_name);
            companySector = (TextView) itemView.findViewById(R.id.text_company_sector);
            memberCount = (TextView) itemView.findViewById(R.id.member_count);
            locations = (TextView) itemView.findViewById(R.id.locations);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bindViewsWith(CompanyDTO item) {
            Context context = companyBannerImage.getContext();
            companyName.setText(item.mCompanyName);
            companySector.setText(item.mCompanySector);
            memberCount.setText(item.mCompanyMemberCount);
            locations.setText(item.mCompanyLocation);
            String token = "?oauth_token=" + mUserAccount.getAuthToken();
            ImageLoaderHelper.loadImage(context, item.mCompanyLogo + token, companyBannerLogo);
            ImageLoaderHelper.loadImage(context, item.mCompanyBanner + token, companyBannerImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
