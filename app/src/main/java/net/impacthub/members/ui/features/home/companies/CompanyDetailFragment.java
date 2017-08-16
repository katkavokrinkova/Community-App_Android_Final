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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.companies.CompanyDTO;
import net.impacthub.members.model.dto.members.MemberDTO;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.presenter.features.companies.CompanyDetailUiContract;
import net.impacthub.members.presenter.features.companies.CompanyDetailUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.members.binders.AboutViewBinder;
import net.impacthub.members.ui.features.home.members.binders.MembersViewBinder;
import net.impacthub.members.ui.features.home.projects.binders.ProjectsViewBinder;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class CompanyDetailFragment extends BaseChildFragment<CompanyDetailUiPresenter> implements CompanyDetailUiContract {

    public static final String TITLES[] = {"ABOUT", "PROJECTS", "MEMBERS"};

    private static final String EXTRA_COMPANY_ID = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_ID";
    private static final String EXTRA_COMPANY_NAME = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_NAME";
    private static final String EXTRA_COMPANY_BANNER_IMAGE_URL = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_BANNER_IMAGE_URL";

    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.tabs) protected TabLayout mCompanyTab;
    @BindView(R.id.pager) protected ViewPager mCompanyPages;

    private AppPagerAdapter mPagerAdapter;

    private ViewBinder<List<ProjectDTO>> mViewBinder2;
    private ViewBinder<List<MemberDTO>> mViewBinder3;

    public static CompanyDetailFragment newInstance(CompanyDTO model) {

        Bundle args = new Bundle();
        args.putString(EXTRA_COMPANY_ID, model.mCompanyId);
        args.putString(EXTRA_COMPANY_NAME, model.mCompanyName);
        args.putString(EXTRA_COMPANY_BANNER_IMAGE_URL, model.mCompanyBanner);
        CompanyDetailFragment fragment = new CompanyDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected CompanyDetailUiPresenter onCreatePresenter() {
        return new CompanyDetailUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_category_detail_pager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();

        String companyId = arguments.getString(EXTRA_COMPANY_ID);
        String companyName = arguments.getString(EXTRA_COMPANY_NAME);
        String imageURL = arguments.getString(EXTRA_COMPANY_BANNER_IMAGE_URL);

        setUpToolbar(companyName);
        ImageLoaderHelper.loadImage(getContext(), buildUrl(imageURL), mImageDetail);

        mPagerAdapter = new AppPagerAdapter(getContext());
        mPagerAdapter.addVieBinder(new AboutViewBinder());
        mPagerAdapter.addVieBinder(mViewBinder2 = new ProjectsViewBinder(new OnListItemClickListener<ProjectDTO>() {
            @Override
            public void onItemClick(ProjectDTO model) {
                showToast("Hello Project");
            }
        }));
        mPagerAdapter.addVieBinder(mViewBinder3 = new MembersViewBinder(new OnListItemClickListener<MemberDTO>() {
            @Override
            public void onItemClick(MemberDTO model) {
                showToast("Hello Member");
            }
        }));

        mCompanyPages.setAdapter(mPagerAdapter);
        mCompanyPages.setOffscreenPageLimit(mPagerAdapter.getCount());

        mCompanyTab.setupWithViewPager(mCompanyPages);

        new TabsDelegate().setUp(mCompanyTab, TITLES);

        getPresenter().loadDetails(companyId);
    }

    @Override
    public void onLoadProjects(List<ProjectDTO> projectDTOs) {
        mViewBinder2.bindView(projectDTOs);
    }

    @Override
    public void onLoadMembers(List<MemberDTO> memberDTOs) {
        mViewBinder3.bindView(memberDTOs);
    }
}
