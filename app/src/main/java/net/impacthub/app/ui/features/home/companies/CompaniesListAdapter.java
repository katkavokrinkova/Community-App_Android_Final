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

package net.impacthub.app.ui.features.home.companies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.vo.companies.CompanyVO;
import net.impacthub.app.ui.base.BaseListAdapter;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.common.RecyclerViewHolder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

class CompaniesListAdapter extends BaseListAdapter<CompaniesListAdapter.ViewHolder, CompanyVO> {

    CompaniesListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = getLayoutInflater().inflate(R.layout.item_layout_company, parent, false);
        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindViewsWith(getItem(position));
    }

    class ViewHolder extends RecyclerViewHolder<CompanyVO> implements View.OnClickListener {

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
        protected void bindViewsWith(CompanyVO item) {
            Context context = companyBannerImage.getContext();
            companyName.setText(item.mCompanyName);
            companySector.setText(item.mCompanySector);
            memberCount.setText(item.mCompanyMemberCount);
            locations.setText(item.mCompanyLocation);
            ImageLoaderHelper.loadImage(context, buildUrl(item.mCompanyLogo), companyBannerLogo);
            ImageLoaderHelper.loadImage(context, buildUrl(item.mCompanyBanner), companyBannerImage);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onItemClick(v.getId(), getItem(position), position);
            }
        }
    }
}
