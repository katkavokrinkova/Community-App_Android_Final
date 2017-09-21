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

package net.impacthub.app.ui.features.home.projects;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.features.projects.ProjectsUiContract;
import net.impacthub.app.presenter.features.projects.ProjectsUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.binder.ViewBinder;
import net.impacthub.app.ui.common.AppPagerAdapter;
import net.impacthub.app.ui.delegate.TabsDelegate;
import net.impacthub.app.ui.features.home.projects.binders.ProjectsViewBinder;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class ProjectsFragment extends BaseChildFragment<ProjectsUiPresenter> implements OnListItemClickListener<ProjectVO>,ProjectsUiContract {

    private static final String TITLES[] = {"ALL", "PROJECTS YOU MANAGE", "YOUR PROJECTS"};

    @BindView(R.id.tabs) protected TabLayout mProjectsTab;
    @BindView(R.id.pager) protected ViewPager mProjectPages;

    private ViewBinder<List<ProjectVO>> mViewBinder1;
    private ViewBinder<List<ProjectVO>> mViewBinder2;
    private ViewBinder<List<ProjectVO>> mViewBinder3;

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
    protected void bindView(View rootView) {
        super.bindView(rootView);
        setUpToolbar(R.string.label_projects);

        AppPagerAdapter adapter = new AppPagerAdapter(getContext());
//
        ProjectsLisAdapter lisAdapter1 = new ProjectsLisAdapter(getLayoutInflater(getArguments()));
        lisAdapter1.setItemClickListener(this);
        ProjectsLisAdapter lisAdapter2 = new ProjectsLisAdapter(getLayoutInflater(getArguments()));
        lisAdapter2.setItemClickListener(this);
        ProjectsLisAdapter lisAdapter3 = new ProjectsLisAdapter(getLayoutInflater(getArguments()));
        lisAdapter3.setItemClickListener(this);
        adapter.addVieBinder(mViewBinder1 = new ProjectsViewBinder(lisAdapter1));
        adapter.addVieBinder(mViewBinder2 = new ProjectsViewBinder(lisAdapter2));
        adapter.addVieBinder(mViewBinder3 = new ProjectsViewBinder(lisAdapter3));
//
        mProjectPages.setAdapter(adapter);
        mProjectPages.setOffscreenPageLimit(adapter.getCount());
        mProjectsTab.setupWithViewPager(mProjectPages);

        new TabsDelegate().setUp(mProjectsTab, TITLES);

        getPresenter().getProjects();
    }

    @Override
    public void onItemClick(int viewId, ProjectVO model) {
        addChildFragment(ProjectDetailFragment.newInstance(model), "FRAG_PROJECT_DETAIL");
    }

    @Override
    public void onLoadAllProjects(List<ProjectVO> projectDTOs) {
        mViewBinder1.bindView(projectDTOs);
    }

    @Override
    public void onLoadYourProjects(List<ProjectVO> projectDTOs) {
        mViewBinder3.bindView(projectDTOs);
    }
}
