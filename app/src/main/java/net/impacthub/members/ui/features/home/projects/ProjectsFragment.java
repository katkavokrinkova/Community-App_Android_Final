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
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.ui.base.BaseChildFragment;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class ProjectsFragment extends BaseChildFragment {

    @BindView(R.id.tabs) protected TabLayout mProjectsTab;

    public static ProjectsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProjectsFragment fragment = new ProjectsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_searchable_list_with_tabs;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.label_projects);

        mProjectsTab.addTab(mProjectsTab.newTab().setCustomView(createTabTitle("ALL")));
        mProjectsTab.addTab(mProjectsTab.newTab().setCustomView(createTabTitle("PROJECTS YOU MANAGE")));
        mProjectsTab.addTab(mProjectsTab.newTab().setCustomView(createTabTitle("YOUR PROJECTS")));
    }
}
