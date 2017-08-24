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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.pojo.SimpleItem;
import net.impacthub.members.model.vo.companies.AboutCompanyVO;
import net.impacthub.members.model.vo.companies.CompanyVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.model.vo.projects.ProjectVO;
import net.impacthub.members.navigator.Navigator;
import net.impacthub.members.presenter.features.companies.CompanyDetailUiContract;
import net.impacthub.members.presenter.features.companies.CompanyDetailUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.delegate.DetailScreenDelegate;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.members.MemberDetailFragment;
import net.impacthub.members.ui.common.binders.AboutViewBinder;
import net.impacthub.members.ui.features.home.members.binders.MembersViewBinder;
import net.impacthub.members.ui.features.home.projects.ProjectDetailFragment;
import net.impacthub.members.ui.features.home.projects.binders.ProjectsViewBinder;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class CompanyDetailFragment extends BaseChildFragment<CompanyDetailUiPresenter> implements CompanyDetailUiContract {

    public static final String TITLES[] = {"ABOUT", "PROJECTS", "MEMBERS"};

    private static final String EXTRA_COMPANY_ID = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_ID";
    private static final String EXTRA_COMPANY_NAME = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_NAME";

    private static final String EXTRA_COMPANY_FACEBOOK_LINK = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_FACEBOOK_LINK";
    private static final String EXTRA_COMPANY_INSTA_LINK = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_INSTA_LINK";
    private static final String EXTRA_COMPANY_LINKEDIN_LINK = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_LINKEDIN_LINK";
    private static final String EXTRA_COMPANY_TWITTER_LINK = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_TWITTER_LINK";


    private static final String EXTRA_COMPANY_DESCRIPTION = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_DESCRIPTION";
    private static final String EXTRA_COMPANY_SECTOR = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_SECTOR";
    private static final String EXTRA_COMPANY_LOCATION = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_LOCATION";
    private static final String EXTRA_COMPANY_MEMBER_COUNT = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_MEMBER_COUNT";
    private static final String EXTRA_COMPANY_LOGO_IMAGE_URL = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_LOGO_IMAGE_URL";
    private static final String EXTRA_COMPANY_BANNER_IMAGE_URL = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_BANNER_IMAGE_URL";
    private static final String EXTRA_COMPANY_WEBSITE_LINK = "net.impacthub.members.ui.features.home.companies.EXTRA_COMPANY_WEBSITE_LINK";

    @BindView(R.id.image_detail) protected ImageView mImageLogo;
    @BindView(R.id.image_company_banner) protected ImageView mImageBanner;

    @BindView(R.id.button_twitter) protected ImageButton mButtonTwitter;
    @BindView(R.id.button_facebook) protected ImageButton mButtonFacebook;
    @BindView(R.id.button_linkedin) protected ImageButton mButtonLinkedin;
    @BindView(R.id.button_instagram) protected ImageButton mButtonInsta;

    @BindView(R.id.text_company_sector) protected TextView mCompanySector;
    @BindView(R.id.locations) protected TextView mCompanyLocation;
    @BindView(R.id.tabs) protected TabLayout mCompanyTab;
    @BindView(R.id.pager) protected ViewPager mCompanyPages;

    private ViewBinder<List<ListItemType>> mViewBinder1;
    private ViewBinder<List<ProjectVO>> mViewBinder2;
    private ViewBinder<List<MemberVO>> mViewBinder3;

    public static CompanyDetailFragment newInstance(CompanyVO model) {

        Bundle args = new Bundle();
        args.putString(EXTRA_COMPANY_ID, model.mCompanyId);
        args.putString(EXTRA_COMPANY_NAME, model.mCompanyName);

        args.putString(EXTRA_COMPANY_FACEBOOK_LINK, model.mLinkFacebook);
        args.putString(EXTRA_COMPANY_INSTA_LINK, model.mLinkInstagram);
        args.putString(EXTRA_COMPANY_LINKEDIN_LINK, model.mLinkLinkedin);
        args.putString(EXTRA_COMPANY_TWITTER_LINK, model.mLinkTwitter);

        args.putString(EXTRA_COMPANY_DESCRIPTION, model.mCompanyDescription);
        args.putString(EXTRA_COMPANY_SECTOR, model.mCompanySector);
        args.putString(EXTRA_COMPANY_LOCATION, model.mCompanyLocation);
        args.putString(EXTRA_COMPANY_MEMBER_COUNT, model.mCompanyMemberCount);
        args.putString(EXTRA_COMPANY_LOGO_IMAGE_URL, model.mCompanyLogo);
        args.putString(EXTRA_COMPANY_BANNER_IMAGE_URL, model.mCompanyBanner);
        args.putString(EXTRA_COMPANY_WEBSITE_LINK, model.mCompanyWebsite);
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
        return R.layout.fragment_company_detail;
    }

    @OnClick(R.id.button_website)
    protected void onWebsiteButtonClicked() {
        Navigator.startOtherWebActivity(getContext(), getArguments().getString(EXTRA_COMPANY_WEBSITE_LINK));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();

        String companyId = arguments.getString(EXTRA_COMPANY_ID);
        String companyName = arguments.getString(EXTRA_COMPANY_NAME);
        String companyLocation = arguments.getString(EXTRA_COMPANY_LOCATION);
        String logoURL = arguments.getString(EXTRA_COMPANY_LOGO_IMAGE_URL);
        String bannerURL = arguments.getString(EXTRA_COMPANY_BANNER_IMAGE_URL);

        setUpToolbar(companyName);

        mCompanySector.setText(arguments.getString(EXTRA_COMPANY_SECTOR));
        mCompanyLocation.setText(companyLocation);

        Context context = getContext();
        ImageLoaderHelper.loadImage(context, buildUrl(logoURL), mImageLogo);
        ImageLoaderHelper.loadImage(context, buildUrl(bannerURL), mImageBanner);

        String twitterFullLink = arguments.getString(EXTRA_COMPANY_TWITTER_LINK);
        if (twitterFullLink != null) {
            twitterFullLink = "https://twitter.com/"+ twitterFullLink.replace("@", "");
        }

        Pair<String, ImageButton> twitterPair = new Pair<>(twitterFullLink, mButtonTwitter);
        Pair<String, ImageButton> facebookPair = new Pair<>(arguments.getString(EXTRA_COMPANY_FACEBOOK_LINK), mButtonFacebook);
        Pair<String, ImageButton> linkedinPair = new Pair<>(arguments.getString(EXTRA_COMPANY_LINKEDIN_LINK), mButtonLinkedin);
        Pair<String, ImageButton> instagramPair = new Pair<>(arguments.getString(EXTRA_COMPANY_INSTA_LINK), mButtonInsta);

        new DetailScreenDelegate().handleButtons(twitterPair, facebookPair, linkedinPair, instagramPair);

        AppPagerAdapter adapter = new AppPagerAdapter(context);
        CompanyInfoListAdapter listAdapter = new CompanyInfoListAdapter(getLayoutInflater(getArguments()));

        List<ListItemType> typeList = new LinkedList<>();
        AboutCompanyVO aboutCompanyVO = new AboutCompanyVO();
        aboutCompanyVO.mLocation = companyLocation;
        aboutCompanyVO.mMembersCount = arguments.getString(EXTRA_COMPANY_MEMBER_COUNT);
        aboutCompanyVO.mDescription = arguments.getString(EXTRA_COMPANY_DESCRIPTION);
        typeList.add(new SimpleItem<String>("ABOUT", 0));
        typeList.add(new SimpleItem<AboutCompanyVO>(aboutCompanyVO, 1));
        listAdapter.setItems(typeList);
        adapter.addVieBinder(mViewBinder1 = new AboutViewBinder(listAdapter));
        adapter.addVieBinder(mViewBinder2 = new ProjectsViewBinder(new OnListItemClickListener<ProjectVO>() {
            @Override
            public void onItemClick(ProjectVO model) {
                addChildFragment(ProjectDetailFragment.newInstance(model), "FRAG_PROJECT_DETAIL");
            }
        }));
        adapter.addVieBinder(mViewBinder3 = new MembersViewBinder(new OnListItemClickListener<MemberVO>() {
            @Override
            public void onItemClick(MemberVO model) {
                addChildFragment(MemberDetailFragment.newInstance(model), "FRAG_MEMBER_DETAIL");
            }
        }));

        mCompanyPages.setAdapter(adapter);
        mCompanyPages.setOffscreenPageLimit(adapter.getCount());

        mCompanyTab.setupWithViewPager(mCompanyPages);

        new TabsDelegate().setUp(mCompanyTab, TITLES);

        getPresenter().loadDetails(companyId);
    }

    @Override
    public void onLoadProjects(List<ProjectVO> projectDTOs) {
        mViewBinder2.bindView(projectDTOs);
    }

    @Override
    public void onLoadMembers(List<MemberVO> memberDTOs) {
        mViewBinder3.bindView(memberDTOs);
    }

    @Override
    public void onLoadCompanyServices(List<ListItemType> listItemTypes) {
        mViewBinder1.bindView(listItemTypes);
    }
}
