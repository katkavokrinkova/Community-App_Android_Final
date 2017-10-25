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

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.features.projects.ProjectsUiContract;
import net.impacthub.app.presenter.features.projects.ProjectsUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.binder.ViewBinder;
import net.impacthub.app.ui.common.AppPagerAdapter;
import net.impacthub.app.ui.features.filters.FilterActivity;
import net.impacthub.app.ui.features.home.projects.binders.ProjectsViewBinder;
import net.impacthub.app.ui.widgets.UISearchView;
import net.impacthub.app.utilities.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static net.impacthub.app.ui.features.filters.FilterActivity.EXTRA_FILTER_DATA;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class ProjectsFragment extends BaseChildFragment<ProjectsUiPresenter> implements OnListItemClickListener<ProjectVO>,ProjectsUiContract {

    private static final String TITLES[] = {"ALL", "PROJECTS YOU MANAGE", "YOUR PROJECTS"};

    @BindView(R.id.tabs) protected TabLayout mProjectsTab;
    @BindView(R.id.pager) protected ViewPager mProjectPages;
    @BindView(R.id.search_from_list) protected UISearchView mSearchView;
    @BindView(R.id.filter_tick) protected ImageView mFilterTick;

    private FilterData mFilterData;

    private ViewBinder<List<ProjectVO>> mViewBinder1;
    private ViewBinder<List<ProjectVO>> mViewBinder2;
    private ViewBinder<List<ProjectVO>> mViewBinder3;

    private ProjectsLisAdapter mLisAdapter1, mLisAdapter2, mLisAdapter3;

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

    @OnClick(R.id.filter_button)
    protected void onFilterClick() {
        Intent intent = new Intent(getActivity(), FilterActivity.class);
        intent.putExtra(EXTRA_FILTER_DATA, mFilterData);
        startActivityForResult(intent, FilterActivity.FILTER_REQUEST_CODE);
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);
        setUpToolbar(R.string.label_projects);

        AppPagerAdapter adapter = new AppPagerAdapter(getContext(), TITLES);
//
        mLisAdapter1 = new ProjectsLisAdapter(getLayoutInflater(getArguments()));
        mLisAdapter1.setItemClickListener(this);
        mLisAdapter2 = new ProjectsLisAdapter(getLayoutInflater(getArguments()));
        mLisAdapter2.setItemClickListener(this);
        mLisAdapter3 = new ProjectsLisAdapter(getLayoutInflater(getArguments()));
        mLisAdapter3.setItemClickListener(this);
        adapter.addVieBinder(mViewBinder1 = new ProjectsViewBinder(mLisAdapter1));
        adapter.addVieBinder(mViewBinder2 = new ProjectsViewBinder(mLisAdapter2));
        adapter.addVieBinder(mViewBinder3 = new ProjectsViewBinder(mLisAdapter3));
//
        mProjectPages.setAdapter(adapter);
        mProjectPages.setOffscreenPageLimit(adapter.getCount());
        mProjectsTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mProjectsTab.setupWithViewPager(mProjectPages);

        getPresenter().getProjects();

        mSearchView.setSearchActionListener(new UISearchView.OnSearchActionListener() {
            @Override
            public void onSearch(String searchValue) {

            }

            @Override
            public void onTextChanged(String query) {
                mLisAdapter1.filterSearch(query);
                mLisAdapter2.filterSearch(query);
                mLisAdapter3.filterSearch(query);
            }
        });

        mFilterData = new FilterData();
        mFilterData.getFilters().put(FilterData.KEY_FILTER_CITY, new ArrayList<String>());
        mFilterData.getFilters().put(FilterData.KEY_FILTER_SECTOR, new ArrayList<String>());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FilterActivity.FILTER_REQUEST_CODE) {
            mFilterData = (FilterData) data.getSerializableExtra(EXTRA_FILTER_DATA);
            getPresenter().handleFilters(mFilterData);
        }
    }

    @Override
    public void onItemClick(int viewId, ProjectVO model, int position) {
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

    @Override
    public void onShowTick(Map<String, List<String>> filters) {
        ViewUtils.visible(mFilterTick);
        mLisAdapter1.applyFilters(filters);
        mLisAdapter2.applyFilters(filters);
        mLisAdapter3.applyFilters(filters);
    }

    @Override
    public void onHideTick() {
        ViewUtils.gone(mFilterTick);
        mLisAdapter1.resetFilters();
        mLisAdapter2.resetFilters();
        mLisAdapter3.resetFilters();
    }
}
