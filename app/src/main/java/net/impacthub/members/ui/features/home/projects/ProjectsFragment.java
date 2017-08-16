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

package net.impacthub.members.ui.features.home.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.presenter.features.projects.ProjectsUiContract;
import net.impacthub.members.presenter.features.projects.ProjectsUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.AppPagerAdapter;
import net.impacthub.members.ui.delegate.TabsDelegate;
import net.impacthub.members.ui.features.home.projects.binders.ProjectsViewBinder;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class ProjectsFragment extends BaseChildFragment<ProjectsUiPresenter> implements OnListItemClickListener<ProjectDTO>,ProjectsUiContract {

    private static final String TITLES[] = {"ALL", "PROJECTS YOU MANAGE", "YOUR PROJECTS"};

    @BindView(R.id.tabs) protected TabLayout mProjectsTab;
    @BindView(R.id.pager) protected ViewPager mProjectPages;

    private ViewBinder<List<ProjectDTO>> mViewBinder1;
    private ViewBinder<List<ProjectDTO>> mViewBinder2;
    private ViewBinder<List<ProjectDTO>> mViewBinder3;

    private AppPagerAdapter mPagerAdapter;

    public static ProjectsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProjectsFragment fragment = new ProjectsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ProjectsUiPresenter onCreatePresenter() {
        return new ProjectsUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_searchable_list_with_tabs;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.label_projects);

        mPagerAdapter = new AppPagerAdapter(getContext());
//
        mPagerAdapter.addVieBinder(mViewBinder1 = new ProjectsViewBinder(this));
        mPagerAdapter.addVieBinder(mViewBinder2 = new ProjectsViewBinder(this));
        mPagerAdapter.addVieBinder(mViewBinder3 = new ProjectsViewBinder(this));
//
        mProjectPages.setAdapter(mPagerAdapter);
        mProjectPages.setOffscreenPageLimit(mPagerAdapter.getCount());
        mProjectsTab.setupWithViewPager(mProjectPages);

        new TabsDelegate().setUp(mProjectsTab, TITLES);

        getPresenter().getProjects();
    }

    @Override
    public void onItemClick(ProjectDTO model) {
        showToast("Hello");
    }

    @Override
    public void onLoadAllProjects(List<ProjectDTO> projectDTOs) {
        mViewBinder1.bindView(projectDTOs);
    }

    @Override
    public void onLoadYourProjects(List<ProjectDTO> projectDTOs) {
        mViewBinder3.bindView(projectDTOs);
    }
}
