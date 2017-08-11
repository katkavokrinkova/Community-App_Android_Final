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

package net.impacthub.members.ui.features.home.members.binders.project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import net.impacthub.members.R;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.ui.binder.ViewBinder;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/10/2017.
 */

public class ProjectsViewBinder implements ViewBinder<List<ProjectDTO>> {

    private MemberProjectsLisAdapter mAdapter;

    @Override
    public View getView(Context context, LayoutInflater inflater, int position) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.common_list_layout, new LinearLayout(context), false);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MemberProjectsLisAdapter(inflater);
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.default_content_medium_gap);
        recyclerView.addItemDecoration(new LinearItemsMarginDecorator(offset, offset, 0, 0));
        recyclerView.setAdapter(mAdapter);
        return recyclerView;
    }

    @Override
    public void bindView(List<ProjectDTO> model) {
        mAdapter.setItems(model);
    }
}
